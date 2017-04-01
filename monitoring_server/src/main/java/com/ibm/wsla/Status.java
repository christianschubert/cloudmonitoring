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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Status complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Status"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}MeasurementDirectiveType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MeasurementURI" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="TimeOut" type="{http://www.ibm.com/wsla}IntervalType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Status", propOrder = {
    "measurementURI",
    "timeOut"
})
public class Status
    extends MeasurementDirectiveType
{

    @XmlElement(name = "MeasurementURI", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String measurementURI;
    @XmlElement(name = "TimeOut", required = true)
    protected IntervalType timeOut;

    /**
     * Ruft den Wert der measurementURI-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasurementURI() {
        return measurementURI;
    }

    /**
     * Legt den Wert der measurementURI-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasurementURI(String value) {
        this.measurementURI = value;
    }

    /**
     * Ruft den Wert der timeOut-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link IntervalType }
     *     
     */
    public IntervalType getTimeOut() {
        return timeOut;
    }

    /**
     * Legt den Wert der timeOut-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link IntervalType }
     *     
     */
    public void setTimeOut(IntervalType value) {
        this.timeOut = value;
    }

}
