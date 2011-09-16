package com.apps4you.moderator;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.apps4you.shared.Warrior;
import com.apps4you.shared.WarriorFactory;

public class Server extends JFrame implements ActionListener
{

   private static final long serialVersionUID = -5693161904305556535L;
	
   private JTextField enterField; // inputs message from user
   private JTextArea displayArea; // display information to user
   private ObjectOutputStream output; // output stream to client
   private ObjectInputStream input; // input stream from client
   private ServerSocket server; // server socket
   private Socket connection; // connection to client
   private int counter = 1; // counter of number of connections
   
   
   //New UI Pieces
   private JFrame mainFrame = new JFrame("Apps4You - Server");
   private JButton closeButton = new JButton("Close");
   private JLabel welcomeLabel = new JLabel("Welcome to the Apps4You Server.");
   private JTextArea combatantsConnectedArea = new JTextArea();
   
   

   // set up GUI
   public Server()
   {
      super( "Server" );

      enterField = new JTextField(); // create enterField
      enterField.setEditable( false );
      enterField.addActionListener(
         new ActionListener() 
         {
            // send message to client
            public void actionPerformed( ActionEvent event )
            {
               sendData( event.getActionCommand() );
               enterField.setText( "" );
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      add( enterField, BorderLayout.NORTH );

      displayArea = new JTextArea(); // create displayArea
      add( new JScrollPane( displayArea ), BorderLayout.CENTER );

      setSize( 300, 150 ); // set size of window
      setVisible( true ); // show window
      

      setupMainFrame(); //Setup the Window and start gathering info form the user
          
      combatantsConnectedArea.append("Combatants Connected:\n");            
      
      
   } // end Server constructor

   // set up and run server 
   public void runServer()
   {
      try // set up server to receive connections; process connections
      {
         server = new ServerSocket( 12345, 100 ); // create ServerSocket

         while ( true ) 
         {
            try 
            {
               waitForConnection(); // wait for a connection
               getStreams(); // get input & output streams
               processConnection(); // process connection
            } // end try
            catch ( EOFException eofException ) 
            {
               displayMessage( "\nServer terminated connection" );
            } // end catch
            finally 
            {
               closeConnection(); //  close connection
               ++counter;
            } // end finally
         } // end while
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method runServer

   // wait for connection to arrive, then display connection info
   private void waitForConnection() throws IOException
   {
      displayMessage( "Waiting for connection\n" );
      connection = server.accept(); // allow server to accept connection            
      displayMessage( "Connection " + counter + " received from: " +
         connection.getInetAddress().getHostName() );
   } // end method waitForConnection

   // get streams to send and receive data
   private void getStreams() throws IOException
   {
      // set up output stream for objects
      output = new ObjectOutputStream( connection.getOutputStream() );
      output.flush(); // flush output buffer to send header information

      // set up input stream for objects
      input = new ObjectInputStream( connection.getInputStream() );

      displayMessage( "\nGot I/O streams\n" );
   } // end method getStreams

   // process connection with client
   private void processConnection() throws IOException
   {
      String message = "Connection successful";
      sendData( message ); // send connection successful message

      // enable enterField so server user can send messages
      setTextFieldEditable( true );

      do // process messages sent from client
      { 
         try // read message and display it
         {
            message = ( String ) input.readObject(); // read new message
//            Warrior w = WarriorFactory.fromJSON(message);
//            sendData("Welcome, " + w.getName()+ "!");
            displayMessage( "\n" + message ); // display message
         } // end try
         catch ( ClassNotFoundException classNotFoundException ) 
         {
            displayMessage( "\nUnknown object type received" );
         } // end catch

      } while ( !message.equals( "CLIENT>>> TERMINATE" ) );
   } // end method processConnection

   // close streams and socket
   private void closeConnection() 
   {
      displayMessage( "\nTerminating connection\n" );
      setTextFieldEditable( false ); // disable enterField

      try 
      {
         output.close(); // close output stream
         input.close(); // close input stream
         connection.close(); // close socket
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method closeConnection

   // send message to client
   private void sendData( String message )
   {
      try // send object to client
      {
         output.writeObject( "SERVER>>> " + message );
         output.flush(); // flush output to client
         displayMessage( "\nSERVER>>> " + message );
      } // end try
      catch ( IOException ioException ) 
      {
         displayArea.append( "\nError writing object" );
      } // end catch
   } // end method sendData

   // manipulates displayArea in the event-dispatch thread
   private void displayMessage( final String messageToDisplay )
   {
      SwingUtilities.invokeLater(
         new Runnable() 
         {
            public void run() // updates displayArea
            {
               displayArea.append( messageToDisplay ); // append message
            } // end method run
         } // end anonymous inner class
      ); // end call to SwingUtilities.invokeLater
   } // end method displayMessage

   // manipulates enterField in the event-dispatch thread
   private void setTextFieldEditable( final boolean editable )
   {
      SwingUtilities.invokeLater(
         new Runnable()
         {
            public void run() // sets enterField's editability
            {
               enterField.setEditable( editable );
            } // end method run
         }  // end inner class
      ); // end call to SwingUtilities.invokeLater
   } // end method setTextFieldEditable
   
   
   
   

   private void setupMainFrame()
   {
	   this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	   this.mainFrame.getContentPane().add(closeButton, BorderLayout.SOUTH);
       this.mainFrame.getContentPane().add(welcomeLabel,BorderLayout.PAGE_START);
       this.mainFrame.getContentPane().add(new JScrollPane(combatantsConnectedArea), BorderLayout.CENTER);

	   this.closeButton.setSize(100, 40);

	   this.closeButton.addActionListener(this);
       	   
	   this.mainFrame.setSize( 500, 300 ); // set size of window
	   this.mainFrame.setVisible( true ); // show window
	   
   }
   

   public void actionPerformed (ActionEvent e)
   {
	 //Handle Select button action.
	    if (e.getSource() == closeButton)
	    {
	    	dispose();
	    	System.exit(0);
	    }
   }
   
   
   
   
} // end class Server