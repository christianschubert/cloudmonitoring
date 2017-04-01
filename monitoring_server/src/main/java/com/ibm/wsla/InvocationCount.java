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
 * <p>Java-Klasse für InvocationCount complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="InvocationCount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}MeasurementDirectiveType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CounterURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvocationCount", propOrder = {
    "counterURI"
})
public class InvocationCount
    extends MeasurementDirectiveType
{

    @XmlElement(name = "CounterURI")
    @XmlSchemaType(name = "anyURI")
    protected String counterURI;

    /**
     * Ruft den Wert der counterURI-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounterURI() {
        return counterURI;
    }

    /**
     * Legt den Wert der counterURI-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounterURI(String value) {
        this.counterURI = value;
    }

}
