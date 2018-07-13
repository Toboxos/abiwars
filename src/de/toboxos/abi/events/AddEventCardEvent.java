package de.toboxos.abi.events;

import de.toboxos.abi.cards.EventCard;

public class AddEventCardEvent extends Event {

	EventCard card;
	
	public AddEventCardEvent(EventCard c) {
		this.card = c;
	}
	
	public EventCard getCard() {
		return this.card;
	}
}
