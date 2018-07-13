package de.toboxos.abi.cards;

import java.util.ArrayList;
import java.util.List;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;

public class Taraschewski extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Taraschewski() {
		this.name = "Taraschweski";
		this.description = "Schickt einen Schüler vor die Tür";
		this.cost = 5;
		this.power = 30;
		this.normal_power = 30;
		this.energy = 30;
		
		this.image = ImageLoader.TARASCHEWSKI;
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
		
		if( l.size() > 0 ) Abi.manager.cardDead( l.get(Abi.random.nextInt(l.size())) );
	}

}
