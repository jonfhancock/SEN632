package com.apps4you.client;

import java.util.UUID;

import com.apps4you.client.Client;

public class Warrior extends Client{


	private static final long serialVersionUID = 6227435703704943869L;

	private UUID warriorId;
	private String  name;
	private int health;
	private Origin origin;
	private String description;
	

	
	public Warrior(String host) {
		super(host);
		// TODO Auto-generated constructor stub
	}
	public Warrior(String host, UUID warriorId, String name, int health, Origin origin, String description){
		super(host);
		this.warriorId = warriorId;
		this.name = name;
		this.health = health;
		this.origin = origin;
		this.description = description;
	}

	

	public static enum Origin{
		JAGLANBETA("Jaglan Beta"),
		ANTARES("Antares"),
		KAKRAFOON("Kakraphoon"),
		KRIKKIT("Krikkit"),
		VOGSPHERE("Vogsphere"),
		EARTH("Earth"),
		BREQUINDA("Brequinda"),
		MAGRATHEA("Magrathea");
		
		private final String origin;
		
		Origin(String origin){
			this.origin = origin;
		}
		public String getOrigin(){
			return origin;
		}
	}
}
