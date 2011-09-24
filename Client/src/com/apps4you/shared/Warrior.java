package com.apps4you.shared;

import java.util.UUID;

/**
 *  Warriors are the combatants in this application
 *  Each connected client assigns themselves a warrior and then
 *  registers there warrior with the Server
 * @author Jon Hancock
 *
 */
public class Warrior{

	//The ID of a warrior - UUID is used for serialization via json
	private UUID warriorId;
	//The name of the warrior
	private String  name;
	//The amount of life that the warrior has remaining
	private int health;
	//The place that a warrior is from
	private Origins origin;
	//A brief description of the warrior
	private String description;
	
	/**
	 * Default constructor for warrior
	 */
	public Warrior() {
	}

	/**
	 * A constructor for Warrior
	 * @param warriorId The ID of a warrior - UUID is used for serialization via json
	 * @param name The name of the warrior
	 * @param health The amount of life that the warrior has remaining
	 * @param origin The place that a warrior is from
	 * @param description A brief description of the warrior
	 */
	public Warrior(UUID warriorId, String name, int health, Origins origin, String description){
		
		this.warriorId = warriorId;
		this.name = name;
		this.health = health;
		this.origin = origin;
		this.description = description;
	}

	/**
	 * A constructor for Warrior that is assumed to be just created
	 * @param name The name of the warrior
	 * @param origin The place that a warrior is from
	 * @param description A brief description of the warrior
	 */
	public Warrior(String name, Origins origin, String description){
		//Since this warriors is new two variables set inside this constructor
		//All the rest are just pass through
		// Need to create a new warriorID
		this.warriorId = UUID.randomUUID();
		//assuming that we are creating a new warrior for the first time
		this.health = 100;
		
		//The pass through variables
		this.name = name;
		this.origin = origin;
		this.description = description;
	}
	/**
	 * Get Method
	 * @return UUID of the warrior
	 */
	public UUID getWarriorId() {
		return warriorId;
	}
	/**
	 * set method
	 * @param warriorId The new UUID for a warrior
	 */
	public void setWarriorId(UUID warriorId) {
		this.warriorId = warriorId;
	}
	/**
	 * Get Method for Name
	 * @return String The warriors name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set Method
	 * @param name A name to be assigned to the warrior
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Get Method
	 * @return int The health level of a warrior
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * Set method
	 * @param health The level of health to set a warrior to
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	/**
	 * Get Method for Origin
	 * @return Origin The home location of a warrior
	 */
	public Origins getOrigin() {
		return origin;
	}
	/**
	 * Set method for Origin
	 * @param origin The origin that needs to be assigned to this warrior
	 */
	public void setOrigin(Origins origin) {
		this.origin = origin;
	}
	/**
	 * Get Method
	 * @return String The description of a warrior
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set method for description
	 * @param description The description of the warrior that is desired to be assigned to this warrior
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * To string Method Override
	 */
@Override
	public String toString() {
		return this.name;
	}

	/**
	 * A toString that is formatted nicely about a warrior
	 * @return String Nicely formated string of all the warriors attributes
	 */
	public String toFormattedString(){
		StringBuilder builder = new StringBuilder();
		builder.append(name).append(" (").append(health).append(")\n")
		.append("From: ").append(origin.toString()).append("\n")
		.append(description).append("\n\n");
		return builder.toString();
	}




	

	
}
