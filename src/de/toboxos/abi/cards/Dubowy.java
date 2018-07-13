package de.toboxos.abi.cards;

import de.toboxos.abi.Controller;
import de.toboxos.abi.Logger;

public class Dubowy extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Dubowy() {
		this.name = "Dubowy";
		this.description = "Erschreckt alle gegnerischen Spieler beim Ausspielen";
		this.cost = 6;
		this.power = 20;
		this.normal_power = 20;
		this.energy = 35;
		
		this.image = ImageLoader.DUBOWY;
	}
	
	@Override
	public void played() {
		Logger.logMessage("Dubowy:Played()");
		switch( Controller.instance.getTurn() ) {
			case 1:
				for( int i = 0; i < 5; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					c.canAttack = false;
					Logger.logMessage("(" + i + ") " + c.getName() + ": " + c.canAttack);
				}
				break;
			case 2:
				for( int i = 5; i < 10; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					c.canAttack = false;
					Logger.logMessage("(" + i + ") " + c.getName() + ": " + c.canAttack);
				}
				break;
		}
	}

}
