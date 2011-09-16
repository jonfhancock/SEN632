package com.apps4you.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.apps4you.shared.Actions;
import com.apps4you.shared.Origins;
import com.apps4you.shared.Warrior;
import com.apps4you.shared.WarriorFactory;

public class ClientCombatantUI extends JFrame {

   private static final long serialVersionUID = -8606655646939582216L;
   private JPanel contentPane;
   private final JButton connectButton = new JButton("Server");
   private final static JButton selectDataFileButton = new JButton("Select File");
   private final static JButton opponentButton = new JButton("Opponent");
   private final JButton newCombatantButton = new JButton("New");
   private final JButton closeButton = new JButton("Close");
   private final JButton actionButton = new JButton("Action");
   private JLabel welcomeLabel = new JLabel("Welcome to the Apps4You Client.");
   private static JTextArea moderatorCommentsArea = new JTextArea();
   
   private static Client client;
   private static File file;
   private static Warrior mWarrior;
   private static Warrior mOpponent;
   private static String hostLocation = "localhost";
   private final JPanel battlePanel = new JPanel();
   private Actions battleAction;

   
   protected void connectToServer()
   {
	   
	   try{
	        new Thread(
	                new Runnable() {
	                    public void run() {
	                    	client = new Client(hostLocation);
	    	            	try {
								client.runClient(WarriorFactory.toJSON(mWarrior));
							} catch (JsonGenerationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
	                    }
	                }).start();
        	
	     
	   } catch(Exception e){
		   displayText("There was an error connecting to the Moderator server.  Are you sure it is running?");
	   }
   }

   public static void displayText(String text){
	   moderatorCommentsArea.append(text);
   }

   private static ClientCombatantUI instance = null;
   
   public static ClientCombatantUI getInstance(){
	   if(instance == null){
		   instance = new ClientCombatantUI();
	   }
	   return instance;
   }
   
	/**
	 * Create the frame.
	 */
	private ClientCombatantUI() {
		setResizable(false);
		   EventHandler handler = new EventHandler();
		   
	       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   setBounds(100, 100, 450, 300);
		   contentPane = new JPanel();
		   contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		   setContentPane(contentPane);
	       JScrollPane scrollPane = new JScrollPane();
	       scrollPane.setBounds(272, 55, 212, 307);
		   JLabel modCommentsLabel = new JLabel("Moderator Comments:");
		   modCommentsLabel.setBounds(272, 30, 162, 20);
		   
		   setTitle("Apps4You - Client");
		   closeButton.setBounds(64, 289, 100, 30);
		   closeButton.addActionListener(handler);
		   
		   JPanel setupPanel = new JPanel();
		   setupPanel.setBounds(5, 30, 234, 108);
		   setupPanel.setLayout(null);
		   setupPanel.setBorder(BorderFactory.createTitledBorder("Setup"));
		   contentPane.setLayout(null);
		   contentPane.add(closeButton);
		   welcomeLabel.setBounds(5, 5, 474, 14);
		   contentPane.add(welcomeLabel);
		   contentPane.add(scrollPane);
		   scrollPane.setViewportView(moderatorCommentsArea);
		   moderatorCommentsArea.setMinimumSize(new Dimension(200, 300));
		   moderatorCommentsArea.setLineWrap(true);
		   contentPane.add(modCommentsLabel);
		   contentPane.add(setupPanel);
		   connectButton.setBounds(10, 26, 100, 30);
		   setupPanel.add(connectButton);
		   selectDataFileButton.setBounds(120, 26, 100, 30);
		   setupPanel.add(selectDataFileButton);
		   newCombatantButton.setBounds(69, 67, 100, 30);
		   setupPanel.add(newCombatantButton);
		   battlePanel.setBounds(5, 160, 234, 60);
		   
		   contentPane.add(battlePanel);
		   battlePanel.setLayout(null);
		   battlePanel.setBorder(BorderFactory.createTitledBorder("Battle"));

		   opponentButton.setBounds(22, 30, 81, 23);
		   battlePanel.add(opponentButton);
		   opponentButton.addActionListener(handler);
		   opponentButton.setEnabled(false);
		   

		   actionButton.setEnabled(false);
		   actionButton.setBounds(113, 30, 89, 23);
		   battlePanel.add(actionButton);
		   newCombatantButton.addActionListener(handler);
		   selectDataFileButton.addActionListener(handler);
		   connectButton.addActionListener(handler);	
		   actionButton.addActionListener(handler);	
		   
		   setSize( 516, 400 ); // set size of window
		   setVisible( true ); // show window
		   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			   
	}
	
	private void setUpUI(){
		
	}
	
	public void setWarrior(Warrior warrior){
		mWarrior = warrior;
        connectToServer();
        selectDataFileButton.setEnabled(false);
        opponentButton.setEnabled(true);		
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
		        
		        int returnVal = fc.showOpenDialog(ClientCombatantUI.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) 
		        {
		        	file = fc.getSelectedFile();
		            System.out.println("File " + file.getName() + " was selected.");
		            setWarrior(Utils.readFileToCreateCombatant(file));
            
		        } else
		        {
		        	System.out.println("Open command cancelled by user./n");
		        }	        
		   }
		    else if (e.getSource() == opponentButton)
		    {	    	
		    	//Get a list of the possible opponents from the server and then 
		    	Warrior[] possibilities = {new Warrior("Zerg",Origins.JAGLANBETA,"Come to the dark side"),
		    							   new Warrior("Woody",Origins.BREQUINDA,"He's a cowboy"),
		    							   new Warrior("Ham",Origins.VOGSPHERE,"AKA Evil Dr. Porkchop"),
		    							   new Warrior("Buzz",Origins.KAKRAFOON,"To infinity and beyond!")};
		    	mOpponent= (Warrior)JOptionPane.showInputDialog(
		    			ClientCombatantUI.this,
		    	                    "Please select your opponent:",
		    	                    "Opponent Selection Dialog",
		    	                    JOptionPane.QUESTION_MESSAGE,
		    	                    null,
		    	                    possibilities,
		    	                    null);	    	
		    	System.out.println("\n Opponent Selection was chosen - with an opponent of: " + mOpponent ); 

		    	JOptionPane.showMessageDialog(ClientCombatantUI.this,
		    		    "The Opponent button is not implemented, yet.",
		    		    "Warning",
		    		    JOptionPane.WARNING_MESSAGE);
		    	ClientCombatantUI.this.actionButton.setEnabled(true);		
		    	//TODO Update who was selected
		    }
		    else if (e.getSource() == actionButton)
		    {
		    	
		    	Object[] possibilities = Actions.values();
		    	battleAction = (Actions)JOptionPane.showInputDialog(
		    			ClientCombatantUI.this,
		    	                    "Please select your battle action:",
		    	                    "Battle Selection Dialog",
		    	                    JOptionPane.QUESTION_MESSAGE,
		    	                    null,
		    	                    possibilities,
		    	                    null);	   
		    	System.out.println("\n The selected action was: " + battleAction.toString() ); 
		    	client.sendData(mWarrior.getName()+" used " + battleAction.toString() + " on " + mOpponent.getName() );
		    }
		    else if (e.getSource() == connectButton)
		    {

		    	String s = (String)JOptionPane.showInputDialog(
		    			ClientCombatantUI.this,
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
		    		ClientCombatantUI.this.hostLocation = s;	    	
			     	 
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
		    	if ((client != null)&&(mWarrior !=null))
		    	{
		    	  if(file == null){
		    		  file = new File(mWarrior.getName()+".wdat");
		    	  }
		    	  Utils.saveWarriorToFile(mWarrior, file);
		    	}
		    	dispose();
		    	System.exit(0);
		    }
	     } 
	 }
  }
