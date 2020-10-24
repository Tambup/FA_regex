package com.faRegex.core;

import java.util.ArrayList;
import java.util.List;

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

	public List<String> outLinks() {
		ArrayList<String> list=new ArrayList<String>();
		for (State outTrans: states)
			for (String string: outTrans.outLinks())
				if (!list.contains(string))
					list.add(string);
		return list;
	}
	
	public List<String> inLinks() {
		ArrayList<String> list=new ArrayList<String>();
		for (State state: states)
			for (String string: state.inLinks())
				if (!list.contains(string))
					list.add(string);
		return list;
	}
	
	public State initState() {
		for (State state: states)
			if (state.isInit())
				return state;
		return null;
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

	public boolean check() {
		if (states == null) return false;
		if (states.length < 1)
			return false;
		byte hasInit=0;
		for (State state: states)
			if (state == null) return false;
			else if (!state.check()) return false;
		
		for (State state: states) {
			if (states.length > 1)
				if (state.noExit() && noEnter(state))
					return false;
			hasInit = state.isInit() ? ++hasInit : hasInit;
			for(State innerState: states)
				if (state != innerState)
					if(state.getName() == null || innerState.getName() == null)
						return false;
					else if (state.getName().equals(innerState.getName()))
						return false;
			
			for (OutTransition outTrans: state.getOutTransition()) {
				boolean mustContinue=false;
				for(State innerState: states) {
					if (outTrans.getDestination().equals(innerState.getName())) {
						mustContinue=true;
						break;
					}
				}
				if (!mustContinue)
					return false;
			}
		}
		return hasInit == 1;
	}
	
	private boolean noEnter(State state) {
		for (State innerState: states) {
			if (state != innerState)
				if (innerState.getOutTransition() != null)
					for (OutTransition outTrans: innerState.getOutTransition())
						if (outTrans.getDestination().equals(state.getName()))
							return false;
		}
		return true;
	}
}
