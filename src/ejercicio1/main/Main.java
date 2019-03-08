package ejercicio1.main;

import ejercicio1.dom.DOMParser;
import ejercicio1.model.Busqueda;
import ejercicio1.stax.StAXBuilder;

public class Main {

	public static void main(String[] args) throws Exception {
		// 2520058 -> Cartagena
		// 3117735 -> Madrid
		// 2513718 -> Molins. TODO: población a 0 (porque no nos viene), preguntar profesor
		// 508635  -> Pochinki
		
		
		final long id = 508635;
		
		DOMParser domParser = new DOMParser();
		
		Busqueda busqueda = domParser.parse(id);
		
		StAXBuilder staxBuilder = new StAXBuilder();
		staxBuilder.build(id, busqueda);
		
		System.out.println("El archivo '" + id + ".xml' se ha generado.");
	}
}
