package servicio.model;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;

public class Busqueda {

	private String nombre;
	private String codigoPais;
	private String nombrePais;
	private int poblacion;
	private double latitud;
	private double longitud;
	private URI wikipedia;
	private URI bdpedia;
	private Date fechaActualizacion;
	private Date tiempoObservacion;
	private String nombreEstacion;
	private double temperatura;
	private String nubes;
	private HashMap<Long, String> sitiosInteres;

	public Busqueda() {
		sitiosInteres = new HashMap<>();
	}

	public Busqueda(String nombre, String codigoPais, String nombrePais, int poblacion, double latitud, double longitud,
			URI wikipedia, URI dbpedia, Date fechaActualizacion, Date tiempoObservacion, String nombreEstacion,
			double temperatura, String nubes, HashMap<Long, String> sitiosInteres) {
		this.nombre = nombre;
		this.codigoPais = codigoPais;
		this.nombrePais = nombrePais;
		this.poblacion = poblacion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.wikipedia = wikipedia;
		this.bdpedia = dbpedia;
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

	public URI getBdpedia() {
		return bdpedia;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNombreEstacion() {
		return nombreEstacion;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public String getNubes() {
		return nubes;
	}

	public int getPoblacion() {
		return poblacion;
	}

	public HashMap<Long, String> getSitiosInteres() {
		return sitiosInteres;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public Date getTiempoObservacion() {
		return tiempoObservacion;
	}

	public URI getWikipedia() {
		return wikipedia;
	}

	public void setBdpedia(URI dbpedia) {
		this.bdpedia = dbpedia;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNombreEstacion(String nombreEstacion) {
		this.nombreEstacion = nombreEstacion;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public void setNubes(String nubes) {
		this.nubes = nubes;
	}

	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}

	public void setSitiosInteres(HashMap<Long, String> sitiosInteres) {
		this.sitiosInteres = sitiosInteres;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public void setTiempoObservacion(Date tiempoObservacion) {
		this.tiempoObservacion = tiempoObservacion;
	}

	public void setWikipedia(URI wikipedia) {
		this.wikipedia = wikipedia;
	}

	@Override
	public String toString() {
		return "Busqueda [nombre=" + nombre + ", codigoPais=" + codigoPais + ", nombrePais=" + nombrePais
				+ ", poblacion=" + poblacion + ", latitud=" + latitud + ", longitud=" + longitud + ", wikipedia="
				+ wikipedia + ", bdpedia=" + bdpedia + ", fechaActualizacion=" + fechaActualizacion
				+ ", tiempoObservacion=" + tiempoObservacion + ", nombreEstacion=" + nombreEstacion + ", temperatura="
				+ temperatura + ", nubes=" + nubes + ", sitiosInteres=" + sitiosInteres + "]";
	}

}
