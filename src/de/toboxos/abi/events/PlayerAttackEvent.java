package de.toboxos.abi.events;

import de.toboxos.abi.Player;
import de.toboxos.abi.cards.StudentCard;

public class PlayerAttackEvent extends Event {
	
	private Player player;
	private StudentCard card;
	private int slot;
	
	public PlayerAttackEvent(Player p, StudentCard c, int s) {
		this.player = p;
		this.card = c;
		this.slot = s;
	}

	public Player getPlayer() {
		return player;
	}
	
	public StudentCard getCard() {
		return card;
	}

	public int getSlot() {
		return slot;
	}
}
