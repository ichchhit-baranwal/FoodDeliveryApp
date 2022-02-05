package com.pods.delivery;

import java.util.Objects;

public class Order {
	private long orderId;
	private String status;

	private DeliveryAgent deliveryAgent; 
	public static final String DELIVERED="delivered"; 
	public static final String ASSIGNED="assigned"; 
	public static final String UNASSIGNED="unassigned";
	
	public Order(long id) {
		super();
		this.orderId = id;
		this.status = UNASSIGNED;
		this.deliveryAgent = null;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long id) {
		this.orderId = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DeliveryAgent getDeliveryAgent() {
		return deliveryAgent;
	}

	public void setDeliveryAgent(DeliveryAgent deliveryAgent) {
		this.deliveryAgent = deliveryAgent;
	}

	@Override
	public String toString() {
		return "Order [id=" + orderId + ", status=" + status + ", deliveryAgent=" + deliveryAgent + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return orderId == other.orderId;
	}
}
