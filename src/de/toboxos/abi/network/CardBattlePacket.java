package de.toboxos.abi.network;

import java.io.Serializable;

public class CardBattlePacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int player;
	private int slot1;
	private int slot2;
	
	public CardBattlePacket(int player, int slot1, int slot2) {
		this.player = player;
		this.slot1 = slot1;
		this.slot2 = slot2;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public int getSlot1() {
		return slot1;
	}
	
	public int getSlot2() {
		return slot2;
	}
}
