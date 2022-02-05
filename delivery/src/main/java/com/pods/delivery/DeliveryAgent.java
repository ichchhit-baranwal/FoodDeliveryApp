package com.pods.delivery;

import java.util.Objects;

public class DeliveryAgent {
	private long agentId;
	private String status;
	public static final String SIGNEDOUT="signed-out"; 
	public static final String AVAILABLE="available"; 
	public static final String UNAVAILABLE="unavailable";
	public DeliveryAgent() {
		super();
		status = SIGNEDOUT;
	}
	public DeliveryAgent(long id) {
		super();
		this.agentId = id;
		this.status = SIGNEDOUT;
	}
	public long getAgentId() {
		return agentId;
	}
	public void setAgentId(long id) {
		this.agentId = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return Objects.hash(agentId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeliveryAgent other = (DeliveryAgent) obj;
		return agentId == other.agentId;
	}
	@Override
	public String toString() {
		return "DeliveryAgent [id=" + agentId + ", status=" + status + "]";
	} 
}
