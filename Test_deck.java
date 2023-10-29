/*
 * NOT PART OF THE GAME, but I didn't have the heart to delete it.
 */


package goFish;

public class Test_deck {

	public static void main(String[] args) {
		Deck deck = new Deck();
		Card drawnCard;
		
		System.out.println("Is deck empty?");
		if (deck.isEmpty()) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
		
		// remove random card
		Card removeRandomCard = new Card("4", "spades");
		deck.removeFromDeck(removeRandomCard);
		
		// draw rest of cards from deck
		while((drawnCard = deck.drawCard()) != null) { 
			System.out.println(drawnCard);
			System.out.println("Card count: " + deck.numOfCards());
		}
		
		System.out.println("Is deck empty?");
		if (deck.isEmpty()) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	
	}

}
