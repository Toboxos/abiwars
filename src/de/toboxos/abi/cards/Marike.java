package de.toboxos.abi.cards;

public class Marike extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Marike() {
		this.name = "Marike";
		this.description = "15 Punkte Kandidatin";
		this.cost = 15;
		this.power = 80;
		this.normal_power = 80;
		this.energy = 100;
		
		this.image = ImageLoader.MARIKE;
	}
}
