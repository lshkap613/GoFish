package goFish;

import java.util.*;

public class HumanPlayer implements Player { 
	private Scanner input = new Scanner(System.in);
	private String name;
	private int handCount;
	private List<Card> hand = new ArrayList<Card>();

	// constructor
	public HumanPlayer(String name) {
		this.name = name;
		hand = new ArrayList<Card>();
		handCount = 0;
	}
	
	// get methods
	public String getName() {
		return name;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	// returns number of cards in hand
	public int getHandCount() {
		return handCount;
	}
	
	// add card to hand
	public void addToHand(Card card) {
		hand.add(card);
		handCount ++;
	}
	
	// remove card from hand
	public void removeFromHand(Card card) {
		Iterator<Card> iterator = hand.iterator();
	    while (iterator.hasNext()) {
	        Card c = iterator.next();
	        if (c.matches(card)) {
	            iterator.remove();
	        }
	    }
		handCount --;
	}
	
	// print out each card in hand on a new line
	public void viewHand() {
		for (Card card : hand) {
			System.out.println(card);
		}
	}
	
	// prints out each card in hand in a numbered list
	public void viewNumberedHand() {
		int cardCounter = 0;
		for (Card card : hand) {
			cardCounter ++;
			System.out.println(cardCounter + ". " + card.toString());
		}
	}
	
	// play method
	@Override
	public void play(GoFishGame game, Player player, Player computer, List<Card> discard, Deck deck, boolean testing) {
		System.out.println("\nYour turn.\n");
		
		boolean cont = true;
		while (cont == true) {
			// Ask player if wants to discard set or ask for card
			String action = action(input);
			
			if (action.equalsIgnoreCase("set")) {
				
				setChoice(game,input, player, computer, testing, discard);
				
				if (game.isGameOver()) {
					game.endGame(player);
				}
			
			}else {
				
				cont = askChoice(game, input, player, computer, deck, testing);
			}
		} 
	}
	
	// method to ask player if wants to identify set or ask for card
	private String action(Scanner input) {
		System.out.println("set/ask");
		String action = input.nextLine();
		
		// input validation
		while (!action.equalsIgnoreCase("set") && !action.equalsIgnoreCase("ask")) {
			System.out.println("Not a valid option. Please type \"set\" or \"ask\"");
			action = input.nextLine();
		}
		
		return action;
	}
	
	// method that handles player's choice to identify a set
	private void setChoice(GoFishGame game, Scanner input, Player player, Player computer, boolean testing, List<Card> discard) {
		
		System.out.println("Enter the 4 numbers of the cards in the set, one at a time");
	
		int[] setNumbers = new int[4];
		
		boolean hasDuplicates = false;
		do {
			// identify set in hand
			setNumbers = identifySet(setNumbers, input, player);
			
			// make sure 4 DISTINCT cards selected
			hasDuplicates = setHasDuplicates(setNumbers); // check for duplicate selection (an otherwise good way to cheat!)
			
		} while (hasDuplicates); // if duplicates, force player to re-enter
		
		// check if 4 cards selected make a set 
		Card[] set = checkSet(player, setNumbers);
		
		if (set != null) {
			System.out.println("You have a set!");
			
			// discard set cards
			for (Card card : set) {
				player.removeFromHand(card);
				discard.add(card);
			}
			
			// TESTING purposes: 
			if (testing) {
				game.testMode(player, null, discard);
			}

			
		} else {
			System.out.println("Those cards do not make a set");
		}
		
	}

	// method that allows player to identify a set in hand
	private int[] identifySet(int[] setNumbers, Scanner input, Player player) {
		for (int num=1; num<=4; num++) {
			System.out.print(num + ": ");
			setNumbers[num-1] = input.nextInt();
			// validation
			while (setNumbers[num-1] < 1 || setNumbers[num-1] > player.getHandCount()) {
				System.out.println("Input out of range");
				System.out.print(num + ": ");
				setNumbers[num-1] = input.nextInt();
			}
		}
		input.nextLine();

			return setNumbers;
	}
	
	// method that checks if 4 cards selected are indeed a set
	public static Card[] checkSet(Player player, int[] setNumbers) {
		Card card1 = player.getHand().get(setNumbers[0]-1);
		Card card2 = player.getHand().get(setNumbers[1]-1);
		Card card3 = player.getHand().get(setNumbers[2]-1);
		Card card4 = player.getHand().get(setNumbers[3]-1);
						
		// check if ranks are the same
		if (card1.getRank() == card2.getRank()
			&& card2.getRank() == card3.getRank()
			&& card3.getRank() == card4.getRank()) {
			
			Card [] set = {card1, card2, card3, card4};
			return set;
		} 
		
		return null;
	}

	// method that handles player's choice to ask for a card
	public static boolean askChoice(GoFishGame game, Scanner input, Player player, Player computer, Deck deck, boolean testing) {
		boolean cont = true; // return variable
		
		// test code
		if (testing) {
			game.testMode(null, computer, null);
		}
		
		
		System.out.println("Enter the number of a card whose rank you would like to ask for.");
		
		int askNumber = input.nextInt();
		
		// validate that card number not out of range of hand
		while (askNumber < 0 || askNumber > player.getHandCount()) {
			System.out.println("Input out of range");
			System.out.println("Enter the number of the card you would like to ask for.");
			askNumber = input.nextInt();
		}
		input.nextLine();
		
		// Repeat back to user selected rank
		System.out.print("\nYou asked for rank ");
		Card askedCard = player.getHand().get(askNumber - 1);
		System.out.print(askedCard.getRank() + "\n");
		
		// check every card in hand if rank matches and save matches in array
		// default: assume player will have to go fish and no card will be removed from hand
		boolean goFish = true;
		boolean handCardOver = false;
		List<Card> removeFromHandCard = new ArrayList<Card>();
		
		for (Card card : computer.getHand()) {
			// if at least one match was found...
			if (askedCard.getRank() == card.getRank()) {
				// set default boolean values to opposite values
				goFish = false;
				handCardOver = true;
				removeFromHandCard.add(card);
			}
		}
		// if removeFromHand reset to true:
		if (handCardOver) {
			// print cards computer hands 
			System.out.println("\n" + computer.getName() + " handing over: ");
			for (Card removeCard : removeFromHandCard) {
				System.out.println(removeCard.toString());
				// remove those cards from computer's hand
				computer.removeFromHand(removeCard);
				// add it to player's hand
				player.addToHand(removeCard);
			}
			System.out.println();
		}
		
		// if gofish still true:
		if (goFish) {
			System.out.println("\n" + computer.getName() + " says: \"GoFish.\"");
			player.addToHand(deck.drawCard());
			System.out.println("\nA card was added to your hand");
			cont = false;
		}
		
		
		// TESTING PURPOSES:
		if (testing) {
			game.testMode(player, computer, null);
		}
		
		return cont;
	}
	
	// method to make sure not duplicates in selected set
	public static boolean setHasDuplicates(int[] setNumbers) {
		boolean hasDuplicates = false;
		
		for (int i=0; i<setNumbers.length; i++) {
			for (int j=i+1; j<setNumbers.length; j++) {
				if (setNumbers[i] == setNumbers[j]) {
					hasDuplicates = true;
					break;
				}
			}
		}
		
		if (hasDuplicates) {
			System.out.println("\nNo duplicates. Start again.\n");

		}
		
		return hasDuplicates;
		
	}

}
