package servicio.controlador;

import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "ciudades_favoritas")
public class CiudadesFavoritas {

	private String id;
	private Collection<String> ciudad;

	public CiudadesFavoritas() {
		this.ciudad = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<String> getCiudad() {
		return this.ciudad;
	}

	public void addCiudad(String ciudad) {
		this.ciudad.add(ciudad);
	}

	public boolean removeCiudad(String ciudad) {
		return this.ciudad.remove(ciudad);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " [id=" + id + ", ciudad=" + ciudad + "]";
	}
	
	

}
