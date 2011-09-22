package com.apps4you.shared;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class WarriorFactory {
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	public static String toJSON(Warrior warrior) {
		String jsonString = null;
		try {
			jsonString =  mapper.writeValueAsString(warrior);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return jsonString;
	}

	// Expects a json string as such: 
	// {"name":"Brainy Viking","health":100,"description":"Silly Viking you are supposed to be tough.","warriorId":"00000000-0000-1092-0000-000000001090","origin":"KRIKKIT"}
	public static Warrior fromJSON(String jsonString){
		Warrior warrior = null;
		try {
			warrior = mapper.readValue(jsonString, Warrior.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return warrior;
	}
}
