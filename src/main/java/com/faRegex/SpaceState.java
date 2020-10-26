package com.faRegex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.faRegex.core.Link;
import com.faRegex.core.OutTransition;
import com.faRegex.core.State;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

class SpaceState implements Cloneable{
	@JsonIgnore
	private static final String NULL_EVT = "\u03B5";
	
	private HashMap<String, String> links = new HashMap<String, String>();
	private List<State> states = new ArrayList<State>();
	private HashMap<OutTransition, SpaceState> nexts = new HashMap<OutTransition, SpaceState>();
	private String name = null;
	
	SpaceState(List<State> states, String[] links) {
		Arrays.stream(links).forEach(linkName -> this.links.put(linkName, NULL_EVT));
		this.states.addAll(states);
	}
	
	SpaceState(List<State> states, Map<String, String> links) {
		this.links.putAll(links);
		this.states.addAll(states);
	}
	
	@JsonGetter("links")
	public HashMap<String, String> jsonGetLinks() {
		return links;
	}
	
	@JsonGetter("states")
	public HashMap<String, String> jsonGetStates() {
		HashMap<String, String> statesMap= new HashMap<String, String>();
		int i= 0;
		for(State s: states)
			statesMap.put("CFAN " + i++, s.getName());
		
		return statesMap;
	}
	
	@JsonGetter("nexts")
	public HashMap<String, String> jsonGetNexts() {
		HashMap<String, String> nextsMap= new HashMap<String, String>();
		for(OutTransition outTrans: nexts.keySet())
			nextsMap.put(outTrans.getName(), nexts.get(outTrans).getName());
		
		return nextsMap;
	}
	
	public String getName() {
		if (name != null)
			return name;
		
		return jsonToStringName();
	}

	private String jsonToStringName() {
		StringBuilder temp= new StringBuilder();
		int i= 0;
		for(State s: states)
			temp.append("CFAN " + i++ + ": " + s.getName() + " ");
		
		for(String linkName: links.keySet())
			temp.append(linkName + ": " + links.get(linkName) + " ");
		
		return temp.toString();
	}

	HashMap<State, List<OutTransition>> nextTransitionPerState() {
		HashMap<State, List<OutTransition>> possibleNextState = new HashMap<State, List<OutTransition>>();
		for (State nextState: states) {
			possibleNextState.put(nextState, new ArrayList<OutTransition>());
			for (OutTransition nextOutTrans: nextState.getOutTransition()) {
				if (mustAdd(nextOutTrans)) 
					possibleNextState.get(nextState).add(nextOutTrans);
			}
			int size=possibleNextState.get(nextState).size();
			if (size == 0) possibleNextState.remove(nextState);
		}
		return possibleNextState;
	}

	private boolean mustAdd(OutTransition nextOutTrans) {
		boolean mustAdd=true;
		for (Link nextLink: nextOutTrans.getLink()) {
			if(nextLink.getType().equals(Link.Type.IN)) {
				if (!links.get(nextLink.getLink()).equals(nextLink.getEvent())) {
					mustAdd=false;
					break;
				}
			} else if (nextLink.getType().equals(Link.Type.OUT)) {
				if (!links.get(nextLink.getLink()).equals(NULL_EVT)) {
					mustAdd=false;
					break;
				}
			}
		}
		return mustAdd;
	}
	
	HashMap<String, String> getLinks() {
		return links;
	}
	
	List<State> getStates() {
		return states;
	}

	void changeState(State oldState, State newState) {
		states.set(states.indexOf(oldState), newState);
	}
	
	void setLink(String linkName, String linkEvt) {
		links.put(linkName, linkEvt);
	}
	
	void clearLink(String linkName) {
		links.put(linkName, NULL_EVT);
	}
	
	void addNext(OutTransition transition, SpaceState next) {
		nexts.put(transition, next);
	}
	
	HashMap<OutTransition, SpaceState> getNext() {
		return nexts;
	}
	
	boolean isFinal() {
		for (String name: links.keySet())
			if(!links.get(name).equals(NULL_EVT))
				return false;
		return true;
	}
	
	@Override
	public Object clone() {
		return new SpaceState(this.states, this.links);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SpaceState)
			return equals((SpaceState)obj);
		
		return false;
	}

	public boolean equals(SpaceState oth) {
		if (states.size() != oth.getStates().size())
				return false;
		
		for (int i=0; i<states.size(); i++)
			if (!states.get(i).equals(oth.getStates().get(i)))
				return false;
		
		if (links.size()!= oth.getLinks().size())
			return false;
		
		for (String name: links.keySet())
			if (!links.get(name).equals(oth.getLinks().get(name)))
				return false;
			
		for (String name: oth.getLinks().keySet())
			if (!links.get(name).equals(oth.getLinks().get(name)))
				return false;
		
		return true;
	}
	
	public boolean deepEqual(SpaceState oth) {
		if (!equals(oth)) return false;
		
		if (nexts.size()!= oth.getNext().size())
			return false;
		
		for (OutTransition name: nexts.keySet())
			if (!nexts.get(name).equals(oth.getNext().get(name)))
				return false;
			
		for (OutTransition name: oth.getNext().keySet())
			if (!nexts.get(name).equals(oth.getNext().get(name)))
				return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder string=new StringBuilder();
		for (State state: states)
			string.append(state.getName() + " ");
		
		for (String name: links.keySet())
			string.append(name + "=" + links.get(name) + " ");
		
		string.append("\n\n");
		for (OutTransition outTrans: nexts.keySet())
			string.append(outTrans.getName() + " ");
		
		return string.toString();
	}
}