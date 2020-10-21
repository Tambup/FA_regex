package com.faRegex.userInputOutput;

import java.io.File;
import java.io.IOException;

import com.faRegex.core.ComportamentalFANetwork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserInputReader {
	public UserInputReader() {
		
	}
	
	public ComportamentalFANetwork readInput(String mainInput) throws JsonMappingException, JsonProcessingException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		if (mainInput.endsWith(".json")) {
			return mapper.readValue(new File(mainInput), ComportamentalFANetwork.class);
		} else
			return mapper.readValue(mainInput, ComportamentalFANetwork.class);
	}
}
