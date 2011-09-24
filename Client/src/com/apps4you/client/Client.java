package com.apps4you.client;

//Client portion of a stream-socket connection between client and server.

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.apps4you.shared.Message;
import com.apps4you.shared.MessageFactory;
import com.apps4you.shared.Warrior;

/**
 * The Client class handles connecting to the server and passing messages back
 * and forth. It will also launch the GUI that the user will see.
 * 
 * @author Craig Mersereau
 */
public class Client {

	private static final long serialVersionUID = 7189340988809001708L;
	private ObjectOutputStream output; // output stream to server
	private ObjectInputStream input; // input stream from server
	private String chatServer; // host server for this application
	private Socket client; // socket to communicate with server
	private ClientCombatantUI uiInstance; // The UI instance we'll be working
											// with

	/**
	 * Instantiate the GUI that the user will interact with, and set the
	 * server's hostname.
	 * 
	 * @param host
	 *            This should be a hostname or IP address of the server you want
	 *            to connect to.
	 */
	public Client(String host) {
		uiInstance = ClientCombatantUI.getInstance();
		chatServer = host; // set server to which this client connects
	} // end Client constructor

	/**
	 * Launches the client connection
	 * 
	 * @param initialMessage
	 *            An initial message to be sent to the server. This should be a
	 *            Message object.
	 */
	public void runClient(Message initialMessage) {
		try // connect to server, get streams, process connection
		{
			// create a Socket to make connection
			connectToServer();
			// get the input and output streams
			getStreams();
			// send the initial message to the server. This should be a
			// NEWWARRIOR message, which will add the warrior to the opponents
			// list.
			sendData(initialMessage);

			if (Consts.LOGGING) {
				System.out
						.println("Sending initial message: " + initialMessage);
			}

			// process connection
			processConnection();

		} // end try
		catch (EOFException eofException) {
			displayMessage("\nClient terminated connection");

		} // end catch
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch
		finally {
			closeConnection(); // close connection
		} // end finally
	} // end method runClient

	/**
	 * Actually perform the task of connecting to the server via the socket.
	 * 
	 * @throws IOException
	 */
	private void connectToServer() throws IOException {
		displayMessage("Attempting connection\n");

		if (Consts.LOGGING) {
			System.out
					.println("Debugging - connectToServer - ChatServer is :***"
							+ chatServer + "***");
		}

		// create Socket to make connection to server
		client = new Socket(InetAddress.getByName(chatServer), 12345);

		// display connection information
		displayMessage("Connected to: " + client.getInetAddress().getHostName());
	} // end method connectToServer

	/**
	 * Get the ObjectOutputStream and ObjectInputStream we will use to send and
	 * receive messages to and from the server.
	 * 
	 * @throws IOException
	 */
	private void getStreams() throws IOException {

		// set up output stream for objects
		output = new ObjectOutputStream(client.getOutputStream());
		// flush output buffer to send header information
		output.flush();

		// set up input stream for objects
		input = new ObjectInputStream(client.getInputStream());

	} // end method getStreams

	// process connection with server

