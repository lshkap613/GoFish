package goFish;

import java.util.*;

public class GoFish_ClientCode {
	public static void main(String[] args) {
		//create list of players, deck, and game
		List<Player> players = new ArrayList<Player>();
		Deck deck = new Deck();
		List<Card> discard = new ArrayList<Card>();
		GoFishGame game = new GoFishGame(players, deck);
				
				
		// testing variable
		boolean testing = false;
		
		// NOTE TO TESTER
		System.out.println("Hi Professor! "
				 + "\n\nWe have some code that allows you to see extra information" 
				 + "\n(such as the computer's hand) for testing purposes."
				 + "\nIt also deals out very specific cards to the players so that"
				 + "\nboth players have a set as well as cards that share ranks with"
				 + "\nthe other player's cards."
				 + "\n\nWould you like to access this code? (yes/no).");
		
		// create Scanner, get and validate response
		Scanner input = new Scanner(System.in);
		String testerAns = input.nextLine();
		System.out.println();
		
		testerAns = game.yesNoValidator(testerAns, input);
		
		if (testerAns.equalsIgnoreCase("yes")) {
			testing = true;
		}				

		
		// Start game
		game.startGame();
				
				
		// Create 2 players (human and computer)
		System.out.println("\nWhat is your name?");
		String name = input.nextLine();
		
		Player player = new HumanPlayer(name);
		Player computer = new ComputerPlayer("Computer");
		
		// Instantiate player list and add 2 players
		players.add(player);
		players.add(computer);
		

		if (testing) {
			game.test_dealSpecificCards(deck, player, computer);
			game.testMode(null, computer, null);
			
		} else {
			// distribute 7 cards to each player
			game.distributeCards(players);
		}
		
		// Read out hand to player
		System.out.println(player.getName() + ", " + "your cards are:");
		player.viewNumberedHand();
		
		// GAME FLOW 
		game.playTurn(game, player, computer, discard, deck, testing);

		
	} 
	
	
	
	
}
