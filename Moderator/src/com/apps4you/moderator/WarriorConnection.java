package com.apps4you.moderator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.codehaus.jackson.annotate.JsonIgnore;


import com.apps4you.shared.Warrior;

public class WarriorConnection implements Runnable {
	private Warrior mWarrior;
	private Socket mConnection;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	public WarriorConnection(Socket socket){
		mConnection = socket;
		try {
			inStream = new ObjectInputStream(socket.getInputStream());
			outStream = new ObjectOutputStream(socket.getOutputStream());
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
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
