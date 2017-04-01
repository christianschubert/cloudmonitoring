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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für NewValue complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="NewValue"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}PredicateType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="SLAParameter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SLAParameterList" type="{http://www.ibm.com/wsla}StringList"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NewValue", propOrder = {
    "slaParameter",
    "slaParameterList"
})
public class NewValue
    extends PredicateType
{

    @XmlElement(name = "SLAParameter")
    protected String slaParameter;
    @XmlList
    @XmlElement(name = "SLAParameterList")
    @XmlSchemaType(name = "anySimpleType")
    protected List<String> slaParameterList;

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
     * Gets the value of the slaParameterList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the slaParameterList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSLAParameterList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSLAParameterList() {
        if (slaParameterList == null) {
            slaParameterList = new ArrayList<String>();
        }
        return this.slaParameterList;
    }

}
