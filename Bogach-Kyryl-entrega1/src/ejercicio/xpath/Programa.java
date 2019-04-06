package ejercicio.xpath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Programa {

	public static void main(String[] args) throws Exception {

		final String documento = "xml/ejercicio1.3/calificaciones.xml";

		// 1. Obtener la factor�a

		XPathFactory factoria = XPathFactory.newInstance();

		// 2. Construir el evaluador XPath

		XPath xpath = factoria.newXPath();

		xpath.setNamespaceContext(new EspaciosNombres());

		// 3. Realizar una consulta

		// consulta 1: //c:nota
		// 2: //c:calificacion/c:nota
		// 3: //c:diligencia/@fecha
		// 4: //c:diligencia[@extraordinaria]
		// 5: //c:calificacion[c:nota >= 9]
		// 6: //c:calificacion[c:nif = '13322156M'] | //c:diligencia[c:nif =
		// '13322156M']
		// 7: //c:calificacion[starts-with(c:nif, '2')]
		// 8: //c:calificacion[2]

		XPathExpression consulta = xpath.compile("//c:nota");

		// Importante: hay que ajustar la evaluaci�n y el tipo de retorno seg�n
		// el dato que se espere

		NodeList resultado = (NodeList) consulta.evaluate(new org.xml.sax.InputSource(documento),
				XPathConstants.NODESET);
		for (int i = 0; i < resultado.getLength(); i++) {

			Node nodo = resultado.item(i);

			// Se muestra informaci�n general sobre el resultado

			System.out.println("Nombre: " + nodo.getNodeName());

			// getTextContent muestra todos los nodos textuales descendientes

			System.out.println("Contenido: " + nodo.getTextContent());

		}

		System.out.println("Fin.");

	}
}
