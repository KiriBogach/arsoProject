package ejercicio1.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import ejercicio1.model.Ciudad;
import ejercicio1.sax.Manejador;
import generatedFiles.TipoCiudad;
public class ServicioGeoNames {
	
	private SAXParserFactory SAXfactoria;
	private static final String XMLURL = "http://api.geonames.org/search?";
	private static final String USERURL = "username=arso";
	
	public ServicioGeoNames() {
		SAXfactoria = SAXParserFactory.newInstance();
	}
	
	public List<Ciudad> buscar(String busqueda) throws ParserConfigurationException, SAXException, URISyntaxException {
		SAXParser analizador = SAXfactoria.newSAXParser();
		try {
			Manejador manejador = new Manejador();
			URI url = new URI(XMLURL + busqueda + "&" + USERURL);
			analizador.parse(url.toString(), manejador);
			return manejador.getCiudades();
		} 
		catch (IOException e) {
			System.out.println("El documento no ha podido ser leído");
			return null;
		}
		catch (SAXException e) {
			System.out.println("Error de pocesamiento: " + e.getMessage());
			return null;
		}
	}
}
