package com.apps4you.moderator;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Multi-threaded server version that allows for multiple clients 
 * to communicate with the server at the same time
 * @author Jon Hancock
 *
 */
public class ThreadedServer {
		private ServerSocket server; // server socket to connect with clients

		private Moderator moderator; //a link to the moderator
		private ModeratorUI uiInstance;// a link to the associated UI instance for the server
		private ExecutorService runGame; // will run players

	   /**
	    * The constructor for the ThreadedServer
	    */
	   public ThreadedServer()
	   {		   
		   //Setup the Moderator UI
		   ModeratorUI mUI = new ModeratorUI();
		   this.uiInstance = mUI;
		   mUI.setVisible(true);
			
		
		   moderator = Moderator.getInstance(); //since moderator is a singleton get an instance of the moderator
		   runGame = Executors.newCachedThreadPool(); //link to the thread pool
		   try
		   {
		       server = new ServerSocket( 12345, 2 ); // set up ServerSocket
		   } // end try
		   catch ( IOException ioException ) 
		   {
		       ioException.printStackTrace();
		       System.exit( 1 );
		   } // end catch
	   }
	
	   /**
	    * A client is connecting so accept the connection and add the client\warrior combatant
	    */
	   public void execute()
	   {
		 //Continuely loop looking for clients that wish to connect  
	     while(true){
	    	 try {
	    		//Setup a WarriorConnection which is a client who wishes to participate in battles 
				WarriorConnection wc = new WarriorConnection(server.accept(),moderator,uiInstance);
				//Maintain a list of the connected clients
				moderator.addOpponent(wc);
				runGame.execute(wc);
				
	    	 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	   } // end method execute
}
