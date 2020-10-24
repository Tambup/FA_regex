package com.faRegex;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.faRegex.core.ComportamentalFANetwork;
import com.faRegex.core.Link;
import com.faRegex.core.OutTransition;
import com.faRegex.core.State;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpaceStateTest {

	private static final String SAMPLE_FSC_NETWORK_SAMPLE_JSON = "sample/FSCNetwork.sample.json";
	
	@Test
	public void testInit() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		ComportamentalFANetwork cFAN= mapper.readValue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON), ComportamentalFANetwork.class);
		ComportamentalFANSpace cFANS= new ComportamentalFANSpace(cFAN);
		String[] links= {"L2", "L3"};
		ArrayList<State> states= new ArrayList<State>();
		states.add(cFAN.getComportamentalFAs()[0].getStates()[0]);
		states.add(cFAN.getComportamentalFAs()[1].getStates()[0]);
		SpaceState toCheck= new SpaceState(states, links);
		/*Link[] link= new Link[1];
		link[0]= new Link(Link.Type.OUT, "L2", "e2");
		String[] obs= new String[1];
		obs[0]= "o3";
		toCheck.addNext(new OutTransition("t3a", "31", link, obs, null), null);*/
		
		cFANS.build();
		assertTrue(cFANS.getStates().get(0).equals(toCheck));
	}
	
	@Test
	public void testInitDeeper() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		ComportamentalFANetwork cFAN= mapper.readValue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON), ComportamentalFANetwork.class);
		ComportamentalFANSpace cFANS= new ComportamentalFANSpace(cFAN);
		ArrayList<State> states= new ArrayList<State>();
		states.add(cFAN.getComportamentalFAs()[0].getStates()[0]);
		states.add(cFAN.getComportamentalFAs()[1].getStates()[0]);
		Link[] link= new Link[1];
		link[0]= new Link(Link.Type.OUT, "L2", "e2");
		String[] obs= new String[1];
		obs[0]= "o3";
		ArrayList<State> states2= new ArrayList<State>();
		states2.add(cFAN.getComportamentalFAs()[0].getStates()[0]);
		states2.add(cFAN.getComportamentalFAs()[1].getStates()[1]);
		
		cFANS.build();
		cFANS.getStates().get(0).getNext().get(cFANS.getStates().get(0).getNext()
				.keySet().iterator().next()).getStates().toArray(new State[2]);
		OutTransition out= new OutTransition("t3a", "31", link, obs, null);
		if (cFANS.getStates().get(0).getNext().keySet().iterator().next().equals(out))
			assertArrayEquals(cFANS.getStates().get(0).getNext().get(cFANS.getStates().get(0).getNext()
				.keySet().iterator().next()).getStates().toArray(new State[2])
				, states2.toArray(new State[2]));
		else
			fail("The Transaction is not the espected one");
	}

}
