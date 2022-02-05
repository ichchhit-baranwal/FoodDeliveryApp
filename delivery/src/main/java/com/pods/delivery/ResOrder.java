package com.pods.delivery;

public class ResOrder {
	private long orderId;
	private String status;
	public ResOrder(long id, String status, long agentId) {
		super();
		this.orderId = id;
		this.status = status;
		this.agentId = agentId;
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
	public long getAgentId() {
		return agentId;
	}
	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}
	private long agentId; 
}
