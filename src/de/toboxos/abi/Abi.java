package de.toboxos.abi;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import de.toboxos.abi.animation.BattleAnimation;
import de.toboxos.abi.animation.CardDrawAnimation;
import de.toboxos.abi.animation.CardPlayedAnimation;
import de.toboxos.abi.animation.EventPlayedAnimation;
import de.toboxos.abi.animation.PlayerAttackAnimation;
import de.toboxos.abi.animation.StatusChangeAnimation;
import de.toboxos.abi.cards.Card;
import de.toboxos.abi.cards.CardGenerator;
import de.toboxos.abi.cards.EventCard;
import de.toboxos.abi.cards.ImageLoader;
import de.toboxos.abi.cards.StudentCard;
import de.toboxos.abi.events.AddEventCardEvent;
import de.toboxos.abi.events.CardBattleEvent;
import de.toboxos.abi.events.CardPlayedEvent;
import de.toboxos.abi.events.EventManager;
import de.toboxos.abi.events.InterfaceListener;
import de.toboxos.abi.events.PlayerAttackEvent;
import de.toboxos.abi.events.StatusChangeEvent;
import de.toboxos.abi.network.AddEventCardPacket;
import de.toboxos.abi.network.CardBattlePacket;
import de.toboxos.abi.network.CardDeadPacket;
import de.toboxos.abi.network.CardDrawPacket;
import de.toboxos.abi.network.CardPlayedPacket;
import de.toboxos.abi.network.HighlightPacket;
import de.toboxos.abi.network.LoginPacket;
import de.toboxos.abi.network.PlayerAttackPacket;
import de.toboxos.abi.network.RoundEndPacket;
import de.toboxos.abi.network.RoundStartPacket;
import de.toboxos.abi.network.SetSlotPacket;
import de.toboxos.abi.network.StatusChangePacket;


public class Abi implements EventManager {

	private final static int PORT = 5544;
	public static Random random;
	public static EventManager manager;
	public static Class<?> classPath;
	public static final String[] allCards = {"Georg", "Axel", "General", "Arwen", "Cooper", "Flamur", "Daniel", "Emilia", "Felix", "Manu", "Felix G.", "Dubowy", "Steffen", "Franzi", "Marike", "Nils", "Sandro", "Anwalt", "Schlachter", "Zelenka", "Allgeier", "Taraschewski", "Forstner", "Schellmann", "Weisswurst", "Wiech", "Tornau", "Kreuzer", "Mischo"};
	
	private ChooseDeck d;
	public GUI gui;
	private Controller controller;
	
	private int role;
	private Server server = null;
	private Client client = null;
	
	private String[] myDeck = new String[30];
	
	// Decks for presentation on abiball
	//private String[] deck1 = new String[] { "General", "Axel", "Arwen", "Franzi", "Forstner", 	"Felix G", 	"Dubowy", 	"Cooper", "Allgeier", "Arwen", "Nils", "Wiech" ,"Manu", "Flamur", "Felix G.", "Steffen", "Felix", "Zelenka", "Flamur", "Dubowy", "Georg", "Schlachter", "Marike", "Marike", "Marike", "Marike", "Marike", "Marike", "Marike", "Marike"};
	//private String[] deck2 = new String[] { "Georg", "Axel", "Taraschewski", "Schellmann", "Forstner", "Flamur", "Daniel", "Emilia", "Felix", "Felix G.", "Daniel", "Flamur" ,"Steffen", "Dubowy", "Tornau", "Sandro", "Kreuzer", "Marike", "Mischo", "General", "Zelenka", "Georg", "Marike", "Marike", "Marike", "Marike", "Marike", "Marike", "Marike", "Marike"};
	
