package ejercicio5.kml;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import servicio.exceptions.GeoNamesException;

public class XSLT {

	public static void main(String[] args) {

		final String ciudad = "cartagena";
		final String documentoEntrada = "http://api.geonames.org/search?q=" + ciudad + "&username=arso";
		final String documentoSalida = "xml-bd/" + ciudad + ".kml";
		final String transformacion = "xml/kml-transformer.xsl";

		TransformerFactory factoria = TransformerFactory.newInstance();

		Transformer transformador;
		try {
			transformador = factoria.newTransformer(new StreamSource(transformacion));
		} catch (TransformerConfigurationException e) {
			throw new GeoNamesException("No se puede generar el documento KML.", e);
		}
		Source origen = new StreamSource(documentoEntrada);
		Result destino = new StreamResult(documentoSalida);
		try {
			transformador.transform(origen, destino);
		} catch (TransformerException e) {
			throw new GeoNamesException("No se puede generar el documento KML.", e);
		}

		System.out.println("KML '" + documentoSalida + "' generado con exito.");
	}
}