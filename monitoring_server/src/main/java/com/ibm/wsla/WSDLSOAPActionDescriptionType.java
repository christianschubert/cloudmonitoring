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
 * <p>Java-Klasse für WSDLSOAPActionDescriptionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WSDLSOAPActionDescriptionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}ActionDescriptionType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="WSDLFile" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *         &lt;element name="ServiceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SOAPBindingName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SOAPOperationName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSDLSOAPActionDescriptionType", propOrder = {
    "wsdlFile",
    "serviceName",
    "soapBindingName",
    "soapOperationName"
})
public class WSDLSOAPActionDescriptionType
    extends ActionDescriptionType
{

    @XmlElement(name = "WSDLFile", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String wsdlFile;
    @XmlElement(name = "ServiceName")
    protected String serviceName;
    @XmlElement(name = "SOAPBindingName", required = true)
    protected String soapBindingName;
    @XmlElement(name = "SOAPOperationName", required = true)
    protected String soapOperationName;

    /**
     * Ruft den Wert der wsdlFile-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWSDLFile() {
        return wsdlFile;
    }

    /**
     * Legt den Wert der wsdlFile-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWSDLFile(String value) {
        this.wsdlFile = value;
    }

    /**
     * Ruft den Wert der serviceName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Legt den Wert der serviceName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Ruft den Wert der soapBindingName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOAPBindingName() {
        return soapBindingName;
    }

    /**
     * Legt den Wert der soapBindingName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOAPBindingName(String value) {
        this.soapBindingName = value;
    }

    /**
     * Ruft den Wert der soapOperationName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOAPOperationName() {
        return soapOperationName;
    }

    /**
     * Legt den Wert der soapOperationName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOAPOperationName(String value) {
        this.soapOperationName = value;
    }

}
