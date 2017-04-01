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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ObligationObjectType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ObligationObjectType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}Constant" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ServiceLevelObjective" type="{http://www.ibm.com/wsla}ServiceLevelObjectiveType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ActionGuarantee" type="{http://www.ibm.com/wsla}ActionGuaranteeType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "ObligationObjectType", propOrder = {
    "constant",
    "serviceLevelObjective",
    "actionGuarantee"
})
@XmlSeeAlso({
    ObligationGroupType.class,
    ObligationsType.class
})
public class ObligationObjectType {

    @XmlElement(name = "Constant")
    protected List<ConstantType> constant;
    @XmlElement(name = "ServiceLevelObjective")
    protected List<ServiceLevelObjectiveType> serviceLevelObjective;
    @XmlElement(name = "ActionGuarantee")
    protected List<ActionGuaranteeType> actionGuarantee;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the constant property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constant property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstant().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConstantType }
     * 
     * 
     */
    public List<ConstantType> getConstant() {
        if (constant == null) {
            constant = new ArrayList<ConstantType>();
        }
        return this.constant;
    }

    /**
     * Gets the value of the serviceLevelObjective property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceLevelObjective property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceLevelObjective().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceLevelObjectiveType }
     * 
     * 
     */
    public List<ServiceLevelObjectiveType> getServiceLevelObjective() {
        if (serviceLevelObjective == null) {
            serviceLevelObjective = new ArrayList<ServiceLevelObjectiveType>();
        }
        return this.serviceLevelObjective;
    }

    /**
     * Gets the value of the actionGuarantee property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actionGuarantee property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActionGuarantee().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionGuaranteeType }
     * 
     * 
     */
    public List<ActionGuaranteeType> getActionGuarantee() {
        if (actionGuarantee == null) {
            actionGuarantee = new ArrayList<ActionGuaranteeType>();
        }
        return this.actionGuarantee;
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
