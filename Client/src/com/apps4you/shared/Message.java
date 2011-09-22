package com.apps4you.shared;

import java.util.ArrayList;

public class Message {
	private Warrior mWarrior = null;
	private ArrayList<Warrior> mOpponents = null;
	private Actions mAction = null;
	private MessageCommand mCommand = null;	
	private Warrior mOpponent = null;
	
	public Message(){};
	
	public Message(Warrior warrior, MessageCommand command){
		mWarrior = warrior;
		mCommand = command;
	}
	public Message(Warrior warrior, MessageCommand command,Actions action){
		mWarrior = warrior;
		mAction = action;
		mCommand = command;
	}

	public Message(Warrior warrior, MessageCommand command,Actions action, Warrior opponent){
		mWarrior = warrior;
		mAction = action;
		mCommand = command;
		mOpponent = opponent;
	}
	
	public Message(Warrior warrior, MessageCommand command,Warrior opponent){
		mWarrior = warrior;
		mCommand = command;
		mOpponent = opponent;
	}
	
	public Message(Warrior warrior, ArrayList<Warrior> opponents, MessageCommand command,Actions action){
		mWarrior = warrior;
		mAction = action;
		mCommand = command;
		mOpponents = opponents;
	}
	public Message(ArrayList<Warrior> opponents, MessageCommand command){
		mCommand = command;
		mOpponents = opponents;
	}
	public Message(MessageCommand command){
		mCommand = command;
	}
	
	public Warrior getWarrior() {
		return mWarrior;
	}
	public void setWarrior(Warrior mWarrior) {
		this.mWarrior = mWarrior;
	}
	public Actions getAction() {
		return mAction;
	}
	public void setAction(Actions mAction) {
		this.mAction = mAction;
	}
	public MessageCommand getCommand() {
		return mCommand;
	}
	public void setCommand(MessageCommand mCommand) {
		this.mCommand = mCommand;
	}
	public void setOpponent(Warrior opponent){
		mOpponent = opponent;
	}
	public Warrior getOpponent() {
		return mOpponent;
	}
	public ArrayList<Warrior> getOpponents(){
		return mOpponents;
	}
	public void setOpponents(ArrayList<Warrior> opponents){
		this.mOpponents = opponents;
	}
	public void addOpponent(Warrior w){
		mOpponents.add(w);
	}
	
	
	public static enum MessageCommand{
		NEWWARRIOR,
		BATTLEWARRIOR,
		GREETWARRIOR,
		NOOPPONENTS,
		SENDOPPONENTS,
		SELECTACTION,
		DEFENSESELECTED,
		HEALTHUPDATE
	}
	
	
}
