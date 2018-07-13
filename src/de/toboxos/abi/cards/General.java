package de.toboxos.abi.cards;


public class General extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public General() {
		this.name = "Der General";
		this.description = "Wenn der kleine Herrscher mal einen\n draufmacht...";
		this.cost = 1;
		this.power = 30;
		this.normal_power = 30;
		this.energy = 10;
		
		this.image = ImageLoader.GENERAL;
	}
}
