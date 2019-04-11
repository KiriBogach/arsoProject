package client;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import servicio.controlador.ListadoCiudades;
import servicio.model.CiudadGeoNames;


public class ClienteServicioGeonames {
	private static final String URL_SERVICIO = "http://localhost:8080/Bogach-Kyryl-entrega2/rest/";

	public static void main(String[] args) {

		// Invocar operación: crear una actividad

		Client cliente = Client.create();
		String path = "ciudades";

		// getResultadosBusqueda
		WebResource recurso = cliente.resource(URL_SERVICIO + path);
		System.out.println(URL_SERVICIO + path);
		Builder builder = recurso.queryParam("busqueda", "cartagena").accept(MediaType.APPLICATION_XML);
		ClientResponse respuesta = builder.method("GET", ClientResponse.class);
		ListadoCiudades listado = respuesta.getEntity(ListadoCiudades.class);
		System.out.println("Código de retorno: " + respuesta.getStatus());

		System.out.println("Nombre de las ciudades obtenidas: ");
		for (CiudadGeoNames ciudad : listado.getCiudades()) {
			System.out.println(ciudad.getNombre());
		}
	}
}
