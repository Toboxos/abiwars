package de.toboxos.abi.events;

public class Event {
	protected boolean canceled = false;
	
	public void setCanceled(boolean cancel) {
		canceled = cancel;
	}
	
	public boolean isCanceled() {
		return canceled;
	}
}
