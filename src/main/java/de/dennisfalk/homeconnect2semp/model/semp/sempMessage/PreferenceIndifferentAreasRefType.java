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
 * <p>Java-Klasse für PreferenceIndifferentAreasRefType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="PreferenceIndifferentAreasRefType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NoPreference"/>
 *     &lt;enumeration value="Early"/>
 *     &lt;enumeration value="Late"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PreferenceIndifferentAreasRefType")
@XmlEnum
public enum PreferenceIndifferentAreasRefType {

    @XmlEnumValue("NoPreference")
    NO_PREFERENCE("NoPreference"),
    @XmlEnumValue("Early")
    EARLY("Early"),
    @XmlEnumValue("Late")
    LATE("Late");
    private final String value;

    PreferenceIndifferentAreasRefType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PreferenceIndifferentAreasRefType fromValue(String v) {
        for (PreferenceIndifferentAreasRefType c: PreferenceIndifferentAreasRefType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
