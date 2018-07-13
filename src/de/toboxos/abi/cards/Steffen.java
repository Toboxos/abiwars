package de.toboxos.abi.cards;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;

public class Steffen extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Steffen() {
		this.name = "Steffen";
		this.description = "Er hat Rechtsschutz";
		this.cost = 11;
		this.power = 50;
		this.normal_power = 50;
		this.energy = 60;
		
		this.image = ImageLoader.STEFFEN;
	}
	
	@Override
	public void played() {
		
		// Server only
		if( !Controller.instance.isMaster() ) return;
		
		switch( Controller.instance.getTurn() ) {
			case 1:
				for( int i = 5; i < 10; i++ ) {
					if( Controller.instance.getSlot(i) == null ) {
						Abi.manager.setSlot(i, "Anwalt");
						break;
					}
				}
				break;
			case 2:
				for( int i = 0; i < 5; i++ ) {
					if( Controller.instance.getSlot(i) == null ) {
						Abi.manager.setSlot(i, "Anwalt");
						break;
					}
				}
				break;
		}
	}
}
