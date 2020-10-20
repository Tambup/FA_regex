package com.faRegex.core;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Link {
	public enum Type {
		IN("in"),
		OUT("out");

		private String val;
		
		@Override
		public String toString() {return val;}
		
		private static Type lowerCaseValueOf(String arg0) {
			switch(arg0) {
			case "in": return IN;
			case "out": return OUT;
			default: throw new IllegalArgumentException();
			}
		}
		
		private Type(String string) {
			val = string;
		}
	}
	
	private Type type;
	private String link;
	private String event;
	
	public Link() {}
	
	public Link(Type type, String link, String event) {
		super();
		this.type = type;
		this.link = link;
		this.event = event;
	}
	
	public Type getType() {
		return type;
	}
	
	@JsonGetter("type")
	private String saveType() {
		return type.toString();
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	@JsonSetter("type")
	private void loadType(String type) {
		this.type = Type.lowerCaseValueOf(type);
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Link)
			return equals((Link)obj);
		
		return false;
	}
	
	public boolean equals(Link other) {
		return type.equals(other.getType()) && link.equals(other.getLink()) && event.equals(other.getEvent());
	}
}
