package goFish;

import java.util.*;

public interface Player {
	
	String getName();
	List<Card> getHand();
	int getHandCount();
	void addToHand(Card card);
	void removeFromHand(Card card);
	void viewHand();
	void viewNumberedHand();
	void play(GoFishGame game, Player player, Player computer, List<Card> discard, Deck deck, boolean testing);
}
