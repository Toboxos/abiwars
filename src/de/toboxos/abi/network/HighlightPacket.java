package de.toboxos.abi.network;

import java.awt.Color;
import java.io.Serializable;

public class HighlightPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int player;
	private boolean table;
	private int pos;
	private Color color;
	
	public HighlightPacket(int player, boolean table, int pos, Color color) {
		this.player = player;
		this.table = table;
		this.pos = pos;
		this.color = color;
	}
	
	public int getPlayer() {
		return player;
	}

	public boolean isTable() {
		return table;
	}

	public int getPos() {
		return pos;
	}

	public Color getColor() {
		return this.color;
	}
}
