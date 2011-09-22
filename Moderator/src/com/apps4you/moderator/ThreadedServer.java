package com.apps4you.moderator;

import java.io.IOException;
import java.net.ServerSocket;


public class ThreadedServer {
	   private ServerSocket server; // server socket to connect with clients

	   public ThreadedServer(){
		   
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
	     
	   } // end method execute
}
