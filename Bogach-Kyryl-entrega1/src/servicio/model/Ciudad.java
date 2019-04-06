package servicio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ciudad")
public class Ciudad {
	private String nombre;
	private String pais;
	private long idGeonames;
	private double longitud;
	private double latitud;
	// Calculada URI en GeoNames

	public Ciudad() {
	}

	public Ciudad(String nombre, String pais, long idGeonames, double longitud, double latitud) {
		this.nombre = nombre;
		this.pais = pais;
		this.idGeonames = idGeonames;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	@XmlElement
	public String getURI() {
		return "http://sws.geonames.org/" + this.idGeonames + "/about.rdf";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public long getIdGeonames() {
		return idGeonames;
	}

	public void setIdGeonames(long idGeonames) {
		this.idGeonames = idGeonames;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	@Override
	public String toString() {
		return "Ciudad [nombre=" + nombre + ", pais=" + pais + ", idGeonames=" + idGeonames + ", longitud=" + longitud
				+ ", latitud=" + latitud + "]";
	}

}
