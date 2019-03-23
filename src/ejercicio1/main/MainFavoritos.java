package ejercicio1.main;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import ejercicio1.controller.ServicioGeoNames;
import ejercicio1.jaxb.ciudades.ListadoCiudades;
import ejercicio1.jaxb.ciudades_favoritas.CiudadesFavoritas;

public class MainFavoritos {

	public static void main(String[] args) throws Exception {
		ServicioGeoNames geonames = new ServicioGeoNames();
		JAXBContext contexto = JAXBContext.newInstance(ListadoCiudades.class);
		Marshaller marshaller = contexto.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", true);

		String busqueda = "Cartagena";
		marshaller.marshal(geonames.getResultadosBusquedaXML(busqueda),
				new File("xml-bd/listadoCiudades-" + busqueda + ".xml"));
		System.out.println("xml-bd/listadoCiudades-" + busqueda + ".xml generado");
		
		String idFavoritos = geonames.crearDocumentoFavorito();
		geonames.addCiudadFavorita(idFavoritos, "123");
		geonames.addCiudadFavorita(idFavoritos, "456");
		
		CiudadesFavoritas ciudadesFavoritas = geonames.getFavoritos(idFavoritos);
		System.out.println(ciudadesFavoritas);
		
		geonames.removeCiudadFavorita(idFavoritos, "789");
		geonames.removeCiudadFavorita(idFavoritos, "123");
		
		geonames.removeDocumentoFavoritos(idFavoritos);

	}
}
