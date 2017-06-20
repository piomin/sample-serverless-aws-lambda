package pl.piomin.services.aws.account.model;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 8331074361667921244L;
	private Integer id;
	private String number;
	private Integer customerId;

	public Account() {
		
	}
	
	public Account(Integer id, String number, Integer customerId) {
		this.id = id;
		this.number = number;
		this.customerId = customerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
