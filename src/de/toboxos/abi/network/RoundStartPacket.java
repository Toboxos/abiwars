package de.toboxos.abi.network;

import java.io.Serializable;

public class RoundStartPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int player;
	private int round;
	
	public RoundStartPacket(int player, int round) {
		this.round = round;
		this.player = player;
	}

	public int getPlayer() {
		return player;
	}

	public int getRound() {
		return round;
	}

}
