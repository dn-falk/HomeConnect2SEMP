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
 *         Information on the characteristics of the device.
 *       
 * 
 * <p>Java-Klasse für CharacteristicsType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CharacteristicsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaxPowerConsumption" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinOnTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MinOffTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CharacteristicsType", propOrder = {
    "maxPowerConsumption",
    "minOnTime",
    "minOffTime"
})
public class CharacteristicsType {

    @XmlElement(name = "MaxPowerConsumption")
    protected int maxPowerConsumption;
    @XmlElement(name = "MinOnTime")
    protected Integer minOnTime;
    @XmlElement(name = "MinOffTime")
    protected Integer minOffTime;

    /**
     * Ruft den Wert der maxPowerConsumption-Eigenschaft ab.
     * 
     */
    public int getMaxPowerConsumption() {
        return maxPowerConsumption;
    }

    /**
     * Legt den Wert der maxPowerConsumption-Eigenschaft fest.
     * 
     */
    public void setMaxPowerConsumption(int value) {
        this.maxPowerConsumption = value;
    }

    /**
     * Ruft den Wert der minOnTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinOnTime() {
        return minOnTime;
    }

    /**
     * Legt den Wert der minOnTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinOnTime(Integer value) {
        this.minOnTime = value;
    }

    /**
     * Ruft den Wert der minOffTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinOffTime() {
        return minOffTime;
    }

    /**
     * Legt den Wert der minOffTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinOffTime(Integer value) {
        this.minOffTime = value;
    }

}
