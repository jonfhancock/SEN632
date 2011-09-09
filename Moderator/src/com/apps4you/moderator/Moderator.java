package com.apps4you.moderator;

import com.apps4you.client.Warrior;

import java.util.ArrayList;
import java.util.UUID;



public class Moderator extends Server {

private ArrayList warriorsList;

private static final long serialVersionUID = 1712048162291486001L;

	public Moderator() {
		// TODO Auto-generated constructor stub
	}
	
	private void moderateAttacks()
	{
		//determine the changes to the health of the warriors
				
	}
	
	private void sendAttackResults()
	{
		//TODO report back to the warriors the result of the attack		
	}
	
	public void addOpponent(Warrior newWarrior)
	{
		this.warriorsList.add(newWarrior);
	}
	public void deleteOpponent(Warrior removeWarrior)
	{
		this.warriorsList.remove(removeWarrior);
	}	
	public ArrayList sendWarriorList()
	{
		return this.warriorsList;		
	}
	
	//Is the Warrior Id the ID of the attacked or the attackers
	public void receiveAttackRequest(UUID warriorId)
	{
		//Use the warriorId to look up the
	}
}
