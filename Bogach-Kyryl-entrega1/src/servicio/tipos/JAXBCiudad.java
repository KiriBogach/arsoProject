//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.03.13 a las 10:41:52 AM CET 
//


package servicio.tipos;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/ciudad}tipoIdentificador">
 *       &lt;sequence>
 *         &lt;element name="pais" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="poblacion" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="ubicacion" type="{http://www.example.org/ciudad}tipoUbicacion"/>
 *         &lt;element name="bdpedia" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="wikipedia" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="fechaActualizacion" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="infoMeteorologica" type="{http://www.example.org/ciudad}tipoInformacionMeteorologica" minOccurs="0"/>
 *         &lt;element name="sitiosInteres" type="{http://www.example.org/ciudad}tipoSitiosInteres"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pais",
    "poblacion",
    "ubicacion",
    "bdpedia",
    "wikipedia",
    "fechaActualizacion",
    "infoMeteorologica",
    "sitiosInteres"
})
@XmlRootElement(name = "ciudad")
public class JAXBCiudad
    extends TipoIdentificador
{

    @XmlElement(required = true)
    protected String pais;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger poblacion;
    @XmlElement(required = true)
    protected TipoUbicacion ubicacion;
    @XmlSchemaType(name = "anyURI")
    protected String bdpedia;
    @XmlSchemaType(name = "anyURI")
    protected String wikipedia;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaActualizacion;
    protected TipoInformacionMeteorologica infoMeteorologica;
    @XmlElement(required = true)
    protected TipoSitiosInteres sitiosInteres;

    /**
     * Obtiene el valor de la propiedad pais.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPais() {
        return pais;
    }

    /**
     * Define el valor de la propiedad pais.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPais(String value) {
        this.pais = value;
    }

    /**
     * Obtiene el valor de la propiedad poblacion.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPoblacion() {
        return poblacion;
    }

    /**
     * Define el valor de la propiedad poblacion.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPoblacion(BigInteger value) {
        this.poblacion = value;
    }

    /**
     * Obtiene el valor de la propiedad ubicacion.
     * 
     * @return
     *     possible object is
     *     {@link TipoUbicacion }
     *     
     */
    public TipoUbicacion getUbicacion() {
        return ubicacion;
    }

    /**
     * Define el valor de la propiedad ubicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoUbicacion }
     *     
     */
    public void setUbicacion(TipoUbicacion value) {
        this.ubicacion = value;
    }

    /**
     * Obtiene el valor de la propiedad bdpedia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBdpedia() {
        return bdpedia;
    }

    /**
     * Define el valor de la propiedad bdpedia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBdpedia(String value) {
        this.bdpedia = value;
    }

    /**
     * Obtiene el valor de la propiedad wikipedia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWikipedia() {
        return wikipedia;
    }

    /**
     * Define el valor de la propiedad wikipedia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWikipedia(String value) {
        this.wikipedia = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaActualizacion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Define el valor de la propiedad fechaActualizacion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaActualizacion(XMLGregorianCalendar value) {
        this.fechaActualizacion = value;
    }

    /**
     * Obtiene el valor de la propiedad infoMeteorologica.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionMeteorologica }
     *     
     */
    public TipoInformacionMeteorologica getInfoMeteorologica() {
        return infoMeteorologica;
    }

    /**
     * Define el valor de la propiedad infoMeteorologica.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionMeteorologica }
     *     
     */
    public void setInfoMeteorologica(TipoInformacionMeteorologica value) {
        this.infoMeteorologica = value;
    }

    /**
     * Obtiene el valor de la propiedad sitiosInteres.
     * 
     * @return
     *     possible object is
     *     {@link TipoSitiosInteres }
     *     
     */
    public TipoSitiosInteres getSitiosInteres() {
        return sitiosInteres;
    }

    /**
     * Define el valor de la propiedad sitiosInteres.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoSitiosInteres }
     *     
     */
    public void setSitiosInteres(TipoSitiosInteres value) {
        this.sitiosInteres = value;
    }

}
