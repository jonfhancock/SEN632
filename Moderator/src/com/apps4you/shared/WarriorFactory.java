package com.apps4you.shared;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class WarriorFactory {
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	public static String toJSON(Warrior warrior) throws JsonGenerationException, JsonMappingException, IOException{
	
		return mapper.writeValueAsString(warrior);	
		
	}

	// Expects a json string as such: 
	// {"name":"Brainy Viking","health":100,"description":"Silly Viking you are supposed to be tough.","warriorId":"00000000-0000-1092-0000-000000001090","origin":"KRIKKIT"}
	public static Warrior fromJSON(String jsonString) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(jsonString, Warrior.class);
	}
}
