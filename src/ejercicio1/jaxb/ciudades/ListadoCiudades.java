package ejercicio1.jaxb.ciudades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ejercicio1.model.Ciudad;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name="listado_ciudades")
public class ListadoCiudades {

	private List<Ciudad> ciudades;
	
	public ListadoCiudades() {
		this.ciudades = new ArrayList<>();
	}
	
	public List<Ciudad> getCiudades() {
		return ciudades;
	}
	
	public void addAll(Collection<Ciudad> ciudades) {
		this.ciudades.addAll(ciudades);
	}
}
