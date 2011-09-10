package com.apps4you.client;

import java.util.UUID;

import com.apps4you.client.Client;
import com.apps4you.shared.Origins;

public class Warrior extends Client{


	private static final long serialVersionUID = 6227435703704943869L;

	private UUID warriorId;
	private String  name;
	private int health;
	private Origins origin;
	private String description;
	

	
	public Warrior(String host) {
		super(host);
		// TODO Auto-generated constructor stub
	}
	public Warrior(String host, UUID warriorId, String name, int health, Origins origin, String description){
		super(host);
		this.warriorId = warriorId;
		this.name = name;
		this.health = health;
		this.origin = origin;
		this.description = description;
	}

	


	

	
}
