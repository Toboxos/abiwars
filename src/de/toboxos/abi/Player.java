package de.toboxos.abi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.toboxos.abi.cards.Card;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int abiPoints = 300;
	private int gradePoints = 0;
	private String name;
	private int num;
	private boolean canView = false;
	
	private List<Card> hand = new ArrayList<>();
	private List<Card> deck = new ArrayList<>();
	
	public Player(String name, int num) {
		this.name = name;
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
	
	public void addCardToHand(Card c) {
		hand.add(c);
	}
	
	public void removeFromHand(Card c) {
		hand.remove( c );
	}
	
	public Card getCardFromHand(int i) {
		if( i >= hand.size() ) {
			Logger.logMessage("Player:getCardFromHand(): ArrayOutOfBounce Index:" + i);
			return null;
		}
		return hand.get(i);
	}
	
	public Card[] getHand() {
		return hand.toArray(new Card[0]);
	}
	
	public Card[] getDeck() {
		return deck.toArray(new Card[0]);
	}
	
	public Card drawCard() {
		if( deck.isEmpty() ) return null;
		
		Card c = deck.get(0);
		deck.remove(0);
		
		return c;
	}
	
	public void setDeck(Card[] c) {
		deck.addAll( Arrays.asList( c ) );
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setGradePoints(int i) {
		gradePoints = i;
	}
	
	public int getAbiPoints() {
		return this.abiPoints;
	}
	
	public int getGradePoints() {
		return this.gradePoints;
		//return 15;
	}
	
	public void setDamage(int damage) {
		abiPoints -= damage;
	}
	
	public void setView(boolean view) {
		this.canView = view;
	}
	
	public boolean getView() {
		return this.canView;
	}
	
}
