package com.apps4you.moderator;

public class Main {

	/**
	 * Controls the server side of the application
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadedServer s = new ThreadedServer();
		s.execute();

	}
	
	
}
