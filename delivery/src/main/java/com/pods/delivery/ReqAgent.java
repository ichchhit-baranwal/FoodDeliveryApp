package com.pods.delivery;

/**
 * Class for data transferring
 * of agents data over
 * the http request
 */

public class ReqAgent {
	private int agentId;

	/**
	 * @return agentId
	 */
	public int getAgentId() {
		return agentId;
	}

	/**
	 * sets the agentId for the agent
	 * @param agentId
	 */
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
}
