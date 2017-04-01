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
 * <p>Java-Klasse für LogicExpressionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="LogicExpressionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="Predicate" type="{http://www.ibm.com/wsla}PredicateType"/&gt;
 *           &lt;element name="And" type="{http://www.ibm.com/wsla}BinaryLogicOperatorType"/&gt;
 *           &lt;element name="Or" type="{http://www.ibm.com/wsla}BinaryLogicOperatorType"/&gt;
 *           &lt;element name="Not" type="{http://www.ibm.com/wsla}UnaryLogicOperatorType"/&gt;
 *           &lt;element name="Implies" type="{http://www.ibm.com/wsla}BinaryLogicOperatorType"/&gt;
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
@XmlType(name = "LogicExpressionType", propOrder = {
    "predicate",
    "and",
    "or",
    "not",
    "implies"
})
public class LogicExpressionType {

    @XmlElement(name = "Predicate")
    protected PredicateType predicate;
    @XmlElement(name = "And")
    protected BinaryLogicOperatorType and;
    @XmlElement(name = "Or")
    protected BinaryLogicOperatorType or;
    @XmlElement(name = "Not")
    protected UnaryLogicOperatorType not;
    @XmlElement(name = "Implies")
    protected BinaryLogicOperatorType implies;

    /**
     * Ruft den Wert der predicate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PredicateType }
     *     
     */
    public PredicateType getPredicate() {
        return predicate;
    }

    /**
     * Legt den Wert der predicate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PredicateType }
     *     
     */
    public void setPredicate(PredicateType value) {
        this.predicate = value;
    }

    /**
     * Ruft den Wert der and-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BinaryLogicOperatorType }
     *     
     */
    public BinaryLogicOperatorType getAnd() {
        return and;
    }

    /**
     * Legt den Wert der and-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BinaryLogicOperatorType }
     *     
     */
    public void setAnd(BinaryLogicOperatorType value) {
        this.and = value;
    }

    /**
     * Ruft den Wert der or-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BinaryLogicOperatorType }
     *     
     */
    public BinaryLogicOperatorType getOr() {
        return or;
    }

    /**
     * Legt den Wert der or-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BinaryLogicOperatorType }
     *     
     */
    public void setOr(BinaryLogicOperatorType value) {
        this.or = value;
    }

    /**
     * Ruft den Wert der not-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link UnaryLogicOperatorType }
     *     
     */
    public UnaryLogicOperatorType getNot() {
        return not;
    }

    /**
     * Legt den Wert der not-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link UnaryLogicOperatorType }
     *     
     */
    public void setNot(UnaryLogicOperatorType value) {
        this.not = value;
    }

    /**
     * Ruft den Wert der implies-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BinaryLogicOperatorType }
     *     
     */
    public BinaryLogicOperatorType getImplies() {
        return implies;
    }

    /**
     * Legt den Wert der implies-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BinaryLogicOperatorType }
     *     
     */
    public void setImplies(BinaryLogicOperatorType value) {
        this.implies = value;
    }

}
