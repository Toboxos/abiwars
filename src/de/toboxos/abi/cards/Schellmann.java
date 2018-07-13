package de.toboxos.abi.cards;

import java.awt.Color;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.events.StatusChangeEvent;

public class Schellmann extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Schellmann() {
		this.name = "Schellmann";
		this.description = "Er macht sich immer über alle lustig";
		this.cost = 3;
		this.power = 20;
		this.normal_power = 20;
		this.energy = 20;
		
		this.image = ImageLoader.SCHELLMANN;
	}
	
	@Override
	public void played() {
		
		// Master only
		if( !Controller.instance.isMaster() ) return;
		
		// Client: 0-4
		// Server: 5-9
		switch( Controller.instance.getTurn() ) {
			case 1:
				for( int i = 0; i < 5; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getPower(null) * 0.8), StatusChangeEvent.STATUS_POWER, Color.CYAN) );
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getNormalPower() * 0.8), StatusChangeEvent.STATUS_NORMAL_POWER, Color.CYAN) );
				}
				break;
			case 2:
				for( int i = 5; i < 10; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getPower(null) * 0.8), StatusChangeEvent.STATUS_POWER, Color.CYAN) );
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getNormalPower() * 0.8), StatusChangeEvent.STATUS_NORMAL_POWER, Color.CYAN) );
				}
				break;
		}
	}

}
