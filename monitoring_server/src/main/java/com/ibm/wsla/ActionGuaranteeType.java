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
 * <p>Java-Klasse für ActionGuaranteeType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ActionGuaranteeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Obliged" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *         &lt;element name="Expression" type="{http://www.ibm.com/wsla}LogicExpressionType"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="EvaluationEvent" type="{http://www.ibm.com/wsla}EvaluationEventType"/&gt;
 *           &lt;element name="Schedule" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}QualifiedAction" maxOccurs="unbounded"/&gt;
 *         &lt;element name="ExecutionModality" type="{http://www.ibm.com/wsla}ExecutionModalityType"/&gt;
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
@XmlType(name = "ActionGuaranteeType", propOrder = {
    "obliged",
    "expression",
    "evaluationEvent",
    "schedule",
    "qualifiedAction",
    "executionModality"
})
public class ActionGuaranteeType {

    @XmlElement(name = "Obliged", required = true)
    protected List<String> obliged;
    @XmlElement(name = "Expression", required = true)
    protected LogicExpressionType expression;
    @XmlElement(name = "EvaluationEvent")
    @XmlSchemaType(name = "string")
    protected EvaluationEventType evaluationEvent;
    @XmlElement(name = "Schedule")
    protected String schedule;
    @XmlElement(name = "QualifiedAction", required = true)
    protected List<QualifiedActionType> qualifiedAction;
    @XmlElement(name = "ExecutionModality", required = true)
    @XmlSchemaType(name = "string")
    protected ExecutionModalityType executionModality;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the obliged property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the obliged property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObliged().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getObliged() {
        if (obliged == null) {
            obliged = new ArrayList<String>();
        }
        return this.obliged;
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
     * Gets the value of the qualifiedAction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualifiedAction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualifiedAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualifiedActionType }
     * 
     * 
     */
    public List<QualifiedActionType> getQualifiedAction() {
        if (qualifiedAction == null) {
            qualifiedAction = new ArrayList<QualifiedActionType>();
        }
        return this.qualifiedAction;
    }

    /**
     * Ruft den Wert der executionModality-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ExecutionModalityType }
     *     
     */
    public ExecutionModalityType getExecutionModality() {
        return executionModality;
    }

    /**
     * Legt den Wert der executionModality-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecutionModalityType }
     *     
     */
    public void setExecutionModality(ExecutionModalityType value) {
        this.executionModality = value;
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
