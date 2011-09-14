package com.apps4you.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class ClientUI extends JFrame implements ActionListener
{
   private JButton selectDataFileButton = new JButton("Select File");
   private JButton opponentButton = new JButton("Opponent");
   private JButton closeButton = new JButton("Close");
   private JLabel welcomeLabel = new JLabel("Welcome to the Apps4You Client.");
   private JLabel moderatorCommentsLabel = new JLabel("Moderator Comments:");
   private JTextArea moderatorCommentsArea = new JTextArea();
   
   private Client client;
   private final String  DEFAULT_HOST_LOCATION = "localhost";
   private String host ;
   
   public ClientUI()
   {
	   //lookAndFeelSetup(); //-- Wasn't happy with the Nimbus look
	   
	   setupFrame();
	   
	   //Need to add a button to connect to Server and allow for the 
	   //user to select the server location
	   host = DEFAULT_HOST_LOCATION;
	   
	   connectToServer(host);
	   
   }
   
   private void connectToServer(String hostLocation)
   {
	   this.client = new Client(hostLocation);
	   this.client.runClient();   
   }
   
   private void lookAndFeelSetup()
   {
	   try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		        	UIManager.setLookAndFeel(info.getClassName());
		        	System.out.println("\n Nimbus UI was selected.");
		        	break;
		            
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, then use the system default.
			System.out.println("\n Nimbus UI was not available \n");
		}   
   }
   
   private void setupFrame()
   {
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.getContentPane().add(selectDataFileButton, BorderLayout.SOUTH);
	   this.getContentPane().add(opponentButton, BorderLayout.SOUTH);
	   this.getContentPane().add(closeButton, BorderLayout.SOUTH);
       this.getContentPane().add(welcomeLabel,BorderLayout.PAGE_START);
       this.getContentPane().add(new JScrollPane(moderatorCommentsArea), BorderLayout.EAST);
	   this.getContentPane().add(new JLabel(""),BorderLayout.SOUTH);
	   
	   this.setTitle("Apps4You - Client");
	   this.selectDataFileButton.setSize(100, 40);
	   this.closeButton.setSize(100, 40);
	   this.opponentButton.setSize(100, 40);
	   this.selectDataFileButton.addActionListener(this);
	   this.closeButton.addActionListener(this);
	   this.opponentButton.addActionListener(this);
	   this.selectDataFileButton.setBounds(80, 60, 100, 30);
	   this.closeButton.setBounds(80, 180, 100, 30);
	   this.opponentButton.setBounds(80, 120, 100, 30);
	   this.moderatorCommentsArea.setBounds(0, 200, 200, 30);
       	   
	   this.setSize( 500, 300 ); // set size of window
	   this.setVisible( true ); // show window
	   
   }
   

   public void actionPerformed (ActionEvent e)
   {
	 //Handle Select button action.
	    if (e.getSource() == selectDataFileButton) 
	    {	    	
	        final JFileChooser fc = new JFileChooser();	    	
	        int returnVal = fc.showOpenDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) 
	        {
	        	File file = fc.getSelectedFile();
	            System.out.println("File " + file.getName() + " was selected.");
	            this.client.readFileToCreateCombatant(file);
	            this.selectDataFileButton.setEnabled(false);
	        } else
	        {
	        	System.out.println("Open command cancelled by user./n");
	        }	        
	   }
	    else if (e.getSource() == opponentButton)
	    {
	    	System.out.println("\n Oppents Selection was chosen - Not implemented Yet. \n");
	    }
	    else if (e.getSource() == closeButton)
	    {
	    	dispose();
	    	System.exit(0);
	    }
   }
}
