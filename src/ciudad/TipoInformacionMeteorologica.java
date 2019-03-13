//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.03.13 a las 10:41:52 AM CET 
//


package ciudad;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para tipoInformacionMeteorologica complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionMeteorologica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tiempoObservacion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="nombreEstacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="temperatura" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="nubes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionMeteorologica", propOrder = {
    "tiempoObservacion",
    "nombreEstacion",
    "temperatura",
    "nubes"
})
public class TipoInformacionMeteorologica {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tiempoObservacion;
    @XmlElement(required = true)
    protected String nombreEstacion;
    @XmlElement(required = true)
    protected BigDecimal temperatura;
    @XmlElement(required = true)
    protected String nubes;

    /**
     * Obtiene el valor de la propiedad tiempoObservacion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTiempoObservacion() {
        return tiempoObservacion;
    }

    /**
     * Define el valor de la propiedad tiempoObservacion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTiempoObservacion(XMLGregorianCalendar value) {
        this.tiempoObservacion = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreEstacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEstacion() {
        return nombreEstacion;
    }

    /**
     * Define el valor de la propiedad nombreEstacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEstacion(String value) {
        this.nombreEstacion = value;
    }

    /**
     * Obtiene el valor de la propiedad temperatura.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTemperatura() {
        return temperatura;
    }

    /**
     * Define el valor de la propiedad temperatura.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTemperatura(BigDecimal value) {
        this.temperatura = value;
    }

    /**
     * Obtiene el valor de la propiedad nubes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNubes() {
        return nubes;
    }

    /**
     * Define el valor de la propiedad nubes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNubes(String value) {
        this.nubes = value;
    }

}
