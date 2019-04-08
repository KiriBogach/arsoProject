package ejercicio4.dom;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import servicio.exceptions.GeoNamesException;
import servicio.model.Busqueda;
import utils.Utils;

public class DOMParser {

	private final DocumentBuilderFactory factoria;
	private static final Pattern GEONAME_URI = Pattern.compile("http://sws.geonames.org/(\\d*)/");

	public DOMParser() {
		this.factoria = DocumentBuilderFactory.newInstance();
	}

	public Busqueda parse(long id) {
		DocumentBuilder analizador;
		Document documento;

		Busqueda busqueda = new Busqueda();

		// Buscamos el pais del identificador
		
		try {
			analizador = factoria.newDocumentBuilder();
			documento = analizador.parse("http://api.geonames.org/get?geonameId=" + id + "&username=arso");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new GeoNamesException("No se pudo analizar el documento.", e);
		}

		NodeList node = null;
		String data = "";

		data = this.getDataFrom(documento, "countryName");
		if (data != null) {
			busqueda.setPais(data);
		}
		

		// Buscamos información del rdf

		try {
			analizador = factoria.newDocumentBuilder();
			documento = analizador.parse("http://sws.geonames.org/" + id + "/about.rdf");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new GeoNamesException("No se pudo analizar el documento.", e);
		}

		node = null;
		data = "";

		data = this.getDataFrom(documento, "gn:name");
		if (data != null) {
			busqueda.setNombre(data);
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
			try {
				busqueda.setWikipedia(new URI(data));
			} catch (URISyntaxException e) {
				throw new GeoNamesException("no se puede analizar el documento", e);
			}
		}

		// OPCIONAL
		data = this.getAttributeFrom(documento, "rdfs:seeAlso", "rdf:resource");
		if (data != null) {
			try {
				busqueda.setBdpedia(new URI(data));
			} catch (URISyntaxException e) {
				throw new GeoNamesException("no se puede analizar el documento", e);
			}
		}

		data = this.getDataFrom(documento, "dcterms:modified");
		if (data != null) {
			busqueda.setFechaActualizacion(Utils.fromStringToDate(data));
		}

		// OPCIONAL
		String nearbyFeatures = this.getAttributeFrom(documento, "gn:nearbyFeatures", "rdf:resource");
		if (nearbyFeatures != null) {
			try {
				documento = analizador.parse(nearbyFeatures);
			} catch (SAXException | IOException e) {
				throw new GeoNamesException("No se puede analizar el documento", e);
			}

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
		// Buscamos información meteorologica
		try {
			documento = analizador.parse("http://api.geonames.org/findNearByWeather?lat=" + busqueda.getLatitud()
					+ "&lng=" + busqueda.getLongitud() + "&username=arso");
		} catch (SAXException | IOException e) {
			throw new GeoNamesException("No se puede analizar el documento", e);
		}

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
