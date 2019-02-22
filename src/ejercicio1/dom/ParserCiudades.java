package ejercicio1.dom;

import java.io.FileOutputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ejercicio1.model.GeoName;

public class ParserCiudades {

	// TODO: arreglar opcionalidad de todos los elementos
	// TODO: Diferentes clases para los objetos
	// TODO: Cuando no se encuentre la información, se utilizará:
	// http://api.geonames.org/findNearByWeatherXML?lat=5&lng=-2&username=arso

	public static void main(String[] args) throws Exception {
		GeoName geoName = new GeoName();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatMeteo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		NodeList node = null;
		String data;

		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder analizador = factoria.newDocumentBuilder();

		final long id = 2520058;
		Document documento = analizador.parse("http://sws.geonames.org/" + id + "/about.rdf");

		node = documento.getElementsByTagName("gn:name");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setNombre(data);

		node = documento.getElementsByTagName("gn:countryCode");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setPais(data);

		node = documento.getElementsByTagName("gn:population");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setPoblacion(Integer.parseInt(data));

		node = documento.getElementsByTagName("wgs84_pos:lat");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setLatitud(Double.parseDouble(data));

		node = documento.getElementsByTagName("wgs84_pos:long");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setLongitud(Double.parseDouble(data));

		node = documento.getElementsByTagName("gn:wikipediaArticle");
		data = ((Element) node.item(0)).getAttribute("rdf:resource");
		geoName.setWikipedia(new URI(data));

		node = documento.getElementsByTagName("rdfs:seeAlso");
		data = ((Element) node.item(0)).getAttribute("rdf:resource");
		geoName.setBdpedia(new URI(data));

		node = documento.getElementsByTagName("dcterms:modified");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setFechaActualizacion(format.parse(data));

		node = documento.getElementsByTagName("gn:nearbyFeatures");
		data = ((Element) node.item(0)).getAttribute("rdf:resource");

		final Pattern pattern = Pattern.compile("http://sws.geonames.org/(\\d*)/");

		documento = analizador.parse(data);

		NodeList features = documento.getElementsByTagName("gn:Feature");

		for (int i = 0; i < features.getLength(); i++) {
			Element feature = (Element) features.item(i);
			Element name = (Element) feature.getElementsByTagName("gn:name").item(0);

			String uriClave = feature.getAttribute("rdf:about");
			Matcher matcher = pattern.matcher(uriClave);

			String clave = "";
			if (matcher.find()) {
				clave = matcher.group(1);
			}

			String nombre = name.getTextContent();
			geoName.addSitioInteres(Long.parseLong(clave), nombre);
		}

		documento = analizador.parse("http://api.geonames.org/findNearByWeather?lat=" + geoName.getLatitud() + "&lng="
				+ geoName.getLongitud() + "&username=arso");

		/* Info meteorológica TODO: arreglar opcionalidad */
		node = documento.getElementsByTagName("observationTime");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setTiempoObservacion(formatMeteo.parse(data));

		node = documento.getElementsByTagName("temperature");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setTemperatura(Double.parseDouble(data));

		node = documento.getElementsByTagName("stationName");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setNombreEstacion(data);

		node = documento.getElementsByTagName("clouds");
		data = ((Element) node.item(0)).getTextContent();
		geoName.setNubes(data);

		/* ESCRITURA */
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = xof.createXMLStreamWriter(new FileOutputStream("xml-bd/" + id + ".xml"));

		writer.writeStartDocument();
		writer.writeStartElement("ciudad");
		writer.writeNamespace("", "http://www.example.org/ciudad"); // Por omisión
		writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		writer.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation",
				"http://www.example.org/ciudad ../xml/ciudad.xsd");
		writer.writeStartElement("nombre");
		writer.writeCharacters(geoName.getNombre());
		writer.writeEndElement();
		writer.writeStartElement("codigo");
		writer.writeCharacters(Long.toString(id));
		writer.writeEndElement();
		writer.writeStartElement("pais");
		writer.writeCharacters(geoName.getPais());
		writer.writeEndElement();
		writer.writeStartElement("poblacion");
		writer.writeCharacters(Integer.toString(geoName.getPoblacion()));
		writer.writeEndElement();
		writer.writeStartElement("ubicacion");
		writer.writeStartElement("latitud");
		writer.writeCharacters(Double.toString(geoName.getLatitud()));
		writer.writeEndElement();
		writer.writeStartElement("longitud");
		writer.writeCharacters(Double.toString(geoName.getLongitud()));
		writer.writeEndElement();
		writer.writeEndElement();
		writer.writeStartElement("bdpedia");
		writer.writeCharacters(geoName.getBdpedia().toString());
		writer.writeEndElement();
		writer.writeStartElement("wikipedia");
		writer.writeCharacters(geoName.getWikipedia().toString());
		writer.writeEndElement();
		writer.writeStartElement("fechaActualizacion");
		writer.writeCharacters(format.format(geoName.getFechaActualizacion()));
		writer.writeEndElement();
		writer.writeStartElement("infoMeteorologica");
		writer.writeStartElement("tiempoObservacion");
		writer.writeCharacters(formatMeteo.format(geoName.getTiempoObservacion()).replaceAll(" ", "T"));
		writer.writeEndElement();
		writer.writeStartElement("nombreEstacion");
		writer.writeCharacters(geoName.getNombreEstacion());
		writer.writeEndElement();
		writer.writeStartElement("temperatura");
		writer.writeCharacters(Double.toString(geoName.getTemperatura()));
		writer.writeEndElement();
		writer.writeStartElement("nubes");
		writer.writeCharacters(geoName.getNubes());
		writer.writeEndElement();
		writer.writeEndElement();

		writer.writeStartElement("sitiosInteres");
		HashMap<Long, String> mapa = geoName.getSitiosInteres();
		for (Map.Entry<Long, String> entry : mapa.entrySet()) {
			writer.writeStartElement("sitio");
			writer.writeStartElement("nombre");
			writer.writeCharacters(entry.getValue());
			writer.writeEndElement();
			writer.writeStartElement("codigo");
			writer.writeCharacters(Long.toString(entry.getKey()));
			writer.writeEndElement();
			writer.writeEndElement();

		}
		writer.writeEndElement();
		writer.writeEndElement();

		writer.writeEndDocument();
		writer.flush();
		writer.close();
	}
}
