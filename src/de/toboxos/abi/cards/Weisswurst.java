package de.toboxos.abi.cards;

import java.awt.Color;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.Logger;
import de.toboxos.abi.events.StatusChangeEvent;

public class Weisswurst extends EventCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int turns;
	
	public Weisswurst() {
		this.name = "Fühstück";
		this.description = "2 Runden lang laben sich alle daran";
		this.cost = 0;
		
		this.image = ImageLoader.WEISSWURST;
	}
	
	@Override
	public void roundStart() {
		turns++;
		Logger.logMessage("Weisswurst:roundStart()");
		for( int i = 0; i < 10; i++ ) {
			StudentCard c = Controller.instance.getSlot(i);
			if( c == null ) continue;
			
			Abi.manager.changeStatus( new StatusChangeEvent(c, i, c.getPower(null) + 10, StatusChangeEvent.STATUS_POWER, Color.CYAN ) );
			Abi.manager.changeStatus( new StatusChangeEvent(c, i, c.getNormalPower() + 10, StatusChangeEvent.STATUS_NORMAL_POWER, Color.CYAN ) );
			Abi.manager.changeStatus( new StatusChangeEvent(c, i, c.getEnergy() + 10, StatusChangeEvent.STATUS_ENERGY, Color.CYAN ) );
		}
		
		if( turns == 4 ) finished = true;
	}
}
