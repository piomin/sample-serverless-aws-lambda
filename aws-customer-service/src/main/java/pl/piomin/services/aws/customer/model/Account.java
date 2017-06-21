package pl.piomin.services.aws.customer.model;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 8331074361667921244L;
	private String id;
	private String number;
	private String customerId;

	public Account() {
		
	}
	
	public Account(String customerId) {
		this.customerId = customerId;
	}

	public Account(String id, String number, String customerId) {
		this.id = id;
		this.number = number;
		this.customerId = customerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
