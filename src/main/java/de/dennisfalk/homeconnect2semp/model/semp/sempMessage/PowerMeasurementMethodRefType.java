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
 * <p>Java-Klasse für PowerMeasurementMethodRefType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="PowerMeasurementMethodRefType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Measurement"/>
 *     &lt;enumeration value="Estimation"/>
 *     &lt;enumeration value="None"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PowerMeasurementMethodRefType")
@XmlEnum
public enum PowerMeasurementMethodRefType {

    @XmlEnumValue("Measurement")
    MEASUREMENT("Measurement"),
    @XmlEnumValue("Estimation")
    ESTIMATION("Estimation"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    PowerMeasurementMethodRefType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PowerMeasurementMethodRefType fromValue(String v) {
        for (PowerMeasurementMethodRefType c: PowerMeasurementMethodRefType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
