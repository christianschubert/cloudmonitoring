//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.01 um 05:16:23 PM CEST 
//


package com.ibm.wsla;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für FunctionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="FunctionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="resultType" type="{http://www.ibm.com/wsla}Type" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FunctionType")
@XmlSeeAlso({
    QConstructor.class,
    TSConstructor.class,
    Size.class,
    TSSelect.class,
    Plus.class,
    Minus.class,
    Divide.class,
    Times.class,
    Average.class,
    Mean.class,
    Median.class,
    Mode.class,
    Round.class,
    Max.class,
    Sum.class,
    ValueOccurs.class,
    PercentageGreaterThanThreshold.class,
    PercentageLessThanThreshold.class,
    NumberGreaterThanThreshold.class,
    NumberLessThanThreshold.class,
    RateOfChange.class,
    Span.class,
    QualifyMeasurementTimeSeries.class
})
public abstract class FunctionType {

    @XmlAttribute(name = "resultType")
    protected Type resultType;

    /**
     * Ruft den Wert der resultType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Type }
     *     
     */
    public Type getResultType() {
        return resultType;
    }

    /**
     * Legt den Wert der resultType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Type }
     *     
     */
    public void setResultType(Type value) {
        this.resultType = value;
    }

}
