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
 * <p>Java-Klasse für QualifyMeasurementTimeSeries complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="QualifyMeasurementTimeSeries"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}FunctionType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BaseMetric" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ValidationMetric" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Value" type="{http://www.ibm.com/wsla}OperandType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualifyMeasurementTimeSeries", propOrder = {
    "baseMetric",
    "validationMetric",
    "value"
})
public class QualifyMeasurementTimeSeries
    extends FunctionType
{

    @XmlElement(name = "BaseMetric", required = true)
    protected String baseMetric;
    @XmlElement(name = "ValidationMetric", required = true)
    protected String validationMetric;
    @XmlElement(name = "Value", required = true)
    protected OperandType value;

    /**
     * Ruft den Wert der baseMetric-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseMetric() {
        return baseMetric;
    }

    /**
     * Legt den Wert der baseMetric-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseMetric(String value) {
        this.baseMetric = value;
    }

    /**
     * Ruft den Wert der validationMetric-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationMetric() {
        return validationMetric;
    }

    /**
     * Legt den Wert der validationMetric-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationMetric(String value) {
        this.validationMetric = value;
    }

    /**
     * Ruft den Wert der value-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link OperandType }
     *     
     */
    public OperandType getValue() {
        return value;
    }

    /**
     * Legt den Wert der value-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link OperandType }
     *     
     */
    public void setValue(OperandType value) {
        this.value = value;
    }

}
