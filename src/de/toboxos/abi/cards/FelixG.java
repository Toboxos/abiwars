package de.toboxos.abi.cards;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Controller;

public class FelixG extends StudentCard {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FelixG() {
		this.name = "Avia Felix";
		this.description = "Felix von der Tanke. Hilfsbereit und füllt den Tank auf\n\n+ Ziehe 2 Karten";
		this.cost = 6;
		this.power = 30;
		this.normal_power = 40;
		this.energy = 50;
		
		this.image = ImageLoader.FELIX_G;
	}
	
	@Override
	public void played() {
		if( !Controller.instance.isMaster() ) return;
		
		switch( Controller.instance.getTurn() ) {
			case 1:
				Abi.manager.drawCard(0);
				Abi.manager.drawCard(0);
				break;
				
			case 2:
				Abi.manager.drawCard(1);
				Abi.manager.drawCard(1);
				break;
		}
	}
	
	@Override
	public void yourTurn() {
		/* switch( Controller.instance.getTurn() ) {
			case 1:
				Controller.instance.getPlayer(0).setGradePoints(Controller.instance.getPlayer(0).getGradePoints() + 1);
				break;
				
			case 2:
				Controller.instance.getPlayer(1).setGradePoints(Controller.instance.getPlayer(1).getGradePoints() + 1);
				break;
		} */
	}
}
