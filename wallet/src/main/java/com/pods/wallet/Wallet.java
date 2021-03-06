package com.pods.wallet;

import java.util.Objects;
/**
 * This class acts a bean to store wallet information 
 * Contains to instance attributes
 * custId to identify customer to which the wallet belongs
 * amount the balance present in the wallet
 */
public class Wallet {
	private long custId;
	private int amount;
	
	/**
	 * @param custId
	 * @param amount
	 */
	public Wallet(long custId, int amount) {
		super();
		this.custId = custId;
		this.amount = amount;
	}
	/**
	 * @return custId to which wallet belongs to
	 */
	public long getCustId() {
		return custId;
	}
	/**
	 * @param custId
	 */
	public void setCustId(long custId) {
		this.custId = custId;
	}
	/**
	 * @return amount present in the wallet
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * For printing purpose to 
	 * so as to change content of the object
	 * to string equivalent
	 */
	@Override
	public String toString() {
		return "Wallet [custId=" + custId + ", amount=" + amount + "]";
	}
	/**
	 * creates the hash function
	 * based on the customer id
	 */
	@Override
	public int hashCode() {
		return Objects.hash(custId);
	}
	/**
	 * compare two objects 
	 * of type wallet based on 
	 * custId only
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wallet other = (Wallet) obj;
		return custId == other.custId;
	}

	
	
}
