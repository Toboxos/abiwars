package de.toboxos.abi.network;

import java.io.Serializable;

public class CardDrawPacket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final int player;
	
	public CardDrawPacket(int player) {
		this.player = player;
	}
	
}
