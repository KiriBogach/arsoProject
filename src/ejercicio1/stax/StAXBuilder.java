package ejercicio1.stax;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import ejercicio1.model.Busqueda;
import ejercicio1.utils.Utils;

public class StAXBuilder {

	
	public void build(long id, Busqueda busqueda) throws Exception {
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = xof.createXMLStreamWriter(new FileOutputStream("xml-bd/" + id + ".xml"));

		writer.writeStartDocument();
		writer.writeStartElement("ciudad");
		writer.writeNamespace("", "http://www.example.org/ciudad"); // Por omisión
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
		writer.writeStartElement("bdpedia");
		writer.writeCharacters(busqueda.getBdpedia().toString());
		writer.writeEndElement();
		writer.writeStartElement("wikipedia");
		writer.writeCharacters(busqueda.getWikipedia().toString());
		writer.writeEndElement();
		writer.writeStartElement("fechaActualizacion");
		writer.writeCharacters(Utils.fromDateToString(busqueda.getFechaActualizacion()));
		writer.writeEndElement();
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
		writer.writeEndElement();

		writer.writeEndDocument();
		writer.flush();
		writer.close();
	}
}
