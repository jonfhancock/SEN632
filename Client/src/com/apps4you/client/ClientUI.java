package com.apps4you.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.apps4you.shared.Warrior;



public class ClientUI extends JFrame 
{

   private static final long serialVersionUID = -8606655646939582216L;
   private JPanel contentPane;
   private final JButton connectButton = new JButton("Server");
   private final JButton selectDataFileButton = new JButton("Select File");
   private final JButton opponentButton = new JButton("Opponent");
   private final JButton newCombatantButton = new JButton("New");
   private final JButton closeButton = new JButton("Close");
   private JLabel welcomeLabel = new JLabel("Welcome to the Apps4You Client.");
   private JLabel moderatorCommentsLabel = new JLabel("Moderator Comments:");
   private JTextArea moderatorCommentsArea = new JTextArea();
   
   private Client client;
   private File file;
   private Warrior w;
   private String hostLocation;
   
   
   public ClientUI()
   {
	   //lookAndFeelSetup(); //-- Wasn't happy with the Nimbus look
	   
	   setupFrame();
	   
   
   }
   
   protected void connectToServer()
   {
	   this.client = new Client(this.hostLocation);
	   try{
	   this.client.runClient();   
	   } catch(Exception e){
		   this.moderatorCommentsArea.setText("There was an error connecting to the Moderator server.  Are you sure it is running?");
	   }
   }

   private void setupFrame()
   {
	   EventHandler handler = new EventHandler();
	   
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setBounds(100, 100, 450, 300);
	   contentPane = new JPanel();
	   contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	   contentPane.setLayout(new BorderLayout(0, 0));
	   setContentPane(contentPane);
	   
	   this.getContentPane().add(connectButton, BorderLayout.SOUTH);
	   this.getContentPane().add(selectDataFileButton, BorderLayout.SOUTH);
	   this.getContentPane().add(opponentButton, BorderLayout.SOUTH);
	   this.getContentPane().add(newCombatantButton, BorderLayout.SOUTH);   
	   this.getContentPane().add(closeButton, BorderLayout.SOUTH);
       this.getContentPane().add(welcomeLabel,BorderLayout.PAGE_START);
       moderatorCommentsArea.setMinimumSize(new Dimension(200, 300));
       moderatorCommentsArea.setText("Moderator Comments:");
       this.getContentPane().add(new JScrollPane(moderatorCommentsArea), BorderLayout.EAST);
	   this.getContentPane().add(new JLabel(""),BorderLayout.SOUTH);
	   
	   this.setTitle("Apps4You - Client");
	   this.selectDataFileButton.setSize(100, 40);
	   this.closeButton.setSize(100, 40);
	   this.opponentButton.setSize(100, 40);
	   this.newCombatantButton.setSize(100, 40);
	   this.selectDataFileButton.addActionListener(handler);
	   this.closeButton.addActionListener(handler);
	   this.opponentButton.addActionListener(handler);
	   this.connectButton.addActionListener(handler);	
	   this.newCombatantButton.addActionListener(handler);
	   this.connectButton.setBounds(80,40,100,30);	
	   this.selectDataFileButton.setBounds(80, 100, 100, 30);
	   this.opponentButton.setBounds(80, 160, 100, 30);
	   this.newCombatantButton.setBounds(80, 220, 100, 30);
	   this.closeButton.setBounds(80, 280, 100, 30);		   
	   this.moderatorCommentsArea.setBounds(0, 200, 200, 30);	      
	   this.moderatorCommentsArea.setColumns(20);
	   this.moderatorCommentsArea.setLineWrap(true);
	   this.opponentButton.setEnabled(false);
	   
	   this.setSize( 500, 400 ); // set size of window
	   this.setVisible( true ); // show window
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   
	   
   }
   
 private class EventHandler implements ActionListener
 {

	@Override
   public void actionPerformed (ActionEvent e)
   {
	 //Handle Select button action.
	    if (e.getSource() == selectDataFileButton) 
	    {	    	
	        final JFileChooser fc = new JFileChooser();	
	        final FileNameExtensionFilter fileFilter= new FileNameExtensionFilter("Warrior data file", "wdat");
	        fc.setFileFilter(fileFilter);
	        File f;
			try {
				f = new File(new File(".").getCanonicalPath());
				fc.setCurrentDirectory(f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        int returnVal = fc.showOpenDialog(ClientUI.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) 
	        {
	        	file = fc.getSelectedFile();
	            System.out.println("File " + file.getName() + " was selected.");
	            w = Utils.readFileToCreateCombatant(file);
	            connectToServer();
	            ClientUI.this.selectDataFileButton.setEnabled(false);
	            ClientUI.this.opponentButton.setEnabled(true);
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
	    			            ClientUI.this,
	    	                    "Please select your opponent:",
	    	                    "Opponent Selection Dialog",
	    	                    JOptionPane.QUESTION_MESSAGE,
	    	                    null,
	    	                    possibilities,
	    	                    null);	    	
	    	System.out.println("\n Opponent Selection was chosen - with an opponent of: " + s ); 

	    	JOptionPane.showMessageDialog(ClientUI.this,
	    		    "The Opponent button is not implemented, yet.",
	    		    "Warning",
	    		    JOptionPane.WARNING_MESSAGE);
	    	
	    	//TODO Update who was selected
	    }

	    else if (e.getSource() == connectButton)
	    {

	    	String s = (String)JOptionPane.showInputDialog(
	    			            ClientUI.this,
	    	                    "Please enter the prefered server that you would like to connect to:",
	    	                    "Prefered Server Dialog",
	    	                    JOptionPane.QUESTION_MESSAGE,
	    	                    null,
	    	                    null,
	    	                    "localhost");

	    	//If a string was returned, say so.
	    	if ((s != null) && (s.length() > 0)&& (s.length()<=15)) 
	    	{
	    		System.out.println("\n Prefered Server location was chosen - with a host of: " + s ); 
	    		ClientUI.this.hostLocation = s;	    	
		     	 
	    	}
	    	else
	    	{
	    		System.out.println("\n Prefered server location is not valid with: " + s + " as a value.");	 
	    	}
   	
	    }
	    else if (e.getSource() == newCombatantButton)
	    {
	    	CreateWarrior dialog = new CreateWarrior();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			
	    }
	    else if (e.getSource() == closeButton)
	    {
	    	if ((client != null)&&(w !=null))
	    	{
	    	  w.setHealth(w.getHealth()-10);
	    	  Utils.saveWarriorToFile(w, file);
	    	}
	    	dispose();
	    	System.exit(0);
	    }
     } 
   }
}
