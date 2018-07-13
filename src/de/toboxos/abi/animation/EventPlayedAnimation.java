package de.toboxos.abi.animation;

import java.awt.Dimension;
import java.awt.Graphics2D;

import de.toboxos.abi.Controller;
import de.toboxos.abi.cards.EventCard;

public class EventPlayedAnimation implements Animation {

	private final double animationTime = 500.0f;
	private long startTime;
	
	private long startX, startY, startWidth, startHeight;
	private long stopX, stopY, stopWidth, stopHeight;
	
	private int player;
	private EventCard card;
	private int pos;
	private int type;
	
	private boolean finished = false;
	
	public EventPlayedAnimation(int p, EventCard c, int pos) {
		this.pos = pos;
		this.player = p;
		this.card = c;
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
				startX = offsetTable.width + 1*table.width/10 + 10 + pos*spaceCard;
				startY = offsetTable.height + table.height + hand.height / 4 + 10;
				startWidth = cardHand.width;
				startHeight = cardHand.height;
				
				/* Dimension card = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
				Dimension offsetCard = new Dimension(
						offsetTable.width + (slot%5+1) * (int) (0.333333333333333333333333333333333333333333333333*table.width/8) + slot%5 * (int) (1.2*table.width/8), 
						offsetTable.height + table.height - (int) (0.4*table.height/10) - card.height
				); */
				
				/* stopX = offsetCard.width;
				stopY = offsetCard.height;
				stopWidth = card.width;
				stopHeight = card.height; */
				stopX = offsetTable.width + table.width / 2;
				stopY = offsetTable.height + table.height / 2;
				stopWidth = 1;
				stopX = 1;
				break;
			case 1:
				startX = offsetTable.width + 1*table.width/10 + 10 + pos*spaceCard;
				startY = 10;
				startWidth = cardHand.width;
				startHeight = cardHand.height;
				
				/* card = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
				offsetCard = new Dimension(
						offsetTable.width + (slot%5+1) * (int) (0.33333333333333333333333333333333333333333333333*table.width/8) + slot%5 * (int) (1.2*table.width/8), 
						offsetTable.height + (int) (0.4*table.height/10)
				); */
				
				/* stopX = offsetCard.width;
				stopY = offsetCard.height;
				stopWidth = card.width;
				stopHeight = card.height; */
				stopX = offsetTable.width + table.width / 2;
				stopY = offsetTable.height + table.height / 2;
				stopWidth = 1;
				stopX = 1;
				break;
		}

	}

	@Override
	public void draw(Graphics2D g2d, long time) {
		if( time - startTime >= animationTime ) {
			if( !card.isFinished() ) Controller.instance.addEvent(card);
			finished = true;
		} else {
			card.drawIcon(g2d, (int) (startX + ((stopX - startX) / animationTime) * (time - startTime)), (int) (startY + ((stopY - startY) / animationTime) * (time - startTime)), (int) (startWidth + ((stopWidth - startWidth) / animationTime) * (time - startTime)), (int) (startHeight + ((stopHeight - startHeight) / animationTime) * (time - startTime)));
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}
