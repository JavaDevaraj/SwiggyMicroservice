package com.stackroute.restaurantpartner.domain;

public class Agent {
	private String agentId;
	private String agentName;
	private AgentStatus agentStatus;
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public AgentStatus getAgentStatus() {
		return agentStatus;
	}
	public void setAgentStatus(AgentStatus agentStatus) {
		this.agentStatus = agentStatus;
	}
	
	
	
}
