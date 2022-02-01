//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.07.12 um 06:55:30 PM CEST 
//


package de.dennisfalk.homeconnect2semp.model.semp.sempMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         Specifies if the device is able to deal with absolute timestamps or only with relative timestamps
 *       
 * 
 * <p>Java-Klasse für CapTimestampType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CapTimestampType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AbsoluteTimestamps" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CapTimestampType", propOrder = {
    "absoluteTimestamps"
})
public class CapTimestampType {

    @XmlElement(name = "AbsoluteTimestamps", defaultValue = "false")
    protected boolean absoluteTimestamps;

    /**
     * Ruft den Wert der absoluteTimestamps-Eigenschaft ab.
     * 
     */
    public boolean isAbsoluteTimestamps() {
        return absoluteTimestamps;
    }

    /**
     * Legt den Wert der absoluteTimestamps-Eigenschaft fest.
     * 
     */
    public void setAbsoluteTimestamps(boolean value) {
        this.absoluteTimestamps = value;
    }

}
