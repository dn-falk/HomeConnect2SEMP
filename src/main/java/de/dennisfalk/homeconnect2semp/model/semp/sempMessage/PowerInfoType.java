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
 *         Information about a power consumption.
 *       
 * 
 * <p>Java-Klasse für PowerInfoType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="PowerInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AveragePower" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinPower" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MaxPower" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Timestamp" type="{http://www.sma.de/communication/schema/SEMP/v1}RelOrAbsTimeType"/>
 *         &lt;element name="AveragingInterval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PowerInfoType", propOrder = {
    "averagePower",
    "minPower",
    "maxPower",
    "timestamp",
    "averagingInterval"
})
public class PowerInfoType {

    @XmlElement(name = "AveragePower")
    protected int averagePower;
    @XmlElement(name = "MinPower")
    protected Integer minPower;
    @XmlElement(name = "MaxPower")
    protected Integer maxPower;
    @XmlElement(name = "Timestamp")
    protected long timestamp;
    @XmlElement(name = "AveragingInterval")
    protected int averagingInterval;

    /**
     * Ruft den Wert der averagePower-Eigenschaft ab.
     * 
     */
    public int getAveragePower() {
        return averagePower;
    }

    /**
     * Legt den Wert der averagePower-Eigenschaft fest.
     * 
     */
    public void setAveragePower(int value) {
        this.averagePower = value;
    }

    /**
     * Ruft den Wert der minPower-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinPower() {
        return minPower;
    }

    /**
     * Legt den Wert der minPower-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinPower(Integer value) {
        this.minPower = value;
    }

    /**
     * Ruft den Wert der maxPower-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxPower() {
        return maxPower;
    }

    /**
     * Legt den Wert der maxPower-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxPower(Integer value) {
        this.maxPower = value;
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

    /**
     * Ruft den Wert der averagingInterval-Eigenschaft ab.
     * 
     */
    public int getAveragingInterval() {
        return averagingInterval;
    }

    /**
     * Legt den Wert der averagingInterval-Eigenschaft fest.
     * 
     */
    public void setAveragingInterval(int value) {
        this.averagingInterval = value;
    }

}
