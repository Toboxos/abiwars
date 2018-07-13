package de.toboxos.abi.cards;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.Logger;

public class Franzi extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Franzi() {
		this.name = "Franzi";
		this.description = "Organisiert alles und hat den Überblick";
		this.cost = 7;
		this.power = 30;
		this.normal_power = 30;
		this.energy = 40;
		
		this.image = ImageLoader.FRANZI;
	}
	
	@Override
	public void played() {
		Logger.logMessage("Frazi:played() Turn: " + Controller.instance.getTurn());
		
		// Only Server
		if( !Controller.instance.isMaster() ) return;
		
		switch( Controller.instance.getTurn() ) {
			case 1:
				Controller.instance.getPlayer(1).setView(true);
				Abi.manager.updatePlayer(Controller.instance.getPlayer(1));
				break;
			case 2:
				Controller.instance.getPlayer(0).setView(true);
				Abi.manager.updatePlayer(Controller.instance.getPlayer(0));
				break;
		}
	}
	
	@Override
	public void dead() {
		Logger.logMessage("Franzi:dead() Turn: " + Controller.instance.getTurn() + " Slot: " + this.slot);
		
		// Only Server
		if( !Controller.instance.isMaster() ) return;
		
		for( int i = 0; i < 10; i++ ) {
			Logger.logMessage("" + i + ": " + Controller.instance.getSlot(i));
		}
		
		if( this.slot < 5 ) {
			for( int i = 0; i < 5; i++ ) {
				StudentCard c = Controller.instance.getSlot(i);
				if( c == null ) continue;
				if( c instanceof Franzi ) {
					Logger.logMessage("One more Franzi on Field");
					return;
				}
			}
			Controller.instance.getPlayer(0).setView(false);
			Abi.manager.updatePlayer(Controller.instance.getPlayer(0));
		}
		
		if( this.slot > 4 ) {
			for( int i = 5; i < 10; i++ ) {
				StudentCard c = Controller.instance.getSlot(i);
				if( c == null ) continue;
				if( c instanceof Franzi ) {
					Logger.logMessage("One more Franzi on Field");
					return;
				}
			}
			Controller.instance.getPlayer(1).setView(false);
			Abi.manager.updatePlayer(Controller.instance.getPlayer(1));
		}
	}
}
