package goFish;

import java.util.List;

public interface Game {

	public void startGame();
	public boolean isGameOver();
	void playTurn(GoFishGame game, goFish.Player player, goFish.Player computer, List<Card> discard, Deck deck,
			boolean testing);	
	void endGame(goFish.Player player);


}