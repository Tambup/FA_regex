package com.faRegex;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import com.faRegex.core.ComportamentalFA;
import com.faRegex.core.ComportamentalFANetwork;
import com.faRegex.core.Link;
import com.faRegex.core.OutTransition;
import com.faRegex.core.State;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author tambu
 * Test superficiale del file .json della rete utilizzata nella spiegazione.
 */
public class PersistentJsonSampleTest {

	private static final String SAMPLE_FSC_NETWORK_SAMPLE_JSON = "sample/FSCNetwork.sample.json";

	@Test
	public void fileExists() {
		assertTrue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON).exists());
	}
	
	@Test
	public void stateMachineNumber() {
		File network=new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON);
			try {
				JsonNode node=new ObjectMapper().readTree(network).get("comportamentalFA");
				int count=0;
				if(node.isArray()) {
					for(JsonNode son: node) {
						if (!son.get("name").asText().equals("C2") && !son.get("name").asText().equals("C3"))
							fail("File non corrispondente alla specifica");
						count++;
						if (son.get("state")==null)
							fail("Il parametro state manca");
					}
					assertTrue(count==2);
				} else
					fail("Formato errato: previste due state machine");
			} catch (JsonProcessingException e) {
				fail("Il file non e' un json corretto");
			} catch (IOException e) {
				fail("File non esistente");
			}
	}
	
	@Test
	public void hasInit() {
		File network=new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON);
		try {
			JsonNode node=new ObjectMapper().readTree(network).get("comportamentalFA");
			if(node.isArray()) {
				for(JsonNode son: node) {
					JsonNode stateList=son.get("state");
					if (stateList.isArray()) {
						boolean hasInit=false;
						for (JsonNode state: stateList) {
							JsonNode init=state.get("init");
							if (init!=null && init.booleanValue())
								hasInit=true;
						}
						assertTrue(hasInit);
					}
				}
			} else
				fail("Formato errato: previste due state machine");
		} catch (JsonProcessingException e) {
			fail("Il file non e' un json corretto");
		} catch (IOException e) {
			fail("File non esistente");
		}
	}
	
	@Test
	public void unmarshallingFile() {
		ObjectMapper mapper=new ObjectMapper();
		try {
			mapper.readValue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON), ComportamentalFANetwork.class);
		} catch (JsonParseException e) {
			fail("Json non ben formattato");
		} catch (JsonMappingException e) {
			fail("Il file non descrive un'istanza di ComportamentalFANetwork");
		} catch (IOException e) {
			fail("File non esistente");
		}
	}

	@Test
	public void checkComportamentalFANetwork() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		ComportamentalFANetwork cfan=mapper.readValue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON), ComportamentalFANetwork.class);
		assertTrue("Nome della comportamental FANetwork errato", cfan.getName()==null);
		assertTrue("Numero di comportamental FA errato", cfan.getComportamentalFAs().length==2);
	}
	
	@Test
	public void checkFirstComportamentalFA() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		ComportamentalFA cFA=mapper.readValue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON), ComportamentalFANetwork.class)
				.getComportamentalFAs()[0];
		
		Link[] link1= {new Link(Link.Type.IN, "L2", "e2"), new Link(Link.Type.OUT, "L3", "e3")};
		Link[] link2= {new Link(Link.Type.OUT, "L3", "e3")};
		String[] observable1= {"o2"};
		String[] relevant2= {"r"};
    	OutTransition[] outTransition1= {new OutTransition("t2a", "21", link1, observable1, null)};
    	OutTransition[] outTransition2= {new OutTransition("t2b", "20", link2, null, relevant2)};
    	State[] states= {new State("20", true, outTransition1), new State("21", outTransition2)};
		
		assertTrue("Nome della comportamental FA errato", cFA.getName().equals("C2"));
		assertTrue("Il primo stato non coincide", cFA.getStates()[0].equals(states[0]));
		assertTrue("Il secondo stato non coincide", cFA.getStates()[1].equals(states[1]));
	}
	
	@Test
	public void checkSecondComportamentalFA() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		ComportamentalFA cFA=mapper.readValue(new File(SAMPLE_FSC_NETWORK_SAMPLE_JSON), ComportamentalFANetwork.class)
				.getComportamentalFAs()[1];
		
		Link[] link1= {new Link(Link.Type.OUT, "L2", "e2")};
		Link[] link2= {new Link(Link.Type.IN, "L3", "e3")};
		Link[]link3= {new Link(Link.Type.IN, "L3", "e3")};
		String[] observable1= {"o3"};
		String[] relevant3= {"f"};
    	OutTransition[] outTransition1= {new OutTransition("t3a", "31", link1, observable1, null)};
    	OutTransition[] outTransition2= {new OutTransition("t3b", "30", link2, null, null), new OutTransition("t3c", "31", link3, null, relevant3)};
    	State[] states= {new State("30", true, outTransition1), new State("31", outTransition2)};
		
    	assertTrue("Nome della comportamental FA errato", cFA.getName().equals("C3"));
		assertTrue("Il primo stato non coincide", cFA.getStates()[0].equals(states[0]));
		assertTrue("Il secondo stato non coincide", cFA.getStates()[1].equals(states[1]));
	}
}
