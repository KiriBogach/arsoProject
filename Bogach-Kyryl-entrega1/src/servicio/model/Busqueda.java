package servicio.model;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Busqueda {
	private String nombre;
	private String pais;
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

	private List<Libro> libros;

	public Busqueda() {
		this.sitiosInteres = new HashMap<>();
		this.libros = new LinkedList<>();
	}

	public void addLibro(Libro libro) {
		this.libros.add(libro);
	}

	public void addSitioInteres(Long codigo, String nombre) {
		this.sitiosInteres.put(codigo, nombre);
	}

	public URI getBdpedia() {
		return bdpedia;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public double getLatitud() {
		return latitud;
	}

	public List<Libro> getLibros() {
		return libros;
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

	public String getNubes() {
		return nubes;
	}

	public String getPais() {
		return pais;
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

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
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

	public void setNubes(String nubes) {
		this.nubes = nubes;
	}

	public void setPais(String pais) {
		this.pais = pais;
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
		return "Busqueda [nombre=" + nombre + ", pais=" + pais + ", poblacion=" + poblacion + ", latitud=" + latitud
				+ ", longitud=" + longitud + ", wikipedia=" + wikipedia + ", bdpedia=" + bdpedia
				+ ", fechaActualizacion=" + fechaActualizacion + ", tiempoObservacion=" + tiempoObservacion
				+ ", nombreEstacion=" + nombreEstacion + ", temperatura=" + temperatura + ", nubes=" + nubes
				+ ", sitiosInteres=" + sitiosInteres + ", libros=" + libros + "]";
	}

}
