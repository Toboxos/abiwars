package de.toboxos.abi.events;

import java.awt.Color;

import de.toboxos.abi.cards.StudentCard;

public class StatusChangeEvent {

	public final static int STATUS_COST = 0;
	public final static int STATUS_ENERGY = 1;
	public final static int STATUS_POWER = 2;
	public final static int STATUS_NORMAL_POWER = 3;
	
	private StudentCard card;
	private int status;
	private int slot;
	private int value;
	private Color color;
	
	public StatusChangeEvent(StudentCard c, int slot, int val, int status, Color color) {
		this.card = c;
		this.slot = slot;
		this.value = val;
		this.status = status;
		this.color = color;
	}
	
	public StudentCard getCard() {
		return card;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getStatus() {
		return status;
	}
	
	public Color getColor() {
		return color;
	}
}