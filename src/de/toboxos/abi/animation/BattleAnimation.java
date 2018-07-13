package de.toboxos.abi.animation;

import java.awt.Dimension;
import java.awt.Graphics2D;

import de.toboxos.abi.Controller;
import de.toboxos.abi.cards.StudentCard;

public class BattleAnimation implements Animation {

	private final double animationTime = 500.0f;
	private long startTime;
	
	private long startX, startY, startWidth, startHeight;
	private long stopX, stopY, stopWidth, stopHeight;
	
	private int player;
	private StudentCard c;
	private int slot1;
	private int slot2;
	private int type;
	
	private boolean finished = false;

	public BattleAnimation(int player, StudentCard c, int slot1, int slot2) {
		this.c = c;
		this.player = player;
		this.slot1 = slot1;
		this.slot2 = slot2;
	}

	@Override
	public void start(long time, int width, int height) {
		
		startTime = time;
		Dimension offsetTable = new Dimension(1*width/10, (int) (1.5*height/10) );
		Dimension table = new Dimension(8*width/10, 7*height/10);
		Dimension hand = new Dimension( 7*table.width/10, 8*(height - (offsetTable.height + table.height))/10 );
		Dimension cardHand = new Dimension( 2*(hand.height - 20)/3, hand.height - 20 );
		
		
		
		// If type == 0 then animation for own player
		// If type == 1 then animation for other player
		type = 0;
		if( Controller.instance.isMaster() && player == 1 || !Controller.instance.isMaster() && player == 0 ) type = 1; 
		
		// Position which new card will be
		int handSize = Controller.instance.getPlayer(player).getHand().length;
		int spaceCard = cardHand.width + 10;
		if( 10 + (handSize-1) * spaceCard + cardHand.width > hand.width - 20 ) {
			spaceCard = (hand.width - 30 - cardHand.width) / (handSize-1);
		}
		
		// Calculate the start and stop positions and sizes
		switch( type ) {
			case 0:
				
				Dimension cardUpper = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
				Dimension offsetCardUpper = new Dimension(
						offsetTable.width + (slot2%5+1) * (int) (0.33333333333333333333333333333333333333333333333*table.width/8) + slot2%5 * (int) (1.2*table.width/8), 
						offsetTable.height + (int) (0.4*table.height/10)
				);
				
				Dimension cardLower = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
				Dimension offsetCardLower = new Dimension(
						offsetTable.width + (slot1%5+1) * (int) (0.333333333333333333333333333333333333333333333333*table.width/8) + slot1%5 * (int) (1.2*table.width/8), 
						offsetTable.height + table.height - (int) (0.4*table.height/10) - cardLower.height
				);
				
				startX = offsetCardLower.width;
				startY = offsetCardLower.height;
				startWidth = cardLower.width;
				startHeight = cardLower.height;
				
				stopX = offsetCardUpper.width;
				stopY = offsetCardUpper.height;
				stopWidth = cardUpper.width;
				stopHeight = cardUpper.height;
				break;
			case 1:
				
				cardUpper = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
				offsetCardUpper = new Dimension(
						offsetTable.width + (slot1%5+1) * (int) (0.33333333333333333333333333333333333333333333333*table.width/8) + slot1%5 * (int) (1.2*table.width/8), 
						offsetTable.height + (int) (0.4*table.height/10)
				);
				
				cardLower = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
				offsetCardLower = new Dimension(
						offsetTable.width + (slot2%5+1) * (int) (0.333333333333333333333333333333333333333333333333*table.width/8) + slot2%5 * (int) (1.2*table.width/8), 
						offsetTable.height + table.height - (int) (0.4*table.height/10) - cardLower.height
				);
				
				startX = offsetCardUpper.width;
				startY = offsetCardUpper.height;
				startWidth = cardUpper.width;
				startHeight = cardUpper.height;
				
				stopX = offsetCardLower.width;
				stopY = offsetCardLower.height;
				stopWidth = cardLower.width;
				stopHeight = cardLower.height;
				break;
		}
		
		c.setDraw(false);
		//Controller.instance.setSlot(slot1, null);
	}

	@Override
	public void draw(Graphics2D g2d, long time) {
		if( time - startTime >= animationTime * 2 ) {
			c = Controller.instance.getSlot(slot1);
			if( c == null ) return;
			
			c.setDraw(true);
			
			// Only for master because of killing cards
			if( Controller.instance.isMaster() ) {
			//	if( c.getEnergy() <= 0) Abi.manager.cardDead(slot1);
			//	if( Controller.instance.getSlot(slot2).getEnergy() <= 0 ) Abi.manager.cardDead(slot2);
			}
			
			finished = true;
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
