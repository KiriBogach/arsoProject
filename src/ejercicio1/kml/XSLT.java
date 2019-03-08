package ejercicio1.kml;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLT {

	public static void main(String[] args) throws Exception {

		final String ciudad = "cartagena";
		final String documentoEntrada = "http://api.geonames.org/search?q="+ciudad+"&username=arso";
		final String documentoSalida = "xml-bd/" + ciudad + ".kml";
		final String transformacion = "xml/kml-transformer.xsl";
		

		TransformerFactory factoria = TransformerFactory.newInstance();

		Transformer transformador = factoria.newTransformer(new StreamSource(transformacion));
		Source origen = new StreamSource(documentoEntrada);
		Result destino = new StreamResult(documentoSalida);
		transformador.transform(origen, destino);

		System.out.println("KML '" + documentoSalida + "' generado con éxito.");
	}
}