package com.apps4you.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class ClientUI extends JFrame implements ActionListener
{

   private static final long serialVersionUID = -8606655646939582216L;
   private JPanel contentPane;
   private final JButton connectButton = new JButton("Connect");
   private final JButton selectDataFileButton = new JButton("Select File");
   private final JButton opponentButton = new JButton("Opponent");
   private final JButton closeButton = new JButton("Close");
   private JLabel welcomeLabel = new JLabel("Welcome to the Apps4You Client.");
   private JLabel moderatorCommentsLabel = new JLabel("Moderator Comments:");
   private JTextArea moderatorCommentsArea = new JTextArea();
   
   private Client client;
   public static final String  DEFAULT_HOST_LOCATION = "localhost";

   
   public ClientUI()
   {
	   //lookAndFeelSetup(); //-- Wasn't happy with the Nimbus look
	   
	   setupFrame();
	   
   
   }
   
   protected void connectToServer(String hostLocation)
   {
	   this.client = new Client(hostLocation);
	   this.client.runClient();   
   }
   
   
   private void setupFrame()
   {
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setBounds(100, 100, 450, 300);
	   contentPane = new JPanel();
	   contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	   contentPane.setLayout(new BorderLayout(0, 0));
	   setContentPane(contentPane);

	   this.getContentPane().add(selectDataFileButton, BorderLayout.SOUTH);
	   this.getContentPane().add(opponentButton, BorderLayout.SOUTH);
	   this.getContentPane().add(closeButton, BorderLayout.SOUTH);
       this.getContentPane().add(welcomeLabel,BorderLayout.PAGE_START);
       moderatorCommentsArea.setMinimumSize(new Dimension(200, 300));
       moderatorCommentsArea.setText("Moderator Comments:");
       this.getContentPane().add(new JScrollPane(moderatorCommentsArea), BorderLayout.EAST);
	   this.getContentPane().add(connectButton, BorderLayout.SOUTH);
	   this.getContentPane().add(new JLabel(""),BorderLayout.SOUTH);
	   
	   this.setTitle("Apps4You - Client");
	   this.selectDataFileButton.setSize(100, 40);
	   this.closeButton.setSize(100, 40);
	   this.opponentButton.setSize(100, 40);
	   this.selectDataFileButton.addActionListener(this);
	   this.closeButton.addActionListener(this);
	   this.opponentButton.addActionListener(this);
	   this.connectButton.addActionListener(this);	
	   this.connectButton.setBounds(80,40,100,30);	
	   this.selectDataFileButton.setBounds(80, 100, 100, 30);
	   this.opponentButton.setBounds(80, 160, 100, 30);
	   this.closeButton.setBounds(80, 220, 100, 30);		   
	   this.moderatorCommentsArea.setBounds(0, 200, 200, 30);	      
       	   
	   this.setSize( 500, 300 ); // set size of window
	   this.setVisible( true ); // show window
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   
	   
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
	    	//Get a list of the possible opponents from the server and then 
	    	Object[] possibilities = {"Zerg", "Woody", "Ham", "Buzz"};
	    	String s = (String)JOptionPane.showInputDialog(
	    	                    this,
	    	                    "Please select your opponent:",
	    	                    "Opponent Selection Dialog",
	    	                    JOptionPane.QUESTION_MESSAGE,
	    	                    null,
	    	                    possibilities,
	    	                    null);	    	
	    	System.out.println("\n Opponent Selection was chosen - with an opponent of: " + s ); 

	    	JOptionPane.showMessageDialog(this,
	    		    "The Opponent button is not implemented, yet.",
	    		    "Warning",
	    		    JOptionPane.WARNING_MESSAGE);
	    	
	    	//TODO Update who was selected
	    }

	    else if (e.getSource() == connectButton)
	    {

	    	String s = (String)JOptionPane.showInputDialog(
	    	                    this,
	    	                    "Please enter the server that you would like to connect to:",
	    	                    "Connection Dialog",
	    	                    JOptionPane.QUESTION_MESSAGE,
	    	                    null,
	    	                    null,
	    	                    "localhost");

	    	//If a string was returned, say so.
	    	if ((s != null) && (s.length() > 0)&& (s.length()<=15)) 
	    	{
	    		System.out.println("\n Connection Selection was chosen - with a host of: " + s ); 
		    	connectToServer(s);	    	
		     	 
	    	}
	    	else
	    	{
	    		System.out.println("\n Connection to host is not valid with: " + s + " as a value.");	 
	    	}
   	
	    }
	    else if (e.getSource() == closeButton)
	    {
	    	dispose();
	    	System.exit(0);
	    }
   }   
}
