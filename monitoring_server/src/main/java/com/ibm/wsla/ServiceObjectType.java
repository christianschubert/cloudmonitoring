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
 * <p>Java-Klasse für ServiceObjectType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ServiceObjectType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}Schedule" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}Trigger" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}Constant" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}MetricMacroDefinition" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}MetricMacroExpansion" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}SLAParameter" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.ibm.com/wsla}Metric" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "ServiceObjectType", propOrder = {
    "schedule",
    "trigger",
    "constant",
    "metricMacroDefinition",
    "metricMacroExpansion",
    "slaParameter",
    "metric"
})
@XmlSeeAlso({
    OperationGroupType.class,
    WebHostingType.class,
    ServiceDefinitionType.class,
    OperationDescriptionType.class
})
public abstract class ServiceObjectType {

    @XmlElement(name = "Schedule")
    protected List<ScheduleType> schedule;
    @XmlElement(name = "Trigger")
    protected List<TriggerType> trigger;
    @XmlElement(name = "Constant")
    protected List<ConstantType> constant;
    @XmlElement(name = "MetricMacroDefinition")
    protected List<MetricMacroDefinitionType> metricMacroDefinition;
    @XmlElement(name = "MetricMacroExpansion")
    protected List<MetricMacroExpansionType> metricMacroExpansion;
    @XmlElement(name = "SLAParameter")
    protected List<SLAParameterType> slaParameter;
    @XmlElement(name = "Metric")
    protected List<MetricType> metric;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the schedule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schedule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchedule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduleType }
     * 
     * 
     */
    public List<ScheduleType> getSchedule() {
        if (schedule == null) {
            schedule = new ArrayList<ScheduleType>();
        }
        return this.schedule;
    }

    /**
     * Gets the value of the trigger property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trigger property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrigger().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TriggerType }
     * 
     * 
     */
    public List<TriggerType> getTrigger() {
        if (trigger == null) {
            trigger = new ArrayList<TriggerType>();
        }
        return this.trigger;
    }

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
     * Gets the value of the metricMacroDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metricMacroDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetricMacroDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetricMacroDefinitionType }
     * 
     * 
     */
    public List<MetricMacroDefinitionType> getMetricMacroDefinition() {
        if (metricMacroDefinition == null) {
            metricMacroDefinition = new ArrayList<MetricMacroDefinitionType>();
        }
        return this.metricMacroDefinition;
    }

    /**
     * Gets the value of the metricMacroExpansion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metricMacroExpansion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetricMacroExpansion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetricMacroExpansionType }
     * 
     * 
     */
    public List<MetricMacroExpansionType> getMetricMacroExpansion() {
        if (metricMacroExpansion == null) {
            metricMacroExpansion = new ArrayList<MetricMacroExpansionType>();
        }
        return this.metricMacroExpansion;
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
     * {@link SLAParameterType }
     * 
     * 
     */
    public List<SLAParameterType> getSLAParameter() {
        if (slaParameter == null) {
            slaParameter = new ArrayList<SLAParameterType>();
        }
        return this.slaParameter;
    }

    /**
     * Gets the value of the metric property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metric property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetric().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetricType }
     * 
     * 
     */
    public List<MetricType> getMetric() {
        if (metric == null) {
            metric = new ArrayList<MetricType>();
        }
        return this.metric;
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
