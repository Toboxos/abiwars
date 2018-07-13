package de.toboxos.abi.events;

import de.toboxos.abi.Player;

public interface EventManager {

	public void cardPlayed(CardPlayedEvent e);
	public void battle(CardBattleEvent e);
	public void attackPlayer(PlayerAttackEvent e);
	public void changeStatus(StatusChangeEvent e);
	public void cardDead(int slot);
	public void drawCard(int player);
	public void roundEnd();
	public void roundStart();
	public void setSlot(int slot, String card);
	public void addEventCard(AddEventCardEvent c);
	public void updatePlayer(Player p);
	
}
