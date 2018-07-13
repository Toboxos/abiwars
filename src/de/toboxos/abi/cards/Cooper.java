
package de.toboxos.abi.cards;

import java.awt.Color;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.Logger;
import de.toboxos.abi.events.StatusChangeEvent;

public class Cooper extends EventCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int turns;
	private boolean change[] = new boolean[10];
	
	public Cooper() {
		this.name = "Cooper-Test";
		this.description = "3 Runden Cooper. Stärkt Läufer, schwächt Nicht-Läufer";
		this.cost = 6;
		
		this.image = ImageLoader.COOPER;
	}
	
	@Override
	public void played() {
		for( int i = 0; i < 10; i++ ) {
			change[i] = false;
			
			StudentCard c = Controller.instance.getSlot(i);
			if( c == null ) continue;
			change[i] = true;
			
			if( 
					c instanceof Georg
				||	c instanceof Arwen
				|| 	c instanceof Emilia
				||	c instanceof Manu
				|| 	c instanceof Felix
				||	c instanceof FelixG
				|| 	c instanceof Franzi
				||	c instanceof Nils
				||	c instanceof Steffen
				|| 	c instanceof Marike
			) {
				Abi.manager.changeStatus( new StatusChangeEvent(c, i, (int) (c.getNormalPower() * 0.7), StatusChangeEvent.STATUS_POWER, Color.CYAN ) );
			}
				
			if( 
					c instanceof General
				|| 	c instanceof Axel
				||	c instanceof Daniel
				||	c instanceof Sandro
			) {
				Abi.manager.changeStatus( new StatusChangeEvent(c, i, (int) (c.getNormalPower() * 1.3), StatusChangeEvent.STATUS_POWER, Color.CYAN ) );
			}
		}
	}
	
	@Override
	public void studentPlayed(StudentCard c, int slot) {
		Logger.logMessage("Cooper:studentPlayed(" + c.getName() + ", " + slot + ")");
		change[slot] = true;
		
		if( 
				c instanceof Georg
			||	c instanceof Arwen
			|| 	c instanceof Emilia
			||	c instanceof Manu
		) {
			Abi.manager.changeStatus( new StatusChangeEvent(c, slot, (int) (c.getNormalPower() * 0.7), StatusChangeEvent.STATUS_POWER, Color.CYAN ) );
		}
			
		if( 
				c instanceof General
			|| 	c instanceof Axel
			||	c instanceof Daniel
		) {
			Abi.manager.changeStatus( new StatusChangeEvent(c, slot, (int) (c.getNormalPower() * 1.3), StatusChangeEvent.STATUS_POWER, Color.CYAN ) );
		}
	}
	
	@Override
	public void studentDead(StudentCard c, int slot) {
		change[slot] = false;
	}
	
	@Override
	public void nextTurn() {
		turns++;
		if( turns == 6 ) {
			for( int i = 0; i < 10; i++ ) {
				StudentCard c = Controller.instance.getSlot(i);
				if( change[i] == false || c == null ) continue;
				
				Abi.manager.changeStatus( new StatusChangeEvent(c, i, c.getNormalPower(), StatusChangeEvent.STATUS_POWER, Color.CYAN ) );
			}
			
			finished = true;
		}
	}
	
	/* @Override
	public void roundStart() {
		rounds++;
	}
	
	@Override
	public void roundEnd() {
		if( rounds == 3 ) {
			
			
		}
	} */
}