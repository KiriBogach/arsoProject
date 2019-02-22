package ejercicio1.dom;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ejercicio1.model.GeoName;

public class ParserCiudades {

	public static void main(String[] args) throws Exception {
		GeoName geoName = new GeoName();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatMeteo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		NodeList node = null;
		String data;

		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder analizador = factoria.newDocumentBuilder();
		Document documento = analizador.parse("http://sws.geonames.org/2520058/about.rdf");

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
		geoName.setDbpedia(new URI(data));

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
		
		/* Info meteorológica */
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
		
		System.out.println(geoName);
	}
}
