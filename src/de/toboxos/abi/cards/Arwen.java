package de.toboxos.abi.cards;

import de.toboxos.abi.Abi;

public class Arwen extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// for abiball
	//private int counter = 0;
	//private boolean[] ausweichen = new boolean[] {true, false};

	public Arwen() {
		this.name = "1Arwin";
		this.description = "Macht e^π Schaden und hat π^e Energie. Dieser Tarzan weicht manchmal Angriffen aus";
		this.cost = 3;
		this.power = 23;
		this.normal_power = 23;
		this.energy = 22;
		
		this.image = ImageLoader.ARWEN;
	}
	
	@Override
	public int setDamage(int power, StudentCard c) {
		int r = Abi.random.nextInt(100);
		if( r < 33 ) return 0;
		//if( ausweichen[counter] ) return 0;
		//counter++;
		
		return power;
	}
	
}
