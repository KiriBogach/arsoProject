package atom.builder;

import java.util.Date;

import javax.ws.rs.core.UriInfo;

import fr.vidal.oss.jaxb.atom.core.Author;
import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.ExtensionElements;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import fr.vidal.oss.jaxb.atom.core.Namespace;
import fr.vidal.oss.jaxb.atom.core.Summary;
import servicio.controlador.ListadoCiudades;
import servicio.model.CiudadGeoNames;

public class AtomBuilder {

	public static final int PAGE_SIZE = 10;

	public static Feed build(ListadoCiudades listadoCiudades, int numeroPagina, UriInfo uriInfo) {
		Feed.Builder feedBuilder = Feed.builder();
		

		Namespace openSearch = Namespace.builder("http://a9.com/-/spec/opensearchrss/1.0/").withPrefix("openSearch")
				.build();		
		


		uriInfo.getBaseUri().toString();
		System.out.println(uriInfo.getBaseUri().toString());
		System.out.println(uriInfo.getPath());
		System.out.println(uriInfo.getAbsolutePath().toString());

		int totalResults = listadoCiudades.getCiudades().size();
		int startIndex = numeroPagina * PAGE_SIZE;

		System.out.println(uriInfo.getAbsolutePathBuilder().replaceQueryParam("pagina", 14).build().toString());

		feedBuilder.withTitle("Ciudades");
		// feedBuilder.addExtensionElement(ExtensionElements.simpleElement("totalResults",
		// Integer.toString(totalResults)).build());
		// feedBuilder.addSimpleElement(SimpleElement.builder("openSearch:totalResults",
		// Integer.toString(totalResults)));

		feedBuilder.addExtensionElement(ExtensionElements.simpleElement("totalResults", String.valueOf(totalResults))
				.withNamespace(openSearch).build());

		feedBuilder.addLink(Link.builder("locahost:8080").build());
		feedBuilder.withAuthor(Author.builder("Servicio Geonames").build());
		feedBuilder.withId("numeroPagina:" + numeroPagina);
		feedBuilder.withUpdateDate(new Date());

		for (CiudadGeoNames ciudad : listadoCiudades.getCiudades()) {
			Entry.Builder entryBuilder = Entry.builder();
			entryBuilder.withTitle(ciudad.getNombre());
			entryBuilder.addLink(Link.builder("locahost:8080").build());
			entryBuilder.withId(Long.toString(ciudad.getIdGeonames()));
			entryBuilder.withUpdateDate(new Date());
			entryBuilder.withSummary(Summary.builder().withValue("test").build());

			feedBuilder.addEntry(entryBuilder.build());
		}
		/*
		 * 
		 * <title>Example Feed</title> <link href="http://example.org/"/>
		 * <updated>2003-12-13T18:30:02Z</updated> <author> <name>John Doe</name>
		 * </author> <id>urn:uuid:60a76c80-d399-11d9-b93C-0003939e0af6</id>
		 * 
		 * <entry> <title>Atom-Powered Robots Run Amok</title> <link
		 * href="http://example.org/2003/12/13/atom03"/>
		 * <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>
		 * <updated>2003-12-13T18:30:02Z</updated> <summary>Some text.</summary>
		 * </entry>
		 * 
		 */
		return feedBuilder.build();
		// Feed feed = Feed.builder().withTitle("PENE").withId("1").withUpdateDate(new
		// Date()).withAuthor(Author.builder("autor").build()).addLink(Link.builder("link").build()).build();
		// return feed;
	}

}
