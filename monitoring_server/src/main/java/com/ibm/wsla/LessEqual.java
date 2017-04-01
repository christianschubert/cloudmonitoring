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
 * <p>Java-Klasse für LessEqual complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="LessEqual"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}PredicateType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SLAParameter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LessEqual", propOrder = {
    "slaParameter",
    "value"
})
public class LessEqual
    extends PredicateType
{

    @XmlElement(name = "SLAParameter", required = true)
    protected String slaParameter;
    @XmlElement(name = "Value")
    protected double value;

    /**
     * Ruft den Wert der slaParameter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSLAParameter() {
        return slaParameter;
    }

    /**
     * Legt den Wert der slaParameter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSLAParameter(String value) {
        this.slaParameter = value;
    }

    /**
     * Ruft den Wert der value-Eigenschaft ab.
     * 
     */
    public double getValue() {
        return value;
    }

    /**
     * Legt den Wert der value-Eigenschaft fest.
     * 
     */
    public void setValue(double value) {
        this.value = value;
    }

}
