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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für PartiesType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="PartiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceProvider" type="{http://www.ibm.com/wsla}SignatoryPartyType"/&gt;
 *         &lt;element name="ServiceConsumer" type="{http://www.ibm.com/wsla}SignatoryPartyType"/&gt;
 *         &lt;element name="SupportingParty" type="{http://www.ibm.com/wsla}SupportingPartyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartiesType", propOrder = {
    "serviceProvider",
    "serviceConsumer",
    "supportingParty"
})
public class PartiesType {

    @XmlElement(name = "ServiceProvider", required = true)
    protected SignatoryPartyType serviceProvider;
    @XmlElement(name = "ServiceConsumer", required = true)
    protected SignatoryPartyType serviceConsumer;
    @XmlElement(name = "SupportingParty")
    protected List<SupportingPartyType> supportingParty;

    /**
     * Ruft den Wert der serviceProvider-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SignatoryPartyType }
     *     
     */
    public SignatoryPartyType getServiceProvider() {
        return serviceProvider;
    }

    /**
     * Legt den Wert der serviceProvider-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatoryPartyType }
     *     
     */
    public void setServiceProvider(SignatoryPartyType value) {
        this.serviceProvider = value;
    }

    /**
     * Ruft den Wert der serviceConsumer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SignatoryPartyType }
     *     
     */
    public SignatoryPartyType getServiceConsumer() {
        return serviceConsumer;
    }

    /**
     * Legt den Wert der serviceConsumer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatoryPartyType }
     *     
     */
    public void setServiceConsumer(SignatoryPartyType value) {
        this.serviceConsumer = value;
    }

    /**
     * Gets the value of the supportingParty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportingParty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportingParty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupportingPartyType }
     * 
     * 
     */
    public List<SupportingPartyType> getSupportingParty() {
        if (supportingParty == null) {
            supportingParty = new ArrayList<SupportingPartyType>();
        }
        return this.supportingParty;
    }

}
