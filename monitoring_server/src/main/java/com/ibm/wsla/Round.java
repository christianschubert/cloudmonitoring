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
 * <p>Java-Klasse für Round complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Round"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}FunctionType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="Metric" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="Function" type="{http://www.ibm.com/wsla}FunctionType"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="Digits" type="{http://www.ibm.com/wsla}OperandType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Round", propOrder = {
    "metric",
    "function",
    "digits"
})
public class Round
    extends FunctionType
{

    @XmlElement(name = "Metric")
    protected String metric;
    @XmlElement(name = "Function")
    protected FunctionType function;
    @XmlElement(name = "Digits", required = true)
    protected OperandType digits;

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
     * Ruft den Wert der digits-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link OperandType }
     *     
     */
    public OperandType getDigits() {
        return digits;
    }

    /**
     * Legt den Wert der digits-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link OperandType }
     *     
     */
    public void setDigits(OperandType value) {
        this.digits = value;
    }

}
