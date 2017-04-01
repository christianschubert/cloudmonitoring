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
 * <p>Java-Klasse für UnaryLogicOperatorType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="UnaryLogicOperatorType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Expresssion" type="{http://www.ibm.com/wsla}LogicExpressionType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnaryLogicOperatorType", propOrder = {
    "expresssion"
})
public class UnaryLogicOperatorType {

    @XmlElement(name = "Expresssion", required = true)
    protected LogicExpressionType expresssion;

    /**
     * Ruft den Wert der expresssion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LogicExpressionType }
     *     
     */
    public LogicExpressionType getExpresssion() {
        return expresssion;
    }

    /**
     * Legt den Wert der expresssion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicExpressionType }
     *     
     */
    public void setExpresssion(LogicExpressionType value) {
        this.expresssion = value;
    }

}
