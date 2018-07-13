package de.toboxos.abi.cards;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.events.AddEventCardEvent;

public class Wiech extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Wiech() {
		this.name = "Wiech";
		this.description = "Ein gratis Weißwurst Frühstück";
		this.cost = 4;
		this.power = 20;
		this.normal_power = 20;
		this.energy = 20;
		
		this.image = ImageLoader.WIECH;
	}
	
	@Override
	public void played() {
		
		// Server only
		if( !Controller.instance.isMaster() ) return;
		
		Abi.manager.addEventCard(new AddEventCardEvent(new Weisswurst()));
	}

}
