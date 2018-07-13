package de.toboxos.abi.cards;

public class Manu extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Manu() {
		this.name = "Die Queen";
		this.description = "Als Mama bekommt sie 10% weniger Schaden durch ihre Kinder";
		this.cost = 9;
		this.power = 40;
		this.normal_power = 40;
		this.energy = 50;
		
		this.image = ImageLoader.MANU;
	}
	
	@Override
	public int setDamage(int damage, StudentCard c) {
		if(
				c instanceof Arwen
			||	c instanceof Axel
			||	c instanceof Daniel
			||	c instanceof Emilia
			||	c instanceof Felix
			|| 	c instanceof FelixG
			||	c instanceof Franzi
			||	c instanceof Georg
			||	c instanceof Marike
			||	c instanceof Nils
			||	c instanceof Sandro
			||	c instanceof Steffen
		) return (int) (damage * 0.9);
		
		return damage;
			
	}

}
