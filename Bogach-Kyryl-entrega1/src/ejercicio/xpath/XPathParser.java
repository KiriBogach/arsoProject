package ejercicio.xpath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import servicio.exceptions.GeoNamesException;
import servicio.model.Busqueda;

public class XPathParser {

	public static final String URL_GOOGLE_BOOKS_START = "http://books.google.com/books/feeds/volumes?q=";
	public static final String URL_GOOGLE_BOOKS_END = "&start-index=1&max-results=100";

	public void parse(Busqueda busqueda) {
		try {
			System.out.println(busqueda);
			String urlConsultada = this.getURL(busqueda.getNombre());
			XPathFactory factoria = XPathFactory.newInstance();
			XPath xpath = factoria.newXPath();

			String subjectBuscado = busqueda.getNombre() + " (" + busqueda.getNombrePais() + ")";
			XPathExpression consulta;
			consulta = xpath.compile("//dc:subject[contains(text(), '" + subjectBuscado + "')]");

			System.out.println(urlConsultada);
			System.out.println("//dc:subject[contains(text(), '" + subjectBuscado + "')]");

			NodeList resultado = (NodeList) consulta.evaluate(new org.xml.sax.InputSource(urlConsultada),
					XPathConstants.NODESET);
			System.out.println(resultado.getLength());
			for (int i = 0; i < resultado.getLength(); i++) {

				Node nodo = resultado.item(i);

				// Se muestra información general sobre el resultado

				System.out.println("Nombre: " + nodo.getNodeName());

				// getTextContent muestra todos los nodos textuales descendientes

				System.out.println("Contenido: " + nodo.getTextContent());

			}
		} catch (XPathExpressionException ex) {
			throw new GeoNamesException("Error obteniendo datos con XPath", ex);
		}
	}

	private String getURL(String nombre) {
		return URL_GOOGLE_BOOKS_START + nombre + URL_GOOGLE_BOOKS_END;
	}
}
