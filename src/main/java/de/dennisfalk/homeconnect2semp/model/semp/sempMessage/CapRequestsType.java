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
 *         Specifies options related to planning requests.
 *       
 * 
 * <p>Java-Klasse für CapRequestsType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CapRequestsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OptionalEnergy" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CapRequestsType", propOrder = {
    "optionalEnergy"
})
public class CapRequestsType {

    @XmlElement(name = "OptionalEnergy", defaultValue = "false")
    protected boolean optionalEnergy;

    /**
     * Ruft den Wert der optionalEnergy-Eigenschaft ab.
     * 
     */
    public boolean isOptionalEnergy() {
        return optionalEnergy;
    }

    /**
     * Legt den Wert der optionalEnergy-Eigenschaft fest.
     * 
     */
    public void setOptionalEnergy(boolean value) {
        this.optionalEnergy = value;
    }

}
