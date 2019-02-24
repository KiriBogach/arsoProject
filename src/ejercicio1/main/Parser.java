package ejercicio1.main;

import ejercicio1.dom.DOMParser;
import ejercicio1.model.Busqueda;
import ejercicio1.stax.StAXBuilder;

public class Parser {

	public static void main(String[] args) throws Exception {
		final long id = 2520058;
		DOMParser domParser = new DOMParser();
		
		Busqueda busqueda = domParser.parse(id);
		
		StAXBuilder staxBuilder = new StAXBuilder();
		staxBuilder.build(id, busqueda);
	}
}
