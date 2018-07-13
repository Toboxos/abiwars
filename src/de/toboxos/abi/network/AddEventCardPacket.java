package de.toboxos.abi.network;

import de.toboxos.abi.cards.EventCard;

public class AddEventCardPacket {

	private EventCard card;
	
	public AddEventCardPacket(EventCard c) {
		this.card = c;
	}
	
	public EventCard getCard() {
		return card;
	}
}
