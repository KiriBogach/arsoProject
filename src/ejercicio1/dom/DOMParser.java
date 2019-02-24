package ejercicio1.dom;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ejercicio1.model.Busqueda;
import ejercicio1.utils.Utils;

public class DOMParser {

	private final DocumentBuilderFactory factoria;
	private static final Pattern GEONAME_URI = Pattern.compile("http://sws.geonames.org/(\\d*)/");

	public DOMParser() {
		this.factoria = DocumentBuilderFactory.newInstance();
	}

	public Busqueda parse(long id) throws Exception {
		DocumentBuilder analizador = factoria.newDocumentBuilder();
		Document documento = analizador.parse("http://sws.geonames.org/" + id + "/about.rdf");

		Busqueda busqueda = new Busqueda();

		NodeList node = null;
		String data = "";

		node = documento.getElementsByTagName("gn:name");
		data = ((Element) node.item(0)).getTextContent();
		busqueda.setNombre(data);

		node = documento.getElementsByTagName("gn:countryCode");
		data = ((Element) node.item(0)).getTextContent();
		busqueda.setPais(data);

		node = documento.getElementsByTagName("gn:population");
		data = ((Element) node.item(0)).getTextContent();
		busqueda.setPoblacion(Integer.parseInt(data));

		node = documento.getElementsByTagName("wgs84_pos:lat");
		data = ((Element) node.item(0)).getTextContent();
		busqueda.setLatitud(Double.parseDouble(data));

		node = documento.getElementsByTagName("wgs84_pos:long");
		data = ((Element) node.item(0)).getTextContent();
		busqueda.setLongitud(Double.parseDouble(data));

		// OPCIONAL
		node = documento.getElementsByTagName("gn:wikipediaArticle");
		data = ((Element) node.item(0)).getAttribute("rdf:resource");
		if (!data.isEmpty()) {
			busqueda.setWikipedia(new URI(data));
		}

		// OPCIONAL
		node = documento.getElementsByTagName("rdfs:seeAlso");
		data = ((Element) node.item(0)).getAttribute("rdf:resource");
		if (!data.isEmpty()) {
			busqueda.setBdpedia(new URI(data));
		}

		node = documento.getElementsByTagName("dcterms:modified");
		data = ((Element) node.item(0)).getTextContent();
		busqueda.setFechaActualizacion(Utils.fromStringToDate(data));

		node = documento.getElementsByTagName("gn:nearbyFeatures");
		data = ((Element) node.item(0)).getAttribute("rdf:resource");

		documento = analizador.parse(data);

		NodeList features = documento.getElementsByTagName("gn:Feature");

		for (int i = 0; i < features.getLength(); i++) {
			Element feature = (Element) features.item(i);
			Element name = (Element) feature.getElementsByTagName("gn:name").item(0);

			String uriClave = feature.getAttribute("rdf:about");
			Matcher matcher = GEONAME_URI.matcher(uriClave);

			String clave = "";
			if (matcher.find()) {
				clave = matcher.group(1);
			}

			String nombre = name.getTextContent();
			busqueda.addSitioInteres(Long.parseLong(clave), nombre);
		}

		documento = analizador.parse("http://api.geonames.org/findNearByWeather?lat=" + busqueda.getLatitud() + "&lng="
				+ busqueda.getLongitud() + "&username=arso");

		node = documento.getElementsByTagName("observation");

		Element elementoRaiz = (Element) node.item(0);
		if (elementoRaiz != null) {
			node = documento.getElementsByTagName("observationTime");
			data = ((Element) node.item(0)).getTextContent();
			busqueda.setTiempoObservacion(Utils.fromStringToDateTime(data));

			node = documento.getElementsByTagName("temperature");
			data = ((Element) node.item(0)).getTextContent();
			busqueda.setTemperatura(Double.parseDouble(data));

			node = documento.getElementsByTagName("stationName");
			data = ((Element) node.item(0)).getTextContent();
			busqueda.setNombreEstacion(data);

			node = documento.getElementsByTagName("clouds");
			data = ((Element) node.item(0)).getTextContent();
			busqueda.setNubes(data);
		}

		return busqueda;
	}

}
