package pl.piomin.services.aws.order.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderMessage {

	@JsonProperty("default")
	private String defaultMsg;
	private String orderId;
	private String accountId;
	private int amount;

	public OrderMessage() {

	}

	public OrderMessage(String defaultMsg, String orderId, String accountId, int amount) {
		super();
		this.defaultMsg = defaultMsg;
		this.orderId = orderId;
		this.accountId = accountId;
		this.amount = amount;
	}

	public String getDefaultMsg() {
		return defaultMsg;
	}

	public void setDefaultMsg(String defaultMsg) {
		this.defaultMsg = defaultMsg;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
