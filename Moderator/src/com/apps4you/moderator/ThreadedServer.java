package com.apps4you.moderator;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadedServer {
		private ServerSocket server; // server socket to connect with clients

		private Moderator moderator;
		private ModeratorUI uiInstance;
		private ExecutorService runGame; // will run players

		
	   public ThreadedServer(){
		   ModeratorUI mUI = new ModeratorUI();
			this.uiInstance = mUI;
			mUI.setVisible(true);
			moderator = new Moderator();
			runGame = Executors.newCachedThreadPool();
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
	
	   public void execute()
	   {
	     while(moderator.getOpponentCount() < 2){
	    	 try {
				WarriorConnection wc = new WarriorConnection(server.accept(),moderator,uiInstance);
				moderator.addOpponent(wc);
				runGame.execute(wc);
	    	 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	   } // end method execute
}
