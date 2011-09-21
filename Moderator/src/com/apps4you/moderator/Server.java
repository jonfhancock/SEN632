package com.apps4you.moderator;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.apps4you.moderator.ModeratorUI;
import com.apps4you.shared.Message;
import com.apps4you.shared.MessageFactory;

public class Server {
	private static final long serialVersionUID = -5693161904305556535L;

//	private ObjectOutputStream output; // output stream to client
//	private ObjectInputStream input; // input stream from client
	private ServerSocket server; // server socket
//	private Socket connection; // connection to client
	private int counter = 1; // counter of number of connections

	private Moderator moderator;
	private ModeratorUI uiInstance;
	
	

	// set up GUI
	public Server() {
		ModeratorUI mUI = new ModeratorUI();
		this.uiInstance = mUI;
		mUI.setVisible(true);
		moderator = new Moderator();

	} // end Server constructor

	// set up and run server
	public void runServer() {
		try // set up server to receive connections; process connections
		{
			server = new ServerSocket(12345, 100); // create ServerSocket
	        
			while (true) {
				
				try {
					
					ConnectedWarrior cw = waitForConnection(); // wait for a connection
					moderator.addOpponent(cw);
//					getStreams(cw);
			        new Thread(
			                new Runnable() {
			                    public void run() {
			                    	try {
										processConnections();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} // process connection

			                    }
			                }).start();
					
									
					
				} // end try
				
				finally {
					closeConnection(); // close connection
					++counter;
				} // end finally
			} // end while
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch
	} // end method runServer

	// wait for connection to arrive, then display connection info
	private ConnectedWarrior waitForConnection() throws IOException {
		displayMessage("Waiting for connection\n");
		ConnectedWarrior cw = new ConnectedWarrior(server.accept());
		
//		Socket connection = server.accept(); // allow server to accept connection
		displayMessage("Connection " + counter + " received from: "
				+ cw.getSocket().getInetAddress().getHostName());
		return cw;
	} // end method waitForConnection

	// get streams to send and receive data
//	private void getStreams(ConnectedWarrior cw) throws IOException {
//		// set up output stream for objects
//		output = new ObjectOutputStream(cw.getSocket().getOutputStream());
//		output.flush(); // flush output buffer to send header information
//
//		// set up input stream for objects
//		input = new ObjectInputStream(cw.getSocket().getInputStream());
//
//		displayMessage("\nGot I/O streams\n");
//	} // end method getStreams

	// process connection with client
	private void processConnections() throws IOException {
		String message = "Connection successful";

		do // process messages sent from client
		{
			try // read message and display it
			{
				
				for(ConnectedWarrior cw: moderator.getOpponents()){
					message = (String) cw.getInputStream().readObject(); // read new message
					Message inMessage = MessageFactory.fromJSON(message);
					processMessage(inMessage, cw);
					System.out.println("Debugging ProcessConnection - Message was: ***"+ message + "***End Message***");
					
					System.out.println("Debugging ProcessConnection - Retrieved Message from MessageFactory");
				}
				

			} // end try
			catch (ClassNotFoundException classNotFoundException) {
				displayMessage("\nUnknown object type received");
			} // end catch

		} while (!message.equals("CLIENT>>> TERMINATE"));
	} // end method processConnection

	// close streams and socket
	private void closeConnection() {
		displayMessage("\nTerminating connection\n");

		try {
			for(ConnectedWarrior cw:moderator.getOpponents()){
			cw.getOutputStream().close(); // close output stream
			cw.getInputStream().close(); // close input stream
			cw.getSocket().close(); // close socket
			}
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch
	} // end method closeConnection

	// send message to client
	private void sendData(String message, ConnectedWarrior cw) {
		try // send object to client
		{
			cw.getOutputStream().writeObject(message);
			cw.getOutputStream().flush(); // flush output to client
			displayMessage(message);
		} // end try
		catch (IOException ioException) {
			displayMessage("\nError writing object");
		} // end catch
	} // end method sendData

	// manipulates displayArea in the event-dispatch thread
	private void displayMessage(final String messageToDisplay) {
		uiInstance.displayText(messageToDisplay); // append message
	} // end method displayMessage

	private void processMessage(Message inMessage, ConnectedWarrior cw){
		switch (inMessage.getCommand()) {
		case NEWWARRIOR:
			System.out.println("Debugging ProcessConnection - In NEWWARRIOR case");
			displayMessage("\nNew warrior added: "
					+ inMessage.getWarrior().getName()); // display
			cw.upgradeWarrior(inMessage.getWarrior());
			sendData(MessageFactory.toJSON(new Message(inMessage.getWarrior(),Message.MessageCommand.GREETWARRIOR)),cw);
			sendData(MessageFactory.toJSON(moderator
					.processNewWarrior(inMessage,cw)),cw);

			break;
			
		case BATTLEWARRIOR:
			System.out.println("Debugging ProcessConnection - In BATTLEWARRIOR case");
			displayMessage("\nBattle commencing between: "
					+ inMessage.getWarrior().getName() + " and " + inMessage.getOpponent().getName() + " with " + inMessage.getAction()); // display

			sendData(MessageFactory.toJSON(new Message(inMessage.getWarrior(),Message.MessageCommand.GREETWARRIOR)),cw);
//			sendData(MessageFactory.toJSON(moderator
//					.processNewWarrior(inMessage)));					
		default:  //Added for debugging to verify that the message was not falling out via not being handled.
			System.out.println("Debugging ProcessConnection -Default portion of Switch which does nothing");
		}
		System.out.println("Debugging ProcessConnection - Out of the switch statment");
	}
	
} // end class Server
