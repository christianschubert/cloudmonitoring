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
 * <p>Java-Klasse für ExecutionModalityType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="ExecutionModalityType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Always"/&gt;
 *     &lt;enumeration value="OnEnteringCondition"/&gt;
 *     &lt;enumeration value="OnEnteringAndOnLeavingCondition"/&gt;
 *     &lt;enumeration value="OnEveryEvaluation"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ExecutionModalityType")
@XmlEnum
public enum ExecutionModalityType {

    @XmlEnumValue("Always")
    ALWAYS("Always"),
    @XmlEnumValue("OnEnteringCondition")
    ON_ENTERING_CONDITION("OnEnteringCondition"),
    @XmlEnumValue("OnEnteringAndOnLeavingCondition")
    ON_ENTERING_AND_ON_LEAVING_CONDITION("OnEnteringAndOnLeavingCondition"),
    @XmlEnumValue("OnEveryEvaluation")
    ON_EVERY_EVALUATION("OnEveryEvaluation");
    private final String value;

    ExecutionModalityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExecutionModalityType fromValue(String v) {
        for (ExecutionModalityType c: ExecutionModalityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
