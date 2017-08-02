package at.tuwien.monitoring.server.processing.mapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import com.ibm.wsla.Equal;
import com.ibm.wsla.Greater;
import com.ibm.wsla.GreaterEqual;
import com.ibm.wsla.Less;
import com.ibm.wsla.LessEqual;
import com.ibm.wsla.Max;
import com.ibm.wsla.Mean;
import com.ibm.wsla.Median;
import com.ibm.wsla.MetricType;
import com.ibm.wsla.OperationDescriptionType;
import com.ibm.wsla.PeriodType;
import com.ibm.wsla.PredicateType;
import com.ibm.wsla.SLAParameterType;
import com.ibm.wsla.ServiceDefinitionType;
import com.ibm.wsla.ServiceLevelObjectiveType;
import com.ibm.wsla.Sum;

import at.tuwien.monitoring.server.processing.MetricProcessor;
import at.tuwien.monitoring.server.wsla.WebServiceLevelAgreement;

public class Wsla2ExpressionMapper {

	private final static Logger logger = Logger.getLogger(Wsla2ExpressionMapper.class);

	private static final int AGGREGATION_MINIMUM_EVENT_SIZE = 3;

	private static final String SIMPLE_EXPRESSION = "select %s as monitoredvalue, '%s' as metrictype, '%s' as requirementdesc, * from %s(%s > = 0) having %s %s %s";
	private static final String AGGREGATION_FUNCTION_EXPRESSION = "select %s(%s) as monitoredvalue, '%s' as metrictype, '%s' as requirementdesc, * from %s(%s > = 0) group by ipAddress having %s(%s) %s %s AND count(*) >= "
			+ AGGREGATION_MINIMUM_EVENT_SIZE;

	private static final String RATIO_FUNCTION = "count(*, %s %s %s)/count(*)";
	private static final String RATIO_EXPRESSION = "select " + RATIO_FUNCTION
			+ " as monitoredvalue, '%s' as metrictype, '%s' as requirementdesc, * from %s(%s > = 0) group by ipAddress having "
			+ RATIO_FUNCTION + " %s %s AND count(*) >=" + AGGREGATION_MINIMUM_EVENT_SIZE;

	@SuppressWarnings("unused")
	private static final String WINDOW_LENGTH = ".win:length(%)";

	@SuppressWarnings("unused")
	private static final String WINDOW_TIME_SEC = ".win:time(% sec)";

	@SuppressWarnings("unused")
	private static final String WINDOW_TIME_MIN = ".win:time(% min)";

	// map of basic metrics and their corresponding messages and variables
	private static Map<String, MetricInformation> basicMetricMap;
	static {
		basicMetricMap = new HashMap<>();
		basicMetricMap.put("responsetime", new MetricInformation("ClientResponseTimeMessage", "responseTime"));
		basicMetricMap.put("successrate", new MetricInformation("ClientResponseTimeMessage", "responseCode"));
		basicMetricMap.put("cpuload", new MetricInformation("CpuLoadMessage", "cpuLoad"));
		basicMetricMap.put("totalmemory", new MetricInformation("MemoryMessage", "totalMemory"));
		basicMetricMap.put("residentmemory", new MetricInformation("MemoryMessage", "residentMemory"));
	}

	private MetricProcessor metricProcessor;

	public Wsla2ExpressionMapper(final WebServiceLevelAgreement wsla, final MetricProcessor metricProcessor) {
		this.metricProcessor = metricProcessor;
		doMapping(wsla);
	}

