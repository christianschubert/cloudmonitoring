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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MDAssignmentType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MDAssignmentType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MeasurementDirective" type="{http://www.ibm.com/wsla}MeasurementDirectiveType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="metricName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MDAssignmentType", propOrder = {
    "measurementDirective"
})
public class MDAssignmentType {

    @XmlElement(name = "MeasurementDirective", required = true)
    protected MeasurementDirectiveType measurementDirective;
    @XmlAttribute(name = "metricName")
    protected String metricName;

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
     * Ruft den Wert der metricName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetricName() {
        return metricName;
    }

    /**
     * Legt den Wert der metricName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetricName(String value) {
        this.metricName = value;
    }

}
