package de.toboxos.abi.cards;

import java.awt.Color;

import de.toboxos.abi.Abi;
import de.toboxos.abi.events.StatusChangeEvent;

public class Zelenka extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int turns;
	
	public Zelenka() {
		this.name = "Zelenka";
		this.description = "Gebrochenes Bein. Wird stark nach\nder Genesung";
		this.cost = 13;
		this.power = 25;
		this.normal_power = 25;
		this.energy = 40;
		
		this.image = ImageLoader.ZELENKA;
	}
	
	@Override
	public void nextTurn() {
		turns++;
		if( turns == 3 ) {
			Abi.manager.changeStatus(new StatusChangeEvent(this, slot, 80, StatusChangeEvent.STATUS_ENERGY, Color.CYAN));
			Abi.manager.changeStatus(new StatusChangeEvent(this, slot, 80, StatusChangeEvent.STATUS_POWER, Color.CYAN));
			Abi.manager.changeStatus(new StatusChangeEvent(this, slot, 80, StatusChangeEvent.STATUS_NORMAL_POWER, Color.CYAN));
		}
		
		super.nextTurn();
	}

}
