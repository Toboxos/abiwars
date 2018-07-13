package de.toboxos.abi.network;

import java.io.Serializable;

public class CardDeadPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int slot;
	
	public CardDeadPacket(int slot) {
		this.slot = slot;
	}
	
	public int getSlot() {
		return slot;
	}
}
