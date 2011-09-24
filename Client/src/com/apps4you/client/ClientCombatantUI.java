package com.apps4you.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.apps4you.shared.Actions;
import com.apps4you.shared.Message;
import com.apps4you.shared.Warrior;

/**
 * The ClientCombatantUI class handles UI side of the client information
 * 
 * @author Craig Mersereau
 */
public class ClientCombatantUI extends JFrame {

	private static final long serialVersionUID = -8606655646939582216L;
	private JPanel contentPane; //Panel to put the content on
	private final JButton connectButton = new JButton("Server");  //Button for selecting the server location
	private final static JButton selectDataFileButton = new JButton(
			"Select File"); //Button for selecting what file to open
	private final static JButton opponentButton = new JButton("Opponent");  //Button to select your opponent
	private final JButton newCombatantButton = new JButton("New"); //Button to allow users to create new warrior
	private final JButton closeButton = new JButton("Close");//Close the client
	private final JButton actionButton = new JButton("Action");//Button to pick what action you want you to use against an opponent
	private JLabel welcomeLabel = new JLabel("Welcome to the Apps4You Client.");//Welcome tag
	private static JTextArea moderatorCommentsArea = new JTextArea(); //Text area for displaying messages to the user

	private static Client client;  //The client that that this UI is tied to
	private static File file; // The file that was either read\written for the warriors
	private static Warrior mWarrior;  //The warrior that this UI is tied to
	private static Warrior mOpponent; //The opponent that this UI is tied to
	
	private static String hostLocation = "localhost";  //The location of the server - defaulted to local host
	private ArrayList<Warrior> opponents; // A list of available opponents to select from
	private final JPanel battlePanel = new JPanel(); // Panel that the buttons for opponent and action are tied to
	private Actions battleAction; //The action that the warrior is going to use on an opponent

	/**
	 * Get Method for getting Warrior
	 * 
	 * @return Warrior
	 */
	public Warrior getWarrior(){
		return mWarrior;
	}
	/**
	 * Get Method for getting the Warrior File
	 * 
	 * @return File that contains the warriors previously saved information
	 */
	public File  getWarriorFile(){
		if (file == null) {
			file = new File(mWarrior.getName() + ".wdat");
		}
		return file;
	}
	
