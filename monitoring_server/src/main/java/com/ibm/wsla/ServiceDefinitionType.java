//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.01 um 05:16:23 PM CEST 
//


package com.ibm.wsla;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ServiceDefinitionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ServiceDefinitionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ibm.com/wsla}ServiceObjectType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}Operation" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}OperationGroup" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}WebHosting" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDefinitionType", propOrder = {
    "operation",
    "operationGroup",
    "webHosting"
})
public class ServiceDefinitionType
    extends ServiceObjectType
{

    @XmlElementRef(name = "Operation", namespace = "http://www.ibm.com/wsla", type = JAXBElement.class, required = false)
    protected List<JAXBElement<? extends OperationDescriptionType>> operation;
    @XmlElement(name = "OperationGroup")
    protected List<OperationGroupType> operationGroup;
    @XmlElement(name = "WebHosting")
    protected List<WebHostingType> webHosting;

    /**
     * Gets the value of the operation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link WSDLSOAPOperationDescriptionType }{@code >}
     * {@link JAXBElement }{@code <}{@link OperationDescriptionType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends OperationDescriptionType>> getOperation() {
        if (operation == null) {
            operation = new ArrayList<JAXBElement<? extends OperationDescriptionType>>();
        }
        return this.operation;
    }

    /**
     * Gets the value of the operationGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operationGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperationGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OperationGroupType }
     * 
     * 
     */
    public List<OperationGroupType> getOperationGroup() {
        if (operationGroup == null) {
            operationGroup = new ArrayList<OperationGroupType>();
        }
        return this.operationGroup;
    }

    /**
     * Gets the value of the webHosting property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the webHosting property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWebHosting().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WebHostingType }
     * 
     * 
     */
    public List<WebHostingType> getWebHosting() {
        if (webHosting == null) {
            webHosting = new ArrayList<WebHostingType>();
        }
        return this.webHosting;
    }

}
