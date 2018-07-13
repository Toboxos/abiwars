package de.toboxos.abi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.toboxos.abi.cards.EventCard;
import de.toboxos.abi.cards.StudentCard;

public class Controller implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Controller instance;
	
	private List<EventCard> events = new ArrayList<>();
	private StudentCard[] table = new StudentCard[10];
	private Player[] players = new Player[2];
	
	
	private int round = 1;
	private int turn = 0;
	private boolean master = false;
	private boolean waiting = false;
	
	public Controller() {
		for( int i = 0; i < 10; i++) {
			setSlot( i, null );
		}
		
		instance = this;
	}
	
	public void setSlot(int slot, StudentCard c) {
		if( slot < 0 || slot > 9 ) return;
		if( c != null ) c.setSlot(slot);
		
		table[slot] = c;
	}
	
	public StudentCard getSlot(int slot) {
		if( slot < 0 || slot > 9 ) return null;
		
		return table[slot];
	}
	
	public void setPlayer(int num, Player p) {
		if( num < 0 || num > 1 ) return;
		
		players[num] = p;
	}
	
	public Player getPlayer(int num) {
		if( num < 0 || num > 1 ) return null;
		
		return players[num];
	}
	
	public void setMaster(boolean master) {
		this.master = master;
	}
	
	public boolean isMaster() {
		return master;
	}
	
	public void setWaiting(boolean wait) {
		waiting = wait;
	}
	
	public boolean isWaiting() {
		return waiting;
	}
	
	public void nextTurn() {
		switch( turn ) {
			case 0:
				turn = 1;
				break;
			case 1:
				turn = 2;
				break;
			case 2:
				turn = 1;
				round++;
				break;
			default:
		}
	}
	
	public int getTurn() {
		return turn;
	}
	
	public int getRound() {
		return round;
	}
	
	public void addEvent(EventCard e) {
		events.add(e);
	}
	
	public void removeEvent(EventCard e) {
		events.remove(e);
	}
	
	public EventCard[] getEvents() {
		return events.toArray(new EventCard[0]);
	}
	
}
