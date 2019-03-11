package ejercicio1.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import ejercicio1.dom.DOMParser;
import ejercicio1.model.Ciudad;
import ejercicio1.sax.Manejador;
import ejercicio1.stax.StAXBuilder;
import ejercicio1.utils.Utils;
import generatedFiles.TipoCiudad;

public class ServicioGeoNames {
	private static final String BD_PATH = "xml-bd";
	private static final String CTXPATH = "generatedFiles";
	private JAXBContext context = null;
	private SAXParserFactory SAXfactoria = null;
	private DOMParser DOMParser = null;
	private StAXBuilder StAXBuilder = null;
	private static final String XMLURL = "http://api.geonames.org/search?";
	private static final String USERURL = "username=arso";
	
	private static void comprobarFichero() {
		File folder = new File(BD_PATH);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}
	
	private DOMParser getDOMParser() {
		if (DOMParser == null) {
			DOMParser = new DOMParser();
		}
		return DOMParser;
	}
	
	private StAXBuilder getStAXBuilder() {
		if (StAXBuilder == null) {
			StAXBuilder = new StAXBuilder();
		}
		return StAXBuilder;
	}
	
	private JAXBContext getJAXBContext() {
		if (context != null) 
			return context;
		else
			try {
				context = JAXBContext.newInstance(CTXPATH);
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
	
	public ServicioGeoNames() {
		comprobarFichero();
	}
	
	public List<Ciudad> buscar(String busqueda) throws ParserConfigurationException, SAXException, URISyntaxException {
		SAXParser analizador = getSAXParserFactory().newSAXParser();
		try {
			Manejador manejador = new Manejador();
			URI url = new URI(XMLURL + busqueda + "&" + USERURL);
			analizador.parse(url.toString(), manejador);
			return manejador.getCiudades();
		} 
		catch (IOException e) {
			System.out.println("El documento no ha podido ser le�do");
			return null;
		}
		catch (SAXException e) {
			System.out.println("Error de pocesamiento: " + e.getMessage());
			return null;
		}
	}
	
	public TipoCiudad getCiudad(String idGeoNames) throws Exception {
		
		try {
			Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
			JAXBElement<TipoCiudad> jaxbElement = (JAXBElement<TipoCiudad>)unmarshaller.unmarshal(recuperarDocumento(idGeoNames));
			TipoCiudad ciudad = jaxbElement.getValue();
			return ciudad;
		} catch (JAXBException e) {
			System.err.println("Unable to create unmarshaller. Is JAXB context: " + CTXPATH + " reachable?" );
			// Debería acabar la ejecución?
			throw new RuntimeException(e.getMessage());
		}	
	}
	
	private File recuperarDocumento(String idGeoNames) throws Exception {
		File file = new File(BD_PATH + "/" + idGeoNames + ".xml");
		Calendar calendarAux = Calendar.getInstance();
		calendarAux.set(0, 0, 0, 1, 0, 0);
		boolean actualizado = Utils.compareInMillis(
				Calendar.getInstance().getTimeInMillis(),
				file.lastModified(),
				calendarAux.getTimeInMillis());
		if (!file.exists() || !actualizado) {
			long id = Long.parseLong(idGeoNames);
			getStAXBuilder().build(id, getDOMParser().parse(id));
		}
		return file;
	}
	
}
