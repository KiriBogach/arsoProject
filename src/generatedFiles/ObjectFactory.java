//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.03.08 a las 12:11:28 PM CET 
//


package generatedFiles;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.ciudad package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Ciudad_QNAME = new QName("http://www.example.org/ciudad", "ciudad");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.ciudad
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TipoCiudad }
     * 
     */
    public TipoCiudad createTipoCiudad() {
        return new TipoCiudad();
    }

    /**
     * Create an instance of {@link TipoInformacionMeteorologica }
     * 
     */
    public TipoInformacionMeteorologica createTipoInformacionMeteorologica() {
        return new TipoInformacionMeteorologica();
    }

    /**
     * Create an instance of {@link TipoSitioInteres }
     * 
     */
    public TipoSitioInteres createTipoSitioInteres() {
        return new TipoSitioInteres();
    }

    /**
     * Create an instance of {@link TipoSitiosInteres }
     * 
     */
    public TipoSitiosInteres createTipoSitiosInteres() {
        return new TipoSitiosInteres();
    }

    /**
     * Create an instance of {@link TipoUbicacion }
     * 
     */
    public TipoUbicacion createTipoUbicacion() {
        return new TipoUbicacion();
    }

    /**
     * Create an instance of {@link TipoIdentificador }
     * 
     */
    public TipoIdentificador createTipoIdentificador() {
        return new TipoIdentificador();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoCiudad }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/ciudad", name = "ciudad")
    public JAXBElement<TipoCiudad> createCiudad(TipoCiudad value) {
        return new JAXBElement<TipoCiudad>(_Ciudad_QNAME, TipoCiudad.class, null, value);
    }

}
