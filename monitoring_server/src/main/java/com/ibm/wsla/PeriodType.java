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
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für PeriodType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="PeriodType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="Start" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *           &lt;element name="End" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="ConditionTime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="ConditionMonthOfYearMask" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="ConditionDayOfMonthMask" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="ConditionDayOfWeekMask" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="ConditionTimeOfDayMask" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="ConditionTimeZone" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="ConditionLocalOrUtcTime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PeriodType", propOrder = {
    "start",
    "end",
    "conditionTime",
    "conditionMonthOfYearMask",
    "conditionDayOfMonthMask",
    "conditionDayOfWeekMask",
    "conditionTimeOfDayMask",
    "conditionTimeZone",
    "conditionLocalOrUtcTime"
})
public class PeriodType {

    @XmlElement(name = "Start")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar start;
    @XmlElement(name = "End")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar end;
    @XmlElement(name = "ConditionTime")
    protected String conditionTime;
    @XmlElement(name = "ConditionMonthOfYearMask")
    protected String conditionMonthOfYearMask;
    @XmlElement(name = "ConditionDayOfMonthMask")
    protected String conditionDayOfMonthMask;
    @XmlElement(name = "ConditionDayOfWeekMask")
    protected String conditionDayOfWeekMask;
    @XmlElement(name = "ConditionTimeOfDayMask")
    protected String conditionTimeOfDayMask;
    @XmlElement(name = "ConditionTimeZone")
    protected String conditionTimeZone;
    @XmlElement(name = "ConditionLocalOrUtcTime")
    protected String conditionLocalOrUtcTime;

    /**
     * Ruft den Wert der start-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStart() {
        return start;
    }

    /**
     * Legt den Wert der start-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStart(XMLGregorianCalendar value) {
        this.start = value;
    }

    /**
     * Ruft den Wert der end-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnd() {
        return end;
    }

    /**
     * Legt den Wert der end-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnd(XMLGregorianCalendar value) {
        this.end = value;
    }

    /**
     * Ruft den Wert der conditionTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionTime() {
        return conditionTime;
    }

    /**
     * Legt den Wert der conditionTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionTime(String value) {
        this.conditionTime = value;
    }

    /**
     * Ruft den Wert der conditionMonthOfYearMask-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionMonthOfYearMask() {
        return conditionMonthOfYearMask;
    }

    /**
     * Legt den Wert der conditionMonthOfYearMask-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionMonthOfYearMask(String value) {
        this.conditionMonthOfYearMask = value;
    }

    /**
     * Ruft den Wert der conditionDayOfMonthMask-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionDayOfMonthMask() {
        return conditionDayOfMonthMask;
    }

    /**
     * Legt den Wert der conditionDayOfMonthMask-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionDayOfMonthMask(String value) {
        this.conditionDayOfMonthMask = value;
    }

    /**
     * Ruft den Wert der conditionDayOfWeekMask-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionDayOfWeekMask() {
        return conditionDayOfWeekMask;
    }

    /**
     * Legt den Wert der conditionDayOfWeekMask-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionDayOfWeekMask(String value) {
        this.conditionDayOfWeekMask = value;
    }

    /**
     * Ruft den Wert der conditionTimeOfDayMask-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionTimeOfDayMask() {
        return conditionTimeOfDayMask;
    }

    /**
     * Legt den Wert der conditionTimeOfDayMask-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionTimeOfDayMask(String value) {
        this.conditionTimeOfDayMask = value;
    }

    /**
     * Ruft den Wert der conditionTimeZone-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionTimeZone() {
        return conditionTimeZone;
    }

    /**
     * Legt den Wert der conditionTimeZone-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionTimeZone(String value) {
        this.conditionTimeZone = value;
    }

    /**
     * Ruft den Wert der conditionLocalOrUtcTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionLocalOrUtcTime() {
        return conditionLocalOrUtcTime;
    }

    /**
     * Legt den Wert der conditionLocalOrUtcTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionLocalOrUtcTime(String value) {
        this.conditionLocalOrUtcTime = value;
    }

}
