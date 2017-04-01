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
 * <p>Java-Klasse für Notification complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Notification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}ActionInvocationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NotificationType" type="{http://www.ibm.com/wsla}NotificationType"/&gt;
 *         &lt;element name="CausingGuarantee" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SLAParameter" type="{http://www.ibm.com/wsla}StringList"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Notification", propOrder = {
    "notificationType",
    "causingGuarantee",
    "slaParameter"
})
public class Notification
    extends ActionInvocationType
{

    @XmlElement(name = "NotificationType", required = true)
    @XmlSchemaType(name = "string")
    protected NotificationType notificationType;
    @XmlElement(name = "CausingGuarantee", required = true)
    protected String causingGuarantee;
    @XmlList
    @XmlElement(name = "SLAParameter", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected List<String> slaParameter;

    /**
     * Ruft den Wert der notificationType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link NotificationType }
     *     
     */
    public NotificationType getNotificationType() {
        return notificationType;
    }

    /**
     * Legt den Wert der notificationType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationType }
     *     
     */
    public void setNotificationType(NotificationType value) {
        this.notificationType = value;
    }

    /**
     * Ruft den Wert der causingGuarantee-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausingGuarantee() {
        return causingGuarantee;
    }

    /**
     * Legt den Wert der causingGuarantee-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausingGuarantee(String value) {
        this.causingGuarantee = value;
    }

    /**
     * Gets the value of the slaParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the slaParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSLAParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSLAParameter() {
        if (slaParameter == null) {
            slaParameter = new ArrayList<String>();
        }
        return this.slaParameter;
    }

}
