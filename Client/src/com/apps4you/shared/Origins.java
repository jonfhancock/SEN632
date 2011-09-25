package com.apps4you.shared;
/**
 * An enumeration class that holds all the possible originating locations of combatants
 * @author Jon Hancock
 *
 */
public enum Origins{
	JAGLANBETA("Jaglan Beta"),
	ANTARES("Antares"),
	KAKRAFOON("Kakraphoon"),
	KRIKKIT("Krikkit"),
	VOGSPHERE("Vogsphere"),
	EARTH("Earth"),
	BREQUINDA("Brequinda"),
	MAGRATHEA("Magrathea"),
	SIRIUSTAU("Sirius Tau");
	
	//Variable that will hold the selected value of the origin
	private final String origin;
	
	/**
	 * Sets the which enum value an instance of this enum will be
	 * @param origin  The place of origin for a warrior
	 */
	Origins(String origin){
		this.origin = origin;
	}
	/**
	 * An Origin Get Method
	 * @return String of the enum value for the origin  
	 */
	public String getOrigin(){
		return origin;
	}
	/**
	 * An override of the toString method 
	 */
	@Override
	public String toString(){
		return origin;
	}
}