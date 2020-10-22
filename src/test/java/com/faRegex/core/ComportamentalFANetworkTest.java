package com.faRegex.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComportamentalFANetworkTest {

	@Test
	public void validCheck() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "23", links1, null, null)};
    	State[] states= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("23", false, outTransition2)};
    	ComportamentalFA[] compFAs= {new ComportamentalFA("C2", states)};
    	ComportamentalFANetwork compFAN=new ComportamentalFANetwork("Mesto", compFAs);
    	assertTrue(compFAN.check());
	}

	@Test
	public void checkNullComportamentalFAs() {
    	ComportamentalFA[] compFAs= null;
    	ComportamentalFANetwork compFAN=new ComportamentalFANetwork("Mesto", compFAs);
    	assertFalse(compFAN.check());
	}
	
	@Test
	public void checkNullComportamentalFA() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "23", links1, null, null)};
    	State[] states= {new State("22", false, outTransition2), new State("23", false, outTransition2), null};
    	ComportamentalFA[] compFAs= {new ComportamentalFA("C2", states)};
    	ComportamentalFANetwork compFAN=new ComportamentalFANetwork("Mesto", compFAs);
    	assertFalse(compFAN.check());
	}
	
	@Test
	public void checkZeroComportamentalFA() {
    	ComportamentalFA[] compFAs= {};
    	ComportamentalFANetwork compFAN=new ComportamentalFANetwork("Mesto", compFAs);
    	assertFalse(compFAN.check());
	}
	
	@Test
	public void checkCorrectLinks() {
		Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L5", "e3"), new Link(Link.Type.OUT, "L6", "e7")};
    	Link[] links2= {new Link(Link.Type.IN, "L5", "e3"), new Link(Link.Type.OUT, "L2", "e3")};
    	Link[] links3= { new Link(Link.Type.IN, "L6", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "23", links1, null, null)};
    	OutTransition[] outTransition3= {new OutTransition("t2a", "26", links2, null, null)};
    	OutTransition[] outTransition4= {new OutTransition("t2a", "27", links3, null, null)};
    	State[] states1= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("23", false, outTransition2)};
    	State[] states2= {new State("25", true, outTransition3), new State("26", false, outTransition4), new State("27", false, outTransition4)};
    	ComportamentalFA[] compFAs= {new ComportamentalFA("C2", states1), new ComportamentalFA("C3", states2)};
    	ComportamentalFANetwork compFAN=new ComportamentalFANetwork("Mesto", compFAs);
    	assertTrue(compFAN.check());
	}
	
	@Test
	public void checkLinkGoesNowhere() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L5", "e3"), new Link(Link.Type.OUT, "L6", "e7")};
    	Link[] links2= {new Link(Link.Type.IN, "L5", "e3"), new Link(Link.Type.OUT, "L2", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "23", links1, null, null)};
    	OutTransition[] outTransition3= {new OutTransition("t2a", "26", links2, null, null)};
    	OutTransition[] outTransition4= {new OutTransition("t2a", "27", links2, null, null)};
    	State[] states1= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("23", false, outTransition2)};
    	State[] states2= {new State("25", true, outTransition3), new State("26", false, outTransition4), new State("27", false, outTransition4)};
    	ComportamentalFA[] compFAs= {new ComportamentalFA("C2", states1), new ComportamentalFA("C3", states2)};
    	ComportamentalFANetwork compFAN=new ComportamentalFANetwork("Mesto", compFAs);
    	assertFalse(compFAN.check());
	}
	
	@Test
	public void checkLinkFromNowhere() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L6", "e3")};
    	Link[] links2= {new Link(Link.Type.IN, "L5", "e3"), new Link(Link.Type.OUT, "L2", "e3")};
    	Link[] links3= { new Link(Link.Type.IN, "L6", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "23", links1, null, null)};
    	OutTransition[] outTransition3= {new OutTransition("t2a", "26", links2, null, null)};
    	OutTransition[] outTransition4= {new OutTransition("t2a", "27", links3, null, null)};
    	State[] states1= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("23", false, outTransition2)};
    	State[] states2= {new State("25", true, outTransition3), new State("26", false, outTransition4), new State("27", false, outTransition4)};
    	ComportamentalFA[] compFAs= {new ComportamentalFA("C2", states1), new ComportamentalFA("C3", states2)};
    	ComportamentalFANetwork compFAN=new ComportamentalFANetwork("Mesto", compFAs);
    	assertFalse(compFAN.check());
	}
	
	@Test
	public void checkAutolinkDoesntCount() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L2", "e3")};
    	Link[] links2= {new Link(Link.Type.IN, "L5", "e3"), new Link(Link.Type.OUT, "L5", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "23", links1, null, null)};
    	OutTransition[] outTransition3= {new OutTransition("t2a", "26", links2, null, null)};
    	OutTransition[] outTransition4= {new OutTransition("t2a", "27", links2, null, null)};
    	State[] states1= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("23", false, outTransition2)};
    	State[] states2= {new State("25", true, outTransition3), new State("26", false, outTransition4), new State("27", false, outTransition4)};
    	ComportamentalFA[] compFAs= {new ComportamentalFA("C2", states1), new ComportamentalFA("C3", states2)};
    	ComportamentalFANetwork compFAN=new ComportamentalFANetwork("Mesto", compFAs);
    	assertFalse(compFAN.check());
	}
}
