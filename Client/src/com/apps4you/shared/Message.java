package com.apps4you.shared;

import java.util.ArrayList;

/**
 * Used to store all the information that needs to be passed between the client
 * and server. Since there are several kinds of messages, and several fields
 * that can be added to a message, there are a lot of constructors. Use
 * whichever provides you with the features you need at the time. There is no
 * special constructor for any given message type. Each message must have at
 * least a MessageCommand.
 * 
 * @author Jon Hancock
 * 
 */
public class Message {
    /**
     * An enumeration that holds the different message types that all messages 
     * will be tagged with in order to know how to direct the message
     */
	public static enum MessageCommand {
		BATTLEWARRIOR, 
		DEFENSESELECTED, 
		GREETWARRIOR, 
		HEALTHUPDATE, 
		NEWWARRIOR, 
		NOOP, 
		NOOPPONENTS, 
		SELECTACTION, 
		SENDOPPONENTS,
		IAMDEAD
	}

	// This is the action that the warrior wishes to perform during an attack
	private Actions mAction = null;
	// This can be used to send multiple actions, and is most useful for sending
	// back to the client the results of a battle
	private Actions[] mActions = null;
	// This is the only truly required field. A message must have a command to
	// be valid, and a message that contains only a command is valid.
	private MessageCommand mCommand = null;
	// This can be used to store another single warrior. Most likely an opponent
	// in the current battle.
	private Warrior mOpponent = null;
	// This is a list of warriors, and is usually used to pass all the warriors
	// on the battlefield to the client so the player knows who to attack
	private ArrayList<Warrior> mOpponents = null;
	// This is usually the warrior that is performing the action
	private Warrior mWarrior = null;

	/**
	 * The message constructors
	 */
	public Message() {
		mCommand = MessageCommand.NOOP;
	}
	/**
	 * The message constructor
	 * @param command
	 */
	public Message(MessageCommand command) {
		mCommand = command;
	}
	/**
	 * The message constructor
	 * @param command
	 * @param warrior
	 */
	public Message(MessageCommand command, Warrior warrior) {
		mWarrior = warrior;
		mCommand = command;
	}
	/**
	 * The message constructor
	 * @param command
	 * @param opponents
	 */
	public Message(MessageCommand command, ArrayList<Warrior> opponents) {
		mCommand = command;
		mOpponents = opponents;
	}
    /**
     * The message constructor
     * @param command
     * @param warrior
     * @param action
     */
	public Message(MessageCommand command, Warrior warrior, Actions action) {
		mWarrior = warrior;
		mAction = action;
		mCommand = command;
	}
    /**
     * The message constructor
     * @param command
     * @param warrior
     * @param action
     * @param opponent
     */
	public Message(MessageCommand command, Warrior warrior, Actions action,
			Warrior opponent) {
		mWarrior = warrior;
		mAction = action;
		mCommand = command;
		mOpponent = opponent;
	}
    /**
     * The message constructor
     * @param command
     * @param warrior
     * @param actions
     * @param opponent
     */
	public Message(MessageCommand command, Warrior warrior,
			Actions[] actions, Warrior opponent) {
		mWarrior = warrior;
		mActions = actions;
		mCommand = command;
		mOpponent = opponent;
	}
    /**
     * The message constructor
     * @param command
     * @param warrior
     * @param opponent
     */
	public Message(MessageCommand command, Warrior warrior, Warrior opponent) {
		mWarrior = warrior;
		mCommand = command;
		mOpponent = opponent;
	}
    /**
     * Adds an opponent to the message list of opponents.
     * Keeps track of all warriors that need to be notified of messages
     * @param w
     */
	public void addOpponent(Warrior w) {
		mOpponents.add(w);
	}
	/**
	 * Can return the action (Attack or defense) that was in a message
	 * @return
	 */
	public Actions getAction() {
		return mAction;
	}
    /**
     * Gets all actions that are available to be chosen
     * @return
     */
	public Actions[] getActions(){
		return mActions;
	}
	/**
	 * Returns what the command in a message was
	 * @return
	 */
	public MessageCommand getCommand() {
		return mCommand;
	}

	/**
	 * returns the specific opponent that was called out in a message
	 * @return
	 */
	public Warrior getOpponent() {
		return mOpponent;
	}
    /**
     * Returns the list of possible warriors to battle
     * @return
     */
	public ArrayList<Warrior> getOpponents() {
		return mOpponents;
	}
    /**
     * Returns the original warrior in a message not the attackie
     * @return
     */
	public Warrior getWarrior() {
		return mWarrior;
	}
    /**
     * Ability to set what the action was intended to be in a message
     * @param action
     */
	public void setAction(Actions action) {
		this.mAction = action;
	}
    /**
     * Ability to set a list of all the possible actions into a message
     * @param actions
     */
	public void setActions(Actions[] actions){
		mActions = actions;
	}
	/**
	 * Ability to set the command that will be included in a message
	 * @param command
	 */
	public void setCommand(MessageCommand command) {
		this.mCommand = command;
	}
    /**
     * Ability to set the specified opponent in a battle is for a message 
     * @param opponent
     */
	public void setOpponent(Warrior opponent) {
		mOpponent = opponent;
	}
    /**
     * Ability to set the list of opponents eligible to do battle in a message
     * @param opponents
     */
	public void setOpponents(ArrayList<Warrior> opponents) {
		this.mOpponents = opponents;
	}
    /**
     * Ability to set the original warrior in a message
     * @param warrior
     */
	public void setWarrior(Warrior warrior) {
		this.mWarrior = warrior;
	}
}
