package de.toboxos.abi.events;

import de.toboxos.abi.Player;
import de.toboxos.abi.cards.StudentCard;

public class CardBattleEvent extends Event {
	
	private Player player;
	
	private StudentCard initiator;
	private StudentCard target;
	
	private int slot1;
	private int slot2;
	
	public CardBattleEvent(Player p, StudentCard c1, StudentCard c2, int s1, int s2) {
		player = p;
		initiator = c1;
		target = c2;
		slot1 = s1;
		slot2 = s2;
	}
	
	public Player getPlayer() {
		return player;
	}

	public int getSlot1() {
		return slot1;
	}

	public int getSlot2() {
		return slot2;
	}

	public StudentCard getInitiator() {
		return initiator;
	}

	public StudentCard getTarget() {
		return target;
	}
	
}
