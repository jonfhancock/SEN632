package com.apps4you.moderator;

import java.util.ArrayList;

import com.apps4you.shared.Message;
import com.apps4you.shared.Warrior;
import com.apps4you.shared.Message.MessageCommand;

public class ModeratorMessage extends Message {
	public  ModeratorMessage(ArrayList<ConnectedWarrior> opponents, MessageCommand command){
		super(command);
		setOpponents(new ArrayList<Warrior>());
		for(ConnectedWarrior cw:opponents){
			addOpponent(cw);
		}
//		mOpponents = opponents;
	}
}
