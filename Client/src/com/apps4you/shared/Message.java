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
 * @author jonfhancock
 * 
 */
public class Message {

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
	// back to the client the resutls of a battle
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

	public Message() {
		mCommand = MessageCommand.NOOP;
	}

	public Message(MessageCommand command) {
		mCommand = command;
	}

	public Message(MessageCommand command, Warrior warrior) {
		mWarrior = warrior;
		mCommand = command;
	}

	public Message(MessageCommand command, ArrayList<Warrior> opponents) {
		mCommand = command;
		mOpponents = opponents;
	}

	public Message(MessageCommand command, Warrior warrior, Actions action) {
		mWarrior = warrior;
		mAction = action;
		mCommand = command;
	}

	public Message(MessageCommand command, Warrior warrior, Actions action,
			Warrior opponent) {
		mWarrior = warrior;
		mAction = action;
		mCommand = command;
		mOpponent = opponent;
	}

	public Message(MessageCommand command, Warrior warrior,
			Actions[] actions, Warrior opponent) {
		mWarrior = warrior;
		mActions = actions;
		mCommand = command;
		mOpponent = opponent;
	}

	public Message(MessageCommand command, Warrior warrior, Warrior opponent) {
		mWarrior = warrior;
		mCommand = command;
		mOpponent = opponent;
	}

	public void addOpponent(Warrior w) {
		mOpponents.add(w);
	}

	public Actions getAction() {
		return mAction;
	}

	public Actions[] getActions(){
		return mActions;
	}
	public MessageCommand getCommand() {
		return mCommand;
	}

	public Warrior getOpponent() {
		return mOpponent;
	}

	public ArrayList<Warrior> getOpponents() {
		return mOpponents;
	}

	public Warrior getWarrior() {
		return mWarrior;
	}

	public void setAction(Actions action) {
		this.mAction = action;
	}

	public void setActions(Actions[] actions){
		mActions = actions;
	}
	public void setCommand(MessageCommand command) {
		this.mCommand = command;
	}

	public void setOpponent(Warrior opponent) {
		mOpponent = opponent;
	}

	public void setOpponents(ArrayList<Warrior> opponents) {
		this.mOpponents = opponents;
	}

	public void setWarrior(Warrior warrior) {
		this.mWarrior = warrior;
	}

}
