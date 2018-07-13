package de.toboxos.abi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import de.toboxos.abi.animation.BattleAnimation;
import de.toboxos.abi.animation.CardDrawAnimation;
import de.toboxos.abi.animation.CardPlayedAnimation;
import de.toboxos.abi.animation.EventPlayedAnimation;
import de.toboxos.abi.animation.PlayerAttackAnimation;
import de.toboxos.abi.cards.Card;
import de.toboxos.abi.cards.EventCard;
import de.toboxos.abi.cards.StudentCard;
import de.toboxos.abi.events.AddEventCardEvent;
import de.toboxos.abi.events.StatusChangeEvent;
import de.toboxos.abi.network.AddEventCardPacket;
import de.toboxos.abi.network.CardBattlePacket;
import de.toboxos.abi.network.CardDeadPacket;
import de.toboxos.abi.network.CardDrawPacket;
import de.toboxos.abi.network.CardPlayedPacket;
import de.toboxos.abi.network.HighlightPacket;
import de.toboxos.abi.network.PlayerAttackPacket;
import de.toboxos.abi.network.RoundStartPacket;
import de.toboxos.abi.network.SetSlotPacket;
import de.toboxos.abi.network.StatusChangePacket;


public class Client {

	private Socket client;
	private Thread tIn = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	private Abi abi;
	
	public Client(Abi abi) {
		this.abi = abi;
	}
	
	public boolean connect(String host, int port, String name) {
		try {
			client = new Socket(host, port);
			
			out = new ObjectOutputStream( client.getOutputStream() );
			in = new ObjectInputStream( client.getInputStream() );
			
			tIn = new Thread(new Runnable() {
				@Override
				public void run() {
					while( true ) {
						try {
							Object obj = in.readObject();
							Logger.logObj("Client:receive(): ", obj);
							
							// Controller Update
							if( obj instanceof Controller ) {
								Controller c = (Controller) obj;
								c.setMaster( false );
								
								abi.setController( c );
							}
							
							// Round Start
							if( obj instanceof RoundStartPacket ) {
								RoundStartPacket p = (RoundStartPacket) obj;
								
								Controller.instance.getPlayer(p.getPlayer()).setGradePoints(Controller.instance.getRound() > 15 ? 15 : Controller.instance.getRound());
								
								
								Controller.instance.setWaiting( false );
							}
							
							//  Card Draw
							if( obj instanceof CardDrawPacket ) {
								CardDrawPacket p = (CardDrawPacket) obj;
								
								// CardDrawAnimation will add card to players hand
								
								Card c = Controller.instance.getPlayer(p.player).drawCard();
								Logger.logObj("Client:CardDrawPacket:Card: ", c);
								
								CardDrawAnimation a = new CardDrawAnimation(p.player, c);
								abi.gui.stageGame.addAnimation( a );
							}
							
							
							// Card Played
							if( obj instanceof CardPlayedPacket ) {
								CardPlayedPacket p = (CardPlayedPacket) obj;
								
								Player pl = Controller.instance.getPlayer(p.getPlayer());
								Card c = pl.getCardFromHand(p.getPos());

								c.unselect();
								c.played();
								pl.setGradePoints( pl.getGradePoints() - c.getCost() );
								pl.removeFromHand(c);
								
								if( c instanceof StudentCard ) {
									CardPlayedAnimation a = new CardPlayedAnimation(
											pl.getNum(), 
											(StudentCard) c, 
											p.getSlot(), 
											p.getPos()
									);
									abi.gui.stageGame.addAnimation(a);
									Controller.instance.setSlot(p.getSlot(), (StudentCard) c);
									((StudentCard) c).setDraw(false);
								}
								
								if( c instanceof EventCard ) {
									EventPlayedAnimation a = new EventPlayedAnimation(
											pl.getNum(), 
											(EventCard) c, 
											p.getPos()
									);
									abi.gui.stageGame.addAnimation(a);
								}
								
								
							}
							
							
							// Card Battle
							if( obj instanceof CardBattlePacket ) {
								CardBattlePacket p = (CardBattlePacket) obj;
								
								StudentCard c1 = Controller.instance.getSlot(p.getSlot1());
								StudentCard c2 = Controller.instance.getSlot(p.getSlot2());
								
								c2.setDamage(c1.getPower(c2), c1);
								c1.setDamage(c2.getPower(c1), c2);
								c1.attacked();
								
								BattleAnimation a = new BattleAnimation(
										p.getPlayer(), 
										Controller.instance.getSlot(p.getSlot1()), 
										p.getSlot1(), 
										p.getSlot2()
								);
								abi.gui.stageGame.addAnimation(a);
							}
							
							
							// Player Attack
							if( obj instanceof PlayerAttackPacket ) {
								PlayerAttackPacket p = (PlayerAttackPacket) obj;
								
								Player pl = Controller.instance.getPlayer(p.getPlayer());
								StudentCard c = Controller.instance.getSlot( p.getSlot() );
								
								pl.setDamage( c.getPower(null) );
								c.attacked();
								
								PlayerAttackAnimation a = new PlayerAttackAnimation(
										p.getPlayer(), 
										Controller.instance.getSlot(p.getSlot()),
										p.getSlot()
								);
								abi.gui.stageGame.addAnimation(a);
							}
							
							
							// Status Change
							if( obj instanceof StatusChangePacket ) {
								StatusChangePacket p = (StatusChangePacket) obj;
								
								StudentCard c = Controller.instance.getSlot( p.getSlot() );
								abi.changeStatus( new StatusChangeEvent(c, p.getSlot(), p.getValue(), p.getStatus(), p.getColor()) );
							}
							
							
							// Card Dead
							if( obj instanceof CardDeadPacket ) {
								CardDeadPacket p = (CardDeadPacket) obj;
								
								StudentCard c = Controller.instance.getSlot(p.getSlot());
								Controller.instance.setSlot(p.getSlot(), null);
								c.dead();
							}
							
							// Highlight Card
							if( obj instanceof HighlightPacket ) {
								HighlightPacket p = (HighlightPacket) obj;
								
								if( p.isTable() ) {
									StudentCard c = Controller.instance.getSlot(p.getPos());
									if( c == null ) {
										Logger.logMessage("Client:receive() NULL POINTER EXCEPTION BY " + p.getClass().getName() + "!!!!!");
										return;
									}
									c.setOriginalColor(p.getColor());
								}
							}
							
							if( obj instanceof SetSlotPacket ) {
								SetSlotPacket p = (SetSlotPacket) obj;
								
								Abi.manager.setSlot(p.getSlot(), p.getCard());
							}
							
							if( obj instanceof Player ) {
								Player p = (Player) obj;
								
								Abi.manager.updatePlayer(p);
							}
							
							if( obj instanceof AddEventCardPacket ) {
								AddEventCardPacket p = (AddEventCardPacket) obj;
								
								Abi.manager.addEventCard(new AddEventCardEvent(p.getCard()));
							}
							
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			tIn.start();

			return true;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void send(Object o) {
		if( out == null ) return;
		
		try {
			out.reset();
			out.writeObject( o );
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
