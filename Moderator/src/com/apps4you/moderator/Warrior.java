package com.apps4you.moderator;

import java.util.UUID;

import com.apps4you.shared.Origins;


public class Warrior {
	private UUID warriorId;
	private String  name;
	private int health;
	private Origins origin;
	private String description;

	public Warrior( UUID warriorId, String name, int health, Origins origin, String description){
	
		this.warriorId = warriorId;
		this.name = name;
		this.health = health;
		this.origin = origin;
		this.description = description;
	}
}