package com.FA_regex;

import static org.junit.Assert.*;

import org.junit.Test;

import com.faRegex.core.ComportamentalFA;
import com.faRegex.core.ComportamentalFANetwork;
import com.faRegex.core.Link;
import com.faRegex.core.OutTransition;
import com.faRegex.core.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MarshallingUnmarshallingTest {

	@Test
	public void marshallUnmarshallEqual() {
    	Link[] links= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	String[] observable= {"o2"};
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", links, observable, null)};
    	State[] states= {new State("20", true, outTransition), new State("20", outTransition)};
    	ComportamentalFA[] compFA= {new ComportamentalFA("C2", states), new ComportamentalFA("C3", states)};
    	ComportamentalFANetwork cFAN=new ComportamentalFANetwork(null, compFA);
		
		ObjectMapper mapper=new ObjectMapper();
			try {
				assertTrue("L'oggetto ricostruito non coincide", mapper.readValue(mapper.writeValueAsString(cFAN), ComportamentalFANetwork.class)
						.equals(cFAN));
			} catch (JsonProcessingException e) {
				fail("Errore indefinito");
			}
	}

	@Test
	public void marshallUnmarshallNotEqual() {
    	Link[] links= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	String[] observable= {"o2"};
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", links, observable, null)};
    	State[] states= {new State("20", true, outTransition), new State("20", outTransition)};
    	ComportamentalFA[] compFA= {new ComportamentalFA("C2", states), new ComportamentalFA("C3", states)};
    	ComportamentalFA[] compFADifferent= {new ComportamentalFA("CX", states), new ComportamentalFA("CY", states)};
    	ComportamentalFANetwork cFAN=new ComportamentalFANetwork(null, compFA);
    	ComportamentalFANetwork cFANDifferent=new ComportamentalFANetwork(null, compFADifferent);
		
		ObjectMapper mapper=new ObjectMapper();
			try {
				assertFalse("L'oggetto ricostruito non coincide", mapper.readValue(mapper.writeValueAsString(cFAN), ComportamentalFANetwork.class)
						.equals(cFANDifferent));
			} catch (JsonProcessingException e) {
				fail("Errore indefinito");
			}
	}
}
