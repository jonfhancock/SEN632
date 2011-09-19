package com.apps4you.moderator;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class ModeratorUI extends JFrame {

	private static final long serialVersionUID = -5171666511283832798L;
	private JPanel contentPane;
	private JButton closeButton = new JButton("Close");
	private JLabel welcomeLabel = new JLabel("Welcome to the Apps4You Server.");
	private static JTextArea combatantsConnectedArea = new JTextArea();

	/**
	 * Create the frame.
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
	
    public void displayText(String text){
    	combatantsConnectedArea.append(text);
    }

	
	 private class EventHandler implements ActionListener
	 {	
	
	   public void actionPerformed (ActionEvent e)
	   {
		 //Handle Select button action.
		    if (e.getSource() == closeButton)
		    {
		    	dispose();
		    	System.exit(0);
		    }
	
	   }
	 }
}
