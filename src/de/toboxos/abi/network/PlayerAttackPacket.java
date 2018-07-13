package de.toboxos.abi.network;

import java.io.Serializable;

public class PlayerAttackPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int player;
	private int slot;
	
	public PlayerAttackPacket(int p, int s) {
		player = p;
		slot = s;
	}

	public int getPlayer() {
		return player;
	}

	public int getSlot() {
		return slot;
	}
}
