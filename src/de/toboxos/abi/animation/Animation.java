package de.toboxos.abi.animation;

import java.awt.Graphics2D;

public interface Animation {

	public void start(long time, int width, int height);
	public void draw(Graphics2D g2d, long time);
	public boolean isFinished();
}
