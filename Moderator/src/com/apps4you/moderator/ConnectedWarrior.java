package com.apps4you.moderator;

import java.net.Socket;

import com.apps4you.shared.Warrior;

public class ConnectedWarrior extends Warrior {
	private Socket mConnection;
	
	public ConnectedWarrior(Socket socket){
		super();
		mConnection = socket;
	}
	
	public Socket getSocket(){
		return mConnection;
	}
	public void setSocket(Socket  socket){
		mConnection = socket;
	}
}
