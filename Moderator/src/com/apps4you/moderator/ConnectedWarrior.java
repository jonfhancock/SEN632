package com.apps4you.moderator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.apps4you.shared.Warrior;

public class ConnectedWarrior extends Warrior {
	private Socket mConnection;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	public ConnectedWarrior(Socket socket){
		super();
		mConnection = socket;
		try {

			outStream = new ObjectOutputStream(socket.getOutputStream());
			outStream.flush();
			inStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Socket getSocket(){
		return mConnection;
	}
	public void setSocket(Socket  socket){
		mConnection = socket;
		try {

			outStream = new ObjectOutputStream(socket.getOutputStream());
			outStream.flush();
			inStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ObjectOutputStream getOutputStream(){
		return outStream;
	}
	public ObjectInputStream getInputStream(){
		return inStream;
	}
	public void upgradeWarrior(Warrior w){
		setHealth(w.getHealth());
		setName(w.getName());
		setDescription(w.getDescription());
		setOrigin(w.getOrigin());
		setWarriorId(w.getWarriorId());
	}
}
