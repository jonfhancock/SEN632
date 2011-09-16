package com.apps4you.shared;

public enum Origins{
	JAGLANBETA("Jaglan Beta"),
	ANTARES("Antares"),
	KAKRAFOON("Kakraphoon"),
	KRIKKIT("Krikkit"),
	VOGSPHERE("Vogsphere"),
	EARTH("Earth"),
	BREQUINDA("Brequinda"),
	MAGRATHEA("Magrathea");
	
	private final String origin;
	
	Origins(String origin){
		this.origin = origin;
	}
	public String getOrigin(){
		return origin;
	}
	@Override
	public String toString(){
		return origin;
	}
}