package com.learn.boot.ProjectDemo.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;


@Document(collection="accountTransaction")
public class AccountTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountTransaction(){
		
	}
	
	public AccountTransaction(Long accountNumber, double amount) {
		super();
		this.accountNumber = accountNumber;
		this.amount = amount;
	}

	@Id
	private Long accountNumber;
	
	/*@Field("amount")
	@JsonInclude(JsonInclude.Include.ALWAYS)*/
	private double amount;
	

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountTransaction other = (AccountTransaction) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		return true;
	}

}
