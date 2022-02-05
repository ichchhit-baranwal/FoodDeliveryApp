package com.pods.wallet;

import java.util.Objects;

public class Wallet {
	private long custId;
	private int amount;
	
	
	public Wallet(long custId, int amount) {
		super();
		this.custId = custId;
		this.amount = amount;
	}
	public long getCustId() {
		return custId;
	}
	public void setCustId(long custId) {
		this.custId = custId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Wallet [custId=" + custId + ", amount=" + amount + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(custId);
	}
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
