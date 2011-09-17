package com.apps4you.shared;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Message {
	private Warrior mWarrior = null;
	private Actions mAction = null;
	private MessageCommand mCommand = null;
	
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
	
	
	
	public static enum MessageCommand{
		NEWWARRIOR,
		BATTLEWARRIOR
	}
	
	
}
