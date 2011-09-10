package com.apps4you.shared;

import java.util.UUID;


public class Warrior{



	private UUID warriorId;
	private String  name;
	private int health;
	private Origins origin;
	private String description;
	

	
	public Warrior() {
	
		// TODO Auto-generated constructor stub
	}
	public Warrior(UUID warriorId, String name, int health, Origins origin, String description){
		
		this.warriorId = warriorId;
		this.name = name;
		this.health = health;
		this.origin = origin;
		this.description = description;
	}

	


	

	
}
