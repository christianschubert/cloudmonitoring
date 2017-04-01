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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MetricMacroExpansionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MetricMacroExpansionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MeasurementDirectiveAssignment" type="{http://www.ibm.com/wsla}MDAssignmentType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="macroName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="expansionName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetricMacroExpansionType", propOrder = {
    "measurementDirectiveAssignment"
})
public class MetricMacroExpansionType {

    @XmlElement(name = "MeasurementDirectiveAssignment")
    protected List<MDAssignmentType> measurementDirectiveAssignment;
    @XmlAttribute(name = "macroName")
    protected String macroName;
    @XmlAttribute(name = "expansionName")
    protected String expansionName;

    /**
     * Gets the value of the measurementDirectiveAssignment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the measurementDirectiveAssignment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeasurementDirectiveAssignment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MDAssignmentType }
     * 
     * 
     */
    public List<MDAssignmentType> getMeasurementDirectiveAssignment() {
        if (measurementDirectiveAssignment == null) {
            measurementDirectiveAssignment = new ArrayList<MDAssignmentType>();
        }
        return this.measurementDirectiveAssignment;
    }

    /**
     * Ruft den Wert der macroName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMacroName() {
        return macroName;
    }

    /**
     * Legt den Wert der macroName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMacroName(String value) {
        this.macroName = value;
    }

    /**
     * Ruft den Wert der expansionName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpansionName() {
        return expansionName;
    }

    /**
     * Legt den Wert der expansionName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpansionName(String value) {
        this.expansionName = value;
    }

}
