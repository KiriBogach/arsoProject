package ejercicio1.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import ciudad.JAXBCiudad;
import ejercicio1.dom.DOMParser;
import ejercicio1.jaxb.ciudades.ListadoCiudades;
import ejercicio1.jaxb.ciudades_favoritas.CiudadesFavoritas;
import ejercicio1.model.Ciudad;
import ejercicio1.sax.Manejador;
import ejercicio1.stax.StAXBuilder;
import ejercicio1.utils.Utils;

public class ServicioGeoNames {
	private static final String BD_PATH = "xml-bd";
	private static final String CTX_PATH = "ciudad";
	private static final String XML_URL = "http://api.geonames.org/search?";
	private static final String USER_URL = "username=arso";
	private JAXBContext context = null;
	private SAXParserFactory SAXfactoria = null;
	private DOMParser DOMParser = null;
	private StAXBuilder StAXBuilder = null;

	public ServicioGeoNames() {
		comprobarFichero();
	}

	private static void comprobarFichero() {
		File folder = new File(BD_PATH);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	public List<Ciudad> buscar(String busqueda) throws ParserConfigurationException, SAXException, URISyntaxException {
		SAXParser analizador = getSAXParserFactory().newSAXParser();
		try {
			Manejador manejador = new Manejador();
			URI url = new URI(XML_URL + busqueda + "&" + USER_URL);
			analizador.parse(url.toString(), manejador);
			return manejador.getCiudades();
		} catch (IOException e) {
			System.out.println("El documento no ha podido ser leído");
			return null;
		} catch (SAXException e) {
			System.out.println("Error de pocesamiento: " + e.getMessage());
			return null;
		}
	}

	public JAXBCiudad getCiudad(String idGeoNames) throws Exception {
		try {
			Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
			return (JAXBCiudad) unmarshaller.unmarshal(recuperarDocumento(idGeoNames));
		} catch (JAXBException e) {
			System.err.println("Unable to create unmarshaller. Is JAXB context: " + CTX_PATH + " reachable?");
			// Debería acabar la ejecución? - Yo creo que sí.
			throw new RuntimeException(e.getMessage());
		}
	}

	public ListadoCiudades getResultadosBusquedaXML(String busqueda) throws Exception {
		ListadoCiudades listadoCiudades = new ListadoCiudades();
		Collection<Ciudad> ciudadesEncontradas = this.buscar(busqueda);
		listadoCiudades.addAll(ciudadesEncontradas);

		return listadoCiudades;
	}

	public String crearDocumentoFavorito() throws Exception {
		CiudadesFavoritas nuevoDocumento = new CiudadesFavoritas();
		String idGenerado = UUID.randomUUID().toString();
		nuevoDocumento.setId(idGenerado);

		this.createCiudadesFavoritasFile(nuevoDocumento);
		return idGenerado;
	}

	public void addCiudadFavorita(String idFavoritos, String idGeoNames) throws Exception {
		JAXBContext contexto = JAXBContext.newInstance(CiudadesFavoritas.class);
		Unmarshaller unmarshaller = contexto.createUnmarshaller();

		File favoritos = getCiudadesFavoritasFile(idFavoritos);
		if (favoritos == null) {
			return;
		}

		CiudadesFavoritas ciudadesFavoritas = (CiudadesFavoritas) unmarshaller.unmarshal(favoritos);
		ciudadesFavoritas.addCiudad(idGeoNames);

		this.createCiudadesFavoritasFile(ciudadesFavoritas);
	}

	public boolean removeCiudadFavorita(String idFavoritos, String idGeoNames) throws Exception {
		JAXBContext contexto = JAXBContext.newInstance(CiudadesFavoritas.class);
		Unmarshaller unmarshaller = contexto.createUnmarshaller();

		File favoritos = getCiudadesFavoritasFile(idFavoritos);
		if (favoritos == null) {
			return false;
		}

		CiudadesFavoritas ciudadesFavoritas = (CiudadesFavoritas) unmarshaller.unmarshal(favoritos);
		boolean isRemoved = ciudadesFavoritas.removeCiudad(idGeoNames);
		this.createCiudadesFavoritasFile(ciudadesFavoritas);

		return isRemoved;
	}

	public CiudadesFavoritas getFavoritos(String idFavoritos) throws Exception {
		JAXBContext contexto = JAXBContext.newInstance(CiudadesFavoritas.class);
		Unmarshaller unmarshaller = contexto.createUnmarshaller();

		File favoritos = getCiudadesFavoritasFile(idFavoritos);
		if (favoritos == null) {
			return null;
		}

		CiudadesFavoritas ciudadesFavoritas = (CiudadesFavoritas) unmarshaller.unmarshal(favoritos);
		return ciudadesFavoritas;
	}

	public void removeDocumentoFavoritos(String idFavoritos) {
		File favoritos = getCiudadesFavoritasFile(idFavoritos);
		if (favoritos == null) {
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
				System.err.println("Unable to find path to JAXB context");
				// Debería acabar la ejecución?
				throw new RuntimeException(e.getMessage());
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

	private File recuperarDocumento(String idGeoNames) throws Exception {
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
			this.getStAXBuilder().build(id, getDOMParser().parse(id));
		}

		return file;
	}

	private File getCiudadesFavoritasFile(String idCiudadesFavoritas) {
		File favoritos = new File("xml-bd/favoritos-" + idCiudadesFavoritas + ".xml");
		if (!favoritos.exists()) {
			System.err.println("No existe el documento: " + favoritos.getPath());
			return null;
		}
		return favoritos;
	}

	private void createCiudadesFavoritasFile(CiudadesFavoritas ciudadesFavoritas) throws Exception {
		JAXBContext contexto = JAXBContext.newInstance(CiudadesFavoritas.class);
		Marshaller marshaller = contexto.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", true);
		marshaller.marshal(ciudadesFavoritas, new File("xml-bd/favoritos-" + ciudadesFavoritas.getId() + ".xml"));
	}
}
