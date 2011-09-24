package com.apps4you.moderator;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.apps4you.client.Consts;
import com.apps4you.shared.Message;
import com.apps4you.shared.MessageFactory;
import com.apps4you.shared.Warrior;
/**
 * Warrior Connection - 
 * Contains information both about warriors that are connected to the server, but also information about the connection itself
 * That makes this class very powerful in that it can manage the message to its client counterpart
 * @author Jon Hancock
 *
 */
public class WarriorConnection implements Runnable {
	private Warrior mWarrior;
	private Socket mConnection;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	private Moderator mModerator;
	private ModeratorUI mUiInstance;
	
	/**
	 * WarriorConnection constructor
	 * @param socket The socket that the client is connected on
	 * @param moderator an link to the moderator for server
	 * @param uiInstance an instance of the UI for the server
	 */
	public WarriorConnection(Socket socket,Moderator moderator,ModeratorUI uiInstance){
		mConnection = socket;
		mModerator = moderator;
		mUiInstance = uiInstance;
		
		try {
			//Initialize and in and out for the socket communication
			inStream = new ObjectInputStream(socket.getInputStream());
			outStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get Method for a socket
	 * @return Socket that this connectivity will be done on
	 */
	public Socket getSocket(){
		return mConnection;
	}
	/**
	 * Set method to setup the sockets to be used
	 * @param socket The socket to be setup
	 */
	public void setSocket(Socket  socket){
		
		mConnection = socket; //set the instance mConnection to the socket
		try {

			//Setup the socket streams
			outStream = new ObjectOutputStream(socket.getOutputStream());
			outStream.flush();
			inStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ObjectOutputStream getOutputStream(){
		return outStream;
	}
	public ObjectInputStream getInputStream(){
		return inStream;
	}
	public Warrior getWarrior(){
		return mWarrior;
	}
	public void setWarrior(Warrior warrior){
		mWarrior = warrior;
	}
	public void run() {
		// TODO Auto-generated method stub
		String jsonString = null;
		while(!mConnection.isClosed()){
			try {
				jsonString = (String) inStream.readObject();
				if(Consts.LOGGING){
				System.out.println("Debugging ProcessConnection - Message was: ***"+ jsonString + "***End Message***");}
				
				Message message = MessageFactory.fromJSON(jsonString);
				processMessage(message);
			}catch (EOFException e){

				try {
					inStream.close();
					outStream.close();
					mConnection.close();
					Moderator.getInstance().deleteOpponent(this);
					Moderator.getInstance().broadCastWarriorList();
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		
		
	}
	private void processMessage(Message inMessage){
		if(Consts.LOGGING){
		System.out.println("Debugging ProcessConnection - Message is ***"+inMessage+"***");}
		switch (inMessage.getCommand()) {
		case NEWWARRIOR:
			if(Consts.LOGGING){
			System.out.println("Debugging ProcessConnection - In NEWWARRIOR case");}
			displayMessage("\nNew warrior added: "
					+ inMessage.getWarrior().getName()); // display
			mWarrior = inMessage.getWarrior();
			sendData(new Message(Message.MessageCommand.GREETWARRIOR,mWarrior));
			mModerator.processNewWarrior(inMessage,mWarrior);

			break;
			
		case BATTLEWARRIOR:
			if(Consts.LOGGING){
			System.out.println("Debugging ProcessConnection - In BATTLEWARRIOR case");}
			displayMessage("\nBattle commencing between: "
					+ inMessage.getWarrior().getName() + " and " + inMessage.getOpponent().getName() + " with " + inMessage.getAction()); // display

			WarriorConnection newOpponent = Moderator.getInstance().findById(inMessage.getOpponent().getWarriorId());
			newOpponent.sendData(new Message(Message.MessageCommand.SELECTACTION,mWarrior,inMessage.getAction()));
			break;
		case DEFENSESELECTED:
			if(Consts.LOGGING){
				System.out.println("Debugging ProcessConnection - In DEFENSESELECTED case");}
			mModerator.processDefenseWasSelected(inMessage);

			break;
		case IAMDEAD:
			Moderator.getInstance().deleteOpponent(this);
			Moderator.getInstance().broadCastWarriorList();
			
			break;
		default:  //Added for debugging to verify that the message was not falling out via not being handled.
			if(Consts.LOGGING){
			System.out.println("Debugging ProcessConnection -Default portion of Switch which does nothing");}
		}
		if(Consts.LOGGING){
		System.out.println("Debugging ProcessConnection - Out of the switch statment");}
	}
	private void displayMessage(final String messageToDisplay) {
		mUiInstance.displayText(messageToDisplay); // append message
	} // end method displayMessage
	
	
	public void sendData(Message message) {
		String jsonString = MessageFactory.toJSON(message);
		try // send object to client
		{
			outStream.writeObject(jsonString);
			outStream.flush(); // flush output to client
			displayMessage(jsonString);
		} // end try
		catch (IOException ioException) {
			displayMessage("\nError writing object");
		} // end catch
	} // end method sendData
}
