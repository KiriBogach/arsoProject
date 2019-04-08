package ejercicio.xpath;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import servicio.exceptions.GeoNamesException;
import servicio.model.Busqueda;
import servicio.model.Libro;

public class XPathParser {

	public static final String URL_GOOGLE_BOOKS_START = "http://books.google.com/books/feeds/volumes?q=";
	public static final String URL_GOOGLE_BOOKS_END = "&start-index=1&max-results=100";

	public void parse(Busqueda busqueda) {
		try {
			String urlConsultada = this.getURL(busqueda.getNombre());
			XPathFactory factoria = XPathFactory.newInstance();
			XPath xpath = factoria.newXPath();
			xpath.setNamespaceContext(new EspaciosNombres());

			String subjectBuscado = busqueda.getNombre() + " (" + busqueda.getPais() + ")";
			XPathExpression consulta = xpath.compile("//ns:entry[dc:subject = '" + subjectBuscado + "']");

			NodeList resultado = (NodeList) consulta.evaluate(new org.xml.sax.InputSource(urlConsultada),
					XPathConstants.NODESET);
			/*
			 * Título del libro. 
			 * Identificador del libro en Google Books. 
			 * ISBN (opcional)
			 * URL de la imagen. 
			 * URL a la web de Google Books.
			 * 
			 */
			for (int i = 0; i < Math.min(3, resultado.getLength()); i++) {
				Node nodo = resultado.item(i);
				
				NodeList hijos = nodo.getChildNodes();
				Libro libro = new Libro();
				
				for (int j = 0; j < hijos.getLength(); j++) {
					Node hijo = hijos.item(j);
					
					String contenidoHijo = hijo.getTextContent();
					NamedNodeMap atributosHijo = hijo.getAttributes();
					switch (hijo.getNodeName()) {
						case "title":
							libro.setTitulo(contenidoHijo);
							break;
						case "id":
							libro.setIdentificador(contenidoHijo);
							break;
						case "dc:identifier":
							if (contenidoHijo.contains("ISBN")) {
								libro.setISBN(contenidoHijo);
							}
							break;
						case "link":
							
							// mal
							if (atributosHijo.getNamedItem("type") != null) {
								System.out.println(contenidoHijo);
								try {
									libro.setUrlImagen(new URI(contenidoHijo));
								} catch (URISyntaxException e) {
									e.printStackTrace();
								}
							}
							break;
						default:
							break;
					}
				}

				
				System.out.println(libro);

			}
		} catch (XPathExpressionException ex) {
			throw new GeoNamesException("Error obteniendo datos con XPath", ex);
		}
	}

	private String getURL(String nombre) {
		return URL_GOOGLE_BOOKS_START + nombre + URL_GOOGLE_BOOKS_END;
	}
}
