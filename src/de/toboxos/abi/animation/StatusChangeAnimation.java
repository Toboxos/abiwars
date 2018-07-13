package de.toboxos.abi.animation;

import java.awt.Color;
import java.awt.Graphics2D;

import de.toboxos.abi.Controller;
import de.toboxos.abi.Logger;
import de.toboxos.abi.cards.StudentCard;

public class StatusChangeAnimation implements Animation {

	private final double animationTime = 300.0f;
	private long startTime;
	
	private Color oldColor;
	private int slot;
	//private Dimension card, offsetCard;
	private StudentCard c;
	
	private boolean finished = false;
	
	public StatusChangeAnimation(StudentCard c, int slot, Color color) {
		this.c = c;
		this.slot = slot;
		this.oldColor = c.getOriginalColor();
		c.setHighlightedColor(color);
	}
	
	@Override
	public void start(long time, int width, int height) {
		this.startTime = time;
	}

	@Override
	public void draw(Graphics2D g2d, long time) {
		if( time - startTime >= animationTime ) {
			finished = true;
			c = Controller.instance.getSlot(slot);
			Logger.logMessage("StatusChangeAnimation:draw() Card: " + c );
			if( c != null ) c.setHighlightedColor(oldColor);
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}
