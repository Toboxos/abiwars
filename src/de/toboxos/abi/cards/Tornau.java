package de.toboxos.abi.cards;

import java.awt.Color;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;
import de.toboxos.abi.Logger;
import de.toboxos.abi.events.StatusChangeEvent;

public class Tornau extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int turns = 1;

	public Tornau() {
		this.name = "Tornau";
		this.description = "Mathelehrerin.\nSchaden:\n(0.5*sin(x*pi/2)+1)*30";
		this.cost = 5;
		this.power = 30;
		this.normal_power = 30;
		this.energy = 40;
		
		this.image = ImageLoader.TORNAU;
	}
	
	@Override
	public void nextTurn() {
		
		// Only Server
		if( !Controller.instance.isMaster() ) return;
		
		turns++;
		Logger.logMessage("Tornau:nextTurn() turns=" + turns);
		int x = (int) Math.floor(turns / 2);
		int p = (int) ((0.5 * Math.sin(x*Math.PI) + 1) * normal_power);
		Abi.manager.changeStatus(new StatusChangeEvent(this, slot, p, StatusChangeEvent.STATUS_POWER, Color.CYAN));
		
		
		super.nextTurn();
	}
}
