package com.apps4you.shared;

/**
 * The battle actions used when attacking or defending against another Warrior
 * 
 * @author jonfhancock
 * 
 */
public enum Actions {

	BLOCK("Block"), 
	HIDEUNDERTOWEL("Hide under Towel"),
	KICK("Kick"), 
	KILOZAPGUN("Kill-o-Zap Pistol"), 
	LASER("Laser"), 
	PARTICLEBEAM("Particle Beam"), 
	POINTOFVIEWGUN("Point of View Gun"), 
	PUNCH("Punch"), 
	SEP("Someone Else's Problem Field"), 
	WHIPWITHTOWEL("Whip with Towel");

	private final String chosenAction;
    /**
     * Setup the enum
     * @param actionChoice
     */
	private Actions(String actionChoice) {
		this.chosenAction = actionChoice;
	}

	/**
	 * Get the enum name of an action
	 * 
	 * @return the enum name of an action as a String
	 */
	public String getAction() {
		return this.chosenAction;
	}

	/**
	 * Get the user friendly value of the action
	 * 
	 * @return the plain value of the enum
	 */
	@Override
	public String toString() {
		return this.chosenAction;
	}
}