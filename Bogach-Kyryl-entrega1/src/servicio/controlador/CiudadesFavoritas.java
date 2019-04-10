package servicio.controlador;

import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ciudades_favoritas")
public class CiudadesFavoritas {

	private String id;

	@XmlElement(name = "ciudad")
	private Collection<String> ciudades;

	public CiudadesFavoritas() {
		this.ciudades = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<String> getCiudades() {
		return ciudades;
	}

	public void addCiudad(String ciudad) {
		this.ciudades.add(ciudad);
	}

	public boolean removeCiudad(String ciudad) {
		return this.ciudades.remove(ciudad);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " [id=" + id + ", ciudad=" + ciudades + "]";
	}

}
