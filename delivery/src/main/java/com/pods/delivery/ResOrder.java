package com.pods.delivery;
/**
 * The class represent the order
 * which should be sent as response
 * contains fields orderId, status
 * agentId
 */
public class ResOrder {
	private long orderId;
	private String status;
	public ResOrder(long id, String status, long agentId) {
		super();
		this.orderId = id;
		this.status = status;
		this.agentId = agentId;
	}
	
	/**
	 * @return orderId
	 */
	public long getOrderId() {
		return orderId;
	}
	
	/**
	 * sets orderId field
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
	 * sets status field
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return agentId
	 */
	public long getAgentId() {
		return agentId;
	}
	
	/**
	 * sets agentId field
	 * @param agentId
	 */
	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}
	private long agentId; 
}
