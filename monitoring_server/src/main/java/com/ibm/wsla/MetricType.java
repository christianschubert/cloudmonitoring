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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MetricType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MetricType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Source" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MetricURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="MeasurementDirective" type="{http://www.ibm.com/wsla}MeasurementDirectiveType"/&gt;
 *           &lt;element name="Function" type="{http://www.ibm.com/wsla}FunctionType"/&gt;
 *           &lt;element name="MeasurementDirectiveVariable" type="{http://www.ibm.com/wsla}MDVariableType"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="type" type="{http://www.ibm.com/wsla}Type" /&gt;
 *       &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="counter" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetricType", propOrder = {
    "source",
    "metricURI",
    "measurementDirective",
    "function",
    "measurementDirectiveVariable"
})
public class MetricType {

    @XmlElement(name = "Source", required = true)
    protected String source;
    @XmlElement(name = "MetricURI")
    @XmlSchemaType(name = "anyURI")
    protected String metricURI;
    @XmlElement(name = "MeasurementDirective")
    protected MeasurementDirectiveType measurementDirective;
    @XmlElement(name = "Function")
    protected FunctionType function;
    @XmlElement(name = "MeasurementDirectiveVariable")
    protected MDVariableType measurementDirectiveVariable;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "type")
    protected Type type;
    @XmlAttribute(name = "unit")
    protected String unit;
    @XmlAttribute(name = "counter")
    protected Boolean counter;

    /**
     * Ruft den Wert der source-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Legt den Wert der source-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Ruft den Wert der metricURI-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetricURI() {
        return metricURI;
    }

    /**
     * Legt den Wert der metricURI-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetricURI(String value) {
        this.metricURI = value;
    }

    /**
     * Ruft den Wert der measurementDirective-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MeasurementDirectiveType }
     *     
     */
    public MeasurementDirectiveType getMeasurementDirective() {
        return measurementDirective;
    }

    /**
     * Legt den Wert der measurementDirective-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasurementDirectiveType }
     *     
     */
    public void setMeasurementDirective(MeasurementDirectiveType value) {
        this.measurementDirective = value;
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
     * Ruft den Wert der measurementDirectiveVariable-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MDVariableType }
     *     
     */
    public MDVariableType getMeasurementDirectiveVariable() {
        return measurementDirectiveVariable;
    }

    /**
     * Legt den Wert der measurementDirectiveVariable-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MDVariableType }
     *     
     */
    public void setMeasurementDirectiveVariable(MDVariableType value) {
        this.measurementDirectiveVariable = value;
    }

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Type }
     *     
     */
    public Type getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Type }
     *     
     */
    public void setType(Type value) {
        this.type = value;
    }

    /**
     * Ruft den Wert der unit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Legt den Wert der unit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Ruft den Wert der counter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCounter() {
        return counter;
    }

    /**
     * Legt den Wert der counter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCounter(Boolean value) {
        this.counter = value;
    }

}
