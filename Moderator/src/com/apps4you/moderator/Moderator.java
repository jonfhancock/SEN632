package com.apps4you.moderator;


import java.util.ArrayList;
import java.util.UUID;

import com.apps4you.shared.Warrior;



public class Moderator{

private ArrayList<Warrior> warriorsList;

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
	public ArrayList<Warrior> sendWarriorList()
	{
		return this.warriorsList;		
	}
	
	//Is the Warrior Id the ID of the attacked or the attackers
	public void receiveAttackRequest(UUID warriorId)
	{
		//Use the warriorId to look up the
	}
}