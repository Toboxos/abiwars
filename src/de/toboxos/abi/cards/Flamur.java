package de.toboxos.abi.cards;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;

public class Flamur extends EventCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Flamur() {
		this.name = "Flamurisieren";
		this.description = "Alle auf dem Feld liegenden Karten geben auf";
		this.cost = 7;
		this.finished = true;
		
		this.image = ImageLoader.FLAMUR;
	}
	
	@Override
	public void played() {
		for( int i = 0; i < 10; i++ ) {
			if( Controller.instance.getSlot(i) == null ) continue;
			
			Abi.manager.cardDead(i);
		}
	}
}
