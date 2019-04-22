package com.learn.boot.ProjectDemo.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userTransactionData")
public class UserTransactionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long idd;

	/*
	 * @Field("accountNumber")
	 * 
	 * @JsonInclude(JsonInclude.Include.ALWAYS)
	 */
	private Long accountNumber;

	/*
	 * @Field("amount")
	 * 
	 * @JsonInclude(JsonInclude.Include.ALWAYS)
	 */
	private double amount;

	public UserTransactionData() {

	}

	public UserTransactionData(Long id, Long accountNumber, double amount) {
		super();
		this.idd = id;
		this.accountNumber = accountNumber;
		this.amount = amount;
	}

	public Long getIdd() {
		return idd;
	}

	public void setIdd(Long idd) {
		this.idd = idd;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "UserTransactionData [idd=" + idd + ", accountNumber=" + accountNumber + ", amount=" + amount + "]";
	}

}
