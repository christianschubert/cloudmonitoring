//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.01 um 05:16:23 PM CEST 
//


package com.ibm.wsla;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r TSSelect complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="TSSelect"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}FunctionType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Operand" type="{http://www.ibm.com/wsla}OperandType"/&gt;
 *         &lt;element name="Element" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TSSelect", propOrder = {
    "operand",
    "element"
})
public class TSSelect
    extends FunctionType
{

    @XmlElement(name = "Operand", required = true)
    protected OperandType operand;
    @XmlElement(name = "Element")
    protected long element;

    /**
     * Ruft den Wert der operand-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link OperandType }
     *     
     */
    public OperandType getOperand() {
        return operand;
    }

    /**
     * Legt den Wert der operand-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link OperandType }
     *     
     */
    public void setOperand(OperandType value) {
        this.operand = value;
    }

    /**
     * Ruft den Wert der element-Eigenschaft ab.
     * 
     */
    public long getElement() {
        return element;
    }

    /**
     * Legt den Wert der element-Eigenschaft fest.
     * 
     */
    public void setElement(long value) {
        this.element = value;
    }

}