	public Abi() {
		random = new Random();
		classPath = this.getClass();
		
		ImageLoader.load();
		
		d = new ChooseDeck();
		d.setVisible(true);
		d.setCallback(new Runnable() {
			@Override
			public void run() {
				List<String> choosed = d.getDeck();
				
				// Add all cards to deck. Fill up 30 with null if needed
				for( int i = 0; i < 30; i++ ) {
					if( choosed.size() > i ) 
						myDeck[i] = choosed.get(i);
					else
						myDeck[i] = null;
				}
				
				// Shuffleing the deck
				for( int i = myDeck.length - 1; i > 0; i--) {
					int j = random.nextInt(i);
					String t = myDeck[i];
					myDeck[i] = myDeck[j];
					myDeck[j] = t;
				}
				
				initGame();
			}
		});
	}
	
	public void initGame() {
		
		// Holds game information
		controller = new Controller();
		d.dispose();
		
		// Start procedure
		String name = JOptionPane.showInputDialog( null, "Dein Name:", "...", JOptionPane.QUESTION_MESSAGE );
		if( name.isEmpty() ) name = "Player";
		
		Object[] startOptions = { "Server", "Client" };
		role = JOptionPane.showOptionDialog( null, "Modus wählen", "...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, startOptions, startOptions[1] );
		manager = this;
		
		String host = "";
		if( role == 1 ) host = JOptionPane.showInputDialog( null, "Server-IP:", "...", JOptionPane.INFORMATION_MESSAGE );
		
		
		gui = new GUI( new InterfaceListener() {
			int slotSelected = -1;
			int cardChose = -1;
			
			private void unselectAll() {
				for(int i = 0; i < 10; i++) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c != null ) c.unselect();
				}
				for( Card c : Controller.instance.getPlayer(role).getHand() ) {
					c.unselect();
				}
			}
			
			// On server side slot numbering is:
			// Top-Row:		Slot 0-4
			// Bottom-Row:	Slot 5-9
			
			// On client side slot numbering is reversed:
			// Top-Row: 	Slot 5-9
			// Bottom-Row: 	Slot 0-4
			private int realNum(int num) {
				if( role == 0 ) return num;
				if( role == 1 && num < 5 ) return num+5;
				if( role == 1 && num > 4 ) return num-5;
				return 0;
			}
			
			@Override
			public void placeClicked(int num) {
				Logger.logMessage("GUI:placeClicked(" + num + ")");
				Logger.logMessage("Controller.Master: " + Controller.instance.isMaster() + " Controller.Turn: " + Controller.instance.getTurn() + " Controller.Waiting: " + Controller.instance.isWaiting());
				
				// Check if its player turn
				if( 
						Controller.instance.isMaster() && Controller.instance.getTurn() == 2
					||	!Controller.instance.isMaster() && Controller.instance.getTurn() == 1
					||	Controller.instance.isWaiting()
				) return;
				
				Logger.logMessage("SelectedSlot: " + slotSelected + " cardChose: " + cardChose);
				
				// If clicked the selected slot, deselect
				if( slotSelected == num ) {
					Controller.instance.getSlot(realNum(num)).unselect();
					slotSelected = -1;
				}
				
				// If no slot is selected and no card is selected, select slot
				else if( slotSelected == -1 && cardChose == -1 && num > 4 ) {
					// Check if slot is empty
					if( Controller.instance.getSlot(realNum(num)) == null ) return;
					
					// Check if card can attack
					if( !Controller.instance.getSlot(realNum(num)).canAttack() ) return;
					
					// Select slot
					slotSelected = num;
					Controller.instance.getSlot(realNum(num)).select();
				}
				
				// If a slot was selected and top row clicked
				else if( slotSelected > -1 && num < 5 ) {
					// Check if slot is empty
					if( Controller.instance.getSlot(realNum(num)) == null ) return;
					
					Controller.instance.getSlot(realNum(slotSelected)).unselect();
					
					// Server: Activate Battle
					// Client: Send BattlePacket to Server
					switch( role ) {
						case 0:
							CardBattleEvent e = new CardBattleEvent(
									controller.getPlayer(0),
									controller.getSlot(5 + slotSelected % 5),
									controller.getSlot( num ),
									5 + slotSelected % 5,
									num
							);
							manager.battle(e);
							break;
						case 1:
							client.send( new CardBattlePacket(1, slotSelected - 5, realNum(num)) );
							break;
					}
					
					slotSelected = -1;
				}
				
				// A card was chose
				else if( cardChose > -1 && num > 4 ) {
					
					// Event Card
					if( Controller.instance.getPlayer(role).getCardFromHand(cardChose) instanceof EventCard ) {
						cardChose = -1;
					}
					
					// StudentCard and bottom row clicked
					else if( num > 4) {
						// Slot is empty
						if( Controller.instance.getSlot(realNum(num)) == null ) {
							// Server: Play Card
							// Client: Send PlayCardPacket to Server
							switch( role ) {
								case 0:
									// Check if Card is a StudentCard
									
									CardPlayedEvent e = new CardPlayedEvent(
											controller.getPlayer(0).getCardFromHand(cardChose), 
											controller.getPlayer(0), 
											num,
											cardChose
									);
									manager.cardPlayed(e);
									break;
								case 1:
									// Check if Card is a StudentCard
									client.send( new CardPlayedPacket(1, realNum(num), cardChose) );
									break;
							}
						} else {
							cardChose = -1;
							slotSelected = num;
							unselectAll();
							Controller.instance.getSlot(realNum(num)).select();
						}
						cardChose = -1;
					}

				}
					
				
				
				gui.repaint();
			}
			
			@Override
			public void cardClicked(int num) {
				Logger.logMessage("GUI:cardClicked(" + num + ")");
				Logger.logMessage("SelectedSlot: " + slotSelected + " CardChosen: " + cardChose);
				
				if( 
						Controller.instance.isMaster() && Controller.instance.getTurn() == 2
					||	!Controller.instance.isMaster() && Controller.instance.getTurn() == 1
					||	Controller.instance.isWaiting()
				) return;
				
				
				if( cardChose == num ) {
					cardChose = -1;
					unselectAll();
				} else {
					unselectAll();
					Controller.instance.getPlayer(role).getHand()[num].select();
					slotSelected = -1;
					cardChose = num;
				}
				
				gui.repaint();
			}
			
			@Override
			public void buttonClicked() {
				Logger.logMessage("Gui:buttonClicked()");
				
				if( 
					(
						Controller.instance.isMaster() && Controller.instance.getTurn() == 1
					||	!Controller.instance.isMaster() && Controller.instance.getTurn() == 2
					) && !Controller.instance.isWaiting() 
				) {
					unselectAll();
					if( role == 0 ) 
						manager.roundEnd();
					else
						client.send( new RoundEndPacket() );
				}
				
			}
			
			@Override
			public void tableClicked() {
				Logger.logMessage("Gui:tableClicked()");
				Logger.logMessage("SlotSelected: " + slotSelected + " CardChose: " + cardChose);
				
				if( 
						Controller.instance.isMaster() && Controller.instance.getTurn() == 2
					||	!Controller.instance.isMaster() && Controller.instance.getTurn() == 1
					||	Controller.instance.isWaiting()
				) return;
				
				if( cardChose > -1 ) {
					 switch(role) {
					 	case 0:
					 		// Check if Card is an EventCard
					 		if( Controller.instance.getPlayer(0).getCardFromHand(cardChose) instanceof StudentCard ) return;
					 		
					 		
					 		// Call CardPlayedEvent
					 		CardPlayedEvent e = new CardPlayedEvent(
					 				Controller.instance.getPlayer(0).getCardFromHand(cardChose), 
					 				Controller.instance.getPlayer(0), 
					 				-1, 
					 				cardChose
					 		);
					 		cardPlayed(e);
					 		cardChose = -1;
					 		
					 		break;
					 	case 1:
					 		// Check if Card is an EventCard
					 		if( Controller.instance.getPlayer(1).getCardFromHand(cardChose) instanceof StudentCard ) return;
					 		
					 		// Send Action to Server
					 		client.send( new CardPlayedPacket(1, -1, cardChose) );
					 		cardChose = -1;
					 		break;
					 }
				}
			}
			
			@Override
			public void playerClicked() {
				System.out.println("GUI:playerClicked()");
				
				if( 
						Controller.instance.isMaster() && Controller.instance.getTurn() == 2
					||	!Controller.instance.isMaster() && Controller.instance.getTurn() == 1
					||	Controller.instance.isWaiting()
				) return;
				if( slotSelected == -1 ) return;
				
				StudentCard c = Controller.instance.getSlot(realNum(slotSelected));
				if( c == null ) return;
				
				unselectAll();
				switch( role ) {
					case 0:
						manager.attackPlayer( new PlayerAttackEvent(Controller.instance.getPlayer(1), c, realNum(slotSelected)));
						slotSelected = -1;
						break;
					case 1:
						client.send( new PlayerAttackPacket(role, realNum(slotSelected)) );
						slotSelected = -1;
						break;
				}
			}
		});
		
