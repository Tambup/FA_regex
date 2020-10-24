package com.faRegex.userInputOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.faRegex.ComportamentalFANSpace;
import com.faRegex.core.ComportamentalFANetwork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
	
	public void writeComportamentalFANSpace(ComportamentalFANSpace cFANS, Path path) throws IOException {
		ObjectWriter mapper=new ObjectMapper().writerWithDefaultPrettyPrinter();
		mapper.writeValue(path.toFile(), cFANS);
	}
}
