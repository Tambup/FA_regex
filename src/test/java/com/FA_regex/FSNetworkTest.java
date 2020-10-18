package com.FA_regex;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author tambu
 * Test superficiale del file .json della rete utilizzata nella spiegazione.
 */
public class FSNetworkTest {

	@Test
	public void fileExists() {
		assertTrue(new File("sample/FSCNetwork.sample.json").exists());
	}
	
	@Test
	public void stateMachineNumber() {
		File network=new File("sample/FSCNetwork.sample.json");
			try {
				JsonNode node=new ObjectMapper().readTree(network).get("fsc");
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
		File network=new File("sample/FSCNetwork.sample.json");
		try {
			JsonNode node=new ObjectMapper().readTree(network).get("fsc");
			if(node.isArray()) {
				for(JsonNode son: node) {
					JsonNode stateList=son.get("state");
					if (stateList.isArray()) {
						boolean hasInit=false;
						for (JsonNode state: stateList) {
							if (state.get("is-init")!=null)
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

}
