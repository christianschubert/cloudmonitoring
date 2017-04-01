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
 * <p>Java-Klasse für PartyType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="PartyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Contact" type="{http://www.ibm.com/wsla}ContactInformationType" minOccurs="0"/&gt;
 *         &lt;element name="Action" type="{http://www.ibm.com/wsla}ActionDescriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "PartyType", propOrder = {
    "contact",
    "action"
})
@XmlSeeAlso({
    SignatoryPartyType.class,
    SupportingPartyType.class
})
public abstract class PartyType {

    @XmlElement(name = "Contact")
    protected ContactInformationType contact;
    @XmlElement(name = "Action")
    protected List<ActionDescriptionType> action;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Ruft den Wert der contact-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ContactInformationType }
     *     
     */
    public ContactInformationType getContact() {
        return contact;
    }

    /**
     * Legt den Wert der contact-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInformationType }
     *     
     */
    public void setContact(ContactInformationType value) {
        this.contact = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the action property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionDescriptionType }
     * 
     * 
     */
    public List<ActionDescriptionType> getAction() {
        if (action == null) {
            action = new ArrayList<ActionDescriptionType>();
        }
        return this.action;
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
