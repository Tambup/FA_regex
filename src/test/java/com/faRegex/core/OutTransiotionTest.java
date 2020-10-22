package com.faRegex.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class OutTransiotionTest {

	@Test
	public void nullEvents() {
    	OutTransition outTransition= new OutTransition("t2a", "21", null, null, null);
    	assertFalse(outTransition.sameEvents(null));
	}
	
	@Test
	public void nullEvent() {
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition outTransition= new OutTransition("t2a", "21", null, null, null);
    	assertFalse(outTransition.sameEvents(links2));
	}
	
	@Test
	public void sameEventsSameTypesMoreList1() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3"), new Link(Link.Type.OUT, "L3", "e2")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertFalse(outTransition.sameEvents(links2));
	}
	
	@Test
	public void sameEventsSameTypesMoreList2() {
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3"), new Link(Link.Type.OUT, "L3", "e2")};
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertFalse(outTransition.sameEvents(links2));
	}
	
	@Test
	public void sameEventsDifferentTypes() {
    	Link[] links1= {new Link(Link.Type.OUT, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3"), new Link(Link.Type.OUT, "L3", "e2")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3"), new Link(Link.Type.OUT, "L3", "e2")};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertFalse(outTransition.sameEvents(links2));
	}
	
	@Test
	public void differentEventsSameTypes() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e3"), new Link(Link.Type.OUT, "L3", "e2")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertFalse(outTransition.sameEvents(links2));
	}
	
	@Test
	public void sameEventSameType() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	Link[] links2= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertTrue(outTransition.sameEvents(links2));
	}
	
	@Test
	public void checkWithNull() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e2"), null};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertFalse(outTransition.check());
	}
	
	@Test
	public void checkCorrect() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertTrue(outTransition.check());
	}
	
	@Test
	public void checkTooManyInput() {
    	Link[] links1= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.IN, "L3", "e3")};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertFalse(outTransition.check());
	}
	
	@Test
	public void checkNoInput() {
    	Link[] links1= null;
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertTrue(outTransition.check());
	}
	
	@Test
	public void checkDuplicateLink() {
    	Link[] links1= {new Link(Link.Type.OUT, "L2", "e2"), new Link(Link.Type.OUT, "L2", "e3")};
    	OutTransition outTransition= new OutTransition("t2a", "21", links1, null, null);
    	assertFalse(outTransition.check());
	}
}
