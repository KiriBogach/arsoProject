package client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import servicio.controlador.CiudadesFavoritas;
import servicio.controlador.ListadoCiudades;
import servicio.model.CiudadGeoNames;
import servicio.tipos.Ciudad;


public class ClienteServicioGeonames {
	private static final String URL_SERVICIO = "http://localhost:8080/Bogach-Kyryl-entrega2/rest/";

	public static void main(String[] args) {

		// Invocar operación: crear una actividad

		Client cliente = Client.create();
		String path = "ciudades";

		// getResultadosBusqueda
		WebResource recurso = cliente.resource(URL_SERVICIO + path);
		Builder builder = recurso.queryParam("busqueda", "cartagena").accept(MediaType.APPLICATION_XML);
		ClientResponse respuesta = builder.method("GET", ClientResponse.class);
		ListadoCiudades listado = respuesta.getEntity(ListadoCiudades.class);
		System.out.println("Código de retorno: " + respuesta.getStatus());

		System.out.println("Nombre de las ciudades obtenidas: ");
		for (CiudadGeoNames ciudad : listado.getCiudades()) {
			System.out.println(ciudad.getNombre());
		}
		System.out.println("--------------------------------------------");
		recurso = cliente.resource(URL_SERVICIO + path + "/2520058");
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		Ciudad ciudad = respuesta.getEntity(Ciudad.class);
		System.out.println("Código de retorno: " + respuesta.getStatus());

		System.out.println("ciudad: " + ciudad.getNombre());
		System.out.println("pais: " + ciudad.getPais());
		System.out.println("wikipedia: " + ciudad.getWikipedia());
		System.out.println("bdpedia: " + ciudad.getBdpedia());
		
		recurso = cliente.resource(URL_SERVICIO + path + "/favoritas");
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("POST", ClientResponse.class);
		List<String> favCodigos = respuesta.getHeaders().get("location");
		String favCodigo = favCodigos.get(0);
		System.out.println(respuesta.getStatus());
		
		recurso = cliente.resource(favCodigo + "/2520058");
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("PUT", ClientResponse.class);
		System.out.println(respuesta.getStatus());
		System.out.println("--------------------------------------------");
		
		recurso = cliente.resource(favCodigo);
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		CiudadesFavoritas favs = respuesta.getEntity(CiudadesFavoritas.class);
		System.out.println(favs.getId());
		favs.getCiudades().forEach(c -> System.out.println(c));
		System.out.println("--------------------------------------------");
		
		recurso = cliente.resource(favCodigo + "/2520058");
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("DELETE", ClientResponse.class);
		System.out.println(respuesta.getStatus());
		System.out.println("--------------------------------------------");
		
		recurso = cliente.resource(favCodigo);
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		favs = respuesta.getEntity(CiudadesFavoritas.class);
		System.out.println(favs.getId());
		favs.getCiudades().forEach(c -> System.out.println(c));
		System.out.println("--------------------------------------------");
		
		recurso = cliente.resource(favCodigo);
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("DELETE", ClientResponse.class);
		System.out.println(respuesta.getStatus());
		System.out.println("--------------------------------------------");
		
		recurso = cliente.resource(URL_SERVICIO + path);
		builder = recurso.queryParam("busqueda", "cartagena").accept(MediaType.APPLICATION_JSON);
		respuesta = builder.method("GET", ClientResponse.class);
		String content = respuesta.getEntity(String.class);
		System.out.println("Código de retorno: " + respuesta.getStatus());

		System.out.println(content);
	}
}
