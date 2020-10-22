package com.faRegex.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class StateTest {

	@Test
	public void hasNotExit() {
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", null, null, null)};
    	State state= new State("20", true, outTransition);
    	assertFalse(state.noExit());
	}
	
	@Test
	public void hasExit() {
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", null, null, null)};
    	State state= new State("21", true, outTransition);
    	assertTrue(state.noExit());
	}
	
	@Test
	public void isIsolatedNullOutTransitions() {
    	State state= new State("21", true, null);
    	assertTrue(state.noExit());
	}
	
	@Test
	public void checkNullOutTransitions() {
    	State state= new State("21", true, null);
    	assertTrue(state.check());
	}

	@Test
	public void checkNullOutTransition() {
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", null, null, null), null};
    	State state= new State("21", true, outTransition);
    	assertFalse(state.check());
	}
	
	@Test
	public void checkDuplicateOutTransaction() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", links1, null, null), new OutTransition("t2a", "21", links2, null, null)};
    	State state= new State("21", true, outTransition);
    	assertFalse(state.check());
	}
	
	@Test
	public void checkOutTransaction() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e3")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition[] outTransition= {new OutTransition("t2a", "21", links1, null, null), new OutTransition("t2a", "21", links2, null, null)};
    	State state= new State("21", true, outTransition);
    	assertTrue(state.check());
	}
}
