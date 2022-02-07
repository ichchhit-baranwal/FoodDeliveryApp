package com.pods.delivery;

import java.util.Objects;
/**
 * class corresponding to agent 
 * It has fields agentId
 * and status which only takes signed-out,
 * available, unavailable
 */
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
	
	/**
	 * @return agentId
	 */
	public long getAgentId() {
		return agentId;
	}
	
	/**
	 * sets agentId field
	 * @param id
	 */
	public void setAgentId(long id) {
		this.agentId = id;
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
	 * generates the hash code of the
	 * the object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(agentId);
	}
	
	/**
	 * Compares the object of to another object
	 * uses the agentId for comparison
	 */
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
	
	/**
	 * For printing purpose to 
	 * so as to change content of the object
	 * to string equivalent
	 */
	@Override
	public String toString() {
		return "DeliveryAgent [id=" + agentId + ", status=" + status + "]";
	} 
}