	private void doMapping(final WebServiceLevelAgreement wsla) {

		Map<String, String> slaMetricMap = new HashMap<>();
		Map<String, MetricInformation> processingMetricMap = new HashMap<>();

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
						parseSimpleMetric(metric, processingMetricMap);
					} else {
						// complex metric with function
						parseComplexMetric(metric, processingMetricMap);
					}
				}
			}
		}

		// parse service level objectives
		for (ServiceLevelObjectiveType serviceLevelObjective : wsla.getWSLA().getObligations().getServiceLevelObjective()) {
			if (!checkValidity(serviceLevelObjective.getValidity())) {
				logger.info("Validity of SLO \"" + serviceLevelObjective.getName() + "\" not given. Ignoring SLO.");
				continue;
			}

			SimplePredicate simplePredicate = parseSimplePredicate(serviceLevelObjective.getExpression().getPredicate());
			if (simplePredicate == null) {
				logger.info("Predicate of SLO \"" + serviceLevelObjective.getName() + "\" not implemented yet or not valid.");
				continue;
			}

			String metricToObserve = slaMetricMap.get(simplePredicate.getSlaParameter());
			if (metricToObserve == null) {
				logger.info("SLA parameter for SLO \"" + serviceLevelObjective.getName() + "\" not defined. Ignoring SLO.");
				continue;
			}

			MetricInformation metricInformation = processingMetricMap.get(metricToObserve);
			createExpression(metricInformation, simplePredicate);
		}
	}

	private void createExpression(final MetricInformation metricInformation, final SimplePredicate simplePredicate) {

		String expression = null;

		if (metricInformation.getPropertyName().toLowerCase().equals("responsecode")) {
			// successrate (detection currently hardcoded)
			String requirementDesc = simplePredicate.getPredicateSign() + simplePredicate.getThreshold();

			// success -> status code begins with 2 (i.e 200 ok)
			String successPattern = "'2%'";
			expression = String.format(RATIO_EXPRESSION, metricInformation.getPropertyName(), "like", successPattern,
					metricInformation.getPropertyName(), requirementDesc, metricInformation.getEventMessageName(),
					metricInformation.getPropertyName(), metricInformation.getPropertyName(), "like", successPattern,
					simplePredicate.getDetectionSign(), simplePredicate.getThreshold());

		} else if (metricInformation.getAggregationFunction() == null) {
			// simple function
			String requirementDesc = simplePredicate.getPredicateSign() + simplePredicate.getThreshold();

			expression = String.format(SIMPLE_EXPRESSION, metricInformation.getPropertyName(),
					metricInformation.getPropertyName(), requirementDesc, metricInformation.getEventMessageName(),
					metricInformation.getPropertyName(), metricInformation.getPropertyName(), simplePredicate.getDetectionSign(),
					simplePredicate.getThreshold());

		} else {
			// aggregation function
			String requirementDesc = metricInformation.getAggregationFunction() + simplePredicate.getPredicateSign()
					+ simplePredicate.getThreshold();

			expression = String.format(AGGREGATION_FUNCTION_EXPRESSION, metricInformation.getAggregationFunction(),
					metricInformation.getPropertyName(), metricInformation.getPropertyName(), requirementDesc,
					metricInformation.getEventMessageName(), metricInformation.getPropertyName(),
					metricInformation.getAggregationFunction(), metricInformation.getPropertyName(),
					simplePredicate.getDetectionSign(), simplePredicate.getThreshold());
		}

		metricProcessor.addExpression(expression);
	}

	private void parseSimpleMetric(final MetricType metric, final Map<String, MetricInformation> processingMetricMap) {
		// simple metric, should be in basic metric map if implemented
		String metricName = metric.getName().toLowerCase();
		if (basicMetricMap.containsKey(metricName)) {
			// put to processing map, get infos from basic map
			processingMetricMap.put(metric.getName(), basicMetricMap.get(metricName));
		} else {
			logger.info("Metric \"" + metric.getName() + "\" is not implemented yet or not valid.");
		}
	}

	// currently one level implemented
	private void parseComplexMetric(final MetricType metric, final Map<String, MetricInformation> processingMetricMap) {

		MetricInformation metricInformation = new MetricInformation();
		String basicMetricName = null;

		// resolve functions
		if (metric.getFunction() instanceof Mean) {
			Mean mean = (Mean) metric.getFunction();
			metricInformation.setAggregationFunction("avg");
			basicMetricName = mean.getMetric();
		} else if (metric.getFunction() instanceof Median) {
			Median median = (Median) metric.getFunction();
			metricInformation.setAggregationFunction("median");
			basicMetricName = median.getMetric();
		} else if (metric.getFunction() instanceof Max) {
			Max max = (Max) metric.getFunction();
			metricInformation.setAggregationFunction("max");
			basicMetricName = max.getMetric();
		} else if (metric.getFunction() instanceof Sum) {
			Sum sum = (Sum) metric.getFunction();
			metricInformation.setAggregationFunction("sum");
			basicMetricName = sum.getMetric();
		}

		// resolve to basic metrics
		if (basicMetricName != null) {
			if (basicMetricMap.containsKey(basicMetricName.toLowerCase())) {
				MetricInformation basicInformation = basicMetricMap.get(basicMetricName.toLowerCase());
				metricInformation.setEventMessageName(basicInformation.getEventMessageName());
				metricInformation.setPropertyName(basicInformation.getPropertyName());

				processingMetricMap.put(metric.getName(), metricInformation);
			} else {
				logger.info("Metric \"" + metric.getName() + "\" is not implemented yet or not valid.");
			}
		} else {
			logger.info("Function \"" + metric.getFunction().toString() + "\" is not implemented yet or not valid.");
		}
	}

	private SimplePredicate parseSimplePredicate(final PredicateType predicate) {
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

	private boolean checkValidity(final List<PeriodType> validity) {
		Date startTime = validity.get(0).getStart().toGregorianCalendar().getTime();
		Date stopTime = validity.get(0).getEnd().toGregorianCalendar().getTime();
		Date now = new Date();

		return startTime.before(now) && stopTime.after(now);
	}
}