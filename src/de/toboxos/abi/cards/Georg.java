package de.toboxos.abi.cards;

public class Georg extends StudentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Georg() {
		this.name = "Georg";
		this.description = "Macht erheblich mehr Schaden gegenüber Franzi";
		this.cost = 2;
		this.energy = 20;
		this.power = 20;
		this.normal_power = 20;
		
		this.image = ImageLoader.GEORG;
	}
	
	@Override
	public int getPower(StudentCard c) {
		if( c instanceof Franzi ) return (int) (power * 2.5);
		else return power;
	}
}
