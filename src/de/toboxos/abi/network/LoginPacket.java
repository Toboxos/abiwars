package de.toboxos.abi.network;

import java.io.Serializable;

public class LoginPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] cards = new String[30];
	private String name;
	
	public LoginPacket(String name, String[] cards) {
		this.name = name;
		this.cards = cards;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getCards() {
		return cards;
	}
}
