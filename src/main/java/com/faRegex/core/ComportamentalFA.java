package com.faRegex.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComportamentalFA {
	private String name;
	@JsonProperty("state")
	private State[] states;
	
	public ComportamentalFA() {}
	
	public ComportamentalFA(String name, State[] states) {
		super();
		this.name = name;
		this.states = states;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public State[] getStates() {
		return states;
	}

	public void setStates(State[] states) {
		this.states = states;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComportamentalFA)
			return equals((ComportamentalFA)obj);
		
		return false;
	}
	
	private boolean equals(ComportamentalFA other) {
		boolean temp = name.equals(other.getName());
		if (states == null || other.getStates() == null)
			return (states != null || other.getStates() != null) ? false : temp && true;
		
		if (temp && states.length == other.getStates().length) {
			for (State local: states) {
				boolean exist=false;
				for (State external: other.getStates()) {
					if(local.equals(external)) {
						exist=true;
						break;
					}
				}
				if (!exist)
					return false;
			}
			for (State external: other.getStates()) {
				boolean exist=false;
				for (State local: states) {
					if(external.equals(local)) {
						exist=true;
						break;
					}
				}
				if (!exist)
					return false;
			}
			return true;
		}
		
		return false;
	}
	
}