	/**
	 * Loop continuously and read messages from the server Naturally, if a
	 * message is received, act upon it.
	 * 
	 * @throws IOException
	 */
	private void processConnection() throws IOException {
		String jsonString = ""; // message from server

		while(!client.isClosed()) // process messages sent from server
		{
			try // read message and display it
			{
				// read the json string sent from the server.
				jsonString = (String) input.readObject();

				// Instantiate a new Message object from the json string.
				Message message = MessageFactory.fromJSON(jsonString);

				// Process the message appropriately depending on the value of
				// message's command.
				switch (message.getCommand()) {

				// This message is the first thing sent back when a new warrior
				// is added to the server
				// We will simply post a welcome message back to the user
				// indicating that they are connected.
				case GREETWARRIOR:
					ClientCombatantUI.getInstance().setWarriorMessage("Welcome to the field of battle, "
							+ message.getWarrior().toString() + "!");

					break;

				// This type of message contains an ArrayList of Warriors that
				// we use to show the player who is on the battle field.
				// We will also use this list to build the drop down where the
				// user chooses who to attack.
				case SENDOPPONENTS:

					// Pass the list of opponents to the GUI instance.
					ClientCombatantUI.getInstance().setOpponents(
							message.getOpponents());

					// Show the user who is on the battle field.
					displayOpponents(message);
					break;

				// This notifies this warrior that another warrior has engaged
				// it in battle
				// From here we will notify the player, and allow him or her to
				// decide what to do next.
				case SELECTACTION:
					ClientCombatantUI.getInstance()
							.haveBeenChosenForBattleSelectAction(
									message.getWarrior(),message.getAction());
					break;

				// There were no opponents already connected to the server. The
				// player must wait for somebody else to join before they can do
				// anything.
				case NOOPPONENTS:
					break;
				
				// This notifies the warrior of the results of the their engagement battle.
				//   From here the warriors will have their health updated
				case HEALTHUPDATE:
					if(Consts.LOGGING){
						System.out.println("Debugging ProcessConnection - Client - In HEALTHUPDATE case");}
						
					displayMessage("Battle Results:\n\n"
							+ message.getOpponent().getName() + " used "+ message.getActions()[1]+".\nHealth: "+message.getOpponent().getHealth()+"\n\n"
							+ message.getWarrior().getName() + " used "+ message.getActions()[0]+".\nHealth: "+message.getWarrior().getHealth()+"\n");
							
					//Determine if I am the originating warrior or the opponent, and call updateMyHealth accordingly.
					if(message.getWarrior().getWarriorId().equals(ClientCombatantUI.getInstance().getWarrior().getWarriorId())){
						ClientCombatantUI.getInstance().updateMyHealth(message.getWarrior().getHealth());
					} else {
						ClientCombatantUI.getInstance().updateMyHealth(message.getOpponent().getHealth());
					}									
					break;					
					
				default:
					break;
				}
			}
			catch (EOFException e){
				e.printStackTrace();
				Utils.saveWarriorToFile(ClientCombatantUI.getInstance().getWarrior(), ClientCombatantUI.getInstance().getWarriorFile());
				displayMessage("The server has disconnected.");
				closeConnection();
			}
			catch (ClassNotFoundException classNotFoundException) {
				displayMessage("\nUnknown object type received");
			} // end catch

		}
	} // end method processConnection

	/**
	 * To be called when the player is done with the connection. This will close
	 * the streams and socket connection.
	 */
	public void closeConnection() {
		try {
			output.close(); // close output stream
			input.close(); // close input stream
			client.close(); // close socket
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch
	} // end method closeConnection

	/**
	 * Pass a json string to the server. You should pass in a Message, which
	 * will be marshalled to JSON via MessageFactory before being sent.
	 * 
	 * @param message
	 *            A Message object to be passed to the server.
	 */
	public void sendData(Message message) {
		String jsonString = MessageFactory.toJSON(message);
		try // send object to server
		{
			output.writeObject(jsonString);
			output.flush(); // flush data to output
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch
	} // end method sendData

	/**
	 * Send a message to the GUI to be displayed to the user
	 * 
	 * @param messageToDisplay
	 *            The string you wish to display
	 */
	protected void displayMessage(final String messageToDisplay) {
		uiInstance.displayText(messageToDisplay);

	} // end method displayMessage

	/**
	 * Show the user the list of warriors (including themselves) on the
	 * battlefield.
	 * 
	 * @param message
	 *            A Message object which contains an opponents ArrayList of
	 *            Warriors. This should come from a SENDOPPONENTS message.
	 */
	private void displayOpponents(Message message) {
		displayMessage("\nWarriors on the battlefield:\n");
		StringBuilder s = new StringBuilder();
		for (Warrior w : message.getOpponents()) {
			s.append(w.toFormattedString());			
		}
		displayMessage(s.toString());
	}
} // end class Client