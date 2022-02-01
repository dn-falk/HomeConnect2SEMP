//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.07.12 um 06:55:30 PM CEST 
//


package de.dennisfalk.homeconnect2semp.model.semp.sempMessage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeviceInfo" type="{http://www.sma.de/communication/schema/SEMP/v1}DeviceInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DeviceStatus" type="{http://www.sma.de/communication/schema/SEMP/v1}DeviceStatusType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PlanningRequest" type="{http://www.sma.de/communication/schema/SEMP/v1}PlanningRequestType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "deviceInfo",
    "deviceStatus",
    "planningRequest",
    "any"
})
@XmlRootElement(name = "Device2EM")
public class Device2EM {

    @XmlElement(name = "DeviceInfo")
    protected List<DeviceInfoType> deviceInfo;
    @XmlElement(name = "DeviceStatus")
    protected List<DeviceStatusType> deviceStatus;
    @XmlElement(name = "PlanningRequest")
    protected List<PlanningRequestType> planningRequest;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the deviceInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviceInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviceInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeviceInfoType }
     * 
     * 
     */
    public List<DeviceInfoType> getDeviceInfo() {
        if (deviceInfo == null) {
            deviceInfo = new ArrayList<DeviceInfoType>();
        }
        return this.deviceInfo;
    }

    /**
     * Gets the value of the deviceStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviceStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviceStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeviceStatusType }
     * 
     * 
     */
    public List<DeviceStatusType> getDeviceStatus() {
        if (deviceStatus == null) {
            deviceStatus = new ArrayList<DeviceStatusType>();
        }
        return this.deviceStatus;
    }

    /**
     * Gets the value of the planningRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the planningRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlanningRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlanningRequestType }
     * 
     * 
     */
    public List<PlanningRequestType> getPlanningRequest() {
        if (planningRequest == null) {
            planningRequest = new ArrayList<PlanningRequestType>();
        }
        return this.planningRequest;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

}
