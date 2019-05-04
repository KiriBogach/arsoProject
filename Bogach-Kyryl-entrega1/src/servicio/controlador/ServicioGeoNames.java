package servicio.controlador;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

import atom.builder.AtomBuilder;
import ejercicio.xpath.XPathParser;
import ejercicio3.sax.Manejador;
import ejercicio4.dom.DOMParser;
import ejercicio4.stax.StAXBuilder;
import fr.vidal.oss.jaxb.atom.core.Feed;
import hal.builder.HalBuilder;
import servicio.exceptions.GeoNamesException;
import servicio.model.Busqueda;
import servicio.model.CiudadGeoNames;
import servicio.tipos.Ciudad;
import utils.Utils;

public class ServicioGeoNames {
	private static final String BD_PATH = "xml-bd";
	private static final String CTX_PATH = "servicio.tipos";
	private static final String XML_URL = "http://api.geonames.org/search?featureClass=P&q=";
	private static final String USER_URL = "username=arso";
	private JAXBContext context = null;
	private SAXParserFactory SAXfactoria = null;
	private DOMParser DOMParser = null;
	private StAXBuilder StAXBuilder = null;
	private XPathParser XPathParser = null;

	public ServicioGeoNames() {
		comprobarFichero();
	}

	private static void comprobarFichero() {
		File folder = new File(BD_PATH);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	public List<CiudadGeoNames> buscar(String busqueda) {
		return this.buscar(busqueda, null);
	}

	public List<CiudadGeoNames> buscar(String busqueda, Integer paginas) {
		SAXParser analizador = null;
		try {
			analizador = getSAXParserFactory().newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			throw new GeoNamesException("No se ha podido buscar el documento.", e);
		}

		try {
			Manejador manejador = new Manejador();
			URI url = null;
			try {
				String busquedaEncoded = URLEncoder.encode(busqueda,
						java.nio.charset.StandardCharsets.UTF_8.toString());
				String uriString = XML_URL + busquedaEncoded + "&" + USER_URL;

				if (paginas != null) {
					int startRow = (paginas - 1) * 10;
					uriString += "&startRow=" + startRow + "&maxRows=10";
				}

				url = new URI(uriString);

				// System.out.println(url);
			} catch (URISyntaxException | UnsupportedEncodingException ex) {
				throw new GeoNamesException("Error codificando el parametro de busqueda.", ex);
			}
			analizador.parse(url.toString(), manejador);
			HalBuilder.TOTAL_RESULT_COUNT = manejador.getTotalResultCount();
			AtomBuilder.TOTAL_RESULT_COUNT = manejador.getTotalResultCount();
			return manejador.getCiudades();
		} catch (IOException e) {
			throw new GeoNamesException("El documento no ha podido ser leído", e);
		} catch (SAXException e) {
			throw new GeoNamesException("No se ha podido buscar el documento.", e);
		}
	}

	public Ciudad getCiudad(String idGeoNames) {
		try {
			Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
			return (Ciudad) unmarshaller.unmarshal(recuperarDocumento(idGeoNames));
		} catch (JAXBException e) {
			throw new GeoNamesException("No se pudo obtener la ciudad.", e);
		}
	}

	public ListadoCiudades getResultadosBusquedaXML(String busqueda) {
		ListadoCiudades listadoCiudades = new ListadoCiudades();
		Collection<CiudadGeoNames> ciudadesEncontradas = this.buscar(busqueda);
		listadoCiudades.addAll(ciudadesEncontradas);

		return listadoCiudades;
	}

	public Feed getResultadosBusquedaATOM(String busqueda, int numeroPagina, UriInfo uriInfo) {
		ListadoCiudades listadoCiudades = new ListadoCiudades();
		Collection<CiudadGeoNames> ciudadesEncontradas = this.buscar(busqueda, numeroPagina);
		listadoCiudades.addAll(ciudadesEncontradas);
		return AtomBuilder.build(listadoCiudades, numeroPagina, uriInfo);
	}
	
	public String getResultadosBusquedaHAL(String busqueda, int numeroPagina, UriInfo uriInfo) {
		ListadoCiudades listadoCiudades = new ListadoCiudades();
		Collection<CiudadGeoNames> ciudadesEncontradas = this.buscar(busqueda, numeroPagina);
		listadoCiudades.addAll(ciudadesEncontradas);
		return HalBuilder.build(listadoCiudades, numeroPagina, uriInfo);
	}

	public File getResultadosBusquedaKML(String busqueda, ServletContext context) {
		final String documentoEntrada = XML_URL + busqueda + "&" + USER_URL;
		final String documentoSalida = "xml-bd/" + busqueda + ".kml";

		TransformerFactory factoria = TransformerFactory.newInstance();

		Transformer transformador;
		try {
			transformador = factoria
					.newTransformer(new StreamSource(context.getResourceAsStream("/WEB-INF/kml-transformer.xsl")));
		} catch (TransformerConfigurationException e) {
			throw new GeoNamesException("No se puede generar el documento KML.", e);
		}
		Source origen = new StreamSource(documentoEntrada);
		Result destino = new StreamResult(documentoSalida);
		try {
			transformador.transform(origen, destino);
		} catch (TransformerException e) {
			throw new GeoNamesException("No se puede generar el documento KML.", e);
		}

		return new File("xml-bd/" + busqueda + ".kml");
	}

	public String crearDocumentoFavorito() {
		CiudadesFavoritas nuevoDocumento = new CiudadesFavoritas();
		String idGenerado = UUID.randomUUID().toString();
		nuevoDocumento.setId(idGenerado);

		this.createCiudadesFavoritasFile(nuevoDocumento);
		return idGenerado;
	}

	public void addCiudadFavorita(String idFavoritos, String idGeoNames) {

		try {
			JAXBContext contexto;
			contexto = JAXBContext.newInstance(CiudadesFavoritas.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			File favoritos = getCiudadesFavoritasFile(idFavoritos);
			CiudadesFavoritas ciudadesFavoritas = (CiudadesFavoritas) unmarshaller.unmarshal(favoritos);
			ciudadesFavoritas.addCiudad(idGeoNames);
			this.createCiudadesFavoritasFile(ciudadesFavoritas);
		} catch (JAXBException e) {
			throw new GeoNamesException("No se pudo añadir a favoritos.", e);
		}
	}

	public boolean removeCiudadFavorita(String idFavoritos, String idGeoNames) {
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(CiudadesFavoritas.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			File favoritos = getCiudadesFavoritasFile(idFavoritos);
			CiudadesFavoritas ciudadesFavoritas = (CiudadesFavoritas) unmarshaller.unmarshal(favoritos);
			boolean isRemoved = ciudadesFavoritas.removeCiudad(idGeoNames);
			this.createCiudadesFavoritasFile(ciudadesFavoritas);
			return isRemoved;
		} catch (JAXBException e) {
			throw new GeoNamesException("No se pudo eliminar de favoritos.", e);
		}
	}

	public CiudadesFavoritas getFavoritos(String idFavoritos) {
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(CiudadesFavoritas.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			File favoritos = getCiudadesFavoritasFile(idFavoritos);
			CiudadesFavoritas ciudadesFavoritas = (CiudadesFavoritas) unmarshaller.unmarshal(favoritos);
			return ciudadesFavoritas;
		} catch (JAXBException e) {
			throw new GeoNamesException("No se pudo obtener el fichero de favoritos.", e);
		}

	}

	public void removeDocumentoFavoritos(String idFavoritos) {
		File favoritos = getCiudadesFavoritasFile(idFavoritos);
		if (!favoritos.exists()) {
			return;
		}

		favoritos.delete();
	}

	private DOMParser getDOMParser() {
		if (DOMParser == null) {
			DOMParser = new DOMParser();
		}
		return DOMParser;
	}

	private JAXBContext getJAXBContext() {
		if (context != null)
			return context;
		else
			try {
				context = JAXBContext.newInstance(CTX_PATH);
				return context;
			} catch (JAXBException e) {
				throw new GeoNamesException("Error de configuración.", e);
			}
	}

	private SAXParserFactory getSAXParserFactory() {
		if (SAXfactoria == null) {
			SAXfactoria = SAXParserFactory.newInstance();
		}
		return SAXfactoria;
	}

	private StAXBuilder getStAXBuilder() {
		if (StAXBuilder == null) {
			StAXBuilder = new StAXBuilder();
		}
		return StAXBuilder;
	}

	private XPathParser getXPathParser() {
		if (XPathParser == null) {
			XPathParser = new XPathParser();
		}
		return XPathParser;
	}

	private File recuperarDocumento(String idGeoNames) {
		File file = new File(BD_PATH + "/" + idGeoNames + ".xml");

		Calendar calendarOneHour = Calendar.getInstance();
		calendarOneHour.set(0, 0, 0, 1, 0, 0);
		boolean actualizado = Utils.compareInMillis(Calendar.getInstance().getTimeInMillis(), file.lastModified(),
				calendarOneHour.getTimeInMillis());

		/*
		 * Si el fichero no existe o no se ha actualizado desde hace una hora se vuelve
		 * a generar.
		 */
		if (!file.exists() || !actualizado) {
			long id = Long.parseLong(idGeoNames);
			Busqueda busqueda = getDOMParser().parse(id);
			this.getXPathParser().parse(busqueda);
			this.getStAXBuilder().build(id, busqueda);
		}

		return file;
	}

	private File getCiudadesFavoritasFile(String idCiudadesFavoritas) {
		File favoritos = new File("xml-bd/favoritos-" + idCiudadesFavoritas + ".xml");
		return favoritos;
	}

	private void createCiudadesFavoritasFile(CiudadesFavoritas ciudadesFavoritas) {
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(CiudadesFavoritas.class);
			Marshaller marshaller = contexto.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", true);
			marshaller.marshal(ciudadesFavoritas, new File("xml-bd/favoritos-" + ciudadesFavoritas.getId() + ".xml"));
		} catch (JAXBException e) {
			throw new GeoNamesException("No se pudo crear el fichero de favoritos.", e);
		}
	}
}
