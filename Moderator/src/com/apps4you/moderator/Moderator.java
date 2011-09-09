package com.apps4you.moderator;

import com.apps4you.client.Warrior;

import java.util.ArrayList;



public class Moderator extends Server {

private ArrayList warriorsList;

private static final long serialVersionUID = 1712048162291486001L;

	public Moderator() {
		// TODO Auto-generated constructor stub
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
}
