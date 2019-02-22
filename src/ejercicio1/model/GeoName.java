package ejercicio1.model;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;

public class GeoName {

	private String nombre;
	private String pais;
	private int poblacion;
	private double latitud;
	private double longitud;
	private URI wikipedia;
	private URI dbpedia;
	private Date fechaActualizacion;
	private Date tiempoObservacion;
	private String nombreEstacion;
	private double temperatura;
	private String nubes;
	private HashMap<Long, String> sitiosInteres;

	
	
	public GeoName() {
		sitiosInteres = new HashMap<>();
	}

	public GeoName(String nombre, String pais, int poblacion, double latitud, double longitud, URI wikipedia,
			URI dbpedia, Date fechaActualizacion, Date tiempoObservacion, String nombreEstacion, double temperatura,
			String nubes, HashMap<Long, String> sitiosInteres) {
		this.nombre = nombre;
		this.pais = pais;
		this.poblacion = poblacion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.wikipedia = wikipedia;
		this.dbpedia = dbpedia;
		this.fechaActualizacion = fechaActualizacion;
		this.tiempoObservacion = tiempoObservacion;
		this.nombreEstacion = nombreEstacion;
		this.temperatura = temperatura;
		this.nubes = nubes;
		this.sitiosInteres = sitiosInteres;
	}
	
	public void addSitioInteres(Long codigo, String nombre) {
		sitiosInteres.put(codigo, nombre);
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

	public int getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public URI getWikipedia() {
		return wikipedia;
	}

	public void setWikipedia(URI wikipedia) {
		this.wikipedia = wikipedia;
	}

	public URI getDbpedia() {
		return dbpedia;
	}

	public void setDbpedia(URI dbpedia) {
		this.dbpedia = dbpedia;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getTiempoObservacion() {
		return tiempoObservacion;
	}

	public void setTiempoObservacion(Date tiempoObservacion) {
		this.tiempoObservacion = tiempoObservacion;
	}

	public String getNombreEstacion() {
		return nombreEstacion;
	}

	public void setNombreEstacion(String nombreEstacion) {
		this.nombreEstacion = nombreEstacion;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public String getNubes() {
		return nubes;
	}

	public void setNubes(String nubes) {
		this.nubes = nubes;
	}

	public HashMap<Long, String> getSitiosInteres() {
		return sitiosInteres;
	}

	public void setSitiosInteres(HashMap<Long, String> sitiosInteres) {
		this.sitiosInteres = sitiosInteres;
	}

	@Override
	public String toString() {
		return "GeoName [nombre=" + nombre + ", pais=" + pais + ", poblacion=" + poblacion + ", latitud=" + latitud
				+ ", longitud=" + longitud + ", wikipedia=" + wikipedia + ", dbpedia=" + dbpedia
				+ ", fechaActualizacion=" + fechaActualizacion + ", tiempoObservacion=" + tiempoObservacion
				+ ", nombreEstacion=" + nombreEstacion + ", temperatura=" + temperatura + ", nubes=" + nubes
				+ ", sitiosInteres=" + sitiosInteres + "]";
	}
	
	

}
