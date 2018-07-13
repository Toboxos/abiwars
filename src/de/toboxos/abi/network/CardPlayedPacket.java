package de.toboxos.abi.network;

import java.io.Serializable;

public class CardPlayedPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int player;
	private int slot;
	private int pos;
	
	public CardPlayedPacket(int player, int slot, int pos) {
		this.player = player;
		this.slot = slot;
		this.pos = pos;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public int getPos() {
		return pos;
	}
}
