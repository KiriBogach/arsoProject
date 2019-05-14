package atom.builder;

import java.util.Date;

import javax.ws.rs.core.UriInfo;

import fr.vidal.oss.jaxb.atom.core.Author;
import fr.vidal.oss.jaxb.atom.core.Contents;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.ExtensionElements;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import fr.vidal.oss.jaxb.atom.core.LinkRel;
import fr.vidal.oss.jaxb.atom.core.Namespace;
import servicio.controlador.ListadoCiudades;
import servicio.model.CiudadGeoNames;

public class AtomBuilder {

	public static final int PAGE_SIZE = 10;
	public static final Namespace OPEN_SEARCH = Namespace.builder("http://a9.com/-/spec/opensearchrss/1.0/")
			.withPrefix("openSearch").build();
	public static final Namespace ARSO = Namespace.builder("http://www.example.org/ciudad").withPrefix("arso").build();

	public int totalResultCount;

	public AtomBuilder() {
		totalResultCount = 0;
	}

	public int getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(int totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public Feed build(ListadoCiudades listadoCiudades, int numeroPagina, UriInfo uriInfo) {
		// Creamos el constructor del elemento principal de ATOM
		Feed.Builder feedBuilder = Feed.builder();
		int startIndex = Math.min(Math.max((numeroPagina - 1) * 10 + 1, 0), totalResultCount);
		int lastPage = (int) Math.ceil((double) totalResultCount / (double) PAGE_SIZE);

		// Obtenemos los datos del uriInfo y páginas
		String busqueda = uriInfo.getQueryParameters().getFirst("busqueda");
		String askedURL = uriInfo.getRequestUri().toString();
		String nextURL = uriInfo.getAbsolutePathBuilder().replaceQueryParam("busqueda", busqueda)
				.replaceQueryParam("pagina", numeroPagina + 1).build().toString();
		String prevURL = uriInfo.getAbsolutePathBuilder().replaceQueryParam("busqueda", busqueda)
				.replaceQueryParam("pagina", Math.max(numeroPagina - 1, 1)).replaceQuery("hal").build().toString();

		// Rellenamos la cabecera
		feedBuilder.withTitle("Ciudades");
		feedBuilder.addExtensionElement(ExtensionElements
				.simpleElement("totalResults", String.valueOf(totalResultCount)).withNamespace(OPEN_SEARCH).build());
		feedBuilder.addExtensionElement(ExtensionElements.simpleElement("startIndex", String.valueOf(startIndex))
				.withNamespace(OPEN_SEARCH).build());
		feedBuilder.addExtensionElement(ExtensionElements.simpleElement("itemsPerPage", String.valueOf(PAGE_SIZE))
				.withNamespace(OPEN_SEARCH).build());
		feedBuilder.withId(uriInfo.getAbsolutePath().toString());
		feedBuilder.withAuthor(Author.builder("Servicio Geonames").build());
		feedBuilder.addLink(Link.builder(askedURL).withRel(LinkRel.self).build());
		if (numeroPagina > 1) {
			feedBuilder.addLink(Link.builder(nextURL).withRel(LinkRel.next).build());
		}
		if (lastPage > numeroPagina) {
			feedBuilder.addLink(Link.builder(prevURL).withRel(LinkRel.previous).build());
		}
		feedBuilder.withUpdateDate(new Date());

		// Rellenamos cada entrada (entry)
		for (CiudadGeoNames ciudad : listadoCiudades.getCiudades()) {
			Entry.Builder entryBuilder = Entry.builder();
			entryBuilder.withTitle(ciudad.getNombre());
			entryBuilder.addLink(Link.builder(ciudad.getURI()).withRel(LinkRel.related).build());
			entryBuilder.addLink(Link.builder(
					uriInfo.getAbsolutePathBuilder().path(String.valueOf(ciudad.getIdGeonames())).build().toString())
					.withRel(LinkRel.source).build());
			entryBuilder.withId(Long.toString(ciudad.getIdGeonames()));
			entryBuilder.withUpdateDate(new Date());

			entryBuilder.addExtensionElement(
					ExtensionElements.simpleElement("pais", ciudad.getPais()).withNamespace(ARSO).build());

			entryBuilder.addExtensionElement(ExtensionElements
					.simpleElement("longitud", String.valueOf(ciudad.getLongitud())).withNamespace(ARSO).build());
			entryBuilder.addExtensionElement(ExtensionElements
					.simpleElement("latitud", String.valueOf(ciudad.getLatitud())).withNamespace(ARSO).build());
			entryBuilder.withContents(Contents.builder().withContents(ciudad.getURI()).build());

			feedBuilder.addEntry(entryBuilder.build());
		}

		// Devolvemos un objeto Feed
		return feedBuilder.build();
	}

}
