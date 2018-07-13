package de.toboxos.abi.cards;

import java.awt.Color;

public class Felix extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Felix() {
		this.name = "Felix";
		this.description = "Hat gar kein Bock. Provoziert aber und zieht den Angriff auf sich";
		this.cost = 7;
		this.power = 0;
		this.normal_power = 0;
		this.energy = 70;
		this.protect = true;
		
		this.image = ImageLoader.FELIX;
	}
	
	@Override
	public void played() {
		this.background = Color.GRAY;
	}

}
