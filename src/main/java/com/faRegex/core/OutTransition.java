package com.faRegex.core;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "destination", "link", "observable", "relevant" })
public class OutTransition {
	private String name;
	private String destination;
	private Link[] links;
	private String[] observable;
	private String[] relevant;
	
	public OutTransition() {}
	
	public OutTransition(String name, String destination, Link[] link, String[] observable, String[] relevant) {
		super();
		this.name = name;
		this.destination = destination;
		this.links = link;
		this.observable = observable;
		this.relevant = relevant;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Link[] getLink() {
		return links;
	}

	public void setLink(Link[] link) {
		this.links = link;
	}

	public String[] getObservable() {
		return observable;
	}

	public void setObservable(String[] observable) {
		this.observable = observable;
	}

	public String[] getRelevant() {
		return relevant;
	}

	public void setRelevant(String[] relevant) {
		this.relevant = relevant;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OutTransition)
			return equals((OutTransition)obj);
		
		return false;
	}
	
	
	
	private boolean equals(OutTransition other) {
		boolean temp = name.equals(other.getName()) && destination.equals(other.getDestination());
		
		if (temp ) {
			if (!equalArray(links, other.getLink())) return false;
			
			if (!equalArray(observable, other.getObservable())) return false;
			
			if (!equalArray(relevant, other.getRelevant())) return false;
			
			return true;
		}
		
		return false;
	}

	private boolean equalArray(Object[] local, Object[] external) {
		boolean bothNull=false;
		if (local == null || external== null)
			if (local != null || external != null) return false;
			else bothNull=true;
		if(!bothNull ) {
			if(local.length == external.length) {
				boolean exists=Arrays.stream(local).filter(link->{
					for (Object othVal: external) {
						if(link.equals(othVal)) {
							return true;
						}
					} return false;
				}).count() > 0;
				if (!exists) return false;
				
				exists=Arrays.stream(external).filter(link->{
					for (Object othVal: local) {
						if(link.equals(othVal)) {
							return true;
						}
					} return false;
				}).count() > 0;
				if (!exists) return false;
			}
			else return false;
		}
		return true;
	}
}
