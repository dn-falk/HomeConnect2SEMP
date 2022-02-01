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
 *         Contains operation and power consumption recommendations for a device.
 *       
 * 
 * <p>Java-Klasse für DeviceControlType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="DeviceControlType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeviceId" type="{http://www.sma.de/communication/schema/SEMP/v1}DeviceIdType"/>
 *         &lt;element name="On" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RecommendedPowerConsumption" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Timestamp" type="{http://www.sma.de/communication/schema/SEMP/v1}RelOrAbsTimeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceControlType", propOrder = {
    "deviceId",
    "on",
    "recommendedPowerConsumption",
    "timestamp"
})
public class DeviceControlType {

    @XmlElement(name = "DeviceId", required = true)
    protected String deviceId;
    @XmlElement(name = "On")
    protected boolean on;
    @XmlElement(name = "RecommendedPowerConsumption")
    protected Double recommendedPowerConsumption;
    @XmlElement(name = "Timestamp")
    protected long timestamp;

    /**
     * Ruft den Wert der deviceId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Legt den Wert der deviceId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceId(String value) {
        this.deviceId = value;
    }

    /**
     * Ruft den Wert der on-Eigenschaft ab.
     * 
     */
    public boolean isOn() {
        return on;
    }

    /**
     * Legt den Wert der on-Eigenschaft fest.
     * 
     */
    public void setOn(boolean value) {
        this.on = value;
    }

    /**
     * Ruft den Wert der recommendedPowerConsumption-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRecommendedPowerConsumption() {
        return recommendedPowerConsumption;
    }

    /**
     * Legt den Wert der recommendedPowerConsumption-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRecommendedPowerConsumption(Double value) {
        this.recommendedPowerConsumption = value;
    }

    /**
     * Ruft den Wert der timestamp-Eigenschaft ab.
     * 
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Legt den Wert der timestamp-Eigenschaft fest.
     * 
     */
    public void setTimestamp(long value) {
        this.timestamp = value;
    }

}
