package de.toboxos.abi.network;

import java.io.Serializable;

public class SetSlotPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int slot;
	private String card;
	
	public SetSlotPacket(int slot, String card) {
		this.slot = slot;
		this.card = card;
	}

	public int getSlot() {
		return slot;
	}

	public String getCard() {
		return card;
	}
}
