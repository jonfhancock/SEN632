package com.apps4you.shared;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Marshal Message objects to JSON strings and parse JSON strings back out to
 * Message objects.
 * 
 * @author Jon Hancock
 * 
 */
public class MessageFactory {
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Get a JSON string for any Message object
	 * 
	 * @param message
	 *            Any Message object that needs to be serialized to JSON
	 * @return a JSON encoded string containing all the details of the given
	 *         message.
	 */
	public static String toJSON(Message message) {
		String returnString = null;
		try {
			returnString = mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	/**
	 * Build a Message object from a JSON string
	 * 
	 * @param jsonString
	 *            a correctly formatted, serialized Message Object as a JSON
	 *            string. You should have used MessageFactory.toJSON() to
	 *            generate this string.
	 * @return A Message object containing all the details that were encoded
	 *         into the JSON string.
	 */
	public static Message fromJSON(String jsonString) {
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
