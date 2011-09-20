package com.apps4you.shared;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.apps4you.moderator.ConnectedWarrior;

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
	
	public Message(ArrayList<ConnectedWarrior> opponents, MessageCommand command, int flag){
		mCommand = command;
		mOpponents = new ArrayList<Warrior>();
		for(ConnectedWarrior cw:opponents){
			mOpponents.add(cw);
		}
//		mOpponents = opponents;
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
	public Warrior getOpponent() {
		return mOpponent;
	}
	public ArrayList<Warrior> getOpponents(){
		return mOpponents;
	}
	public void setOpponents(ArrayList<Warrior> opponents){
		this.mOpponents = opponents;
	}
	
	 
	
	
	public static enum MessageCommand{
		NEWWARRIOR,
		BATTLEWARRIOR,
		GREETWARRIOR,
		NOOPPONENTS,
		SENDOPPONENTS,
		SELECTACTION
	}
	
	
}
