//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.01 um 05:16:23 PM CEST 
//


package com.ibm.wsla;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für IntervalType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="IntervalType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Years" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Months" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Days" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Hours" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Minutes" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Seconds" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="MilliSeconds" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntervalType", propOrder = {
    "years",
    "months",
    "days",
    "hours",
    "minutes",
    "seconds",
    "milliSeconds"
})
public class IntervalType {

    @XmlElement(name = "Years")
    protected BigInteger years;
    @XmlElement(name = "Months")
    protected BigInteger months;
    @XmlElement(name = "Days")
    protected BigInteger days;
    @XmlElement(name = "Hours")
    protected BigInteger hours;
    @XmlElement(name = "Minutes")
    protected BigInteger minutes;
    @XmlElement(name = "Seconds")
    protected BigInteger seconds;
    @XmlElement(name = "MilliSeconds")
    protected BigInteger milliSeconds;

    /**
     * Ruft den Wert der years-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getYears() {
        return years;
    }

    /**
     * Legt den Wert der years-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setYears(BigInteger value) {
        this.years = value;
    }

    /**
     * Ruft den Wert der months-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMonths() {
        return months;
    }

    /**
     * Legt den Wert der months-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMonths(BigInteger value) {
        this.months = value;
    }

    /**
     * Ruft den Wert der days-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDays() {
        return days;
    }

    /**
     * Legt den Wert der days-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDays(BigInteger value) {
        this.days = value;
    }

    /**
     * Ruft den Wert der hours-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHours() {
        return hours;
    }

    /**
     * Legt den Wert der hours-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHours(BigInteger value) {
        this.hours = value;
    }

    /**
     * Ruft den Wert der minutes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinutes() {
        return minutes;
    }

    /**
     * Legt den Wert der minutes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinutes(BigInteger value) {
        this.minutes = value;
    }

    /**
     * Ruft den Wert der seconds-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSeconds() {
        return seconds;
    }

    /**
     * Legt den Wert der seconds-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSeconds(BigInteger value) {
        this.seconds = value;
    }

    /**
     * Ruft den Wert der milliSeconds-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMilliSeconds() {
        return milliSeconds;
    }

    /**
     * Legt den Wert der milliSeconds-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMilliSeconds(BigInteger value) {
        this.milliSeconds = value;
    }

}
