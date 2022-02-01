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
 *         Timeframe as part of a planning request, that allows specification of the energy needs of a device.
 *       
 * 
 * <p>Java-Klasse für TimeframeType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="TimeframeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeviceId" type="{http://www.sma.de/communication/schema/SEMP/v1}DeviceIdType"/>
 *         &lt;element name="EarliestStart" type="{http://www.sma.de/communication/schema/SEMP/v1}RelOrAbsTimeType"/>
 *         &lt;element name="LatestEnd" type="{http://www.sma.de/communication/schema/SEMP/v1}RelOrAbsTimeType"/>
 *         &lt;element name="MinRunningTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxRunningTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeframeType", propOrder = {
    "deviceId",
    "earliestStart",
    "latestEnd",
    "minRunningTime",
    "maxRunningTime"
})
public class TimeframeType {

    @XmlElement(name = "DeviceId", required = true)
    protected String deviceId;
    @XmlElement(name = "EarliestStart")
    protected long earliestStart;
    @XmlElement(name = "LatestEnd")
    protected long latestEnd;
    @XmlElement(name = "MinRunningTime")
    protected int minRunningTime;
    @XmlElement(name = "MaxRunningTime")
    protected int maxRunningTime;

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
     * Ruft den Wert der earliestStart-Eigenschaft ab.
     * 
     */
    public long getEarliestStart() {
        return earliestStart;
    }

    /**
     * Legt den Wert der earliestStart-Eigenschaft fest.
     * 
     */
    public void setEarliestStart(long value) {
        this.earliestStart = value;
    }

    /**
     * Ruft den Wert der latestEnd-Eigenschaft ab.
     * 
     */
    public long getLatestEnd() {
        return latestEnd;
    }

    /**
     * Legt den Wert der latestEnd-Eigenschaft fest.
     * 
     */
    public void setLatestEnd(long value) {
        this.latestEnd = value;
    }

    /**
     * Ruft den Wert der minRunningTime-Eigenschaft ab.
     * 
     */
    public int getMinRunningTime() {
        return minRunningTime;
    }

    /**
     * Legt den Wert der minRunningTime-Eigenschaft fest.
     * 
     */
    public void setMinRunningTime(int value) {
        this.minRunningTime = value;
    }

    /**
     * Ruft den Wert der maxRunningTime-Eigenschaft ab.
     * 
     */
    public int getMaxRunningTime() {
        return maxRunningTime;
    }

    /**
     * Legt den Wert der maxRunningTime-Eigenschaft fest.
     * 
     */
    public void setMaxRunningTime(int value) {
        this.maxRunningTime = value;
    }

}
