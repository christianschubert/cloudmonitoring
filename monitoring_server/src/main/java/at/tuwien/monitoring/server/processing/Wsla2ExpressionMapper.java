package at.tuwien.monitoring.server.processing;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import com.ibm.wsla.Equal;
import com.ibm.wsla.FunctionType;
import com.ibm.wsla.Greater;
import com.ibm.wsla.GreaterEqual;
import com.ibm.wsla.Less;
import com.ibm.wsla.LessEqual;
import com.ibm.wsla.Mean;
import com.ibm.wsla.MetricType;
import com.ibm.wsla.OperationDescriptionType;
import com.ibm.wsla.PeriodType;
import com.ibm.wsla.PredicateType;
import com.ibm.wsla.SLAParameterType;
import com.ibm.wsla.ServiceDefinitionType;
import com.ibm.wsla.ServiceLevelObjectiveType;

import at.tuwien.monitoring.server.wsla.WebServiceLevelAgreement;

public class Wsla2ExpressionMapper {

	private final static Logger logger = Logger.getLogger(Wsla2ExpressionMapper.class);

	private static final String DEFAULT_SIMPLE_EXPRESSION = "select * from %s having %s %s %s";

	private Map<String, MetricInformation> metricMap = new HashMap<>();

	public Wsla2ExpressionMapper(WebServiceLevelAgreement wsla, MetricProcessor metricProcessor) {
		initInformationMap();
		doMapping(wsla, metricProcessor);
	}

	private void initInformationMap() {
		// map of basic metrics and their corresponding messages and variables
		metricMap.put("responsetime", new MetricInformation("ClientResponseTimeMessage", "responseTime"));
		metricMap.put("cpuload", new MetricInformation("CpuLoadMessage", "cpuLoad"));
		metricMap.put("totalmemory", new MetricInformation("MemoryMessage", "totalMemory"));
		metricMap.put("residentmemory", new MetricInformation("MemoryMessage", "residentMemory"));
	}

	private void doMapping(WebServiceLevelAgreement wsla, MetricProcessor metricProcessor) {

		Map<String, String> slaMetricMap = new HashMap<>();

		// parse service definitions including slas and metrics
		for (ServiceDefinitionType serviceDefinition : wsla.getWSLA().getServiceDefinition()) {
			for (JAXBElement<? extends OperationDescriptionType> operation : serviceDefinition.getOperation()) {
				OperationDescriptionType op = operation.getValue();

				// find out all metrics for all sla parameters
				for (SLAParameterType slaParameter : op.getSLAParameter()) {
					slaMetricMap.put(slaParameter.getName(), slaParameter.getMetric());
				}

				// iterate metrics
				for (MetricType metric : op.getMetric()) {
					if (metric.getFunction() == null) {
						// simple metric, should be in metric map if implemented
						if (!metricMap.containsKey(metric.getName().toLowerCase())) {
							logger.info("Metric \"" + metric.getName() + "\" is not implemented yet or not valid.");
						}
					} else {
						// complex metric with function
						parseFunction(metric.getFunction());
					}
				}
			}
		}

		// parse service level objectives
		for (ServiceLevelObjectiveType serviceLevelObjective : wsla.getWSLA().getObligations()
				.getServiceLevelObjective()) {
			if (!checkValidity(serviceLevelObjective.getValidity())) {
				logger.info("Validity of SLO \"" + serviceLevelObjective.getName() + "\" not given.");
				continue;
			}

			SimplePredicate simplePredicate = parseSimplePredicate(
					serviceLevelObjective.getExpression().getPredicate());
			if (simplePredicate == null) {
				logger.info("Predicate of SLO \"" + serviceLevelObjective.getName()
						+ "\" not implemented yet or not valid.");
				continue;
			}

			String metricToObserve = slaMetricMap.get(simplePredicate.getSlaParameter()).toLowerCase();
			if (metricToObserve == null || metricMap.get(metricToObserve) == null) {
				logger.info(
						"SLA parameter for SLO \"" + serviceLevelObjective.getName() + "\" not defined. Ignoring SLO.");
				continue;
			}

			MetricInformation metricInformation = metricMap.get(metricToObserve);

			String expression = String.format(DEFAULT_SIMPLE_EXPRESSION, metricInformation.getEventMessageName(),
					metricInformation.getPropertyName(), simplePredicate.getDetectionSign(),
					simplePredicate.getThreshold());
			String requirementDesc = simplePredicate.getPredicateSign() + simplePredicate.getThreshold();

			metricProcessor.addExpression(expression, requirementDesc);
		}
	}

