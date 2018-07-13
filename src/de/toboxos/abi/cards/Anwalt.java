package de.toboxos.abi.cards;

import java.awt.Color;

public class Anwalt extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Anwalt() {
		this.name = "Anwalt";
		this.description = "Er bietet Rechtsschutz";
		this.cost = 0;
		this.power = 30;
		this.normal_power = 30;
		this.energy = 30;
		
		this.protect = true;
		this.image = ImageLoader.ANWALT;
	}
	
	@Override
	public void played() {
		this.background = Color.GRAY;
	}

}
