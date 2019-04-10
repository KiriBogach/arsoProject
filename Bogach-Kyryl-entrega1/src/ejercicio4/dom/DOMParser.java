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
		this.factoria.setNamespaceAware(true);
		this.factoria.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				"http://www.w3.org/2001/XMLSchema");
	}

	public Busqueda parse(long id) {
		// Variables reutilizadas en distintos análisis
		DocumentBuilder analizador;
		Document documento;
		NodeList node = null;
		String data = "";

		// Busqueda construida
		Busqueda busqueda = new Busqueda();

		// Buscamos información del rdf
		try {
			analizador = factoria.newDocumentBuilder();
			documento = analizador.parse("http://sws.geonames.org/" + id + "/about.rdf");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new GeoNamesException("No se pudo analizar el documento.", e);
		}

		node = null;
		data = "";

		data = this.getDataFrom(documento, "name", "http://www.geonames.org/ontology#");
		if (data != null) {
			busqueda.setNombre(data);
		}

		data = this.getDataFrom(documento, "countryCode", "http://www.geonames.org/ontology#");
		if (data != null) {
			busqueda.setCodigoPais(data);
		}

		data = this.getDataFrom(documento, "population", "http://www.geonames.org/ontology#");
		if (data != null) {
			busqueda.setPoblacion(Integer.parseInt(data));
		}

		data = this.getDataFrom(documento, "lat", "http://www.w3.org/2003/01/geo/wgs84_pos#");
		if (data != null) {
			busqueda.setLatitud(Double.parseDouble(data));
		}

		data = this.getDataFrom(documento, "long", "http://www.w3.org/2003/01/geo/wgs84_pos#");
		if (data != null) {
			busqueda.setLongitud(Double.parseDouble(data));
		}

		// OPCIONAL
		data = this.getAttributeFrom(documento, "wikipediaArticle", "http://www.geonames.org/ontology#", "resource",
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		if (data != null) {
			try {
				busqueda.setWikipedia(new URI(data));
			} catch (URISyntaxException e) {
				throw new GeoNamesException("no se puede analizar el documento", e);
			}
		}

		// OPCIONAL
		data = this.getAttributeFrom(documento, "seeAlso", "http://www.w3.org/2000/01/rdf-schema#", "resource",
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		if (data != null) {
			try {
				busqueda.setBdpedia(new URI(data));
			} catch (URISyntaxException e) {
				throw new GeoNamesException("no se puede analizar el documento", e);
			}
		}

		data = this.getDataFrom(documento, "modified", "http://purl.org/dc/terms/");
		if (data != null) {
			busqueda.setFechaActualizacion(Utils.fromStringToDate(data));
		}

		// OPCIONAL
		String nearbyFeatures = this.getAttributeFrom(documento, "nearbyFeatures", "http://www.geonames.org/ontology#",
				"resource", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		if (nearbyFeatures != null) {
			try {
				documento = analizador.parse(nearbyFeatures);
			} catch (SAXException | IOException e) {
				throw new GeoNamesException("No se puede analizar el documento", e);
			}

			NodeList features = documento.getElementsByTagNameNS("Feature", "http://www.geonames.org/ontology#");

			for (int i = 0; i < features.getLength(); i++) {
				Element feature = (Element) features.item(i);
				Element name = (Element) feature.getElementsByTagNameNS("name", "http://www.geonames.org/ontology#")
						.item(0);

				String uriClave = feature.getAttributeNS("about", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
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

		// Cogemos el país en función del código de país: ES -> Spain
		try {
			analizador = factoria.newDocumentBuilder();
			documento = analizador.parse(
					"http://api.geonames.org/countryInfo?country=" + busqueda.getCodigoPais() + "&username=arso");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new GeoNamesException("No se pudo analizar el documento.", e);
		}

		data = this.getDataFrom(documento, "countryName");
		if (data != null) {
			busqueda.setPais(data);
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

	private String getDataFrom(Document documento, String name, String ns) {
		NodeList node = documento.getElementsByTagNameNS(ns, name);
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

	private String getAttributeFrom(Document documento, String name, String ns, String attribute, String nsAttr) {
		NodeList node = documento.getElementsByTagNameNS(ns, name);
		Element element = (Element) node.item(0);
		if (element == null) {
			return null;
		}

		String data = element.getAttributeNS(nsAttr, attribute);

		if (data == null || data.isEmpty()) {
			return null;
		}

		return data;
	}
}
