package at.tuwien.monitoring.server.processing.mapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import com.ibm.wsla.Average;
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

import at.tuwien.common.Settings;
import at.tuwien.monitoring.server.processing.MetricProcessor;
import at.tuwien.monitoring.server.wsla.WebServiceLevelAgreement;

public class Wsla2ExpressionMapper {

	private final static Logger logger = Logger.getLogger(Wsla2ExpressionMapper.class);

	private static final int AGGREGATION_MINIMUM_EVENT_SIZE = 3;
	private static final int THROUGHPUT_EVAL_TIME = 10; // seconds

	private static final String SIMPLE_EXPRESSION = "select %s as monitoredvalue, '%s' as metrictype, '%s' as requirementdesc, * from %s%s having %s %s %s";
	private static final String AGGREGATION_FUNCTION_EXPRESSION = "select %s(%s) as monitoredvalue, '%s' as metrictype, '%s' as requirementdesc, * from %s%s group by serviceName having %s(%s) %s %s AND count(*) >= "
			+ AGGREGATION_MINIMUM_EVENT_SIZE;

	private static final String RATIO_FUNCTION = "count(*, %s %s %s)/count(*)";

	private static final String OUTPUT_SNAPSHOT = "output snapshot every %d sec";

	private static final String WINDOW_TIME_SEC = "#time(%d sec)";

	@SuppressWarnings("unused")
	private static final String WINDOW_TIME_MIN = "#time(%d min)";

	@SuppressWarnings("unused")
	private static final String WINDOW_LENGTH = "#length(%d)";

	// map of basic metrics and their corresponding messages and variables
	private static Map<String, MetricInformation> basicMetricMap;
	static {
		basicMetricMap = new HashMap<>();
		basicMetricMap.put("responsetime", new MetricInformation("ClientInfoMessage", "responseTime"));
		basicMetricMap.put("successability", new MetricInformation("ClientInfoMessage", "responseCode"));
		basicMetricMap.put("throughput",
				new MetricInformation("ServerInfoMessage", "count(*)/" + THROUGHPUT_EVAL_TIME));
		basicMetricMap.put("cpuusage", new MetricInformation("CpuMessage", "cpuUsagePerc"));
		basicMetricMap.put("memoryusage", new MetricInformation("MemoryMessage", "memoryUsagePerc"));
	}

	private MetricProcessor metricProcessor;

	public Wsla2ExpressionMapper(MetricProcessor metricProcessor, Settings settings) {
		this.metricProcessor = metricProcessor;

		if (settings.logMetrics) {
			addSnapshotStatementsForLogging();
		}
	}

	public void mapAgreement(WebServiceLevelAgreement wsla) {

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
		for (ServiceLevelObjectiveType serviceLevelObjective : wsla.getWSLA().getObligations()
				.getServiceLevelObjective()) {
			if (!checkValidity(serviceLevelObjective.getValidity())) {
				logger.info("Validity of SLO \"" + serviceLevelObjective.getName() + "\" not given. Ignoring SLO.");
				continue;
			}

			SimplePredicate simplePredicate = parseSimplePredicate(
					serviceLevelObjective.getExpression().getPredicate());
			if (simplePredicate == null) {
				logger.info("Predicate of SLO \"" + serviceLevelObjective.getName()
						+ "\" not implemented yet or not valid.");
				continue;
			}

			String metricToObserve = slaMetricMap.get(simplePredicate.getSlaParameter());
			if (metricToObserve == null) {
				logger.info(
						"SLA parameter for SLO \"" + serviceLevelObjective.getName() + "\" not defined. Ignoring SLO.");
				continue;
			}

			MetricInformation metricInformation = processingMetricMap.get(metricToObserve);
			createExpression(metricToObserve, metricInformation, simplePredicate);
		}
	}

