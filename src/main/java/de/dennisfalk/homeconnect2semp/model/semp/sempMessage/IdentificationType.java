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
 *         General information for identifying the device.
 *       
 * 
 * <p>Java-Klasse für IdentificationType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="IdentificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeviceId" type="{http://www.sma.de/communication/schema/SEMP/v1}DeviceIdType"/>
 *         &lt;element name="DeviceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeviceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeviceSerial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeviceVendor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeviceURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificationType", propOrder = {
    "deviceId",
    "deviceName",
    "deviceType",
    "deviceSerial",
    "deviceVendor",
    "deviceURL"
})
public class IdentificationType {

    @XmlElement(name = "DeviceId", required = true)
    protected String deviceId;
    @XmlElement(name = "DeviceName", required = true)
    protected String deviceName;
    @XmlElement(name = "DeviceType", required = true)
    protected String deviceType;
    @XmlElement(name = "DeviceSerial", required = true)
    protected String deviceSerial;
    @XmlElement(name = "DeviceVendor", required = true)
    protected String deviceVendor;
    @XmlElement(name = "DeviceURL")
    protected String deviceURL;

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
     * Ruft den Wert der deviceName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Legt den Wert der deviceName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceName(String value) {
        this.deviceName = value;
    }

    /**
     * Ruft den Wert der deviceType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Legt den Wert der deviceType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceType(String value) {
        this.deviceType = value;
    }

    /**
     * Ruft den Wert der deviceSerial-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceSerial() {
        return deviceSerial;
    }

    /**
     * Legt den Wert der deviceSerial-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceSerial(String value) {
        this.deviceSerial = value;
    }

    /**
     * Ruft den Wert der deviceVendor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceVendor() {
        return deviceVendor;
    }

    /**
     * Legt den Wert der deviceVendor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceVendor(String value) {
        this.deviceVendor = value;
    }

    /**
     * Ruft den Wert der deviceURL-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceURL() {
        return deviceURL;
    }

    /**
     * Legt den Wert der deviceURL-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceURL(String value) {
        this.deviceURL = value;
    }

}
