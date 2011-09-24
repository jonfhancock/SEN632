package com.apps4you.moderator;


import java.util.ArrayList;
import java.util.UUID;
import java.util.Random;

import com.apps4you.client.Consts;
import com.apps4you.shared.Actions;
import com.apps4you.shared.Message;
import com.apps4you.shared.Warrior;



public class Moderator{

	private ArrayList<WarriorConnection> warriorsList;
	
	private static final long serialVersionUID = 1712048162291486001L;
	private static Moderator instance;
	public  static Moderator getInstance(){
		if(instance == null){
			instance = new Moderator();
		}
		return instance;
	}
	
	private Moderator() {
		warriorsList = new ArrayList<WarriorConnection>();
	}
	
	private void moderateAttacks(Warrior w1, Warrior w2)
	{
		//determine the changes to the health of the warriors
		int w1PointsToReduce = determineHealthPointsToDeduct();		
	    int w2PointsToReduce = determineHealthPointsToDeduct();
	    w1.setHealth(w1.getHealth()-w1PointsToReduce);
	    w2.setHealth(w2.getHealth()-w2PointsToReduce);    
	    
	}

	public void addOpponent(WarriorConnection newWarrior)
	{
		warriorsList.add(newWarrior);
	}
	public void deleteOpponent(WarriorConnection removeWarrior)
	{
		this.warriorsList.remove(removeWarrior);
	}	
	public int getOpponentCount(){
		return warriorsList.size();
	}
	public ArrayList<WarriorConnection> getOpponents()
	{
		return this.warriorsList;		
	}
	
	//Is the Warrior Id the ID of the attacked or the attackers
	public void receiveAttackRequest(UUID warriorId)
	{
		//Use the warriorId to look up the
	}
	
	
	private int determineHealthPointsToDeduct()
	{
		 Random rand = new Random(); 
		 return rand.nextInt(20);
	}
	
	public void processNewWarrior(Message message,Warrior cw){
	    		broadCastWarriorList();
	}
	
	public Message processBattleRequet(Message message){
		//Need to notify the Opponent of a Battle Request and allow them to pick an Action
		if(warriorsList.size() != 0)
		{
			if(Consts.LOGGING){
				System.out.println("Process Battle - Debugging " + message);
			}
			Warrior opponent = findById(message.getOpponent().getWarriorId()).getWarrior();
			Warrior original = findById(message.getWarrior().getWarriorId()).getWarrior();

			return new Message(Message.MessageCommand.SELECTACTION,opponent,original);        	
    	} else {
    		return new Message(Message.MessageCommand.NOOPPONENTS);
    	}
	}
	
	public void processDefenseWasSelected(Message message)
	{
		if(Consts.LOGGING){
			System.out.println("Process Defense - Debugging " + message);
		}
		Warrior opponent = findById(message.getOpponent().getWarriorId()).getWarrior();
		Warrior originalWarrior = findById(message.getWarrior().getWarriorId()).getWarrior();

		//Need to call moderateAttacks(w1,w2)
		moderateAttacks(originalWarrior,opponent);
		
		//update the health of the original Warrior
		broadCastWarriorHealth(originalWarrior,opponent,message.getActions());
		//Update the health of the opponent
//		broadCastWarriorHealth(opponent,originalWarrior,message.getActions());

    }
	
	public WarriorConnection findById(UUID uuid){
		WarriorConnection r = null;
		for(WarriorConnection w:warriorsList){
			if(uuid.equals(w.getWarrior().getWarriorId())){
				r = w;
			}
		}
		return r;
	}
	public ArrayList<Warrior> getWarriorList(){
		ArrayList<Warrior> warriors = new ArrayList<Warrior>();
		for(WarriorConnection w:warriorsList){
			warriors.add(w.getWarrior());
		}
		return warriors;
	}
	
	public void broadCastWarriorList(){
		for(WarriorConnection w:warriorsList){
			Message message =  null;
			if(warriorsList.size() == 1){
				message = new Message(Message.MessageCommand.NOOPPONENTS);  
			} else {
				message = new Message(
						Message.MessageCommand.SENDOPPONENTS,
	    					getWarriorList());
			}
			w.sendData(message);
		}
	}
	
	private void broadCastWarriorHealth(Warrior warrior, Warrior opponent,Actions[] actions){
		
		WarriorConnection w = findById(warrior.getWarriorId());
		w.sendData(new Message(Message.MessageCommand.HEALTHUPDATE,
		warrior,actions,opponent));
		
		w = findById(opponent.getWarriorId());
		w.sendData(new Message(Message.MessageCommand.HEALTHUPDATE,
				warrior,actions,opponent));
	}		
}
