package goFish;
import java.util.*;

public class Deck {
	private List<Card> cards;
	private int cardCount = 0;
	
	// enum???????????????????????????????????????????????????????????????????????????????
	private String[] suits = {"spades", "hearts", "diamonds",
	"clubs" };
	private String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", 
		"8", "9", "10", "Jack", "Queen", "King" };

	// constructor
	public Deck () {		
		cards = new ArrayList<Card>();
		for (String suit : suits) {
			for (String rank : ranks) {
				Card newCard = new Card(rank, suit);
				cards.add(newCard);
				cardCount ++;		
			}
		}
	}
	
	// draw card from deck
	public Card drawCard() {
		if (cardCount == 0) {
			System.out.println("No cards left in deck!");
			return null;
		}
		Card drawnCard = cards.get(0);
		cards.remove(0);
		cardCount --;
		return drawnCard;
	}
	
	// get number of cards in deck
	public int numOfCards() {
		return this.cardCount;
	}
	
	// check if deck is empty
	public boolean isEmpty() {
		if (this.cardCount == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// shuffle deck
	public void shuffleDeck() {
		Collections.shuffle(cards);
		
	}
	
	// see every card in deck (for testing purposes)
	public void viewDeck() {
		for (Card card : cards) {
			System.out.println(card);
		}
	}
	
	// remove specific card from deck (testing). 
	// Utilizes matches method from Card class
	public void removeFromDeck(Card card) {
		Iterator<Card> iterator = cards.iterator();
	    while (iterator.hasNext()) {
	        Card c = iterator.next();
	        if (c.matches(card)) {
	            iterator.remove();
	        }
	    }
	    this.cardCount --;
	}
}
