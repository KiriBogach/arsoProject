package hal.builder;

import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import servicio.controlador.ListadoCiudades;
import servicio.exceptions.GeoNamesException;
import servicio.model.CiudadGeoNames;

public class HalBuilder {

	public static final int PAGE_SIZE = 10;
	private static final String LINK_KEY = "_links";
	private static final String HREF_KEY = "href";
	private static final String EMBEDDED_KEY = "_embedded";

	public static int TOTAL_RESULT_COUNT; // Modificamos con cada búsqueda

	public static String build(ListadoCiudades ciudades, int numeroPagina, UriInfo uriInfo) {

		String busqueda = uriInfo.getQueryParameters().getFirst("busqueda");
		String baseURL = uriInfo.getAbsolutePathBuilder().replaceQueryParam("busqueda", busqueda).replaceQuery("hal")
				.build().toString();
		String askedURL = uriInfo.getRequestUri().toString();
		String prevURL = uriInfo.getAbsolutePathBuilder().replaceQueryParam("busqueda", busqueda)
				.replaceQueryParam("pagina", Math.max(numeroPagina - 1, 1)).replaceQuery("hal").build().toString();
		String nextURL = uriInfo.getAbsolutePathBuilder().replaceQueryParam("busqueda", busqueda)
				.replaceQueryParam("pagina", numeroPagina + 1).replaceQuery("hal").build().toString();
		int startIndex = Math.min(Math.max((numeroPagina - 1) * PAGE_SIZE + 1, 0), TOTAL_RESULT_COUNT);
		int elementsCount = Math.max(Math.min(PAGE_SIZE, TOTAL_RESULT_COUNT - startIndex), 0);

		JSONObject json = new JSONObject();
		JSONObject paginationNav = new JSONObject();
		JSONObject embeddedObject = new JSONObject();
		JSONArray ciudadesArray = new JSONArray();

		try {
			paginationNav.put("self", new JSONObject().put(HREF_KEY, askedURL));
			paginationNav.put("first", new JSONObject().put(HREF_KEY, baseURL));
			paginationNav.put("prev", new JSONObject().put(HREF_KEY, prevURL));
			paginationNav.put("next", new JSONObject().put(HREF_KEY, nextURL));
			json.put(LINK_KEY, paginationNav);
			json.put("element", startIndex);
			json.put("count", elementsCount);
			json.put("total", TOTAL_RESULT_COUNT);

			for (CiudadGeoNames ciudadGeoNames : ciudades.getCiudades()) {
				JSONObject ciudad = new JSONObject();
				JSONObject links = new JSONObject();

				links.put("source", new JSONObject().put(HREF_KEY, uriInfo.getAbsolutePathBuilder()
						.path(String.valueOf(ciudadGeoNames.getIdGeonames())).build().toString()));
				links.put("about", new JSONObject().put(HREF_KEY, ciudadGeoNames.getURI()));
				ciudad.put(LINK_KEY, links);

				ciudad.put("id", ciudadGeoNames.getIdGeonames());
				ciudad.put("nombre", ciudadGeoNames.getNombre());
				ciudad.put("pais", ciudadGeoNames.getPais());
				ciudad.put("latitud", ciudadGeoNames.getLatitud());
				ciudad.put("longitud", ciudadGeoNames.getLongitud());
				ciudadesArray.put(ciudad);
			}
			embeddedObject.put("ciudades", ciudadesArray);
			json.put(EMBEDDED_KEY, embeddedObject);
			return json.toString();

		} catch (JSONException e) {
			throw new GeoNamesException("No se puede generar el archivo HAL", e);
		}

	}
}
