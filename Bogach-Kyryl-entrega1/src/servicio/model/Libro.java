package servicio.model;

import java.net.URI;

public class Libro {
	private String titulo;
	private String identificador;
	private String ISBN;
	private URI urlImagen;
	private URI urlGoogleBooks;

	public Libro() {
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getISBN() {
		return ISBN;
	}

	public String getTitulo() {
		return titulo;
	}

	public URI getUrlGoogleBooks() {
		return urlGoogleBooks;
	}

	public URI getUrlImagen() {
		return urlImagen;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setUrlGoogleBooks(URI urlGoogleBooks) {
		this.urlGoogleBooks = urlGoogleBooks;
	}

	public void setUrlImagen(URI urlImagen) {
		this.urlImagen = urlImagen;
	}

	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", identificador=" + identificador + ", ISBN=" + ISBN + ", urlImagen="
				+ urlImagen + ", urlGoogleBooks=" + urlGoogleBooks + "]";
	}
	
	

}