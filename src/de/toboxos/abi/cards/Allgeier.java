package de.toboxos.abi.cards;

import java.awt.Color;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.events.StatusChangeEvent;

public class Allgeier extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Allgeier() {
		this.name = "Allgeier";
		this.description="\"Ich kann jetzt nicht zur Uni, mein Clan braucht mich\"";
		this.cost = 3;
		this.power = 20;
		this.normal_power = 20;
		this.power = 20;
		this.energy = 20;
		
		this.image = ImageLoader.ALLGEIER;
	}
	
	@Override
	public void played() {
		
		// Master only
		if( !Controller.instance.isMaster() ) return;
		
		// Client: 0-4
		// Server: 5-9
		switch( Controller.instance.getTurn() ) {
			case 1:
				for( int i = 5; i < 10; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					if( c instanceof Allgeier ) continue;
					
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getEnergy() * 1.2), StatusChangeEvent.STATUS_ENERGY, Color.CYAN) );
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getPower(null) * 1.2), StatusChangeEvent.STATUS_POWER, Color.CYAN) );
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getNormalPower() * 1.2), StatusChangeEvent.STATUS_NORMAL_POWER, Color.CYAN) );
				}
				break;
			case 2:
				for( int i = 0; i < 5; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					if( c instanceof Allgeier ) continue;
					
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getEnergy() * 1.2), StatusChangeEvent.STATUS_ENERGY, Color.CYAN) );
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getPower(null) * 1.2), StatusChangeEvent.STATUS_POWER, Color.CYAN) );
					Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getNormalPower() * 1.2), StatusChangeEvent.STATUS_NORMAL_POWER, Color.CYAN) );
				}
				break;
		}
	}
	
	@Override
	public void dead() {
		// Master only
		if( !Controller.instance.isMaster() ) return;
				
		if( slot > 4 ) {
			for( int i = 5; i < 10; i++ ) {
				StudentCard c = Controller.instance.getSlot(i);
				if( c == null ) continue;
							
				Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getEnergy() * 0.83), StatusChangeEvent.STATUS_ENERGY, Color.CYAN) );
				Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getPower(null) * 0.83), StatusChangeEvent.STATUS_POWER, Color.CYAN) );
				Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getNormalPower() * 0.83), StatusChangeEvent.STATUS_NORMAL_POWER, Color.CYAN) );
			}
		} else {
			for( int i = 0; i < 5; i++ ) {
				StudentCard c = Controller.instance.getSlot(i);
				if( c == null ) continue;
						
				Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getEnergy() * 0.83), StatusChangeEvent.STATUS_ENERGY, Color.CYAN) );
				Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getPower(null) * 0.83), StatusChangeEvent.STATUS_POWER, Color.CYAN) );
				Abi.manager.changeStatus(new StatusChangeEvent(c, i, (int) (c.getNormalPower() * 0.83), StatusChangeEvent.STATUS_NORMAL_POWER, Color.CYAN) );
			}
		}
	}

}
