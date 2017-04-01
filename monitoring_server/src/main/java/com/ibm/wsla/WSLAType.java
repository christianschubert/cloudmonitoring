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
 * <p>Java-Klasse für WSLAType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WSLAType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}Parties"/&gt;
 *         &lt;element name="ServiceDefinition" type="{http://www.ibm.com/wsla}ServiceDefinitionType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="Obligations" type="{http://www.ibm.com/wsla}ObligationsType"/&gt;
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
@XmlType(name = "WSLAType", propOrder = {
    "parties",
    "serviceDefinition",
    "obligations"
})
public class WSLAType {

    @XmlElement(name = "Parties", required = true)
    protected PartiesType parties;
    @XmlElement(name = "ServiceDefinition", required = true)
    protected List<ServiceDefinitionType> serviceDefinition;
    @XmlElement(name = "Obligations", required = true)
    protected ObligationsType obligations;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Ruft den Wert der parties-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PartiesType }
     *     
     */
    public PartiesType getParties() {
        return parties;
    }

    /**
     * Legt den Wert der parties-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PartiesType }
     *     
     */
    public void setParties(PartiesType value) {
        this.parties = value;
    }

    /**
     * Gets the value of the serviceDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceDefinitionType }
     * 
     * 
     */
    public List<ServiceDefinitionType> getServiceDefinition() {
        if (serviceDefinition == null) {
            serviceDefinition = new ArrayList<ServiceDefinitionType>();
        }
        return this.serviceDefinition;
    }

    /**
     * Ruft den Wert der obligations-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ObligationsType }
     *     
     */
    public ObligationsType getObligations() {
        return obligations;
    }

    /**
     * Legt den Wert der obligations-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ObligationsType }
     *     
     */
    public void setObligations(ObligationsType value) {
        this.obligations = value;
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
