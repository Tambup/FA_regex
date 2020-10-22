package com.faRegex.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "init", "outTransition" })
public class State {
	private String name;
	private boolean isInit;
	private OutTransition[] outTransitions;
	
	public State() {}
	
	public State(String name, boolean isInit, OutTransition[] outTransition) {
		super();
		this.name = name;
		this.isInit = isInit;
		this.outTransitions = outTransition;
	}
	
	public State(String name, OutTransition[] outTransition) {
		super();
		this.name = name;
		this.isInit = false;
		this.outTransitions = outTransition;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isInit() {
		return isInit;
	}
	
	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}
	
	public OutTransition[] getOutTransition() {
		return outTransitions;
	}
	
	public void setOutTransition(OutTransition[] outTransition) {
		this.outTransitions = outTransition;
	}

	public List<String> outLinks() {
		ArrayList<String> list=new ArrayList<String>();
		for (OutTransition outTrans: outTransitions)
			for (String string: outTrans.outLinks())
				if (!list.contains(string))
					list.add(string);
		return list;
	}
	
	public List<String> inLinks() {
		ArrayList<String> list=new ArrayList<String>();
		for (OutTransition outTrans: outTransitions)
			for (String string: outTrans.inLinks())
				if (!list.contains(string))
					list.add(string);
		return list;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof State)
			return equals((State)obj);
		
		return false;
	}
	
	private boolean equals(State other) {
		boolean temp = name.equals(other.getName()) && isInit == other.isInit();
		if (outTransitions == null || other.getOutTransition() == null)
			return (outTransitions != null || other.getOutTransition() != null) ? false : true;

		if (temp && outTransitions.length == other.getOutTransition().length) {
			for (OutTransition trans: outTransitions) {
				boolean exist=false;
				for (OutTransition othTrans: other.getOutTransition()) {
					if(trans.equals(othTrans)) {
						exist=true;
						break;
					}
				}
				if (!exist)
					return false;
			}
			for (OutTransition othTrans: other.getOutTransition()) {
				boolean exist=false;
				for (OutTransition trans: outTransitions) {
					if(othTrans.equals(trans)) {
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
		if (outTransitions == null) return true;
		if (name == null) return false;
		for(OutTransition outTrans: outTransitions)
			if (outTrans == null) return false;
			else if (!outTrans.check()) return false;
		
		for(OutTransition outTrans: outTransitions)
			for(OutTransition innerOutTrans: outTransitions)
				if (outTrans != innerOutTrans)
					if(outTrans.getName().equals(innerOutTrans.getName()))
						if(outTrans.sameEvents(innerOutTrans.getLink()))
							return false;
		return true;
	}

	public boolean noExit() {
		if (outTransitions == null) return true;
		for (OutTransition outTrans: outTransitions) {
			if (!outTrans.getDestination().equals(name))
				return false;
		}
		return true;
	}
	
}
