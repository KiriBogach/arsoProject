package ejercicio4.main;

import java.util.List;

import ejercicio4.dom.DOMParser;
import ejercicio4.stax.StAXBuilder;
import servicio.controlador.ServicioGeoNames;
import servicio.model.Busqueda;
import servicio.model.CiudadGeoNames;
import servicio.tipos.Ciudad;

public class Main {

	public static void main(String[] args) throws Exception {
		// 2520058 -> Cartagena
		// 3117735 -> Madrid
		// 2513718 -> Molins. TODO: poblaci�n a 0 (porque no nos viene), preguntar
		// profesor
		// 508635 -> Pochinki

		ServicioGeoNames geonames = new ServicioGeoNames();
		final long id = 2520058;

		DOMParser domParser = new DOMParser();

		Busqueda busqueda = domParser.parse(id);

		StAXBuilder staxBuilder = new StAXBuilder();
		staxBuilder.build(id, busqueda);

		System.out.println("El archivo '" + id + ".xml' se ha generado.");

		List<CiudadGeoNames> ciudad = geonames.buscar("Cartagena");

		if (ciudad.size() > 2) {
			Ciudad sitio1 = geonames.getCiudad(Long.toString(ciudad.get(0).getIdGeonames()));
			Ciudad sitio2 = geonames.getCiudad(Long.toString(ciudad.get(1).getIdGeonames()));
			System.out.println(sitio1.getNombre());
			System.out.println(sitio2.getNombre());
		}

	}
}
