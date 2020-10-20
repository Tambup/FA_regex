package com.faRegex.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComportamentalFANetwork {
	private String name;
	@JsonProperty("comportamentalFA")
	private ComportamentalFA[] comportamentalFAs;
	
	public ComportamentalFANetwork() {}
	
	public ComportamentalFANetwork(String name, ComportamentalFA[] comportamentalFAs) {
		this.name = name;
		this.comportamentalFAs = comportamentalFAs;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ComportamentalFA[] getComportamentalFAs() {
		return comportamentalFAs;
	}
	
	public void setComportamentalFAs(ComportamentalFA[] comportamentalFAs) {
		this.comportamentalFAs = comportamentalFAs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComportamentalFANetwork)
			return equals((ComportamentalFANetwork)obj);
		
		return false;
	}
	
	private boolean equals(ComportamentalFANetwork other) {
		boolean temp=false;
		if (name == null || other.getName() == null)
			if (name != null || other.getName() != null) return false;
			else temp=true;
		if(!temp)
			temp = name.equals(other.getName());
		
		if (comportamentalFAs == null || other.getComportamentalFAs() == null)
			return (comportamentalFAs != null || other.getComportamentalFAs() != null) ? false : true;
		
		if (temp && comportamentalFAs.length == other.getComportamentalFAs().length) {
			for (ComportamentalFA local: comportamentalFAs) {
				boolean exist=false;
				for (ComportamentalFA external: other.getComportamentalFAs()) {
					if(local.equals(external)) {
						exist=true;
						break;
					}
				}
				if (!exist)
					return false;
			}
			for (ComportamentalFA external: other.getComportamentalFAs()) {
				boolean exist=false;
				for (ComportamentalFA local: comportamentalFAs) {
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