	private String parseFunction(FunctionType function) {
		// currently one level and average function implemented
		if (function instanceof Mean) {
			Mean mean = (Mean) function;
			return mean.getMetric();
		}

		return null;
	}

	private SimplePredicate parseSimplePredicate(PredicateType predicate) {
		SimplePredicate simplePredicate = new SimplePredicate();
		if (predicate instanceof Less) {
			Less pred = (Less) predicate;
			simplePredicate.setPredicateSign("<");
			simplePredicate.setDetectionSign(">=");
			simplePredicate.setSlaParameter(pred.getSLAParameter());
			simplePredicate.setThreshold(pred.getValue());
			return simplePredicate;
		} else if (predicate instanceof LessEqual) {
			LessEqual pred = (LessEqual) predicate;
			simplePredicate.setPredicateSign("<=");
			simplePredicate.setDetectionSign(">");
			simplePredicate.setSlaParameter(pred.getSLAParameter());
			simplePredicate.setThreshold(pred.getValue());
			return simplePredicate;
		} else if (predicate instanceof Greater) {
			Greater pred = (Greater) predicate;
			simplePredicate.setPredicateSign(">");
			simplePredicate.setDetectionSign("<=");
			simplePredicate.setSlaParameter(pred.getSLAParameter());
			simplePredicate.setThreshold(pred.getValue());
			return simplePredicate;
		} else if (predicate instanceof GreaterEqual) {
			GreaterEqual pred = (GreaterEqual) predicate;
			simplePredicate.setPredicateSign(">=");
			simplePredicate.setDetectionSign("<");
			simplePredicate.setSlaParameter(pred.getSLAParameter());
			simplePredicate.setThreshold(pred.getValue());
			return simplePredicate;
		} else if (predicate instanceof Equal) {
			Equal pred = (Equal) predicate;
			simplePredicate.setPredicateSign("=");
			simplePredicate.setDetectionSign("!=");
			simplePredicate.setSlaParameter(pred.getSLAParameter());
			simplePredicate.setThreshold(pred.getValue());
			return simplePredicate;
		}

		return null;
	}

	private boolean checkValidity(List<PeriodType> validity) {
		Date startTime = validity.get(0).getStart().toGregorianCalendar().getTime();
		Date stopTime = validity.get(0).getEnd().toGregorianCalendar().getTime();
		Date now = new Date();

		return startTime.before(now) && stopTime.after(now);
	}

	class SimplePredicate {
		private String predicateSign;
		private String detectionSign;
		private String slaParameter;
		private double threshold;

		public SimplePredicate() {
		}

		public String getPredicateSign() {
			return predicateSign;
		}

		public void setPredicateSign(String predicateSign) {
			this.predicateSign = predicateSign;
		}

		public String getSlaParameter() {
			return slaParameter;
		}

		public void setSlaParameter(String slaParameter) {
			this.slaParameter = slaParameter;
		}

		public double getThreshold() {
			return threshold;
		}

		public void setThreshold(double threshold) {
			this.threshold = threshold;
		}

		public String getDetectionSign() {
			return detectionSign;
		}

		public void setDetectionSign(String detectionSign) {
			this.detectionSign = detectionSign;
		}
	}

	class MetricInformation {
		private String eventMessageName;
		private String propertyName;

		public MetricInformation(String eventMessageName, String propertyName) {
			this.setEventMessageName(eventMessageName);
			this.setPropertyName(propertyName);
		}

		public String getEventMessageName() {
			return eventMessageName;
		}

		public void setEventMessageName(String eventMessageName) {
			this.eventMessageName = eventMessageName;
		}

		public String getPropertyName() {
			return propertyName;
		}

		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}
	}
}