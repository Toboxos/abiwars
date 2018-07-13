package de.toboxos.abi.cards;

public class Daniel extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Daniel() {
		this.name = "Russen-Daniel";
		this.description = "Durchtrainierter Rewe-Mitarbeiter. Kostest wenig, kann viel";
		this.cost = 4;
		this.power = 40;
		this.normal_power = 40;
		this.energy = 30;
		
		this.image = ImageLoader.DANIEL;
	}

}
