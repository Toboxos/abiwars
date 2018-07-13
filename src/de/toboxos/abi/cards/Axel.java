package de.toboxos.abi.cards;

public class Axel extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Axel() {
		this.name = "Schlägeraxel";
		this.description = "Doppelter Schaden gegen kleine Personen.";
		this.cost = 8;
		this.power = 30;
		this.normal_power = 30;
		this.energy = 60;
		
		this.image = ImageLoader.AXEL;
	}
	
	
	@Override
	public int getPower(StudentCard c) {
		if(
				c instanceof General
			||	c instanceof Emilia
			||	c instanceof Manu
			|| 	c instanceof Marike
			||	c instanceof Franzi
		) return power*2;
		
		return power;
	}
	
}
