package de.toboxos.abi.cards;

public class Sandro extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int attacks = 2;
	
	public Sandro() {
		this.name = "Sandro";
		this.description = "1A-Sportskanone. Fitt genung um 2 mal anzugreifen";
		this.cost = 10;
		this.power = 30;
		this.normal_power = 30;
		this.energy = 60;
		
		this.image = ImageLoader.SANDRO;
	}
	
	@Override
	public void attacked() {
		this.attacks--;
		if( this.attacks <= 0 ) this.canAttack = false;
	}
	
	@Override
	public void nextTurn() {
		this.canAttack = true;
		this.attacks = 2;
	}

}
