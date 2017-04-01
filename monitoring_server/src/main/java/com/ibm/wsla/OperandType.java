//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.01 um 05:16:23 PM CEST 
//


package com.ibm.wsla;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für OperandType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="OperandType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="Metric" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="Function" type="{http://www.ibm.com/wsla}FunctionType"/&gt;
 *           &lt;element name="LongScalar" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *           &lt;element name="FloatScalar" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *           &lt;element name="StringScalar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="Constant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperandType", propOrder = {
    "metric",
    "function",
    "longScalar",
    "floatScalar",
    "stringScalar",
    "constant"
})
public class OperandType {

    @XmlElement(name = "Metric")
    protected String metric;
    @XmlElement(name = "Function")
    protected FunctionType function;
    @XmlElement(name = "LongScalar")
    protected Long longScalar;
    @XmlElement(name = "FloatScalar")
    protected Float floatScalar;
    @XmlElement(name = "StringScalar")
    protected String stringScalar;
    @XmlElement(name = "Constant")
    protected String constant;

    /**
     * Ruft den Wert der metric-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetric() {
        return metric;
    }

    /**
     * Legt den Wert der metric-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetric(String value) {
        this.metric = value;
    }

    /**
     * Ruft den Wert der function-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link FunctionType }
     *     
     */
    public FunctionType getFunction() {
        return function;
    }

    /**
     * Legt den Wert der function-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link FunctionType }
     *     
     */
    public void setFunction(FunctionType value) {
        this.function = value;
    }

    /**
     * Ruft den Wert der longScalar-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLongScalar() {
        return longScalar;
    }

    /**
     * Legt den Wert der longScalar-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLongScalar(Long value) {
        this.longScalar = value;
    }

    /**
     * Ruft den Wert der floatScalar-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFloatScalar() {
        return floatScalar;
    }

    /**
     * Legt den Wert der floatScalar-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFloatScalar(Float value) {
        this.floatScalar = value;
    }

    /**
     * Ruft den Wert der stringScalar-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringScalar() {
        return stringScalar;
    }

    /**
     * Legt den Wert der stringScalar-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringScalar(String value) {
        this.stringScalar = value;
    }

    /**
     * Ruft den Wert der constant-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConstant() {
        return constant;
    }

    /**
     * Legt den Wert der constant-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConstant(String value) {
        this.constant = value;
    }

}
