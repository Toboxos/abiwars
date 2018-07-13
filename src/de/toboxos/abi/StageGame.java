package de.toboxos.abi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.toboxos.abi.animation.Animation;
import de.toboxos.abi.cards.Card;
import de.toboxos.abi.cards.EventCard;
import de.toboxos.abi.cards.StudentCard;
import de.toboxos.abi.events.InterfaceListener;

public class StageGame extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	private int width = 0, height = 0;
	private boolean[][] cardPos = new boolean[2][5];
	private boolean playerHover = false;
	private int cardPreview = -1;
	private int eventPreview = -1;
	private Card dummy = new Card();
	
	private InterfaceListener evtman = null;
	private List<Animation> animations = new ArrayList<>();
	
	private Dimension offsetTable = new Dimension(0, 0), table = new Dimension(0, 0);
	
	private Image blackboard, classroom;

	/**
	 * Create the frame.
	 */
	public StageGame(InterfaceListener e) {
		this.evtman = e;
		
		for( int i = 0; i < 2; i++ ) {
			for( int j = 0; j < 5; j++ ) {
				cardPos[i][j] = false;
			}
		}
		
		try {
			blackboard = ImageIO.read( getClass().getResource("/de/toboxos/abi/res/blackboard.jpg") );
			classroom = ImageIO.read( getClass().getResource("/de/toboxos/abi/res/classroom.jpg") );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
	public void addAnimation(Animation a) {
		animations.add( a );
		a.start(System.currentTimeMillis(), width, height);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.width = this.getWidth();
		this.height = this.getHeight();
		
		offsetTable = new Dimension(1*width/10, (int) (1.5*height/10) );
		table = new Dimension(8*width/10, 7*height/10);
		
		Graphics2D g2d = (Graphics2D) g;
		Font oldFont = g2d.getFont();
		
		g2d.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, 18) );
		Font f = g2d.getFont();
		
		// Table
		g2d.setColor( new Color(0, 160, 0) );
		g2d.fillRect( offsetTable.width, offsetTable.height, table.width, table.height );
		
		g2d	.setColor( Color.BLACK );
		g2d.drawRect( offsetTable.width, offsetTable.height, table.width, table.height );
		
		g2d.drawImage( classroom, 0, 0, width, height, null );
		g2d.drawImage( blackboard, offsetTable.width, offsetTable.height, table.width, table.height, null );
				
		Card cardBig = null;
		Dimension offsetBig = null;
		Dimension sizeBig = null;
		
		// Upper Row
		for( int i = 0; i < 5; i++ ) {
			
			// Dimensions of a card
			Dimension card = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
			Dimension offsetCard = new Dimension(
					offsetTable.width + (i+1) * (int) (0.33333333333333333333333333333333333333333333333*table.width/8) + i * (int) (1.2*table.width/8), 
					offsetTable.height + (int) (0.4*table.height/10)
			);
			
			StudentCard c = Controller.instance.isMaster() ? Controller.instance.getSlot(0+i) : Controller.instance.getSlot(5+i);
			if( c == null ) {
				if( cardPos[0][i] ) 
					g2d.setColor( new Color(113, 224, 255) );
				else
					g2d.setColor( new Color(255, 255, 255) );
				g2d.drawRect( offsetCard.width, offsetCard.height, card.width, card.height );
			} else {
				if( c.getDraw() ) c.drawIcon( g2d, offsetCard.width, offsetCard.height, card.width, card.height );
				if( cardPos[0][i] ) {
					//c.drawIcon(g2d, offsetCard.width - card.width / 2, offsetCard.height, card.width * 2, card.height * 2);
					cardBig = c;
					offsetBig = new Dimension(offsetCard.width - card.width / 2, offsetCard.height);
					sizeBig = new Dimension(card.width * 2, card.height * 2);
				}
			}
		}
		
		// Lower Row
		for( int i = 0; i < 5; i++ ) {
			
			// Dimensions of a card
			Dimension card = new Dimension(1*table.width/8, (int) (1.5*table.width/8) );
			Dimension offsetCard = new Dimension(
					offsetTable.width + (i+1) * (int) (0.333333333333333333333333333333333333333333333333*table.width/8) + i * (int) (1.2*table.width/8), 
					offsetTable.height + table.height - (int) (0.4*table.height/10) - card.height
			);
			
			StudentCard c = Controller.instance.isMaster() ? Controller.instance.getSlot(5+i) : Controller.instance.getSlot(0+i);
			if( c == null ) {
				if( cardPos[1][i] ) 
					g2d.setColor( new Color(133, 224, 255) );
				else
					g2d.setColor( new Color(255, 255, 255) );
				g2d.drawRect( offsetCard.width, offsetCard.height, card.width, card.height );
			} else {
				if( c.getDraw() ) c.drawIcon( g2d, offsetCard.width, offsetCard.height, card.width, card.height );
				if( cardPos[1][i] ) {
					//c.drawIcon(g2d, offsetCard.width - card.width / 2, offsetCard.height - card.height, card.width * 2, card.height * 2);
					cardBig = c;
					offsetBig = new Dimension(offsetCard.width - card.width / 2, offsetCard.height - card.height);
					sizeBig = new Dimension(card.width * 2, card.height * 2);
				}
			}
		}
		
		if( cardBig != null ) cardBig.drawIcon(g2d, offsetBig.width, offsetBig.height, sizeBig.width, sizeBig.height);
		
		
		// Button
		Dimension offsetButton = new Dimension( offsetTable.width + table.width + 10, height / 2 - 25 );
		Dimension button = new Dimension( width - (int) offsetButton.width - 10, 50 );
			
		g2d.setColor( new Color(180, 180, 180) );
		g2d.drawRect( offsetButton.width, offsetButton.height, button.width, button.height );
		Rectangle2D boundsDone = f.getStringBounds( "Fertig", g2d.getFontRenderContext());
		Rectangle2D boundsWait = f.getStringBounds( "Warten...", g2d.getFontRenderContext());
				
		if( 
			(
				Controller.instance.isMaster() && Controller.instance.getTurn() == 1 
			||	!Controller.instance.isMaster() && Controller.instance.getTurn() == 2 
			) && !Controller.instance.isWaiting()
		) g2d.drawString( "Fertig", offsetButton.width + (button.width - (int) boundsDone.getWidth()) / 2, offsetButton.height + button.height - (int) ((button.height - (int) boundsDone.getHeight()) / 1.6) ); 
		else g2d.drawString( "Warten...", offsetButton.width + (button.width - (int) boundsWait.getWidth()) / 2, offsetButton.height + button.height - (int) ((button.height - (int) boundsWait.getHeight()) / 1.6) ); 
		
		
		// Events
		Dimension event = new Dimension(offsetTable.width - 10, 40);
		
		EventCard[] events = Controller.instance.getEvents();
		int eventsHeight = 40 * events.length + 10 * (events.length-1);
		for( int i = 0; i < events.length; i++ ) {
			g2d.drawRect(5, height / 2 - eventsHeight / 2 + i*40 + i * 10 , event.width, event.height);
		
			if( eventPreview == i ) {
				events[i].drawCard(g2d, offsetTable.width + 5, height / 2 - eventsHeight / 2 + i*40 + i * 10 - event.height / 2 - dummy.getHeight(table.width / 5) / 2, table.width / 5, dummy.getHeight(table.width / 5));
			}
			g2d.setColor( new Color(180, 180, 180) );
		}
		//g2d.drawRect(5, height / 2 - 20, event.width, event.height);
		
		// Dimension of the hand and a card on the hand
		Dimension hand = new Dimension( 7*table.width/10, 8*(height - (offsetTable.height + table.height))/10 );
		Dimension card = new Dimension( 2*(hand.height - 20)/3, hand.height - 20 );
		
		// Player 1


		
		// Display hand and deck
		g2d.drawRect( offsetTable.width + 1*table.width/10, offsetTable.height + table.height + hand.height / 4, hand.width, hand.height );
		g2d.drawRect( offsetTable.width + 2*table.width/10 + hand.width, offsetTable.height + table.height + hand.height / 4, 1*table.width/10, hand.height );
		
		Player p1 = Controller.instance.isMaster() ? Controller.instance.getPlayer(0) : Controller.instance.getPlayer(1);
		if( p1 != null ) {
				
			// Player Icon
			g2d.drawOval(offsetTable.width + (table.width / 10 - 3*offsetTable.height / 4) / 2, offsetTable.height + table.height + hand.height / 4, 3*offsetTable.height / 4, 3*offsetTable.height / 4 );
			
			// Player Name
			Font fPlayer = new Font(Font.SANS_SERIF, Font.BOLD, hand.height / 6);
			g2d.setFont( fPlayer );
			
			g2d.setColor(Color.BLACK);
			Rectangle2D boundsName = fPlayer.getStringBounds(p1.getName(), g2d.getFontRenderContext());
			g2d.drawString(p1.getName(), offsetTable.width + (int) (table.width / 10 - boundsName.getWidth()) / 2, offsetTable.height + table.height + (int) boundsName.getHeight() );
			
			// Abi-Points
			Font fAbiPoints = new Font(Font.SANS_SERIF, Font.BOLD, hand.height / 4);
			g2d.setFont( fAbiPoints );
			
			Rectangle2D boundsAbiPoints = fAbiPoints.getStringBounds( Integer.toString(p1.getAbiPoints()), g2d.getFontRenderContext());
			g2d.setColor( Color.GREEN );
			g2d.drawString( Integer.toString(p1.getAbiPoints()), offsetTable.width + (3*offsetTable.height / 4 - (int) (boundsAbiPoints.getWidth()*0.4)) / 2, offsetTable.height + table.height + hand.height / 4 + 3*offsetTable.height / 4 /* + (int) boundsAbiPoints.getHeight() / 3  */ );
			
			
			// Grade-Points
			Font fGradePoints = new Font(Font.SANS_SERIF, Font.PLAIN, hand.height / 8 );
			g2d.setFont( fGradePoints );
			
			g2d.setColor( new Color(67, 167, 255) );
			g2d.drawRect(offsetTable.width + 1*table.width/10, offsetTable.height + table.height + hand.height / 16, 7*table.width / 10, hand.height / 8);
			for( int i = 15; i > 0; i-- ) {
				Rectangle2D boundsNumber = fGradePoints.getStringBounds(Integer.toString(i), g2d.getFontRenderContext()); 
				
				g2d.setColor( new Color(67, 167, 255) );
				if( p1.getGradePoints() >= i ) {
					g2d.fillRect(offsetTable.width + 1*table.width/10, offsetTable.height + table.height + hand.height / 16, i*7*table.width / 150, hand.height / 8);
					g2d.setColor( Color.WHITE );
					g2d.drawString(Integer.toString(i), offsetTable.width + 1*table.width/10 + i*7*table.width / 150 - (int) boundsNumber.getWidth() - (int) (7*table.width / 150 - boundsNumber.getWidth()) / 2, offsetTable.height + table.height + hand.height / 16 + 2*hand.height / 17);
				} else {
					g2d.drawRect(offsetTable.width + 1*table.width/10, offsetTable.height + table.height + hand.height / 16, i*7*table.width / 150, hand.height / 8);
					g2d.drawString(Integer.toString(i), offsetTable.width + 1*table.width/10 + i*7*table.width / 150 - (int) boundsNumber.getWidth() - (int) (7*table.width / 150 - boundsNumber.getWidth()) / 2, offsetTable.height + table.height + hand.height / 16 + 2*hand.height / 17);
				}
			}
			
			
			g2d.setFont( f );
			
			
			// Display cards on hand
			Card[] h = p1.getHand();
			int spaceCard = card.width + 10;
			if( 10 + (h.length-1) * spaceCard + card.width > hand.width - 20 ) {
				spaceCard = (hand.width - 30 - card.width) / (h.length-1);
			}
			
			for( int i = 0; i < h.length; i++ ) {
				h[i].drawCard(g2d, offsetTable.width + 1*table.width/10 + 10 + i*spaceCard, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height );
			}
			
			if( cardPreview > -1 ) {
				h[cardPreview].drawCard(g2d, offsetTable.width + 1*table.width/10 + 10 + cardPreview*spaceCard + card.width / 2 - hand.width / 6, height - hand.height - 10 - h[cardPreview].getHeight(hand.width / 3), hand.width / 3, h[cardPreview].getHeight(hand.width / 3) );
			}
			
			// Draw deck
			switch( p1.getDeck().length ) {
				case 0:
					break;
				case 1:
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					break;
				case 2:
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 0, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					break;
				case 3:
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 0, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 5, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					break;
				default:
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 0, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 5, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 10, offsetTable.height + table.height + hand.height / 4 + 10, card.width, card.height);
					break;
			}
		}
		
		
		// Player 2
		g2d.setColor( new Color(180, 180, 180) );
		
		// Display hand and deck
		g2d.drawRect( offsetTable.width + 1*table.width/10, 0, hand.width, hand.height );
		g2d.drawRect( offsetTable.width + 2*table.width/10 + hand.width, 0, 1*table.width/10, hand.height );
		
		Player p2 = Controller.instance.isMaster() ? Controller.instance.getPlayer(1) : Controller.instance.getPlayer(0);
		if( p2 != null ) {
		
			// Player Icon
			if( playerHover ) g2d.setColor( Color.RED );
			g2d.drawOval(offsetTable.width + (table.width / 10 - 3*offsetTable.height / 4) / 2, offsetTable.height - hand.height / 4 - 3*offsetTable.height / 4, 3*offsetTable.height / 4, 3*offsetTable.height / 4 );
						
			// Player Name
			Font fPlayer = new Font(Font.SANS_SERIF, Font.BOLD, hand.height / 6);
			g2d.setFont( fPlayer );
					
			Rectangle2D boundsName = fPlayer.getStringBounds(p2.getName(), g2d.getFontRenderContext());
			g2d.setColor( Color.BLACK );
			g2d.drawString(p2.getName(), offsetTable.width + (int) (table.width / 10 - boundsName.getWidth()) / 2, offsetTable.height - 10);
						
			// Abi-Points
			Font fAbiPoints = new Font(Font.SANS_SERIF, Font.BOLD, hand.height / 4);
			g2d.setFont( fAbiPoints );
						
			Rectangle2D boundsAbiPoints = fAbiPoints.getStringBounds( Integer.toString(p2.getAbiPoints()), g2d.getFontRenderContext());
			g2d.setColor( Color.GREEN );
			g2d.drawString( Integer.toString(p2.getAbiPoints()), offsetTable.width + (3*offsetTable.height / 4 - (int) (boundsAbiPoints.getWidth() * 0.4)) / 2, offsetTable.height - hand.height / 4 - 3*offsetTable.height / 4 + (int) boundsAbiPoints.getHeight() / 3 );
				
				
			// Grade-Points
			Font fGradePoints = new Font(Font.SANS_SERIF, Font.PLAIN, hand.height / 8 );
			g2d.setFont( fGradePoints );
						
			g2d.setColor( new Color(67, 167, 255) );
			g2d.drawRect(offsetTable.width + 1*table.width/10, offsetTable.height - hand.height / 16 - hand.height / 8, 7*table.width / 10, hand.height / 8);
			for( int i = 15; i > 0; i-- ) {
				Rectangle2D boundsNumber = fGradePoints.getStringBounds(Integer.toString(i), g2d.getFontRenderContext()); 
					
				g2d.setColor( new Color(67, 167, 255) );
				if( p2.getGradePoints() >= i ) {
					g2d.fillRect(offsetTable.width + 1*table.width/10, offsetTable.height - hand.height / 16 - hand.height / 8, i*7*table.width / 150, hand.height / 8);
					g2d.setColor( Color.WHITE );
					g2d.drawString(Integer.toString(i), offsetTable.width + 1*table.width/10 + i*7*table.width / 150 - (int) boundsNumber.getWidth() - (int) (7*table.width / 150 - boundsNumber.getWidth()) / 2, offsetTable.height - 3*hand.height / 16 + 2*hand.height / 17);
				} else {
					g2d.drawRect(offsetTable.width + 1*table.width/10, offsetTable.height - hand.height / 16 - hand.height / 8, i*7*table.width / 150, hand.height / 8);
					g2d.drawString(Integer.toString(i), offsetTable.width + 1*table.width/10 + i*7*table.width / 150 - (int) boundsNumber.getWidth() - (int) (7*table.width / 150 - boundsNumber.getWidth()) / 2, offsetTable.height - 3*hand.height / 16 + 2*hand.height / 17);
				}
			}
			
			// Display cards on hand
			Card[] h = p2.getHand();
			int spaceCard = card.width + 10;
			if( 10 + (h.length-1) * spaceCard + card.width > hand.width - 20 ) {
				spaceCard = (hand.width - 30 - card.width) / (h.length-1);
			}
					
			for( int i = 0; i < h.length; i++ ) {
				if( p2.getView() ) h[i].drawCard(g2d, offsetTable.width + 1*table.width/10 + 10 + i*spaceCard, 10, card.width, card.height );
				else h[i].drawBackground(g2d, offsetTable.width + 1*table.width/10 + 10 + i*spaceCard, 10, card.width, card.height );
			}
			
			// Draw deck
			switch( p2.getDeck().length ) {
				case 0:
					break;
				case 1:
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5, 10, card.width, card.height);
					break;
				case 2:
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5, 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 0, 10, card.width, card.height);
					break;
				case 3:
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5, 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 0, 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 5, 10, card.width, card.height);
					break;
				default:
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 + 5, 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 0, 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 5, 10, card.width, card.height);
					dummy.drawBackground(g2d, offsetTable.width + 2*table.width/10 + hand.width + (1*table.width/10 - card.width) / 2 - 10, 10, card.width, card.height);
					break;
			}
		}
		
		// Draw all Animation
		for( Animation c : animations.toArray(new Animation[0]) ) {
			c.draw(g2d, System.currentTimeMillis());
			if( c.isFinished() ) animations.remove(c);
		}
		
		g2d.setFont( oldFont );
	}
	
	
	public void mouse(MouseEvent e) {
		
		Dimension offsetButton = new Dimension( offsetTable.width + table.width + 10, height / 2 - 25 );
		Dimension button = new Dimension( width - (int) offsetButton.width - 10, 50 );
		
		if(
					e.getX() > offsetButton.getWidth()
				&&	e.getX() < offsetButton.getWidth() + button.getWidth()
				&&	e.getY() > offsetButton.getHeight()
				&&	e.getY() < offsetButton.getHeight() + button.getHeight()
		) {
			if( e.getButton() == MouseEvent.BUTTON1 ) evtman.buttonClicked();
		}
			
		if(
				e.getX() > offsetTable.width 
			&&	e.getX() < offsetTable.width + table.width
			&&	e.getY() > offsetTable.height
			&&	e.getY() < offsetTable.height + table.height
		) {
			boolean place = false;
		
			// Upper
			for( int i = 0; i < 5; i++ ) {
				
				Dimension offsetCard = new Dimension(
						offsetTable.width + (i+1) * (int) (0.33333333333333333333333333333333333333333333333*table.width/8) + i * (int) (1.2*table.width/8), 
						offsetTable.height + (int) (0.4*table.height/10)
				);
				
				Dimension card = new Dimension(1*table.width/8, (int) (3.5*table.height/10));
				
				if( 
						e.getX() > offsetCard.width 
					&& 	e.getX() < offsetCard.width + card.width 
					&&	e.getY() > offsetCard.height
					&& 	e.getY() < offsetCard.height + card.height 
				) {
					if( e.getButton() == MouseEvent.BUTTON1 ) evtman.placeClicked( i );
					cardPos[0][i] = true;
					place = true;
				}
				else cardPos[0][i] = false;
			}
			
			// Lower
			for( int i = 0; i < 5; i++ ) {
						
				Dimension offsetCard = new Dimension(
						offsetTable.width + (i+1) * (int) (0.33333333333333333333333333333333333333333333333*table.width/8) + i * (int) (1.2*table.width/8), 
						offsetTable.height + (int) (6.1*table.height/10)
				);
						
				Dimension card = new Dimension(1*table.width/8, (int) (3.5*table.height/10));
					
				if( 
						e.getX() > offsetCard.width 
					&& 	e.getX() < offsetCard.width + card.width 
					&&	e.getY() > offsetCard.height
					&& 	e.getY() < offsetCard.height + card.height 
				) {
					if( e.getButton() == MouseEvent.BUTTON1 ) evtman.placeClicked( i + 5 );
					cardPos[1][i] = true;
					place = true;
				}
				else cardPos[1][i] = false;
			}
			
			
			if( !place && e.getButton() == MouseEvent.BUTTON1 ) evtman.tableClicked();
		}
		
		// Cards
		Dimension hand = new Dimension( 7*table.width/10, 8*(height - (offsetTable.height + table.height))/10 );
		Dimension card = new Dimension( 2*(hand.height - 20)/3, hand.height - 20 );
		
		cardPreview = -1;
		Player p1 = Controller.instance.isMaster() ? Controller.instance.getPlayer(0) : Controller.instance.getPlayer(1);
		if( p1 != null ) {
			Card[] h = p1.getHand();
			for( int i = 0; i < h.length; i++ ) {
				int spaceCard = card.width + 10;
				if( 10 + (h.length-1) * spaceCard + card.width > hand.width - 20 ) {
					spaceCard = (hand.width - 30 - card.width) / (h.length-1);
				}
				int realLength = spaceCard < card.width ? spaceCard : card.width;
				
				int begin = offsetTable.width + 1*table.width/10 + 10 + spaceCard*i;
				int end = begin + realLength;
				if( i == h.length - 1 ) end = begin + card.width;
				
				
				if( 
						e.getX() > begin
					&&	e.getX() < end
					&&	e.getY() > offsetTable.height + table.height + hand.height / 4 + 10
					&& 	e.getY() < offsetTable.height + table.height + hand.height / 4 + hand.height - 10
				) {
					cardPreview = i;
					if( e.getButton() == MouseEvent.BUTTON1 ) evtman.cardClicked( i );
				}
				
			}
		}
		
		
		// Events
		Dimension event = new Dimension(offsetTable.width - 10, 40);
		
		
		EventCard[] events = Controller.instance.getEvents();
		int eventsHeight = 40 * events.length + 10 * (events.length-1);
		eventPreview = -1;
		for( int i = 0; i < events.length; i++ ) {
			if( 
					e.getX() > 5
				&&	e.getX() < 5 + event.width
				&&	e.getY() > height / 2 - eventsHeight / 2 + i*40 + i * 10 
				&& 	e.getY() < height / 2 - eventsHeight / 2 + i*40 + i * 10 + event.height
				
			) eventPreview = i;
		}
		
		
		// Player 
		Point pMid = new Point(offsetTable.width + (table.width / 20 - table.width / 22) + table.width / 22, offsetTable.height - hand.height / 4 - table.width / 11 + table.width / 22);
		Dimension diff = new Dimension((int) (e.getX() - pMid.getX()), (int) (e.getY() - pMid.getY()));
		
		playerHover = false;
		if( Math.sqrt(diff.width*diff.width + diff.height*diff.height) < table.width / 22 ) {
			playerHover = true;
			if( e.getButton() == MouseEvent.BUTTON1 ) evtman.playerClicked();
		}
	}

}
