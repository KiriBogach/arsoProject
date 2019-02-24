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

		data = this.getDataFrom(documento, "gn:name");
		if (data != null) {
			busqueda.setNombre(data);
		}

		data = this.getDataFrom(documento, "gn:countryCode");
		if (data != null) {
			busqueda.setPais(data);
		}

		data = this.getDataFrom(documento, "gn:population");
		if (data != null) {
			busqueda.setPoblacion(Integer.parseInt(data));
		}

		data = this.getDataFrom(documento, "wgs84_pos:lat");
		if (data != null) {
			busqueda.setLatitud(Double.parseDouble(data));
		}

		data = this.getDataFrom(documento, "wgs84_pos:long");
		if (data != null) {
			busqueda.setLongitud(Double.parseDouble(data));
		}

		// OPCIONAL
		data = this.getAttributeFrom(documento, "gn:wikipediaArticle", "rdf:resource");
		if (data != null) {
			busqueda.setWikipedia(new URI(data));
		}

		// OPCIONAL
		data = this.getAttributeFrom(documento, "rdfs:seeAlso", "rdf:resource");
		if (data != null) {
			busqueda.setWikipedia(new URI(data));
		}

		data = this.getDataFrom(documento, "dcterms:modified");
		if (data != null) {
			busqueda.setFechaActualizacion(Utils.fromStringToDate(data));
		}

		// OPCIONAL
		String nearbyFeatures = this.getAttributeFrom(documento, "gn:nearbyFeatures", "rdf:resource");
		if (nearbyFeatures != null) {
			documento = analizador.parse(nearbyFeatures);

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
		}

		// OPCIONAL
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

	private String getDataFrom(Document documento, String name) {
		NodeList node = documento.getElementsByTagName(name);
		Element element = (Element) node.item(0);
		if (element == null) {
			return null;
		}

		String data = element.getTextContent();

		if (data == null || data.isEmpty()) {
			return null;
		}

		return data;
	}

	private String getAttributeFrom(Document documento, String name, String attribute) {
		NodeList node = documento.getElementsByTagName(name);
		Element element = (Element) node.item(0);
		if (element == null) {
			return null;
		}

		String data = element.getAttribute(attribute);

		if (data == null || data.isEmpty()) {
			return null;
		}

		return data;
	}
}
