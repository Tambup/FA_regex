package com.faRegex.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComportamentalFATest {

	@Test
	public void checkNoInitState() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", links1, null, null), new OutTransition("t2a", "21", links2, null, null)};
    	State[] states= {new State("21", false, outTransition)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
	
	@Test
	public void checkOneInitState() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", links1, null, null), new OutTransition("t2a", "21", links2, null, null)};
    	State[] states= {new State("21", true, outTransition)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertTrue(compFA.check());
	}
	
	@Test
	public void checkMultipleInitState() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", links1, null, null), new OutTransition("t2a", "21", links2, null, null)};
    	State[] states= {new State("21", true, outTransition), new State("22", true, outTransition)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
	
	@Test
	public void checkisNotIsolated() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "21", links1, null, null)};
    	State[] states= {new State("21", true, outTransition1), new State("22", false, outTransition2)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertTrue(compFA.check());
	}
	
	@Test
	public void checkisIsolated() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", links1, null, null)};
    	State[] states= {new State("21", true, outTransition), new State("22", false, null)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
	
	@Test
	public void checkisIsolatedOnlyLoop() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "21", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "22", links1, null, null)};
    	State[] states= {new State("21", true, outTransition1), new State("22", false, outTransition2)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
	
	@Test
	public void checkisIsolatedOnlyNoEnter() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "21", links1, null, null)};
    	State[] states= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("23", false, outTransition2)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertTrue(compFA.check());
	}
	
	@Test
	public void checkisIsolatedOnlyNoExit() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "23", links1, null, null)};
    	State[] states= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("23", false, outTransition2)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertTrue(compFA.check());
	}
	
	@Test
	public void checkOutTransitionDestinationNotExists() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "25", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "23", links1, null, null)};
    	State[] states= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("23", false, outTransition2)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
	
	@Test
	public void checkDuplicateName() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "21", links1, null, null)};
    	State[] states= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("21", false, outTransition2)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
	
	@Test
	public void checkNullStates() {
    	State[] states= null;
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
	
	@Test
	public void checkNullState() {
		Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "22", links1, null, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2a", "21", links1, null, null)};
    	State[] states= {new State("21", true, outTransition1), new State("22", false, outTransition2), new State("21", false, outTransition2)};
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
	
	@Test
	public void checkZeroStates() {
    	State[] states= new State[0];
    	ComportamentalFA compFA= new ComportamentalFA("C2", states);
    	assertFalse(compFA.check());
	}
}
