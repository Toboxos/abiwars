package de.toboxos.abi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import de.toboxos.abi.events.CardBattleEvent;
import de.toboxos.abi.events.CardPlayedEvent;
import de.toboxos.abi.events.PlayerAttackEvent;
import de.toboxos.abi.network.CardBattlePacket;
import de.toboxos.abi.network.CardPlayedPacket;
import de.toboxos.abi.network.LoginPacket;
import de.toboxos.abi.network.PlayerAttackPacket;
import de.toboxos.abi.network.RoundEndPacket;
public class Server extends Thread {
	
	private int port;
	private ServerSocket server;
	private Abi abi;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private Thread tIn;
	
	private boolean bRunning = true;
	
	public Server(int port, Abi abi) {
		this.port = port;
		this.abi = abi;
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket( port );
			server.setSoTimeout( 10 );
			
			while( bRunning ) {
				try {
					Socket client = server.accept();
			
					in = new ObjectInputStream( client.getInputStream() );
					out = new ObjectOutputStream( client.getOutputStream() );
					
					tIn = new Thread(new Runnable() {
						@Override
						public void run() {
							while( bRunning ) {
								try {
									Object obj = in.readObject();
									Logger.logObj("Server:receive(): ", obj);
									
									if( obj instanceof LoginPacket ) {
										LoginPacket p = (LoginPacket) obj;
										abi.playerLogin( p );
									}
									
									if( obj instanceof RoundEndPacket ) {
										if( Controller.instance.isWaiting() || Controller.instance.getTurn() != 2 ) return;
										abi.roundEnd();
									}
									
									if( obj instanceof CardPlayedPacket ) {
										if( Controller.instance.isWaiting() || Controller.instance.getTurn() != 2) return;
										
										CardPlayedPacket p = (CardPlayedPacket) obj;
										
										Player pl = Controller.instance.getPlayer(1);
										
										
										if( p.getSlot() > -1 && Controller.instance.getSlot(p.getSlot()) != null ) return;
										if( p.getPos() >= pl.getHand().length && p.getPos() >= 0 ) return;
										
										abi.cardPlayed(
												new CardPlayedEvent(
														pl.getHand()[p.getPos()],
														pl,
														p.getSlot(),
														p.getPos()
												)
										);
										
									}
									
									if( obj instanceof CardBattlePacket ) {
										if( Controller.instance.isWaiting() || Controller.instance.getTurn() != 2) return;
										
										CardBattlePacket p = (CardBattlePacket) obj;
										
										abi.battle(
												new CardBattleEvent(
														Controller.instance.getPlayer(1),
														Controller.instance.getSlot(p.getSlot1()), 
														Controller.instance.getSlot(p.getSlot2()),
														p.getSlot1(),
														p.getSlot2()
												)
										);
									}
									
									if( obj instanceof PlayerAttackPacket ) {
										if( Controller.instance.isWaiting() || Controller.instance.getTurn() != 2) return;
										
										PlayerAttackPacket p = (PlayerAttackPacket) obj;
										
										if( p.getSlot() > 4 ) return;
										
										abi.attackPlayer(
												new PlayerAttackEvent(
														Controller.instance.getPlayer(0), 
														Controller.instance.getSlot(p.getSlot()),
														p.getSlot()
												)
										);
									}
									
									
									
								} catch (ClassNotFoundException | IOException e) {
									e.printStackTrace();
								}
							}
						}
					});
					tIn.start();
					
				} catch(IOException e) {
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(Object o) {
		if( out == null ) return;
		
		try {
			out.reset();
			out.writeObject(o);
			out.flush(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void shutdown() {
		this.bRunning = false;
	}
	
	
}
