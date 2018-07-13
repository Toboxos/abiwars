package de.toboxos.abi.cards;

import java.awt.Color;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Logger;
import de.toboxos.abi.events.StatusChangeEvent;

public class Emilia extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Emilia() {
		this.name = "Emilia";
		this.description = "Isst immer, wenn sie nix zu tun hat und wird dadurch stärker";
		this.cost = 4;
		this.energy = 40;
		this.power = 20;
		this.normal_power = 20;
		
		this.image = ImageLoader.EMILIA;
	}
	
	@Override
	public void nextTurn() {
		Logger.logMessage("Emilia:nextTurn() canAttack=" + canAttack);
		if( canAttack ) {
			this.normal_power = this.normal_power + 10;
			Abi.manager.changeStatus(new StatusChangeEvent(this, this.slot, power + 10, StatusChangeEvent.STATUS_POWER, Color.CYAN));
		}
		super.nextTurn();
	}
	

}
