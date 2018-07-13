package de.toboxos.abi.network;

import java.awt.Color;
import java.io.Serializable;

public class StatusChangePacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int slot;
	private int status;
	private int value;
	private Color color;
	
	public StatusChangePacket(int slot, int status, int value, Color c) {
		this.slot = slot;
		this.status = status;
		this.value = value;
		this.color = c;
	}

	public int getSlot() {
		return slot;
	}

	public int getStatus() {
		return status;
	}

	public int getValue() {
		return value;
	}
	
	public Color getColor() {
		return color;
	}
}
