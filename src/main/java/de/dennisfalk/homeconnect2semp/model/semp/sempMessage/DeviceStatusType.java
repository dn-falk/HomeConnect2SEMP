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
 *         A DeviceStatus encapsulates the status information of a device, i.e. all measurements and properties representing the current status of the device
 *       
 * 
 * <p>Java-Klasse für DeviceStatusType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="DeviceStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeviceId" type="{http://www.sma.de/communication/schema/SEMP/v1}DeviceIdType"/>
 *         &lt;element name="EMSignalsAccepted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PowerConsumption" type="{http://www.sma.de/communication/schema/SEMP/v1}PowerConsumptionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceStatusType", propOrder = {
    "deviceId",
    "emSignalsAccepted",
    "status",
    "errorCode",
    "powerConsumption"
})
public class DeviceStatusType {

    @XmlElement(name = "DeviceId", required = true)
    protected String deviceId;
    @XmlElement(name = "EMSignalsAccepted")
    protected boolean emSignalsAccepted;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "ErrorCode")
    protected Integer errorCode;
    @XmlElement(name = "PowerConsumption")
    protected PowerConsumptionType powerConsumption;

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
     * Ruft den Wert der emSignalsAccepted-Eigenschaft ab.
     * 
     */
    public boolean isEMSignalsAccepted() {
        return emSignalsAccepted;
    }

    /**
     * Legt den Wert der emSignalsAccepted-Eigenschaft fest.
     * 
     */
    public void setEMSignalsAccepted(boolean value) {
        this.emSignalsAccepted = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Ruft den Wert der errorCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * Legt den Wert der errorCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setErrorCode(Integer value) {
        this.errorCode = value;
    }

    /**
     * Ruft den Wert der powerConsumption-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PowerConsumptionType }
     *     
     */
    public PowerConsumptionType getPowerConsumption() {
        return powerConsumption;
    }

    /**
     * Legt den Wert der powerConsumption-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PowerConsumptionType }
     *     
     */
    public void setPowerConsumption(PowerConsumptionType value) {
        this.powerConsumption = value;
    }

}
