package de.toboxos.abi.cards;

import de.toboxos.abi.Abi;
import de.toboxos.abi.Logger;

public class CardGenerator {
	
	private static String[] cards = new String[] {"Georg", "Axel", "General", "Arwen", "Cooper", "Flamur", "Daniel", "Emilia", "Felix", "Manu", "Felix G.", "Dubowy", "Steffen", "Franzi", "Marike", "Nils", "Sandro", "Zelenka", "Allgeier", "Taraschewski", "Forstner", "Schellmann", "Wiech", "Tornau", "Kreuzer", "Mischo"};;

	public static Card getCardFromName(String name) {
		switch( name ) {
			case "Arwen":
				return new Arwen();
				
			case "General":
				return new General();
				
			case "Axel":
				return new Axel();
				
			case "Cooper":
				return new Cooper();
				
			case "Georg":
				return new Georg();
				
			case "Flamur":
				return new Flamur();
				
			case "Daniel":
				return new Daniel();
				
			case "Emilia":
				return new Emilia();
				
			case "Felix":
				return new Felix();
				
			case "Dubowy":
				return new Dubowy();
			
			case "Manu":
				return new Manu();
				
			case "Felix G.":
				return new FelixG();
				
			case "Steffen":
				return new Steffen();
				
			case "Anwalt":
				return new Anwalt();
				
			case "Franzi":
				return new Franzi();
				
			case "Marike":
				return new Marike();
				
			case "Nils":
				return new Nils();
				
			case "Sandro":
				return new Sandro();
			
			case "Schlachter":
				return new Schlachter();
				
			case "Zelenka":
				return new Zelenka();
				
			case "Allgeier":
				return new Allgeier();
				
			case "Taraschewski":
				return new Taraschewski();
				
			case "Forstner":
				return new Forstner();
				
			case "Schellmann":
				return new Schellmann();
				
			case "Weisswurst":
				return new Weisswurst();
				
			case "Wiech":
				return new Wiech();
				
			case "Tornau":
				return new Tornau();
				
			case "Kreuzer":
				return new Kreuzer();
			
			case "Mischo":
				return new Mischo();
				
			default:
				return null;
		}
	}
	
	public static Card getRandomCard() {
		int r = Abi.random.nextInt(1000);
		
		// Generiere Random Card
		if( r < 996 ) {
			r = Abi.random.nextInt(cards.length);
			return getCardFromName(cards[r]);
		} 
		
		// Generiere Herr Schlachter mit 0.3% Wahrscheinlichkeit
		else {
			Logger.logMessage("HERR SCHLACHTER WURDE GENERIERT!!! HELL YEAH!!!");
			return new Schlachter();
		}
	}
	
	
	public static Card[] generateDeck(String[] cards) {
		Card[] deck = new Card[30];
		for( int i = 0; i < 30; i++ ) {
			if( i >= cards.length || cards[i] == null ) {
				deck[i] = getRandomCard();				
			} else {
				deck[i] = getCardFromName( cards[i] );
			}
		}
		
		return deck;
	}
}