	private void createExpression(String metricName, MetricInformation metricInformation,
			SimplePredicate simplePredicate) {

		String expression = null;
		String filter = String.format("(%s >= 0)", metricInformation.getPropertyName());
		if (metricInformation.getPropertyName().startsWith("count(*)")) {
			// throughput
			filter = String.format(WINDOW_TIME_SEC, THROUGHPUT_EVAL_TIME);
		}

		if (metricInformation.getPropertyName().toLowerCase().equals("responsecode")) {
			// successrate (detection currently hardcoded)
			String requirementDesc = simplePredicate.getPredicateSign() + simplePredicate.getThreshold();
			String aggregationFunction = metricInformation.getAggregationFunction() == null ? ""
					: metricInformation.getAggregationFunction();

			// success -> status code begins with 2 (i.e 200 ok)
			String successPattern = "'2%'";
			String ratioFunction = String.format(RATIO_FUNCTION, metricInformation.getPropertyName(), "like",
					successPattern);

			expression = String.format(AGGREGATION_FUNCTION_EXPRESSION, aggregationFunction, ratioFunction, metricName,
					requirementDesc, metricInformation.getEventMessageName(), filter, aggregationFunction,
					ratioFunction, simplePredicate.getDetectionSign(), simplePredicate.getThreshold());

		} else if (metricInformation.getAggregationFunction() == null) {
			// simple function
			String requirementDesc = simplePredicate.getPredicateSign() + simplePredicate.getThreshold();

			expression = String.format(SIMPLE_EXPRESSION, metricInformation.getPropertyName(), metricName,
					requirementDesc, metricInformation.getEventMessageName(), filter,
					metricInformation.getPropertyName(), simplePredicate.getDetectionSign(),
					simplePredicate.getThreshold());

		} else {
			// aggregation function
			String requirementDesc = metricInformation.getAggregationFunction() + simplePredicate.getPredicateSign()
					+ simplePredicate.getThreshold();

			expression = String.format(AGGREGATION_FUNCTION_EXPRESSION, metricInformation.getAggregationFunction(),
					metricInformation.getPropertyName(), metricName, requirementDesc,
					metricInformation.getEventMessageName(), filter, metricInformation.getAggregationFunction(),
					metricInformation.getPropertyName(), simplePredicate.getDetectionSign(),
					simplePredicate.getThreshold());
		}

		if (metricInformation.getPropertyName().startsWith("count(*)")) {
			// throughput
			expression += " " + String.format(OUTPUT_SNAPSHOT, THROUGHPUT_EVAL_TIME);
		}

		metricProcessor.addExpression(expression);
	}

	private void parseSimpleMetric(MetricType metric, Map<String, MetricInformation> processingMetricMap) {
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
	private void parseComplexMetric(MetricType metric, Map<String, MetricInformation> processingMetricMap) {

		MetricInformation metricInformation = new MetricInformation();
		String basicMetricName = null;

		// resolve functions
		if (metric.getFunction() instanceof Mean) {
			Mean mean = (Mean) metric.getFunction();
			metricInformation.setAggregationFunction("avg");
			basicMetricName = mean.getMetric();
		} else if (metric.getFunction() instanceof Average) {
			Average average = (Average) metric.getFunction();
			metricInformation.setAggregationFunction("avg");
			basicMetricName = average.getMetric();
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

	private void addSnapshotStatementsForLogging() {
		// successability
		String successPattern = "'2%'";
		String ratioFunction = String.format(RATIO_FUNCTION, basicMetricMap.get("successability").getPropertyName(),
				"like", successPattern);
		String expressionSuccessability = "select " + ratioFunction
				+ " as monitoredvalue, 'successability' as logging, * from "
				+ basicMetricMap.get("successability").getEventMessageName();
		metricProcessor.addExpression(expressionSuccessability);

		// throughput
		String expressionThroughput = "select count(*) as monitoredvalue, 'throughput' as logging, * from ClientInfoMessage#time(1 sec) output last every 1 sec";
		metricProcessor.addExpression(expressionThroughput);
	}

	private boolean checkValidity(List<PeriodType> validity) {
		Date startTime = validity.get(0).getStart().toGregorianCalendar().getTime();
		Date stopTime = validity.get(0).getEnd().toGregorianCalendar().getTime();
		Date now = new Date();

		return startTime.before(now) && stopTime.after(now);
	}
}