package de.toboxos.abi;


import de.toboxos.abi.cards.Card;
import de.toboxos.abi.cards.StudentCard;
import de.toboxos.abi.events.StatusChangeEvent;
import de.toboxos.abi.network.CardDrawPacket;
import de.toboxos.abi.network.CardPlayedPacket;
import de.toboxos.abi.network.HighlightPacket;
import de.toboxos.abi.network.StatusChangePacket;

public class Logger {

	public static void logObj(String msg, Object obj) {
		
		if( obj instanceof StatusChangePacket ) {
			StatusChangePacket scp = (StatusChangePacket) obj;
			Logger.logMessage(msg + "StatusChangePacket(" + scp.getSlot() + ", " + scp.getValue() + ", " + scp.getStatus() + ", " + scp.getColor().toString() + ")" );
			Logger.logMessage("--------------------------------");
			for( int i = 0; i < 10; i++ ) {
				StudentCard c = Controller.instance.getSlot(i);
				if( c == null ) Logger.logMessage("" + i + ": " + "null");
				else Logger.logMessage("" + i + ": " + c.getName());
			}
			Logger.logMessage("--------------------------------");
		} else if( obj instanceof CardPlayedPacket ) {
			CardPlayedPacket cpp = (CardPlayedPacket) obj;
			Logger.logMessage(msg + "CardPlayedPacket(" + cpp.getPlayer() + ", " + cpp.getPos() + ", " + cpp.getSlot() + ")" );
			Logger.logMessage("Size of Hand: " + Controller.instance.getPlayer(cpp.getPlayer()).getHand().length );
			Logger.logMessage("--------------------------------");
			for( int i = 0; i < Controller.instance.getPlayer(cpp.getPlayer()).getHand().length; i++ ) {
				Logger.logMessage("" + i + ": " + Controller.instance.getPlayer(cpp.getPlayer()).getHand()[i].getName() );
			}
			Logger.logMessage("--------------------------------");
		} else if( obj instanceof StatusChangeEvent ) {
			StatusChangeEvent sce = (StatusChangeEvent) obj;
			Logger.logMessage(msg + "StatusChangeEvent(" + sce.getCard() + ", " + sce.getSlot() + ", " + sce.getValue() + ", " + sce.getStatus() + ", " + sce.getColor().toString() + ")" );
		} else if( obj instanceof CardDrawPacket ) {
			CardDrawPacket cdp = (CardDrawPacket) obj;
			Logger.logMessage(msg + "CardDrawPacket(" + cdp.player + ")");
		} else if( obj instanceof Card ) {
			if( obj instanceof StudentCard ) {
				StudentCard sc = (StudentCard) obj;
				Logger.logMessage(msg + "StudentCard(" + sc.getName() + ", " + sc.getDraw() + ")");
			} else {
				Card c = (Card) obj;
				Logger.logMessage(msg + "Card(" + c.getName() + ")");
			}
		} else if( obj instanceof HighlightPacket ) { 
			HighlightPacket hp = (HighlightPacket) obj;
			Logger.logMessage(msg + "HighlightPacket(" + hp.getPlayer() + ", " + hp.isTable() + ", " + hp.getPos() + ", " + hp.getColor() + ")");
		} else {
			Logger.logMessage(msg + obj.getClass().toString());
		}
		
		
		
	}
	
	public static void logMessage(String msg) {
		System.out.println("[*] " + msg);
	}
}
