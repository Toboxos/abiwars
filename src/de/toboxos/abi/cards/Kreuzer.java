package de.toboxos.abi.cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.events.StatusChangeEvent;

public class Kreuzer extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Kreuzer()  {
		this.name = "Kreuzer";
		this.description = "Macht einem Schüler Schaden, weil er das W-Buch vergesse hat";
		this.cost = 9;
		this.power = 30;
		this.normal_power = 30;
		this.energy = 60;
		
		this.image = ImageLoader.KREUZER;
	}
	
	@Override
	public void played() {
		
		// Server only
		if( !Controller.instance.isMaster() ) return;
				
		List<Integer> l = new ArrayList<>();
				
		switch( Controller.instance.getTurn() ) {
			case 1:
				for( int i = 0; i < 4; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
						
					if( 
							c instanceof Arwen
						||	c instanceof Axel
						||	c instanceof Daniel
						||	c instanceof Emilia
						||	c instanceof FelixG
						||	c instanceof Felix
						||	c instanceof Franzi
						||	c instanceof General
						||	c instanceof Georg
						||	c instanceof Manu
						||	c instanceof Marike
						||	c instanceof Nils
						||	c instanceof Sandro
						||	c instanceof Steffen
					) l.add(i);	
				}
				break;
			case 2:
				for( int i = 5; i < 10; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					if( 
							c instanceof Arwen
						||	c instanceof Axel
						||	c instanceof Daniel
						||	c instanceof Emilia
						||	c instanceof FelixG
						||	c instanceof Felix
						||	c instanceof Franzi
						||	c instanceof General
						||	c instanceof Georg
						||	c instanceof Manu
						||	c instanceof Marike
						||	c instanceof Nils
						||	c instanceof Sandro
						||	c instanceof Steffen
					) l.add(i);	
				}
				break;
		}
	
		if( l.size() > 0 ) {
			int i = l.get(Abi.random.nextInt(l.size()));
			StudentCard c = Controller.instance.getSlot(i);
			Abi.manager.changeStatus(new StatusChangeEvent(c, i, c.getEnergy() - 30, StatusChangeEvent.STATUS_ENERGY, Color.CYAN));
		}
	}
}
