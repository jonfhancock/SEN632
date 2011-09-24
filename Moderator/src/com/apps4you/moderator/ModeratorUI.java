package com.apps4you.moderator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * A UI module that just gives the end user insight into the server
 * @author Craig Mersereau
 */
public class ModeratorUI extends JFrame {

	private static final long serialVersionUID = -5171666511283832798L;
	private JPanel contentPane; //Pane that holds the content for the window
	private JButton closeButton = new JButton("Close"); //A close button for the server
	private JLabel welcomeLabel = new JLabel("Welcome to the Apps4You Server."); //Label that explains what the intent of the window is
	private static JTextArea combatantsConnectedArea = new JTextArea();//Area for the end user to se action occuring on the server

	/**
	 * Create the frame for the Moderator
	 */
	public ModeratorUI() 
	{
		setTitle("Apps4You - Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EventHandler handler = new EventHandler();
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		closeButton.setLocation(152, 207);
		
		
		contentPane.add(closeButton);
		welcomeLabel.setBounds(5, 5, 474, 14);
		contentPane.add(welcomeLabel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 38, 373, 158);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(combatantsConnectedArea);

		closeButton.setSize(99, 31);

		closeButton.addActionListener(handler);
	       	   
		setSize( 426, 288 ); // set size of window
		setVisible( true ); // show window		
	}
	
	/**
	 * Allows other components to add messages to be displayed to the end user
	 * @param text String of the text that the is desired to be shown to the end user
	 */
    public void displayText(String text){
    	combatantsConnectedArea.append(text);
    }

	/**
	 * Inner class that handles the Action Listener for the this UI
	 * @author Craig Mersereau
	 */
	 private class EventHandler implements ActionListener
	 {	
       /**
        * Handles the close button event
        * @param e is an ActionEvent that needs to be handled	
        */
	   public void actionPerformed (ActionEvent e)
	   {
		  //The close button was selected.  Clean up and exit
		  if (e.getSource() == closeButton)
		  {
		     dispose();
		     System.exit(0);
		  }	
	   }
	 }
}
