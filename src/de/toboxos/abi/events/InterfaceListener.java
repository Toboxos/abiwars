package de.toboxos.abi.events;

public interface InterfaceListener {
	public abstract void cardClicked(int num);
	public abstract void placeClicked(int num);
	public abstract void buttonClicked();
	public abstract void tableClicked();
	public abstract void playerClicked();
}
