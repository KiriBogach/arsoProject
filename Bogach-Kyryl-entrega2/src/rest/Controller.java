package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import fr.vidal.oss.jaxb.atom.core.Feed;
import servicio.controlador.ServicioGeoNames;
import servicio.exceptions.GeoNamesException;

@Path("ciudades")
public class Controller {

	private ServicioGeoNames controlador = new ServicioGeoNames();
	@Context private UriInfo uriInfo;
	@Context ServletContext context;
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getResultadosBusqueda(@QueryParam(value = "busqueda") String busqueda, @QueryParam(value = "kml") String kml) {
		try {
			Object resultado;
			
			if (kml != null && kml.isEmpty()) {
				resultado = controlador.getResultadosBusquedaKML(busqueda, context);
			} else {
				resultado = controlador.getResultadosBusquedaXML(busqueda);
			}
			
			return Response.status(Status.OK)
					.entity(resultado)
					.build();
		} catch (GeoNamesException ex) {
			return Response.status(Status.BAD_REQUEST)
					.entity(ex.getMessage())
					.build();
		}
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_ATOM_XML})
	public Response getResultadosBusquedaATOM(@QueryParam(value = "busqueda") String busqueda, @QueryParam(value = "pagina") String pagina) {
		try {
			int numeroPagina;
			try {
				numeroPagina = Integer.parseInt(pagina);
			} catch (NumberFormatException ex) {
				numeroPagina = 1;
			}
			
			Feed actividad = controlador.getResultadosBusquedaATOM(busqueda, numeroPagina, uriInfo);
			return Response.status(Status.OK)
					.entity(actividad)
					.build();
		} catch (GeoNamesException ex) {
			return Response.status(Status.BAD_REQUEST)
					.entity(ex.getMessage())
					.build();
		}
	}
	
	
	@GET
	@Path("/{idGeonames}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getCiudad(@PathParam("idGeonames") String id) {
		return Response.status(Status.OK)
				.entity(controlador.getCiudad(id))
				.build();	
	}
	
	
	@POST
	@Path("/favoritas")
	public Response crearDocumentoFavorito() {
		String idFav = controlador.crearDocumentoFavorito();	
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(idFav);
		return Response.created(builder.build()).build();	
	}
	
	@PUT
	@Path("/favoritas/{idDocumento}/{idGeonames}")
	public Response addCiudadFavorita(@PathParam("idDocumento") String idDocumento,
			@PathParam("idGeonames") String idGeonames) {
		controlador.addCiudadFavorita(idDocumento, idGeonames);	
		return Response.status(Status.NO_CONTENT)
				.build();		
	}
	
	@DELETE
	@Path("/favoritas/{idDocumento}/{idGeonames}")
	public Response removeCiudadFavorita(@PathParam("idDocumento") String idDocumento,
			@PathParam("idGeonames") String idGeonames) {
		boolean removed = controlador.removeCiudadFavorita(idDocumento, idGeonames);
		if (removed) {
			return Response.status(Status.NO_CONTENT)
					.build();
		}
		else {
			return Response.status(Status.NOT_FOUND)
					.build();
		}
	}
	
	@GET
	@Path("/favoritas/{idDocumento}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getFavoritos(@PathParam("idDocumento") String idDocumento) {
		try {
		return Response.status(Status.OK)
				.entity(controlador.getFavoritos(idDocumento))
				.build();
		}
		catch (GeoNamesException ex) {
			return Response.status(Status.NOT_FOUND)
					.entity(ex.getMessage())
					.build();
		}
	}
	
	@DELETE
	@Path("/favoritas/{idDocumento}")
	public Response removeFavoritos(@PathParam("idDocumento") String idDocumento) {
		controlador.removeDocumentoFavoritos(idDocumento);
		return Response.status(Status.NO_CONTENT)
				.build();		
	}
}
