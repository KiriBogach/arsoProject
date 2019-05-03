package ejercicio4.stax;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import servicio.exceptions.GeoNamesException;
import servicio.model.Busqueda;
import servicio.model.Libro;
import utils.Utils;

public class StAXBuilder {

	public void build(long id, Busqueda busqueda) {
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		try {
			writer = xof.createXMLStreamWriter(new FileOutputStream("xml-bd/" + id + ".xml"), "UTF-8");
			writer.writeStartDocument();
			writer.writeStartElement("ciudad");
			writer.writeNamespace("", "http://www.example.org/ciudad"); // Por omisi�n
			writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			writer.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation",
					"http://www.example.org/ciudad ../xml/ciudad.xsd");
			writer.writeStartElement("nombre");
			writer.writeCharacters(busqueda.getNombre());
			writer.writeEndElement();
			writer.writeStartElement("codigo");
			writer.writeCharacters(Long.toString(id));
			writer.writeEndElement();
			writer.writeStartElement("pais");
			writer.writeCharacters(busqueda.getPais());
			writer.writeEndElement();
			writer.writeStartElement("poblacion");
			writer.writeCharacters(Integer.toString(busqueda.getPoblacion()));
			writer.writeEndElement();
			writer.writeStartElement("ubicacion");
			writer.writeStartElement("latitud");
			writer.writeCharacters(Double.toString(busqueda.getLatitud()));
			writer.writeEndElement();
			writer.writeStartElement("longitud");
			writer.writeCharacters(Double.toString(busqueda.getLongitud()));
			writer.writeEndElement();
			writer.writeEndElement();
			if (busqueda.getBdpedia() != null) {
				writer.writeStartElement("bdpedia");
				writer.writeCharacters(busqueda.getBdpedia().toString());
				writer.writeEndElement();
			}
			if (busqueda.getWikipedia() != null) {
				writer.writeStartElement("wikipedia");
				writer.writeCharacters(busqueda.getWikipedia().toString());
				writer.writeEndElement();
			}
			writer.writeStartElement("fechaActualizacion");
			writer.writeCharacters(Utils.fromDateToString(busqueda.getFechaActualizacion()));
			writer.writeEndElement();

			if (busqueda.getNombreEstacion() != null) {
				writer.writeStartElement("infoMeteorologica");
				writer.writeStartElement("tiempoObservacion");
				writer.writeCharacters(Utils.fromDateTimeToString(busqueda.getTiempoObservacion()).replaceAll(" ", "T"));
				writer.writeEndElement();
				writer.writeStartElement("nombreEstacion");
				writer.writeCharacters(busqueda.getNombreEstacion());
				writer.writeEndElement();
				writer.writeStartElement("temperatura");
				writer.writeCharacters(Double.toString(busqueda.getTemperatura()));
				writer.writeEndElement();
				writer.writeStartElement("nubes");
				writer.writeCharacters(busqueda.getNubes());
				writer.writeEndElement();
				writer.writeEndElement();
			}

			writer.writeStartElement("sitiosInteres");
			HashMap<Long, String> mapa = busqueda.getSitiosInteres();
			for (Map.Entry<Long, String> entry : mapa.entrySet()) {
				writer.writeStartElement("sitio");
				writer.writeStartElement("nombre");
				writer.writeCharacters(entry.getValue());
				writer.writeEndElement();
				writer.writeStartElement("codigo");
				writer.writeCharacters(Long.toString(entry.getKey()));
				writer.writeEndElement();
				writer.writeEndElement();
			}

			writer.writeEndElement();
			writer.writeStartElement("libros");
			List<Libro> libros = busqueda.getLibros();
			for (Libro libro : libros) {
				writer.writeStartElement("libro");
				writer.writeStartElement("titulo");
				writer.writeCharacters(libro.getTitulo());
				writer.writeEndElement();
				writer.writeStartElement("identificador");
				writer.writeCharacters(libro.getIdentificador());
				writer.writeEndElement();
				writer.writeStartElement("ISBN");
				writer.writeCharacters(libro.getISBN());
				writer.writeEndElement();
				writer.writeStartElement("urlImagen");
				writer.writeCharacters(libro.getUrlImagen().toString());
				writer.writeEndElement();
				writer.writeStartElement("urlGoogleBooks");
				writer.writeCharacters(libro.getUrlGoogleBooks().toString());
				writer.writeEndElement();
				writer.writeEndElement();
			}

			writer.writeEndElement();
			writer.writeEndElement();

			writer.writeEndDocument();
			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			throw new GeoNamesException("no se encontró el fichero.", e);
		} catch (XMLStreamException e) {
			throw new GeoNamesException("no se puede generar el documento XML", e);
		}

	}
}
