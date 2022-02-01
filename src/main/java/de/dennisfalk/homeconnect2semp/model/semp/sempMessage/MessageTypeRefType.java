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
 * <p>Java-Klasse für MessageTypeRefType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="MessageTypeRefType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="InvalidTimeframe"/>
 *     &lt;enumeration value="DeviceControlIgnored"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MessageTypeRefType")
@XmlEnum
public enum MessageTypeRefType {

    @XmlEnumValue("InvalidTimeframe")
    INVALID_TIMEFRAME("InvalidTimeframe"),
    @XmlEnumValue("DeviceControlIgnored")
    DEVICE_CONTROL_IGNORED("DeviceControlIgnored"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    MessageTypeRefType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MessageTypeRefType fromValue(String v) {
        for (MessageTypeRefType c: MessageTypeRefType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
