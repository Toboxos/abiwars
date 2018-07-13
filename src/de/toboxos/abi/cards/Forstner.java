package de.toboxos.abi.cards;

import java.awt.Color;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.events.StatusChangeEvent;

public class Forstner extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Forstner() {
		this.name = "Forstner";
		this.description = "Chemische Giftgas Attacke\nAlle Gegner 15 Schaden";
		this.cost = 5;
		this.power = 20;
		this.normal_power = 20;
		this.energy = 40;
		
		this.image = ImageLoader.FORSTNER;
	}
	
	@Override
	public void played() {
		
		// Server only
		if( !Controller.instance.isMaster() ) return;
		
		switch( Controller.instance.getTurn() ) {
			case 1:
				for( int i = 0; i < 5; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, c.getEnergy() - 15, StatusChangeEvent.STATUS_ENERGY, Color.CYAN));
				}
				break;
			case 2:
				for( int i = 5; i < 10; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, c.getEnergy() - 15, StatusChangeEvent.STATUS_ENERGY, Color.CYAN));
				}
				break;
		}
	}

}