	/**
	 * This method instantiates the client class and registers with the server
	 * with the hostlocation that the user has provided or the default
	 * 
	 */
	protected void connectToServer() {

		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					client = new Client(hostLocation);
					//Send a message that indicates that a new warriror has been loaded and registering
					Message message = new Message(Message.MessageCommand.NEWWARRIOR,
							mWarrior);
					
					client.runClient(message);

				}
			}).start();

		} catch (Exception e) {
			displayText("There was an error connecting to the Moderator server.  Are you sure it is running?");
		}
	}

	/**
	 * A convenience method to have text to be displayed to the user added
	 * 
	 * @param String text The text to be added to the moderators comments area
	 */
	public void displayText(String text) {
		moderatorCommentsArea.setText(text);
	}

	//Setup of a static variable that can be used
	//in conjunction with a get instance
	private static ClientCombatantUI instance = null;  

	/**
	 * ClientCombatantUI is a singleton class.
	 * Gain an instance of this UI from outside
	 * areas in order to pass information or call methods
	 * 
	 * @return ClientCombatantUI an instance of this ClientCombatantUI
	 */
	public static ClientCombatantUI getInstance() {
		//If no instance is available create a new one
		if (instance == null) {
			instance = new ClientCombatantUI();
		}
		//return the instance
		return instance;
	}

	/**
	 * Since ClientCombtantUI is a singleton, you cannot instantiate it.
	 * Use getInstance() instead.
	 * Create the frame.
	 */
	private ClientCombatantUI() {
		setResizable(false);
		opponents = new ArrayList<Warrior>();

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

		setSize(516, 400); // set size of window
		setVisible(true); // show window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


	/**
	 * Set Method for the Warrior
	 * 
	 * @param Warrior The warrior that is intended to be associated with thsi UI
	 */
	public void setWarrior(Warrior warrior) {
		mWarrior = warrior;
		connectToServer();
		
		//Enable & disable other buttons to help control flow in the client
		selectDataFileButton.setEnabled(false);
		opponentButton.setEnabled(true);
	}

	/**
	 * Identifies the warrior that they have been selected for battle and 
	 * sets up the choice on how to defend their warrior
	 * 
	 * @param Warrior The warrior that has selected you for battle
	 * @param Actions The action that the warrior is using against you
	 */
	public void haveBeenChosenForBattleSelectAction(Warrior whoSelectedMe,Actions action) {
		//Logging
		if(Consts.LOGGING){
			System.out.println("Been Selected for Battle now choose Action");
			System.out.println("Who selected me "+ whoSelectedMe.getName());
			System.out.println("They are using: " + action);
		}
		
		    //Setup dialog that allows the warrior to chose how to defend themselves
			Object[] possibilities = Actions.values();
			battleAction = (Actions) JOptionPane
					.showInputDialog(
							ClientCombatantUI.this,
							whoSelectedMe.getName() + " wants to fight you using "+ action +".  Please select your battle action:",
							"Battle Selection Dialog",
							JOptionPane.QUESTION_MESSAGE, null, possibilities, null);
			
			if(Consts.LOGGING){
				System.out.println("\n The selected action as a defense was: "
						+ battleAction.toString());
				System.out.println("Warrior is: " + mWarrior.getName());
			//Send the message that indicates that the defense was selected
			Message message = new Message(Message.MessageCommand.DEFENSESELECTED,
					mWarrior, new Actions[]{battleAction,action},whoSelectedMe);
			client.sendData(message);
		}
	}

	/**
	 * Update my health allows for the Warrior to have their health 
	 * properly set after the battle
	 * 
	 * @param int The health that the warrior needs to be set to post battle
	 */
	public void updateMyHealth(int health)
	{
		//Just set the health to the new number if the current health is greater than 0
		if(health > 0){
			mWarrior.setHealth(health);
			
		} 
		//Health is to low and the warrior must 
		//close as they are no longer capable of continuing
		else {
			mWarrior.setHealth(0);
			Utils.saveWarriorToFile(mWarrior, getWarriorFile());
			Message message = new Message(Message.MessageCommand.IAMDEAD);
			displayText("Sadly, you didn't survive.");
			setWarriorMessage(mWarrior.getName()+", You are dead.");
			client.sendData(message);
			client.closeConnection();
			
		}
		mWarrior.setHealth(health);
		if(Consts.LOGGING){
			System.out.println("Update my health to " + health);
		}
			
	}
	
	/**
	 * Class that handles the events that are tracked by this class
	 */
	private class EventHandler implements ActionListener {

		/**
		 * Overrides the action Performed method so that each action can be handled correctly
		 * @Override
		 */
		public void actionPerformed(ActionEvent e) {
			// Handle Select button action.
			
			//The choosing of a file to open to read the warrior
			if (e.getSource() == selectDataFileButton) {
				//launch a file chooser dialog
				final JFileChooser fc = new JFileChooser();
				//Filter down the file types expected to be seen to a wdat
				final FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(
						"Warrior data file", "wdat");
				//Apply the filter
				fc.setFileFilter(fileFilter);
				File f;
				try {
					//Setup default start location and open the selected file
					f = new File(new File(".").getCanonicalPath());
					fc.setCurrentDirectory(f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//Get the selected file
				int returnVal = fc.showOpenDialog(ClientCombatantUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					if(Consts.LOGGING){
						System.out.println("File " + file.getName()
								+ " was selected.");
					}
					//Read the file that was chosen
					setWarrior(Utils.readFileToCreateWarrior(file));

				} else {
					//Identify if issues are occurring
					if(Consts.LOGGING){
						System.out.println("Open command cancelled by user./n");
					}
				}
			}
			//The oppononet button was selected so pick who to battle
			else if (e.getSource() == opponentButton) {
				//Get all the possible opponents
				ArrayList<Warrior> opponentsList = new ArrayList<Warrior>();
				//add them to the list of opponents
				for (Warrior w : opponents) {
					if (!w.getWarriorId().equals(mWarrior.getWarriorId())) {
						opponentsList.add(w);
					}
				}
				//Display the dialog box that indicates allows the user who to battle
				mOpponent = (Warrior) JOptionPane.showInputDialog(
						ClientCombatantUI.this, "Please select your opponent:",
						"Opponent Selection Dialog",
						JOptionPane.QUESTION_MESSAGE, null,
						opponentsList.toArray(), null);
				if(Consts.LOGGING){
					System.out
							.println("\n" + mWarrior.getName()
									+ " is prepared to engage " + mOpponent
									+ " in battle!");
				}
				// Set availability of other buttons to help manage flow
				ClientCombatantUI.this.actionButton.setEnabled(true);

			} 
			//The action button was selected
			else if (e.getSource() == actionButton) {

				//Get the possible actions to do
				Object[] possibilities = Actions.values();
				
				//Allow the user to pick the action they desire to battle with
				battleAction = (Actions) JOptionPane
						.showInputDialog(ClientCombatantUI.this,
								"Please select your battle action:",
								"Battle Selection Dialog",
								JOptionPane.QUESTION_MESSAGE, null,
								possibilities, null);
				
				if(Consts.LOGGING){
					System.out.println("\n The selected action was: "
							+ battleAction.toString());
				}
				//Create and send a message indicating that battles are being started
				Message message = new Message(Message.MessageCommand.BATTLEWARRIOR,
						mWarrior, battleAction,
						mOpponent);
				client.sendData(message);

				//Setup UI to manage workflow through the system
				selectDataFileButton.setEnabled(true);
			} 
			//The connect button allows the user to identify what there preferred host server is 
			else if (e.getSource() == connectButton) {

				//Initiates a dialog that allows the user to pick their host server
				String s = (String) JOptionPane
						.showInputDialog(
								ClientCombatantUI.this,
								"Please enter the prefered server that you would like to connect to:",
								"Prefered Server Dialog",
								JOptionPane.QUESTION_MESSAGE, null, null,
								"localhost");

				// If a string was returned, say so.
				if ((s != null) && (s.length() > 0) && (s.length() <= 15)) {
					if(Consts.LOGGING){
						System.out
								.println("\n Prefered Server location was chosen - with a host of: "
										+ s);
					}
					//Set the local variable to the entered value
					hostLocation = s;

				} 
				//Indicate that the value was not valid for the host server
				else {
					if(Consts.LOGGING){
						System.out
								.println("\n Prefered server location is not valid with: "
										+ s + " as a value.");
					}
				}

			} 
			
			//Allows the user to be able to create a new warrior via the UI
			else if (e.getSource() == newCombatantButton) {
				//Launch a dialog to gather the information from
				CreateWarrior dialog = new CreateWarrior();
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				
				//Actually turn on the window
				dialog.setVisible(true);

			}
			//Close the client UI has been chosen
			else if (e.getSource() == closeButton) {
				//If warriors are open save them 
				if ((client != null) && (mWarrior != null)) {
					
					Utils.saveWarriorToFile(mWarrior, getWarriorFile());
					client.closeConnection();
				}
				//clean up and exit
				dispose();
				System.exit(0);
			}
		}
	}
	/**
	 * Add Opponents to the list of opponents
	 * @param opponent
	 */
	public void addOpponent(Warrior opponent) {
		opponents.add(opponent);
	}
	/**
	 * Remove an opponent from the list.  Maybe they have closed
	 * @param opponent
	 */
	public void removeOpponent(Warrior opponent) {
		opponents.remove(opponent);
	}

	/**
	 * Set the list of opponents
	 * @param opponentsList
	 */
	public void setOpponents(ArrayList<Warrior> opponentsList) {
		opponents = opponentsList;
	}
	/**
	 * Set the welcome label text
	 * @param s
	 */
	public void setWarriorMessage(String s){
		welcomeLabel.setText(s);
	}
}
