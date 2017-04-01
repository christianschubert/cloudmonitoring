//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.04.01 um 05:16:23 PM CEST 
//


package com.ibm.wsla;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ibm.wsla package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SLA_QNAME = new QName("http://www.ibm.com/wsla", "SLA");
    private final static QName _Parties_QNAME = new QName("http://www.ibm.com/wsla", "Parties");
    private final static QName _Schedule_QNAME = new QName("http://www.ibm.com/wsla", "Schedule");
    private final static QName _OperationGroup_QNAME = new QName("http://www.ibm.com/wsla", "OperationGroup");
    private final static QName _Operation_QNAME = new QName("http://www.ibm.com/wsla", "Operation");
    private final static QName _WebHosting_QNAME = new QName("http://www.ibm.com/wsla", "WebHosting");
    private final static QName _SLAParameter_QNAME = new QName("http://www.ibm.com/wsla", "SLAParameter");
    private final static QName _Metric_QNAME = new QName("http://www.ibm.com/wsla", "Metric");
    private final static QName _MetricMacroDefinition_QNAME = new QName("http://www.ibm.com/wsla", "MetricMacroDefinition");
    private final static QName _MetricMacroExpansion_QNAME = new QName("http://www.ibm.com/wsla", "MetricMacroExpansion");
    private final static QName _Constant_QNAME = new QName("http://www.ibm.com/wsla", "Constant");
    private final static QName _Trigger_QNAME = new QName("http://www.ibm.com/wsla", "Trigger");
    private final static QName _ObligationGroup_QNAME = new QName("http://www.ibm.com/wsla", "ObligationGroup");
    private final static QName _QualifiedAction_QNAME = new QName("http://www.ibm.com/wsla", "QualifiedAction");
    private final static QName _WSDLSOAPOperation_QNAME = new QName("http://www.ibm.com/wsla", "WSDLSOAPOperation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ibm.wsla
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WSLAType }
     * 
     */
    public WSLAType createWSLAType() {
        return new WSLAType();
    }

    /**
     * Create an instance of {@link PartiesType }
     * 
     */
    public PartiesType createPartiesType() {
        return new PartiesType();
    }

    /**
     * Create an instance of {@link ScheduleType }
     * 
     */
    public ScheduleType createScheduleType() {
        return new ScheduleType();
    }

    /**
     * Create an instance of {@link OperationGroupType }
     * 
     */
    public OperationGroupType createOperationGroupType() {
        return new OperationGroupType();
    }

    /**
     * Create an instance of {@link OperationDescriptionType }
     * 
     */
    public OperationDescriptionType createOperationDescriptionType() {
        return new OperationDescriptionType();
    }

    /**
     * Create an instance of {@link WebHostingType }
     * 
     */
    public WebHostingType createWebHostingType() {
        return new WebHostingType();
    }

    /**
     * Create an instance of {@link SLAParameterType }
     * 
     */
    public SLAParameterType createSLAParameterType() {
        return new SLAParameterType();
    }

    /**
     * Create an instance of {@link MetricType }
     * 
     */
    public MetricType createMetricType() {
        return new MetricType();
    }

    /**
     * Create an instance of {@link MetricMacroDefinitionType }
     * 
     */
    public MetricMacroDefinitionType createMetricMacroDefinitionType() {
        return new MetricMacroDefinitionType();
    }

    /**
     * Create an instance of {@link MetricMacroExpansionType }
     * 
     */
    public MetricMacroExpansionType createMetricMacroExpansionType() {
        return new MetricMacroExpansionType();
    }

    /**
     * Create an instance of {@link ConstantType }
     * 
     */
    public ConstantType createConstantType() {
        return new ConstantType();
    }

    /**
     * Create an instance of {@link TriggerType }
     * 
     */
    public TriggerType createTriggerType() {
        return new TriggerType();
    }

    /**
     * Create an instance of {@link ObligationGroupType }
     * 
     */
    public ObligationGroupType createObligationGroupType() {
        return new ObligationGroupType();
    }

    /**
     * Create an instance of {@link QualifiedActionType }
     * 
     */
    public QualifiedActionType createQualifiedActionType() {
        return new QualifiedActionType();
    }

    /**
     * Create an instance of {@link WSDLSOAPOperationDescriptionType }
     * 
     */
    public WSDLSOAPOperationDescriptionType createWSDLSOAPOperationDescriptionType() {
        return new WSDLSOAPOperationDescriptionType();
    }

    /**
     * Create an instance of {@link ContactInformationType }
     * 
     */
    public ContactInformationType createContactInformationType() {
        return new ContactInformationType();
    }

    /**
     * Create an instance of {@link SignatoryPartyType }
     * 
     */
    public SignatoryPartyType createSignatoryPartyType() {
        return new SignatoryPartyType();
    }

    /**
     * Create an instance of {@link SupportingPartyType }
     * 
     */
    public SupportingPartyType createSupportingPartyType() {
        return new SupportingPartyType();
    }

    /**
     * Create an instance of {@link ServiceDefinitionType }
     * 
     */
    public ServiceDefinitionType createServiceDefinitionType() {
        return new ServiceDefinitionType();
    }

    /**
     * Create an instance of {@link SLAParameterCommunicationType }
     * 
     */
    public SLAParameterCommunicationType createSLAParameterCommunicationType() {
        return new SLAParameterCommunicationType();
    }

    /**
     * Create an instance of {@link MDVariableType }
     * 
     */
    public MDVariableType createMDVariableType() {
        return new MDVariableType();
    }

    /**
     * Create an instance of {@link MDAssignmentType }
     * 
     */
    public MDAssignmentType createMDAssignmentType() {
        return new MDAssignmentType();
    }

    /**
     * Create an instance of {@link IntervalType }
     * 
     */
    public IntervalType createIntervalType() {
        return new IntervalType();
    }

    /**
     * Create an instance of {@link PeriodType }
     * 
     */
    public PeriodType createPeriodType() {
        return new PeriodType();
    }

    /**
     * Create an instance of {@link ObligationObjectType }
     * 
     */
    public ObligationObjectType createObligationObjectType() {
        return new ObligationObjectType();
    }

    /**
     * Create an instance of {@link ObligationsType }
     * 
     */
    public ObligationsType createObligationsType() {
        return new ObligationsType();
    }

    /**
     * Create an instance of {@link ActionGuaranteeType }
     * 
     */
    public ActionGuaranteeType createActionGuaranteeType() {
        return new ActionGuaranteeType();
    }

    /**
     * Create an instance of {@link ServiceLevelObjectiveType }
     * 
     */
    public ServiceLevelObjectiveType createServiceLevelObjectiveType() {
        return new ServiceLevelObjectiveType();
    }

    /**
     * Create an instance of {@link LogicExpressionType }
     * 
     */
    public LogicExpressionType createLogicExpressionType() {
        return new LogicExpressionType();
    }

    /**
     * Create an instance of {@link BinaryLogicOperatorType }
     * 
     */
    public BinaryLogicOperatorType createBinaryLogicOperatorType() {
        return new BinaryLogicOperatorType();
    }

    /**
     * Create an instance of {@link UnaryLogicOperatorType }
     * 
     */
    public UnaryLogicOperatorType createUnaryLogicOperatorType() {
        return new UnaryLogicOperatorType();
    }

    /**
     * Create an instance of {@link WSDLSOAPActionDescriptionType }
     * 
     */
    public WSDLSOAPActionDescriptionType createWSDLSOAPActionDescriptionType() {
        return new WSDLSOAPActionDescriptionType();
    }

    /**
     * Create an instance of {@link WSDLGetPostActionDescriptionType }
     * 
     */
    public WSDLGetPostActionDescriptionType createWSDLGetPostActionDescriptionType() {
        return new WSDLGetPostActionDescriptionType();
    }

    /**
     * Create an instance of {@link StringOperationDescriptionType }
     * 
     */
    public StringOperationDescriptionType createStringOperationDescriptionType() {
        return new StringOperationDescriptionType();
    }

    /**
     * Create an instance of {@link Counter }
     * 
     */
    public Counter createCounter() {
        return new Counter();
    }

    /**
     * Create an instance of {@link Gauge }
     * 
     */
    public Gauge createGauge() {
        return new Gauge();
    }

    /**
     * Create an instance of {@link ResponseTime }
     * 
     */
    public ResponseTime createResponseTime() {
        return new ResponseTime();
    }

    /**
     * Create an instance of {@link SumResponseTime }
     * 
     */
    public SumResponseTime createSumResponseTime() {
        return new SumResponseTime();
    }

    /**
     * Create an instance of {@link Status }
     * 
     */
    public Status createStatus() {
        return new Status();
    }

    /**
     * Create an instance of {@link InvocationCount }
     * 
     */
    public InvocationCount createInvocationCount() {
        return new InvocationCount();
    }

    /**
     * Create an instance of {@link StatusRequest }
     * 
     */
    public StatusRequest createStatusRequest() {
        return new StatusRequest();
    }

    /**
     * Create an instance of {@link Downtime }
     * 
     */
    public Downtime createDowntime() {
        return new Downtime();
    }

    /**
     * Create an instance of {@link MaintenancePeriodQuery }
     * 
     */
    public MaintenancePeriodQuery createMaintenancePeriodQuery() {
        return new MaintenancePeriodQuery();
    }

    /**
     * Create an instance of {@link QConstructor }
     * 
     */
    public QConstructor createQConstructor() {
        return new QConstructor();
    }

    /**
     * Create an instance of {@link TSConstructor }
     * 
     */
    public TSConstructor createTSConstructor() {
        return new TSConstructor();
    }

    /**
     * Create an instance of {@link Size }
     * 
     */
    public Size createSize() {
        return new Size();
    }

    /**
     * Create an instance of {@link TSSelect }
     * 
     */
    public TSSelect createTSSelect() {
        return new TSSelect();
    }

    /**
     * Create an instance of {@link Plus }
     * 
     */
    public Plus createPlus() {
        return new Plus();
    }

    /**
     * Create an instance of {@link Minus }
     * 
     */
    public Minus createMinus() {
        return new Minus();
    }

    /**
     * Create an instance of {@link Divide }
     * 
     */
    public Divide createDivide() {
        return new Divide();
    }

    /**
     * Create an instance of {@link Times }
     * 
     */
    public Times createTimes() {
        return new Times();
    }

    /**
     * Create an instance of {@link Average }
     * 
     */
    public Average createAverage() {
        return new Average();
    }

    /**
     * Create an instance of {@link Mean }
     * 
     */
    public Mean createMean() {
        return new Mean();
    }

    /**
     * Create an instance of {@link Median }
     * 
     */
    public Median createMedian() {
        return new Median();
    }

    /**
     * Create an instance of {@link Mode }
     * 
     */
    public Mode createMode() {
        return new Mode();
    }

    /**
     * Create an instance of {@link Round }
     * 
     */
    public Round createRound() {
        return new Round();
    }

    /**
     * Create an instance of {@link Max }
     * 
     */
    public Max createMax() {
        return new Max();
    }

    /**
     * Create an instance of {@link Sum }
     * 
     */
    public Sum createSum() {
        return new Sum();
    }

    /**
     * Create an instance of {@link ValueOccurs }
     * 
     */
    public ValueOccurs createValueOccurs() {
        return new ValueOccurs();
    }

    /**
     * Create an instance of {@link PercentageGreaterThanThreshold }
     * 
     */
    public PercentageGreaterThanThreshold createPercentageGreaterThanThreshold() {
        return new PercentageGreaterThanThreshold();
    }

    /**
     * Create an instance of {@link PercentageLessThanThreshold }
     * 
     */
    public PercentageLessThanThreshold createPercentageLessThanThreshold() {
        return new PercentageLessThanThreshold();
    }

    /**
     * Create an instance of {@link NumberGreaterThanThreshold }
     * 
     */
    public NumberGreaterThanThreshold createNumberGreaterThanThreshold() {
        return new NumberGreaterThanThreshold();
    }

    /**
     * Create an instance of {@link NumberLessThanThreshold }
     * 
     */
    public NumberLessThanThreshold createNumberLessThanThreshold() {
        return new NumberLessThanThreshold();
    }

    /**
     * Create an instance of {@link RateOfChange }
     * 
     */
    public RateOfChange createRateOfChange() {
        return new RateOfChange();
    }

    /**
     * Create an instance of {@link Span }
     * 
     */
    public Span createSpan() {
        return new Span();
    }

    /**
     * Create an instance of {@link QualifyMeasurementTimeSeries }
     * 
     */
    public QualifyMeasurementTimeSeries createQualifyMeasurementTimeSeries() {
        return new QualifyMeasurementTimeSeries();
    }

    /**
     * Create an instance of {@link OperandType }
     * 
     */
    public OperandType createOperandType() {
        return new OperandType();
    }

    /**
     * Create an instance of {@link NewValue }
     * 
     */
    public NewValue createNewValue() {
        return new NewValue();
    }

    /**
     * Create an instance of {@link Violation }
     * 
     */
    public Violation createViolation() {
        return new Violation();
    }

    /**
     * Create an instance of {@link Greater }
     * 
     */
    public Greater createGreater() {
        return new Greater();
    }

    /**
     * Create an instance of {@link Less }
     * 
     */
    public Less createLess() {
        return new Less();
    }

    /**
     * Create an instance of {@link Equal }
     * 
     */
    public Equal createEqual() {
        return new Equal();
    }

    /**
     * Create an instance of {@link GreaterEqual }
     * 
     */
    public GreaterEqual createGreaterEqual() {
        return new GreaterEqual();
    }

    /**
     * Create an instance of {@link LessEqual }
     * 
     */
    public LessEqual createLessEqual() {
        return new LessEqual();
    }

    /**
     * Create an instance of {@link True }
     * 
     */
    public True createTrue() {
        return new True();
    }

    /**
     * Create an instance of {@link False }
     * 
     */
    public False createFalse() {
        return new False();
    }

    /**
     * Create an instance of {@link ActionInvocationType }
     * 
     */
    public ActionInvocationType createActionInvocationType() {
        return new ActionInvocationType();
    }

    /**
     * Create an instance of {@link Notification }
     * 
     */
    public Notification createNotification() {
        return new Notification();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSLAType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "SLA")
    public JAXBElement<WSLAType> createSLA(WSLAType value) {
        return new JAXBElement<WSLAType>(_SLA_QNAME, WSLAType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartiesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "Parties")
    public JAXBElement<PartiesType> createParties(PartiesType value) {
        return new JAXBElement<PartiesType>(_Parties_QNAME, PartiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ScheduleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "Schedule")
    public JAXBElement<ScheduleType> createSchedule(ScheduleType value) {
        return new JAXBElement<ScheduleType>(_Schedule_QNAME, ScheduleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationGroupType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "OperationGroup")
    public JAXBElement<OperationGroupType> createOperationGroup(OperationGroupType value) {
        return new JAXBElement<OperationGroupType>(_OperationGroup_QNAME, OperationGroupType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationDescriptionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "Operation")
    public JAXBElement<OperationDescriptionType> createOperation(OperationDescriptionType value) {
        return new JAXBElement<OperationDescriptionType>(_Operation_QNAME, OperationDescriptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebHostingType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "WebHosting")
    public JAXBElement<WebHostingType> createWebHosting(WebHostingType value) {
        return new JAXBElement<WebHostingType>(_WebHosting_QNAME, WebHostingType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SLAParameterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "SLAParameter")
    public JAXBElement<SLAParameterType> createSLAParameter(SLAParameterType value) {
        return new JAXBElement<SLAParameterType>(_SLAParameter_QNAME, SLAParameterType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetricType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "Metric")
    public JAXBElement<MetricType> createMetric(MetricType value) {
        return new JAXBElement<MetricType>(_Metric_QNAME, MetricType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetricMacroDefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "MetricMacroDefinition")
    public JAXBElement<MetricMacroDefinitionType> createMetricMacroDefinition(MetricMacroDefinitionType value) {
        return new JAXBElement<MetricMacroDefinitionType>(_MetricMacroDefinition_QNAME, MetricMacroDefinitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetricMacroExpansionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "MetricMacroExpansion")
    public JAXBElement<MetricMacroExpansionType> createMetricMacroExpansion(MetricMacroExpansionType value) {
        return new JAXBElement<MetricMacroExpansionType>(_MetricMacroExpansion_QNAME, MetricMacroExpansionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConstantType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "Constant")
    public JAXBElement<ConstantType> createConstant(ConstantType value) {
        return new JAXBElement<ConstantType>(_Constant_QNAME, ConstantType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TriggerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "Trigger")
    public JAXBElement<TriggerType> createTrigger(TriggerType value) {
        return new JAXBElement<TriggerType>(_Trigger_QNAME, TriggerType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObligationGroupType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "ObligationGroup")
    public JAXBElement<ObligationGroupType> createObligationGroup(ObligationGroupType value) {
        return new JAXBElement<ObligationGroupType>(_ObligationGroup_QNAME, ObligationGroupType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QualifiedActionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "QualifiedAction")
    public JAXBElement<QualifiedActionType> createQualifiedAction(QualifiedActionType value) {
        return new JAXBElement<QualifiedActionType>(_QualifiedAction_QNAME, QualifiedActionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSDLSOAPOperationDescriptionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibm.com/wsla", name = "WSDLSOAPOperation", substitutionHeadNamespace = "http://www.ibm.com/wsla", substitutionHeadName = "Operation")
    public JAXBElement<WSDLSOAPOperationDescriptionType> createWSDLSOAPOperation(WSDLSOAPOperationDescriptionType value) {
        return new JAXBElement<WSDLSOAPOperationDescriptionType>(_WSDLSOAPOperation_QNAME, WSDLSOAPOperationDescriptionType.class, null, value);
    }

}
