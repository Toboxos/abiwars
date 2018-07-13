package de.toboxos.abi.cards;

public class Schlachter extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Schlachter() {
		this.name = "King Arthur";
		this.description = "Er hätte es anders gemacht";
		this.cost = 10;
		this.power = 600;
		this.normal_power = 600;
		this.energy = 600;
		
		this.canAttack = true;
		this.image = ImageLoader.SCHLACHTER;
	}
 
}
