package com.apps4you.moderator;


import java.util.ArrayList;
import java.util.UUID;
import java.util.Random;

import com.apps4you.shared.Message;
import com.apps4you.shared.MessageFactory;
import com.apps4you.shared.Origins;
import com.apps4you.shared.Warrior;



public class Moderator{

	private ArrayList<ConnectedWarrior> warriorsList;
	
	private static final long serialVersionUID = 1712048162291486001L;

	public Moderator() {
		// TODO Auto-generated constructor stub
		warriorsList = new ArrayList<ConnectedWarrior>();
//		warriorsList.add(new Warrior("Zerg",Origins.JAGLANBETA,"Come to the dark side"));
//		warriorsList.add(new Warrior("Woody",Origins.BREQUINDA,"He's a cowboy"));
//		warriorsList.add(new Warrior("Ham",Origins.VOGSPHERE,"AKA Evil Dr. Porkchop"));
//		warriorsList.add(new Warrior("Buzz",Origins.KAKRAFOON,"To infinity and beyond!"));
	}
	
	private void moderateAttacks(Warrior w1, Warrior w2)
	{
		//determine the changes to the health of the warriors
		int w1PointsToReduce = determineHealthPointsToDeduct();		
	    int w2PointsToReduce = determineHealthPointsToDeduct();
	    w1.setHealth(w1.getHealth()-w1PointsToReduce);
	    w2.setHealth(w2.getHealth()-w2PointsToReduce);	        
	}
	
	private void sendAttackResults()
	{
		//TODO report back to the warriors the result of the attack		
	}
	
	public void addOpponent(ConnectedWarrior newWarrior)
	{
		warriorsList.add(newWarrior);
	}
	public void deleteOpponent(ConnectedWarrior removeWarrior)
	{
		this.warriorsList.remove(removeWarrior);
	}	
	public int getOpponentCount(){
		return warriorsList.size();
	}
	public ArrayList<ConnectedWarrior> getOpponents()
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
		 return rand.nextInt(101);
	}
	
	public Message processNewWarrior(Message message,ConnectedWarrior cw){
		cw.upgradeWarrior(message.getWarrior());
		Message returnMessage = null;
		if(warriorsList.size() == 0){
    		returnMessage = new Message(Message.MessageCommand.NOOPPONENTS);        	
    	} else {
    		returnMessage = new ModeratorMessage(
    					warriorsList,
    					Message.MessageCommand.SENDOPPONENTS);
    	}
		warriorsList.add(cw);
		return returnMessage;
	}
	
	public Message processBattleRequet(Message message){
		//Need to notify the Opponent of a Battle Request and allow them to pick an Action
		if(warriorsList.size() != 0)
		{
			System.out.println("Process Battle - Debugging " + message);
			ConnectedWarrior opponent = findById(message.getOpponent().getWarriorId());
			ConnectedWarrior original = findById(message.getWarrior().getWarriorId());

			return new Message((Warrior)opponent,Message.MessageCommand.SELECTACTION,(Warrior)original);        	
    	} else {
    		return new Message(Message.MessageCommand.NOOPPONENTS);
    	}
	}
	
	public Message processDefenseWasSelected(Message message)
	{
		System.out.println("Process Defense - Debugging " + message);
		ConnectedWarrior opponent = findById(message.getOpponent().getWarriorId());
		ConnectedWarrior originalWarrior = findById(message.getWarrior().getWarriorId());
		//Action defense = message.getAction();
		
		//Need to call moderateAttacks(w1,w2)
		moderateAttacks(originalWarrior,opponent);
		
		//sendupdate to the warriors about their new health
		return new Message((Warrior)originalWarrior,Message.MessageCommand.HEALTHUPDATE,(Warrior)opponent);
    }
	
	public ConnectedWarrior findById(UUID uuid){
		ConnectedWarrior r = null;
		for(ConnectedWarrior w:warriorsList){
			if(uuid.equals(w.getWarriorId())){
				r = w;
			}
		}
		return r;
	}
}
