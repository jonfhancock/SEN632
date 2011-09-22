package com.apps4you.shared;

import java.util.UUID;

public class Warrior{



	public UUID getWarriorId() {
		return warriorId;
	}
	public void setWarriorId(UUID warriorId) {
		this.warriorId = warriorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public Origins getOrigin() {
		return origin;
	}
	public void setOrigin(Origins origin) {
		this.origin = origin;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private UUID warriorId;
	private String  name;
	private int health;
	private Origins origin;
	private String description;

	
	public Warrior() {
	
	}
	public Warrior(UUID warriorId, String name, int health, Origins origin, String description){
		
		this.warriorId = warriorId;
		this.name = name;
		this.health = health;
		this.origin = origin;
		this.description = description;
	}


	public Warrior(String name, Origins origin, String description){
		
		// Need to create a new warriorID
		this.warriorId = UUID.randomUUID();
		this.name = name;
		//assuming that we are creating a new warrior for the first time
		this.health = 100;
		this.origin = origin;
		this.description = description;
	}
	
@Override
	public String toString() {
		return this.name;
	}

	public String toFormattedString(){
		StringBuilder builder = new StringBuilder();
		builder.append(name).append(" (").append(health).append(")\n")
		.append("From: ").append(origin.toString()).append("\n")
		.append(description).append("\n\n");
		return builder.toString();
	}




	

	
}
