package com.apps4you.shared;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * A factory to build the Warriors from JSON
 * @author Jon Hancock
 *
 */
public class WarriorFactory {
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	/**
	 * Ability to take a warrior and turn it into a JSON serialized string
	 * @param warrior The warrior to be changed to a jSON string
	 * @return String that contains the JSON formated serialized warrior
	 */
	public static String toJSON(Warrior warrior) {
		String jsonString = null;
		try {
			jsonString =  mapper.writeValueAsString(warrior);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return jsonString;
	}

	/**
	 * Ability to take a JSON based string of a warrior and fill out a warrior object
	 * @param jsonString The JSON serialized string that represents a warrior
	 * @return Warrior that holds all the values from the JSON serialized string
	 */
	public static Warrior fromJSON(String jsonString){
		Warrior warrior = null;
		try {
			warrior = mapper.readValue(jsonString, Warrior.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return warrior;
	}
}
