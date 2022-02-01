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
 *         Encapsulates information about the capabilities of the device
 *       
 * 
 * <p>Java-Klasse für CapabilitiesType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CapabilitiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CurrentPower" type="{http://www.sma.de/communication/schema/SEMP/v1}CapPowerMeasurementType"/>
 *         &lt;element name="Timestamps" type="{http://www.sma.de/communication/schema/SEMP/v1}CapTimestampType" minOccurs="0"/>
 *         &lt;element name="Interruptions" type="{http://www.sma.de/communication/schema/SEMP/v1}CapInterruptionsType" minOccurs="0"/>
 *         &lt;element name="Requests" type="{http://www.sma.de/communication/schema/SEMP/v1}CapRequestsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CapabilitiesType", propOrder = {
    "currentPower",
    "timestamps",
    "interruptions",
    "requests"
})
public class CapabilitiesType {

    @XmlElement(name = "CurrentPower", required = true)
    protected CapPowerMeasurementType currentPower;
    @XmlElement(name = "Timestamps")
    protected CapTimestampType timestamps;
    @XmlElement(name = "Interruptions")
    protected CapInterruptionsType interruptions;
    @XmlElement(name = "Requests")
    protected CapRequestsType requests;

    /**
     * Ruft den Wert der currentPower-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CapPowerMeasurementType }
     *     
     */
    public CapPowerMeasurementType getCurrentPower() {
        return currentPower;
    }

    /**
     * Legt den Wert der currentPower-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CapPowerMeasurementType }
     *     
     */
    public void setCurrentPower(CapPowerMeasurementType value) {
        this.currentPower = value;
    }

    /**
     * Ruft den Wert der timestamps-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CapTimestampType }
     *     
     */
    public CapTimestampType getTimestamps() {
        return timestamps;
    }

    /**
     * Legt den Wert der timestamps-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CapTimestampType }
     *     
     */
    public void setTimestamps(CapTimestampType value) {
        this.timestamps = value;
    }

    /**
     * Ruft den Wert der interruptions-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CapInterruptionsType }
     *     
     */
    public CapInterruptionsType getInterruptions() {
        return interruptions;
    }

    /**
     * Legt den Wert der interruptions-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CapInterruptionsType }
     *     
     */
    public void setInterruptions(CapInterruptionsType value) {
        this.interruptions = value;
    }

    /**
     * Ruft den Wert der requests-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CapRequestsType }
     *     
     */
    public CapRequestsType getRequests() {
        return requests;
    }

    /**
     * Legt den Wert der requests-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CapRequestsType }
     *     
     */
    public void setRequests(CapRequestsType value) {
        this.requests = value;
    }

}
