//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.01 um 05:16:23 PM CEST 
//


package com.ibm.wsla;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="Type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Q"/&gt;
 *     &lt;enumeration value="TS"/&gt;
 *     &lt;enumeration value="integer"/&gt;
 *     &lt;enumeration value="float"/&gt;
 *     &lt;enumeration value="double"/&gt;
 *     &lt;enumeration value="long"/&gt;
 *     &lt;enumeration value="byte"/&gt;
 *     &lt;enumeration value="boolean"/&gt;
 *     &lt;enumeration value="string"/&gt;
 *     &lt;enumeration value="time"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Type")
@XmlEnum
public enum Type {

    Q("Q"),
    TS("TS"),
    @XmlEnumValue("integer")
    INTEGER("integer"),
    @XmlEnumValue("float")
    FLOAT("float"),
    @XmlEnumValue("double")
    DOUBLE("double"),
    @XmlEnumValue("long")
    LONG("long"),
    @XmlEnumValue("byte")
    BYTE("byte"),
    @XmlEnumValue("boolean")
    BOOLEAN("boolean"),
    @XmlEnumValue("string")
    STRING("string"),
    @XmlEnumValue("time")
    TIME("time");
    private final String value;

    Type(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Type fromValue(String v) {
        for (Type c: Type.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
