package goFish;

public class Card {
	private String rank;
	private String suit;
	
	// Constructor
	public Card(String rank, String suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	// Get methods
	public String getRank() {
		return this.rank;
	}

	public String getSuit() {
		return this.suit;
	}
	
	// to string
	@ Override
	public String toString() {
		return this.rank + " of " + this.suit;
	}
	
	// check if 2 cards math (only used in test code)
	public boolean matches(Card card) {
		if (this.rank == card.rank &&
			this.suit == card.suit) {
			return true;
		} else {
			return false;
		}
	}
}
