package com.faRegex;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
	public void testMaintain() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		ComportamentalFANetwork cFAN= mapper.readValue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON), ComportamentalFANetwork.class);
		ComportamentalFANSpace cFANS= new ComportamentalFANSpace(cFAN);
		String[] links= {"L2", "L3"};
		ArrayList<State> states= new ArrayList<State>();
		states.add(cFAN.getComportamentalFAs()[0].getStates()[0]);
		states.add(cFAN.getComportamentalFAs()[1].getStates()[0]);
		SpaceState toCheck= new SpaceState(states, links);
		
		cFANS.build();
		assertTrue(cFANS.getStates().contains(toCheck));
	}
	
	@Test
	public void testNotMantain() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		ComportamentalFANetwork cFAN= mapper.readValue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON), ComportamentalFANetwork.class);
		ComportamentalFANSpace cFANS= new ComportamentalFANSpace(cFAN);
		HashMap<String, String> links= new HashMap<String, String>();
		links.put("L2", "e2");
		links.put("L3", "\u03B5");
		ArrayList<State> states= new ArrayList<State>();
		states.add(cFAN.getComportamentalFAs()[0].getStates()[1]);
		states.add(cFAN.getComportamentalFAs()[1].getStates()[0]);
		SpaceState toCheck= new SpaceState(states, links);
		
		cFANS.build();
		assertFalse(cFANS.getStates().contains(toCheck));
	}

}
