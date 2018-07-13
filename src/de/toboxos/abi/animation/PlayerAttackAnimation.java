package de.toboxos.abi.animation;

import java.awt.Dimension;
import java.awt.Graphics2D;

import de.toboxos.abi.Controller;
import de.toboxos.abi.Logger;
import de.toboxos.abi.cards.StudentCard;

public class PlayerAttackAnimation implements Animation {

	private final double animationTime = 500.0f;
	private long startTime;
	
	private long startX, startY, startWidth, startHeight;
	private long stopX, stopY, stopWidth, stopHeight;
	
	private int player;
	private StudentCard c;
	private int slot;
	private int type;
	
	private boolean finished = false;

	public PlayerAttackAnimation(int player, StudentCard c, int slot) {
		this.c = c;
		this.player = player;
		this.slot = slot;
	}

	@Override
	public void start(long time, int width, int height) {
		
		startTime = time;
		Dimension offsetTable = new Dimension(1*width/10, (int) (1.5*height/10) );
		Dimension table = new Dimension(8*width/10, 7*height/10);
		Dimension hand = new Dimension( 7*table.width/10, 8*(height - (offsetTable.height + table.height))/10 );
		
		
		
		// If type == 0 then animation for own player
		// If type == 1 then animation for other player
		type = 0;
		if( Controller.instance.isMaster() && player == 0 || !Controller.instance.isMaster() && player == 1 ) type = 1; 
		
		// Calculate the start and stop positions and sizes
		switch( type ) {
			case 0:
				
				Dimension card = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
				Dimension offsetCard = new Dimension(
						offsetTable.width + (slot%5+1) * (int) (0.333333333333333333333333333333333333333333333333*table.width/8) + slot%5 * (int) (1.2*table.width/8), 
						offsetTable.height + table.height - (int) (0.4*table.height/10) - card.height
				);
				
				startX = offsetCard.width;
				startY = offsetCard.height;
				startWidth = card.width;
				startHeight = card.height;
				
				stopX = offsetTable.width + (table.width / 10 - 3*offsetTable.height / 4) / 2;
				stopY = offsetTable.height - hand.height / 4 - 3*offsetTable.height / 4;
				stopWidth = card.width;
				stopHeight = card.height;
				break;
			case 1:
				
				card = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
				offsetCard = new Dimension(
						offsetTable.width + (slot%5+1) * (int) (0.33333333333333333333333333333333333333333333333*table.width/8) + slot%5 * (int) (1.2*table.width/8), 
						offsetTable.height + (int) (0.4*table.height/10)
				);
				
				startX = offsetCard.width;
				startY = offsetCard.height;
				startWidth = card.width;
				startHeight = card.height;
				
				stopX = offsetTable.width + (table.width / 10 - 3*offsetTable.height / 4) / 2;
				stopY = offsetTable.height + table.height + hand.height / 4 + 3*offsetTable.height / 4 - card.height;
				stopWidth = card.width;
				stopHeight = card.height;
				break;
		}
		
		c.setDraw(false);
		//Controller.instance.setSlot(slot, null);
	}

	@Override
	public void draw(Graphics2D g2d, long time) {
		if( time - startTime >= animationTime * 2 ) { 
			c = Controller.instance.getSlot(slot);
			if( c != null ) c.setDraw(true);
			
			finished = true;
			Logger.logMessage("PlayerAttackAnimation for " + c.getName() + " finished");
			Logger.logMessage("Slot " + slot + " is " + Controller.instance.getSlot(slot));
		} else {
			if( time - startTime < animationTime ) {
				c.drawIcon(g2d, (int) (startX + ((stopX - startX) / animationTime) * (time - startTime)), (int) (startY + ((stopY - startY) / animationTime) * (time - startTime)), (int) (startWidth + ((stopWidth - startWidth) / animationTime) * (time - startTime)), (int) (startHeight + ((stopHeight - startHeight) / animationTime) * (time - startTime)));
			} else {
				c.drawIcon(g2d, (int) (stopX + ((startX - stopX) / animationTime) * (time - startTime - animationTime)), (int) (stopY + ((startY - stopY) / animationTime) * (time - startTime - animationTime)), (int) (stopWidth + ((startWidth - stopWidth) / animationTime) * (time - startTime - animationTime)), (int) (stopHeight + ((startHeight - stopHeight) / animationTime) * (time - startTime - animationTime)));
			}
		}
	}
	
	@Override
	public boolean isFinished() {
		return finished;
	}
}

