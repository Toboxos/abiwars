package de.toboxos.abi.cards;

public class Nils extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Nils() {
		this.name = "Nils";
		this.description = "Er hat das Tagebuch verloren. 20% weniger Schaden durch Lehrer";
		this.cost = 12;
		this.power = 50;
		this.normal_power = 50;
		this.energy = 60;
		
		this.image = ImageLoader.NILS;
	}
	
	@Override
	public int setDamage(int power, StudentCard c) {
		if(
				c instanceof Allgeier
			||	c instanceof Dubowy
			||	c instanceof Forstner
			||	c instanceof Kreuzer
			||	c instanceof Mischo
			||	c instanceof Schellmann
			||	c instanceof Taraschewski
			||	c instanceof Tornau
			|| 	c instanceof Wiech
			||	c instanceof Zelenka
		) return (int) (power * 0.8);
		
		return power;
	}

}
