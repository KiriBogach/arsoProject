package servicio.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import servicio.model.CiudadGeoNames;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="listado_ciudades")
public class ListadoCiudades {

	@XmlElement(name="ciudad")
	private List<CiudadGeoNames> ciudades;
	
	public ListadoCiudades() {
		this.ciudades = new ArrayList<>();
	}
	
	public List<CiudadGeoNames> getCiudades() {
		return ciudades;
	}
	
	public void addAll(Collection<CiudadGeoNames> ciudades) {
		this.ciudades.addAll(ciudades);
	}
}