		gui.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if( server != null ) server.shutdown();
				System.exit(0);
			}
		});
		
		//ImageLoader.load();
		
		if( role == 0 ) {
			Logger.logMessage("Mode: SERVER");
			controller.setPlayer(0, new Player(name, 0));
			controller.setMaster( true );
			server = new Server( PORT, this );
			server.start();
			
			
			
			controller.getPlayer(0).setDeck( CardGenerator.generateDeck(myDeck) );
			//controller.getPlayer(0).setDeck( CardGenerator.generateDeck(deck1) );
		}
		
		if( role == 1 ) {
			Logger.logMessage("Mode: CLIENT");
			controller.setPlayer(1, new Player(name, 1));
			controller.setMaster( false );
			client = new Client(this);
			
			int reconnect = 0;
			while( reconnect == 0 ) {
				if( client.connect(host, PORT, name) ) {
					reconnect = 1;
					client.send( new LoginPacket(name, myDeck) );
				} else {
					reconnect = JOptionPane.showConfirmDialog( null, "Failed to connect. Try Reconnect?", "Network Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE );
					if( reconnect == 1 ) {
						gui.close();
						return;
					}
				}
			}
		}
		
		
	}

	public void playerLogin(LoginPacket p) {
		Logger.logMessage("Abi:playerLogin()");
		
		if( controller.getPlayer(1) == null ) {
			controller.setPlayer(1, new Player(p.getName(), 1));
			controller.getPlayer(1).setDeck( CardGenerator.generateDeck(p.getCards()) );
			//controller.getPlayer(1).setDeck( CardGenerator.generateDeck(deck2) );
			
			server.send( controller );
			
			for( int i = 0; i < 3; i++ ) {
				drawCard(0);
				drawCard(1);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			manager.roundStart();
		} else {
			server.send(controller);
		}
		
		
		
	
		
	}
	
	public void setController(Controller c) {
		Logger.logMessage("Abi:setController()");
		
		this.controller = c;
		Controller.instance = c;
		
		gui.repaint();
	}
	
	@Override
	public void cardPlayed(CardPlayedEvent e) {
		Logger.logObj("Abi:cardPlayed(): ", e.getCard());
		
		// Check if player has enough grade points
		if( e.getPlayer().getGradePoints() < e.getCard().getCost() ) return;
		
		// Send action to client
		server.send(new CardPlayedPacket(e.getPlayer().getNum(), e.getSlot(), e.getPos()));
		
		// Unhighlight card, remove grade points and remove card from hand
		e.getCard().unselect();
		e.getPlayer().setGradePoints(e.getPlayer().getGradePoints() - e.getCard().getCost());
		e.getPlayer().removeFromHand( e.getCard() );
		
		// Check if card is student or event card
		if( e.getCard() instanceof StudentCard ) {
			
			StudentCard sc = (StudentCard) e.getCard();
			
			for( EventCard c : Controller.instance.getEvents() ) {
				c.studentPlayed(sc, e.getSlot());
			}
			
			// Create animation
			CardPlayedAnimation a = new CardPlayedAnimation(e.getPlayer().getNum(), sc, e.getSlot(), e.getPos());
			gui.stageGame.addAnimation(a);
			Controller.instance.setSlot(e.getSlot(), sc);
			sc.setDraw(false);
			
			if( sc.canAttack() ) {
				sc.setOriginalColor(Color.GREEN);
				server.send(new HighlightPacket(e.getPlayer().getNum(), true, e.getSlot(), Color.GREEN));
			} else {
				sc.setOriginalColor(Color.RED);
				server.send(new HighlightPacket(e.getPlayer().getNum(), true, e.getSlot(), Color.RED));
			}
		}
		
		if( e.getCard() instanceof EventCard ) {
			EventPlayedAnimation a = new EventPlayedAnimation( e.getPlayer().getNum(), (EventCard) e.getCard(), e.getPos() );
			gui.stageGame.addAnimation(a);
		}
		
		// First update data instances, then signal card
		e.getCard().played();
		
		
		
		gui.repaint();
	}
	
	@Override
	public void battle(CardBattleEvent e) {
		Logger.logObj("Abi:battle(): ", e);
		
		if( e.getInitiator() == null || e.getTarget() == null ) {
			e.setCanceled(true);
			return;
		}
		
		if( !e.getInitiator().canAttack() ) {
			e.setCanceled( true );
			return;
		}
		
		// Check if a protector is on field
		if( !e.getTarget().doProtect() ) {
			if( e.getSlot2() < 5 ) {
				for( int i = 0; i < 5; i++ ) {
					if( Controller.instance.getSlot(i) != null && Controller.instance.getSlot(i).doProtect() ) {
						e.setCanceled(true);
						return;
					}
				}
			} else {
				for( int i = 5; i < 10; i++ ) {
					if( Controller.instance.getSlot(i) != null && Controller.instance.getSlot(i).doProtect() ) {
						e.setCanceled(true);
						return;
					}
				}
			}
		}
		
		// Initiator get damage of target, target get damage of initiator
		int dmgInit = e.getInitiator().setDamage( e.getTarget().getPower(e.getInitiator()), e.getTarget() );
		int dmgTarg = e.getTarget().setDamage( e.getInitiator().getPower(e.getTarget()), e.getInitiator() );
		
		// Attacked signal to attacker
		e.getInitiator().attacked();
						
		// Change Highlight if can not attack anymore
		if( !e.getInitiator().canAttack() ) {
			e.getInitiator().setOriginalColor(Color.RED);
			server.send(new HighlightPacket(e.getPlayer().getNum(), true, e.getSlot1(), Color.RED));
		}
		
		// Send action to client
		server.send(new CardBattlePacket(e.getPlayer().getNum(), e.getSlot1(), e.getSlot2()));
				
		// Create animation
		BattleAnimation a = new BattleAnimation(e.getPlayer().getNum(), e.getInitiator(), e.getSlot1(), e.getSlot2());
		gui.stageGame.addAnimation(a);
		
		changeStatus(new StatusChangeEvent(e.getInitiator(), e.getSlot1(), e.getInitiator().getEnergy() - dmgInit, StatusChangeEvent.STATUS_ENERGY, Color.BLACK));
		changeStatus(new StatusChangeEvent(e.getTarget(), e.getSlot2(), e.getTarget().getEnergy() - dmgTarg, StatusChangeEvent.STATUS_ENERGY, Color.BLACK));
		
		gui.repaint();
	}
	
	@Override
	public void attackPlayer(PlayerAttackEvent e) {	
		Logger.logObj("Abi:attackPlayer():", e);
		
		if( !e.getCard().canAttack() ) {
			e.setCanceled( true );
			return;
		}
		
		switch( Controller.instance.getTurn() ) {
			case 1:
				for( int i = 0; i < 5; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					if( c.doProtect() ) {
						e.setCanceled(true);
						return;
					}
				}
				break;
				
			case 2:
				for( int i = 5; i < 10; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					
					if( c.doProtect() ) {
						e.setCanceled(true);
						return;
					}
				}
				break;
		}
		
		// Send action to client
		server.send(new PlayerAttackPacket(e.getPlayer().getNum(), e.getSlot()));
		
		PlayerAttackAnimation a = new PlayerAttackAnimation(e.getPlayer().getNum(), e.getCard(), e.getSlot());
		gui.stageGame.addAnimation(a);
		
		// Set damage to player and send attacked signal to card
		e.getPlayer().setDamage(e.getCard().getPower(null));
		e.getCard().attacked();
		
		if( !e.getCard().canAttack() ) {
			e.getCard().setOriginalColor(Color.RED);
			server.send(new HighlightPacket(e.getPlayer().getNum(), true, e.getSlot(), Color.RED));
		}
		
		if( e.getPlayer().getAbiPoints() <= 0 ) {
			
		}
		
		gui.repaint();
	}
	
	@Override
	public void roundEnd(){ 
		Logger.logMessage("Abi:roundEnd()");
		
		Controller.instance.setWaiting( true );
		
		for( EventCard e : Controller.instance.getEvents() ) {
			if( Controller.instance.getTurn() == 2 ) e.roundEnd();
			if( e.isFinished() ) Controller.instance.removeEvent(e);
		}
		
		switch( Controller.instance.getTurn() ) {
			case 1:
				// Call next turn for each card of player being on the table
				for( int i = 5; i < 10; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					c.setOriginalColor(null);
					server.send(new HighlightPacket(0, true, i, null));
					c.nextTurn();
				}
				break;
				
			case 2:
				// Call next turn for each card of player being on the table
				for( int i = 0; i < 5; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					c.setOriginalColor(null);
					server.send(new HighlightPacket(1, true, i, null));
					c.nextTurn();
				}
				break;
		}
			
		roundStart();
	}
	
	@Override
	public void roundStart() {
		Logger.logMessage("Abi:roundStart()");
		
		Controller.instance.nextTurn();
		server.send( Controller.instance );
		
		// yourRound() Event to all cards of player
		switch( Controller.instance.getTurn() ) {
			case 1:
				for( int i = 5; i < 10; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					c.yourTurn();
					
					Logger.logMessage("(" + i + ") " + c.getName() + ": " + c.canAttack());
					if( c.canAttack() ) {
						Logger.logMessage("Abi:roundStart() highlight GREEN"); 
						c.setOriginalColor(Color.GREEN);
						server.send(new HighlightPacket(0, true, i, Color.GREEN));
					} else {
						c.setOriginalColor(Color.RED);
						server.send(new HighlightPacket(0, true, i, Color.RED));
					}
				}
				break;
				
			case 2:
				for( int i = 0; i < 5; i++ ) {
					StudentCard c = Controller.instance.getSlot(i);
					if( c == null ) continue;
					c.yourTurn();
					
					Logger.logMessage("(" + i + ") " + c.getName() + ": " + c.canAttack());
					if( c.canAttack() ) {
						Logger.logMessage("Abi:roundStart() highlight GREEN"); 
						c.setOriginalColor(Color.GREEN);
						server.send(new HighlightPacket(1, true, i, Color.GREEN));
					} else {
						c.setOriginalColor(Color.RED);
						server.send(new HighlightPacket(1, true, i, Color.RED));
					}
					
				}
				break;
		}
		
		
		for( EventCard e : Controller.instance.getEvents() ) {
			if( Controller.instance.getTurn() == 1 ) e.roundStart();
			e.nextTurn();
			if( e.isFinished() ) Controller.instance.removeEvent(e);
		} 
		
		server.send( new RoundStartPacket(Controller.instance.getTurn() - 1, Controller.instance.getRound()) );
		Controller.instance.setWaiting( false );
		
		
		
		
		// CardDrawAnimation will add card to players hand
		
		switch( Controller.instance.getTurn() ) {
			case 1:
				// Player 2 only draw Card after Round 1
				if( Controller.instance.getRound() > 1) drawCard(0);
				
				// Set grade points for player. Max is 15
				Controller.instance.getPlayer(0).setGradePoints(Controller.instance.getRound() > 15 ? 15 : Controller.instance.getRound());
				break;
			case 2:
				drawCard(1);
				
				// Set grade points for player. Max is 15
				Controller.instance.getPlayer(1).setGradePoints(Controller.instance.getRound() > 15 ? 15 : Controller.instance.getRound());
				break;
		}
		
		gui.repaint();
	}
	
	@Override
	public void drawCard(int player) {
		Logger.logMessage("Abi:drawCard(" + player + ")");
		
		Card c = Controller.instance.getPlayer(player).drawCard();
		if( c == null ) return;
		
		CardDrawAnimation a = new CardDrawAnimation(player, c);
		gui.stageGame.addAnimation( a );
		server.send( new CardDrawPacket(player) ); 
		Logger.logMessage("Abi:drawCard() end");
	}
	
	@Override
	public void setSlot(int slot, String card) {
		StudentCard c = (StudentCard) CardGenerator.getCardFromName(card);
		c.played();
		
		Controller.instance.setSlot(slot, c);
		if( c.canAttack() ) {
			c.setOriginalColor(Color.GREEN);
		} else {
			c.setOriginalColor(Color.RED);
		}
		
		
		
		// Server
		if( role == 0 ) server.send(new SetSlotPacket(slot, card));
	}
	
	@Override
	public void addEventCard(AddEventCardEvent e) {
		controller.addEvent(e.getCard());
		
		// Server
		if( role == 0 ) server.send(new AddEventCardPacket(e.getCard()));
	}
	
	@Override
	public void updatePlayer(Player p) {
		Controller.instance.setPlayer(p.getNum(), p);
		
		// Server
		if( role == 0 ) server.send(p);
	}
	
	@Override
	public void changeStatus(StatusChangeEvent e) {
		Logger.logObj("Abi:changeStatus():" , e);
		if( e.getCard() == null ) {
			Logger.logMessage("Abi:changeStatus() Null Pointer exception!!!!"); 
			return;
		}
		
		if( role == 0 ) server.send( new StatusChangePacket(e.getSlot(), e.getStatus(), e.getValue(), e.getColor()) );
		gui.stageGame.addAnimation( new StatusChangeAnimation(e.getCard(), e.getSlot(), e.getColor()) );
		
		switch( e.getStatus() ) {
			case StatusChangeEvent.STATUS_COST:
				e.getCard().changeCost( e.getValue() );
				break;
				
			case StatusChangeEvent.STATUS_ENERGY:
				e.getCard().changeEnegery( e.getValue() );
				if( e.getValue() <= 0 ) {
					cardDead( e.getSlot() );
				}
				break;
				
			case StatusChangeEvent.STATUS_POWER:
				e.getCard().changePower( e.getValue() );
				break;
			
			case StatusChangeEvent.STATUS_NORMAL_POWER:
				e.getCard().changeNormalPoer( e.getValue() );
				break;
				
			default:
		}
	}
	
	@Override
	public void cardDead(int slot) {
		Logger.logMessage("Abi:cardDead(" + slot + ")");
		
		
		if( !Controller.instance.isMaster() ) return;
		
		for( EventCard e : Controller.instance.getEvents() ) {
			e.studentDead(Controller.instance.getSlot(slot), slot);
		}
		
		StudentCard c = Controller.instance.getSlot(slot);
		Controller.instance.setSlot(slot, null);
		server.send( new CardDeadPacket(slot) );
		c.dead();
	}
	
	
	
	public static void main(String[] args) {
		new Abi();
	}

	
}