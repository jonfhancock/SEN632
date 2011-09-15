package com.apps4you.shared;

public enum Actions {
	
	SLASH("Slash"),
	STAB("Stab"),
	BLOCK("Block"),
	SIDESTEP("Sidestep"),
	LASER("Laser"),
	PARTICLEBEAM("Particle Beam"),
	RAILGUN("Rail-Gun"),
	SHIELD("Shield"),
	PUNCH("Punch"),
	COUNTERPUNCH("Counter Punch"),
	KICK("Kick");		
	
	private final String chosenAction;
	
	private Actions (String actionChoice)
	{
		this.chosenAction = actionChoice;
	}

	public String getAction(){
		return this.chosenAction;
	}
}	