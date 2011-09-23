package com.apps4you.shared;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author jonfhancock
 *
 */
public class MessageFactory {
	private static ObjectMapper mapper = new ObjectMapper(); 

	public static String toJSON(Message message){
		String returnString = null;
		try {
			returnString =  mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	public static Message fromJSON(String jsonString){
		Message message = null;
		
		try {
			message = mapper.readValue(jsonString, Message.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
}
