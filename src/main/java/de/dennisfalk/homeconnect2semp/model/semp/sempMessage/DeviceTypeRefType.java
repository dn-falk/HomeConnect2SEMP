//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.07.12 um 06:55:30 PM CEST 
//


package de.dennisfalk.homeconnect2semp.model.semp.sempMessage;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für DeviceTypeRefType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="DeviceTypeRefType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AirConditioning"/>
 *     &lt;enumeration value="Charger"/>
 *     &lt;enumeration value="DishWasher"/>
 *     &lt;enumeration value="Dryer"/>
 *     &lt;enumeration value="ElectricVehicle"/>
 *     &lt;enumeration value="EVCharger"/>
 *     &lt;enumeration value="Freezer"/>
 *     &lt;enumeration value="Fridge"/>
 *     &lt;enumeration value="Heater"/>
 *     &lt;enumeration value="HeatPump"/>
 *     &lt;enumeration value="Motor"/>
 *     &lt;enumeration value="Pump"/>
 *     &lt;enumeration value="WashingMachine"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DeviceTypeRefType")
@XmlEnum
public enum DeviceTypeRefType {

    @XmlEnumValue("AirConditioning")
    AIR_CONDITIONING("AirConditioning"),
    @XmlEnumValue("Charger")
    CHARGER("Charger"),
    @XmlEnumValue("DishWasher")
    DISH_WASHER("DishWasher"),
    @XmlEnumValue("Dryer")
    DRYER("Dryer"),
    @XmlEnumValue("ElectricVehicle")
    ELECTRIC_VEHICLE("ElectricVehicle"),
    @XmlEnumValue("EVCharger")
    EV_CHARGER("EVCharger"),
    @XmlEnumValue("Freezer")
    FREEZER("Freezer"),
    @XmlEnumValue("Fridge")
    FRIDGE("Fridge"),
    @XmlEnumValue("Heater")
    HEATER("Heater"),
    @XmlEnumValue("HeatPump")
    HEAT_PUMP("HeatPump"),
    @XmlEnumValue("Motor")
    MOTOR("Motor"),
    @XmlEnumValue("Pump")
    PUMP("Pump"),
    @XmlEnumValue("WashingMachine")
    WASHING_MACHINE("WashingMachine"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    DeviceTypeRefType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DeviceTypeRefType fromValue(String v) {
        for (DeviceTypeRefType c: DeviceTypeRefType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
