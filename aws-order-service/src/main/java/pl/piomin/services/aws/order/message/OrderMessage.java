package pl.piomin.services.aws.order.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderMessage {

	@JsonProperty("default")
	private String defaultVal;

	public OrderMessage() {
	
	}
	
	public OrderMessage(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	
}
