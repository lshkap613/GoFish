package goFish;

import java.util.*;


public class GoFishGame implements Game {

	private List<Player> players;
	private Deck deck;

	public GoFishGame(List<Player> players, Deck deck) {
		//constructor
	        this.players = players;
	        this.deck = deck;
	    }
	
	@Override
	public void startGame() {
		// Ask player if wants to start
		System.out.println("Welcome to our game! Are you ready to start? (yes/no)");
		
		Scanner userInput = new Scanner(System.in);
		String response = userInput.nextLine();
		
		response = yesNoValidator(response, userInput);
		
		if (response.equalsIgnoreCase("no")) {
			System.out.println("Too bad, so sad");
			System.exit(0);
		} else {
			System.out.println("\nWould you like to see the instructions? (yes/no)");
			
			response = userInput.nextLine();
			response = yesNoValidator(response, userInput);
			
			if (response.equalsIgnoreCase("yes")) {
				instructions();
			}
			
			// shuffle deck
			deck.shuffleDeck();
		}
	}
	
	public String yesNoValidator(String response, Scanner userInput) {
		while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
			System.out.println("Invalid entry. Please try again:");
			response = userInput.nextLine();
		}
		
		return response;
	}
	
	public void instructions() {
		System.out.println("\n ~~~~~ INSTRUCTION: ~~~~~");
		System.out.println("1.When prompted, enter either \"set\" or \"ask\"");
		System.out.println("2. If you see a set of four cards with the same rank,"
							+"\nyou can get rid of those cards by choosing the set"
							+ "\n option. Your cards will be diplayed in a numbered"
							+ "\n list. Select the cards of the set by entering in"
							+ "\nthe numbers corresponding to those cards, as prompted."
							+ "\nIf the 4 cards are indeed a set, they will be discarded"
							+ "\nand you may go again.");
		System.out.println("3. If you do not have a set or if you have already identified"
							+ "\nthe set, you can ask for a rank from the computer. Ask for"
							+ "\na rank by entering the number corresponding to a card in your"
							+ "\nhand with the rank that you wish to ask for. If the computer"
							+ "\nhas a card or cards with that rank, it will hand them to you"
							+ "\nand you can go again. If not, your turn is over.");
		System.out.println("3. Continue to play until you or the computer no longer have any "
							+"\ncards in your hand. The first to get rid of all his/her cards"
							+ "\nwins the game!");
		System.out.println("HAVE FUN!!!");
		System.out.println();

	}
	
	public void distributeCards(List<Player> players) {
		for (Player bothPlayers : players) {
			for (int i = 0; i < 7; i++) {
				Card drawnCard = deck.drawCard();
				bothPlayers.addToHand(drawnCard);
			}
		}
	}

	
	@Override
	public void playTurn(GoFishGame game, Player player, Player computer, List<Card> discard, Deck deck, boolean testing) {
		boolean gameOver = false;
		while (!gameOver) {
			player.play(game, player, computer, discard, deck, testing);
			gameOver = isGameOver();
			
			if (!gameOver) {
				computer.play(game, computer, player, discard, deck, testing);
				gameOver = game.isGameOver();
			}
		}
		
	}
	
	
	@Override
	public boolean isGameOver() { // game over if a player has an empty hand
		for (Player player : players) {
			if (player.getHandCount() == 0) {
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void endGame(Player player) { // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//determine winner
		System.out.println("\n" + player.getName() + " has no cards left in hand\nand is therfore the WINNER!!!");
		System.out.println("\nWe hope you enjoyed playing our game! Goodbye.");
		System.exit(0);
		
	}

	public void testMode(Player player, Player computer, List<Card> discard) {
		System.out.println("***** SECRET TEST MODE *****");
		if (player != null) {
			System.out.println("\nYour hand now: ");
			player.viewNumberedHand();
			System.out.println();
		}
		
		if (computer != null) {
			System.out.println("\nMy hand now: ");
			computer.viewNumberedHand();
			System.out.println();
		}
		
		if (discard != null) {
			System.out.println("\nDiscard pile now:"); 
			for (Card card : discard) {
				System.out.println(card.toString());
			}
			System.out.println();
		}
		
		System.out.println("****************************");
	}
	
	
	// deal specific cards to players for testing purposes
		public void test_dealSpecificCards(Deck deck, Player player, Player computer) {
			// 5 cards that include a set
			Card card1 = new Card("3", "spades");
			Card card2 = new Card("4", "spades");
			Card card3 = new Card("3", "hearts");
			Card card4 = new Card("3", "diamonds");
			Card card5 = new Card("3", "clubs");
			
			// remove those cards from the deck
			deck.removeFromDeck(card1);
			deck.removeFromDeck(card2);
			deck.removeFromDeck(card3);
			deck.removeFromDeck(card4);
			deck.removeFromDeck(card5);

			//add to player's hand
			player.addToHand(card1);
			player.addToHand(card2);
			player.addToHand(card3);
			player.addToHand(card4);
			player.addToHand(card5);
			
			// hand that includes 2 cards of same rank of a card in human player's hand and set
			Card card6 = new Card("4", "hearts");
			Card card7 = new Card("7", "diamonds");
			Card card8 = new Card("7", "clubs");
			Card card9 = new Card("4", "clubs");
			Card card10 = new Card("7", "spades");
			Card card11 = new Card("7", "hearts");

			// remove those cards from the deck
			deck.removeFromDeck(card6);
			deck.removeFromDeck(card7);
			deck.removeFromDeck(card8);
			deck.removeFromDeck(card9);
			deck.removeFromDeck(card10);
			deck.removeFromDeck(card11);
			
			// add to computer's hand
			computer.addToHand(card6);
			computer.addToHand(card7);
			computer.addToHand(card8);
			computer.addToHand(card9);
			computer.addToHand(card10);
			computer.addToHand(card11);
			
		}

}
