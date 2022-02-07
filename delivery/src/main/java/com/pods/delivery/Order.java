package com.pods.delivery;

import java.util.Objects;

/**
 * The class represents the Order entity
 * It contains the attributes order id 
 * status which must be one of delivered,
 * assigned, unassigned and refer to the object
 * Of agent id assigned to order
 */
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

	/**
	 * @return orderId
	 */
	public long getOrderId() {
		return orderId;
	}

	/**
	 * sets order id
	 * @param id
	 */
	public void setOrderId(long id) {
		this.orderId = id;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * sets staus of the order
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return deliveryAgent
	 */
	public DeliveryAgent getDeliveryAgent() {
		return deliveryAgent;
	}

	/**
	 * sets delivery agent for the order
	 * @param deliveryAgent
	 */
	public void setDeliveryAgent(DeliveryAgent deliveryAgent) {
		this.deliveryAgent = deliveryAgent;
	}

	/**
	 * For printing purpose to 
	 * so as to change content of the object
	 * to string equivalent
	 */
	@Override
	public String toString() {
		return "Order [id=" + orderId + ", status=" + status + ", deliveryAgent=" + deliveryAgent + "]";
	}

	/**
	 * generates the hash code of the
	 * the object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}
	
	/**
	 * Compares the object of to another object
	 * uses the orderId for comparison
	 */
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
