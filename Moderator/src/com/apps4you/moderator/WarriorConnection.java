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
	/**
	 * Get Method to the output stream for this Connected Warrior
	 * @return ObjectOutputStream the stream of data to the socket
	 */
	public ObjectOutputStream getOutputStream(){
		return outStream;
	}
	/**
	 * Get Method to the input stream for this Connected Warrior
	 * @return ObjectInputStream the stream of data from the socket
	 */
	public ObjectInputStream getInputStream(){
		return inStream;
	}
	/**
	 * Get the associated warrior for this Connected Warrior
	 * @return Warrior the warrior for this instance
	 */
	public Warrior getWarrior(){
		return mWarrior;
	}
	/**
	 * Set the associated warrior for this Connected Warrior
	 * @param warrior object to add to the WarriorConnected instance
	 */
	public void setWarrior(Warrior warrior){
		mWarrior = warrior;
	}
	/**
	 * The method that runs that will loop in order to listen for information to read from the socket
	 */
	public void run() {
        //The local string that the JSON information will be read into from the stream
		String jsonString = null;
		//Loop to check for new messages
		while(!mConnection.isClosed()){
			try {
				//Actually trying to  read incoming data
				jsonString = (String) inStream.readObject();
				
				//  Debug log to help debug
				if(Consts.LOGGING){
				System.out.println("Debugging ProcessConnection - Message was: ***"+ jsonString + "***End Message***");}
				
				//Takes the string that was retrieved form the input stream and then uses the Message Factory to create
				//a message that is understood and can be handled 
				Message message = MessageFactory.fromJSON(jsonString);
				//Calls the process message were the message type is evaluated and directed to the next proper place depending on the message 
				processMessage(message);
				
			}catch (EOFException e){
                //An EOFException occurred so clean up and get out of this instance of the loop
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
	/**
	 * process message is were the message type is evaluated and directed 
	 * to the next proper place depending on the message that was received 
	 * @param inMessage The message that needs to be processed and set on properly
	 */
	private void processMessage(Message inMessage){
		//Debug Log
		if(Consts.LOGGING){
		System.out.println("Debugging ProcessConnection - Message is ***"+inMessage+"***");}
		//Evaluate what type of message it is based on the command field
		switch (inMessage.getCommand()) {
		case NEWWARRIOR:
			//Debug Log
			if(Consts.LOGGING){
			System.out.println("Debugging ProcessConnection - In NEWWARRIOR case");}
			
			//Indicate to the user that a new warrior named X has joined
			displayMessage("\nNew warrior added: "
					+ inMessage.getWarrior().getName()); // display
			
			mWarrior = inMessage.getWarrior();//save off the warrior to a local copy
			
			//Direct a new message about greeting the newly added warrior
			sendData(new Message(Message.MessageCommand.GREETWARRIOR,mWarrior));
			//Allow the Moderator to process the newly added warrior
			mModerator.processNewWarrior(inMessage,mWarrior);
			break;
			
		case BATTLEWARRIOR:
			//Debug Log
			if(Consts.LOGGING){
			System.out.println("Debugging ProcessConnection - In BATTLEWARRIOR case");}
			
			//Indicate to the user that a new battle has been initiated
			displayMessage("\nBattle commencing between: "
					+ inMessage.getWarrior().getName() + " and " + inMessage.getOpponent().getName() + " with " + inMessage.getAction()); // display

			//Determine who the opponent is
			WarriorConnection newOpponent = Moderator.getInstance().findById(inMessage.getOpponent().getWarriorId());
			//Indicate to the opponent that they have been selected for battle and that they need to pick a action for the battle
			newOpponent.sendData(new Message(Message.MessageCommand.SELECTACTION,mWarrior,inMessage.getAction()));
			break;
			
		case DEFENSESELECTED:
			//Debug Log
			if(Consts.LOGGING){
				System.out.println("Debugging ProcessConnection - In DEFENSESELECTED case");}
			
			//Notify the moderator that a defense was selected for the opponent so that the battle can now occur
			mModerator.processDefenseWasSelected(inMessage);
			break;

		case IAMDEAD:
			//The warrior is now dead so they must be removed form the system as a choice for other warriors to battle
			Moderator.getInstance().deleteOpponent(this);
			Moderator.getInstance().broadCastWarriorList();
			
			break;
		default:  //Added for debugging purposes
			//Debug Log
			if(Consts.LOGGING){
			System.out.println("Debugging ProcessConnection -Default portion of Switch which does nothing");}
		}
		//Debug Log
		if(Consts.LOGGING){
		System.out.println("Debugging ProcessConnection - Out of the switch statment");}
	}
	/**
	 * A helper to display information to the UI
	 * 
	 * @param messageToDisplay will be passed to the Moderator UI instance to be displayed to the user 
	 */
	private void displayMessage(final String messageToDisplay) {
		mUiInstance.displayText(messageToDisplay); // append message
	} // end method displayMessage
	
	/**
	 * Handles packaging up the information from a Message to actually send out
	 * @param message a Message that needs to be put on the output stream
	 */
	public void sendData(Message message) {
		//A JSON serialized message in a string format to be place on the output stream
		String jsonString = MessageFactory.toJSON(message);
		try // send object to client
		{
			//Actually add the message to the output stream
			outStream.writeObject(jsonString);
			outStream.flush(); // flush output to client
			//Display to the user what the message was
			displayMessage(jsonString);
		} // end try
		catch (IOException ioException) {
			displayMessage("\nError writing object");
		} // end catch
	} // end method sendData
}
