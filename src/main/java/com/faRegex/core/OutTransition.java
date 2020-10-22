package com.faRegex.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public List<String> outLinks() {
		ArrayList<String> lista=new ArrayList<String>();
		for (Link link: links)
			if (link.getType().equals(Link.Type.OUT) && !lista.contains(link.getLink()))
				lista.add(link.getLink());
		return lista;
	}
	
	public List<String> inLinks() {
		ArrayList<String> lista=new ArrayList<String>();
		for (Link link: links)
			if (link.getType().equals(Link.Type.IN) && !lista.contains(link.getLink()))
				lista.add(link.getLink());
		return lista;
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

	public boolean check() {
		if(links == null) return true;
		boolean inLinkExist=false;
		
		for (Link link: links) {
			if (link == null) return false;
			if (link.getType().equals(Link.Type.IN)) {
				if (inLinkExist)
					return false;
				inLinkExist=true;
			}
		}
		
		for (Link link: links) 
			for (Link innerLink: links)
				if (link != innerLink)
					if (link.getType().equals(Link.Type.OUT) && link.getType().equals(Link.Type.OUT))
						if (link.getLink().equals(innerLink.getLink()))
							return false;
		
		return true;
	}
	
	public boolean sameEvents(Link[] othLinks) {
		boolean bothNull=false;
		if (links == null || othLinks== null)
			if (links != null || othLinks != null) return false;
			else bothNull=true;
		if (!bothNull ) {
			for (Link link: links) {
				boolean isContained=false;
				for (Link outLink: othLinks)
					if (link.getEvent().equals(outLink.getEvent())) {
						if (link.getType().equals(outLink.getType())) {
							isContained=true;
							break;
						}
					}
				if (!isContained) return false;
			}
			for (Link outLink: othLinks) {
				boolean isContained=false;
				for (Link link: links)
					if (link.getEvent().equals(outLink.getEvent())) {
						if (link.getType().equals(outLink.getType())) {
							isContained=true;
							break;
						}
					}
				if (!isContained) return false;
			}
			return true;
		}
		return false;
	}
}
