package at.tuwien.monitoring.server.processing.mapping;

class MetricInformation {
	private String eventMessageName;
	private String propertyName;
	private String aggregationFunction;

	public MetricInformation() {
	}

	public MetricInformation(final String eventMessageName, final String propertyName) {
		setEventMessageName(eventMessageName);
		setPropertyName(propertyName);
	}

	public MetricInformation(final String eventMessageName, final String propertyName, final String aggregationFunction) {
		setEventMessageName(eventMessageName);
		setPropertyName(propertyName);
		setAggregationFunction(aggregationFunction);
	}

	public String getEventMessageName() {
		return eventMessageName;
	}

	public void setEventMessageName(final String eventMessageName) {
		this.eventMessageName = eventMessageName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	public String getAggregationFunction() {
		return aggregationFunction;
	}

	public void setAggregationFunction(final String aggregationFunction) {
		this.aggregationFunction = aggregationFunction;
	}

	@Override
	public String toString() {
		return "MetricInformation [eventMessageName=" + eventMessageName + ", propertyName=" + propertyName + ", aggregationFunction="
				+ aggregationFunction + "]";
	}
}