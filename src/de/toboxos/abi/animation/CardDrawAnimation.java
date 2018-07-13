package de.toboxos.abi.animation;

import java.awt.Dimension;
import java.awt.Graphics2D;

import de.toboxos.abi.Controller;
import de.toboxos.abi.cards.Card;

public class CardDrawAnimation implements Animation {

	private final double animationTime = 700.0f;
	private long startTime;
	
	private long startX, startY, startWidth, startHeight;
	private long stopX, stopY, stopWidth, stopHeight;
	
	private int player;
	private Card c;
	private int type;
	
	private boolean finished = false;
	

	public CardDrawAnimation(int player, Card c) {
		this.c = c;
		this.player = player;
	}

	@Override
	public void start(long time, int width, int height) {
		startTime = time;
		Dimension offsetTable = new Dimension(1*width/10, (int) (1.5*height/10) );
		Dimension table = new Dimension(8*width/10, 7*height/10);
		Dimension hand = new Dimension( 7*table.width/10, 8*(height - (offsetTable.height + table.height))/10 );
		Dimension card = new Dimension( 2*(hand.height - 20)/3, hand.height - 20 );
		
		// If type == 0 then animation for own player
		// If type == 1 then animation for other player
		type = 0;
		if( Controller.instance.isMaster() && player == 1 || !Controller.instance.isMaster() && player == 0 ) type = 1; 
		
		// Position which new card will be
		int pos = Controller.instance.getPlayer(player).getHand().length;
		int spaceCard = card.width + 10;
		if( 10 + (pos-1) * spaceCard + card.width > hand.width - 20 ) {
			spaceCard = (hand.width - 30 - card.width) / (pos-1);
		}
		
		
		// How many cards on deck left
		int count = Controller.instance.getPlayer(player).getDeck().length;
		
		// Calculate the start and stop positions and sizes
		switch( type ) {
			case 0:
				switch( count ) {
					case 0:
						break;
					case 1:
						startX = offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5;
						startY = offsetTable.height + table.height + hand.height / 4 + 10;
						break;
					case 2:
						startX = offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 0;
						startY = offsetTable.height + table.height + hand.height / 4 + 10;
						break;
					case 3:
						startX =  offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 5;
						startY = offsetTable.height + table.height + hand.height / 4 + 10;
						break;
					default:
						startX = offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 10;
						startY = offsetTable.height + table.height + hand.height / 4 + 10;
				}
				startWidth = card.width;
				startHeight = card.height;
				
				stopX = offsetTable.width + 1*table.width/10 + 10 + pos*spaceCard;
				stopY = offsetTable.height + table.height + hand.height / 4 + 10;
				stopWidth = card.width;
				stopHeight = card.height;
				break;
			case 1:
				switch( count ) {
					case 0:
						break;
					case 1:
						startX = offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5;
						startY = 10;
						startWidth = card.width;
						startHeight = card.height;
						break;
					case 2:
						startX = offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 0;
						startY = 10; 
						startWidth = card.width;
						startHeight = card.height;
						break;
					case 3:
						startX = offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 5;
						startY = 10;
						startWidth = card.width;
						startHeight = card.height;
						break;
					default:
						startX = offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 10;
						startY = 10;
						startWidth = card.width;
						startHeight = card.height;
				}
				startWidth = card.width;
				startHeight = card.height;

				stopX = offsetTable.width + 1*table.width/10 + 10 + pos*spaceCard;
				stopY = 10;
				stopWidth = card.width;
				stopHeight = card.height;
				break;
		}
		
	}

	@Override
	public void draw(Graphics2D g2d, long time) {
		if( time - startTime >= animationTime ) {
			Controller.instance.getPlayer(player).addCardToHand(c);
			finished = true;
		} else {
			if( time - startTime <= animationTime / 2 || type == 1 )
				c.drawBackground(g2d, (int) (startX + ((stopX - startX) / animationTime) * (time - startTime)), (int) (startY + ((stopY - startY) / animationTime) * (time - startTime)), (int) (startWidth + ((stopWidth - startWidth) / animationTime) * (time - startTime)), (int) (startHeight + ((stopHeight - startHeight) / animationTime) * (time - startTime)));
			else
				c.drawCard(g2d, (int) (startX + ((stopX - startX) / animationTime) * (time - startTime)), (int) (startY + ((stopY - startY) / animationTime) * (time - startTime)), (int) (startWidth + ((stopWidth - startWidth) / animationTime) * (time - startTime)), (int) (startHeight + ((stopHeight - startHeight) / animationTime) * (time - startTime)));
	
		}
	}
	
	@Override
	public boolean isFinished() {
		return finished;
	}
}
