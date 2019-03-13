package ejercicio1.main;

import java.util.List;

import ciudad.JAXBCiudad;
import ejercicio1.controller.ServicioGeoNames;
import ejercicio1.dom.DOMParser;
import ejercicio1.model.Busqueda;
import ejercicio1.model.Ciudad;
import ejercicio1.stax.StAXBuilder;

public class Main {

	public static void main(String[] args) throws Exception {
		// 2520058 -> Cartagena
		// 3117735 -> Madrid
		// 2513718 -> Molins. TODO: poblaci�n a 0 (porque no nos viene), preguntar profesor
		// 508635  -> Pochinki
		
		ServicioGeoNames geonames = new ServicioGeoNames();
		final long id = 508635;
		
		DOMParser domParser = new DOMParser();
		
		Busqueda busqueda = domParser.parse(id);
		
		StAXBuilder staxBuilder = new StAXBuilder();
		staxBuilder.build(id, busqueda);
		
		System.out.println("El archivo '" + id + ".xml' se ha generado.");
		
		List<Ciudad> ciudad = geonames.buscar("Cartagena");
		JAXBCiudad sitio1 = geonames.getCiudad(Long.toString(ciudad.get(0).getIdGeonames()));
		JAXBCiudad sitio2 = geonames.getCiudad(Long.toString(ciudad.get(1).getIdGeonames()));
		System.out.println(sitio1.getNombre());
		System.out.println(sitio2.getNombre());
	}
}
