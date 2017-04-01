//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.01 um 05:16:23 PM CEST 
//


package com.ibm.wsla;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ServiceLevelObjectiveType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ServiceLevelObjectiveType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Obliged" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Validity" type="{http://www.ibm.com/wsla}PeriodType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="Expression" type="{http://www.ibm.com/wsla}LogicExpressionType"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="EvaluationEvent" type="{http://www.ibm.com/wsla}EvaluationEventType"/&gt;
 *           &lt;element name="Schedule" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLevelObjectiveType", propOrder = {
    "obliged",
    "validity",
    "expression",
    "evaluationEvent",
    "schedule"
})
public class ServiceLevelObjectiveType {

    @XmlElement(name = "Obliged", required = true)
    protected String obliged;
    @XmlElement(name = "Validity", required = true)
    protected List<PeriodType> validity;
    @XmlElement(name = "Expression", required = true)
    protected LogicExpressionType expression;
    @XmlElement(name = "EvaluationEvent")
    @XmlSchemaType(name = "string")
    protected EvaluationEventType evaluationEvent;
    @XmlElement(name = "Schedule")
    protected String schedule;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Ruft den Wert der obliged-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObliged() {
        return obliged;
    }

    /**
     * Legt den Wert der obliged-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObliged(String value) {
        this.obliged = value;
    }

    /**
     * Gets the value of the validity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the validity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValidity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PeriodType }
     * 
     * 
     */
    public List<PeriodType> getValidity() {
        if (validity == null) {
            validity = new ArrayList<PeriodType>();
        }
        return this.validity;
    }

    /**
     * Ruft den Wert der expression-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LogicExpressionType }
     *     
     */
    public LogicExpressionType getExpression() {
        return expression;
    }

    /**
     * Legt den Wert der expression-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicExpressionType }
     *     
     */
    public void setExpression(LogicExpressionType value) {
        this.expression = value;
    }

    /**
     * Ruft den Wert der evaluationEvent-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EvaluationEventType }
     *     
     */
    public EvaluationEventType getEvaluationEvent() {
        return evaluationEvent;
    }

    /**
     * Legt den Wert der evaluationEvent-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EvaluationEventType }
     *     
     */
    public void setEvaluationEvent(EvaluationEventType value) {
        this.evaluationEvent = value;
    }

    /**
     * Ruft den Wert der schedule-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Legt den Wert der schedule-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchedule(String value) {
        this.schedule = value;
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

}
