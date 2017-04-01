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
 * <p>Java-Klasse für RoleType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="RoleType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MeasurementService"/&gt;
 *     &lt;enumeration value="ManagementService"/&gt;
 *     &lt;enumeration value="ConditionEvaluationService"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RoleType")
@XmlEnum
public enum RoleType {

    @XmlEnumValue("MeasurementService")
    MEASUREMENT_SERVICE("MeasurementService"),
    @XmlEnumValue("ManagementService")
    MANAGEMENT_SERVICE("ManagementService"),
    @XmlEnumValue("ConditionEvaluationService")
    CONDITION_EVALUATION_SERVICE("ConditionEvaluationService");
    private final String value;

    RoleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RoleType fromValue(String v) {
        for (RoleType c: RoleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
