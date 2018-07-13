package de.toboxos.abi.events;

import de.toboxos.abi.Player;
import de.toboxos.abi.cards.Card;

public class CardPlayedEvent extends Event {
	
	private int slot;
	private int pos;
	private Card card;
	private Player player;
	
	public CardPlayedEvent(Card c, Player p, int slot, int pos) {
		this.slot = slot;
		this.card = c;
		this.player = p;
		this.pos = pos;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public int getPos() {
		return pos;
	}

	public Card getCard() {
		return card;
	}

	public Player getPlayer() {
		return player;
	}
}	