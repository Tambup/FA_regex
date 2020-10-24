package com.faRegex.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

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

	public String[] getLinksNames() {
		ArrayList<String> linksNames = new ArrayList<String>();
		Arrays.stream(comportamentalFAs).flatMap(
				mapper -> mapper.inLinks().stream()
				).filter(Predicate.not(linksNames::contains)).forEach(linksNames::add);

		return linksNames.toArray(new String[linksNames.size()]);
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

	
	public boolean check() {
		if (comportamentalFAs == null) return false;
		if (comportamentalFAs.length < 1)
			return false;
		for (ComportamentalFA compFa: comportamentalFAs)
			if (compFa==null) return false;
		
		
		for (ComportamentalFA compFa: comportamentalFAs) {
			if (!compFa.check())
				return false;
			
			if(comportamentalFAs.length > 1) {
				for (String inLink: compFa.inLinks()) {
					byte numOut=0;
					for (ComportamentalFA innerCompFa: comportamentalFAs)
						if (compFa != innerCompFa) {
							for (String innerInLink: innerCompFa.inLinks())
								if (inLink.equals(innerInLink)) return false;
							
							
							for (String innerOutLink: innerCompFa.outLinks()) {
								if (inLink.equals(innerOutLink))
									numOut++;
								if (numOut >= 2) return false;
							}
						}
					
					if (numOut != 1) return false;
				}
				
				for (String outLink: compFa.outLinks()) {
					byte numIn=0;
					for (ComportamentalFA innerCompFa: comportamentalFAs)
						if (compFa != innerCompFa) {
							for (String innerOutLink: innerCompFa.outLinks())
								if (outLink.equals(innerOutLink)) return false;
							
							for (String innerInLink: innerCompFa.inLinks()) {
								if (outLink.equals(innerInLink))
									numIn++;
								if (numIn >= 2) return false;
							}
						}
					
					if (numIn != 1) return false;
				}
			}
		}
		return true;
	}
	
}
