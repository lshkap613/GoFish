package goFish;

import java.util.*;


public class ComputerPlayer implements Player {
	
	private Scanner input = new Scanner(System.in);
	private String name;
	private int handCount;
	private List<Card> hand = new ArrayList<Card>();

	// constructor
	public ComputerPlayer(String name) {
		this.name = name;
		hand = new ArrayList<Card>();
		handCount = 0;
	}
	
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
	
	@Override
	public void play(GoFishGame game, Player computer, Player player, List<Card> discard, Deck deck, boolean testing) {
		System.out.println("\nMy turn.");
				
		boolean turnOver = false;
		while (!turnOver) {
			computerSetChoice(game, computer, discard, testing);  // this method has a possible route to the endGame() method 
			turnOver = computerAskChoice(game, computer, player, input, deck, testing, discard);
		}

	}
	
	
	// handles computer's turn to search for a set
	public static void computerSetChoice(GoFishGame game, Player computer, List<Card> discard, boolean testing) {
		
		List<Card> hand = computer.getHand();
		List<Card> set;
		
		System.out.println("\nChecking my hand for set...");

		set = hasFourCardsWithSameRank(hand);
		
		if (!set.isEmpty() ) {
			System.out.println("\nI have a set! It is:\n");
			for (Card card : set) {
				System.out.println(card);
				computer.removeFromHand(card);
				discard.add(card);
			}

			// TESTING purposes: 
			if (testing) {
				game.testMode(null, computer, discard);
			}
			
			
			// check if no more cards in hand (i.e. victory)
			if (game.isGameOver()) {
				game.endGame(computer);
				
			} else {
				System.out.println("\nGoing again!\n");
			}
			
		} else {
			System.out.println("No set");
		}
	}

	
	public static List<Card> hasFourCardsWithSameRank(List<Card> hand) {
	    Map<String, List<Card>> rankCardsMap = new HashMap<>();
	    
	    for (Card card : hand) {
	        String rank = card.getRank();
	        if (!rankCardsMap.containsKey(rank)) {
	        	rankCardsMap.put(rank, new ArrayList<>());
	        }
	        
	        rankCardsMap.get(rank).add(card);
	        
	        if (rankCardsMap.get(rank).size() == 4) {
	            return rankCardsMap.get(rank);
	        }
	    }
	    
	    return Collections.emptyList();
	}
	
	
	// method that handles computer's turn to ask for a card
	public static boolean computerAskChoice(GoFishGame game, Player computer, Player player, Scanner input, Deck deck, boolean testing, List<Card> discard) {
		boolean turnOver = false;
		
		int numCardsInHand = computer.getHandCount();
		Random rand = new Random();
		int randomCardNum = rand.nextInt(numCardsInHand) + 1;
		
		
		System.out.println("\nYour deck:\n");
		
		player.viewNumberedHand();
		
		String askRank = computer.getHand().get(randomCardNum - 1).getRank();
		
		System.out.println("\nDo you have a(n) " + askRank + "? (yes/go fish)");
		
		String answer = input.nextLine();
		
		// validation
		while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("go fish") && !answer.equalsIgnoreCase("no")) { // being nice here since entering no is intuitive
			System.out.println("Invalid input. Please enter \"yes\" or \"go fish\")");
			answer = input.nextLine();
		}
		
		// if player has asked card
		if (answer.equalsIgnoreCase("yes")) {
			
			// to save inputed card numbers
			int[] selectedCards = new int[4];
			
			boolean hasDuplicates = false;
			do {
				System.out.println("Enter the card numbers with rank " + askRank + " IN ORDER. Enter 0 to stop selecting.");
				
				// select a card
				System.out.print("1: ");
				int playerInput = input.nextInt();
				selectedCards[0] = playerInput; // add first card to array
				
				// select more cards or enter 0 to stop selecting
				int listNum = 2;
				
				while (playerInput != 0) { 
					while (playerInput < 1 || playerInput > player.getHandCount()) {
						System.out.println("Input out of range. Please re-enter.");
						playerInput = input.nextInt();
					}
					
					System.out.print(listNum + ": ");
					playerInput = input.nextInt();
					
					if (playerInput != 0) { // make sure exit code "0" doesn't get saved
						selectedCards[listNum-1] = playerInput;
					}
					listNum ++; // increment list index
					
					hasDuplicates = setHasDuplicates(selectedCards);
					
				}
			} while (hasDuplicates);
			input.nextLine();
			
			int indexDifference = 1; // initially, index number of card in hand is 1 less than selectedCards
			for (int cardNum : selectedCards) {
				if (cardNum != 0) { 		// empty indexes default to 0 
					Card removeCard = player.getHand().get(cardNum - indexDifference);
					player.removeFromHand(removeCard);
					computer.addToHand(removeCard);
					indexDifference ++; // if card removed, difference between selectedCards and card index
										// increases by 1, since now less cards in deck (assuming cards selected in order)
				}
			}
			
			System.out.println("Thanks for the card(s)");
			
			// TESTING purposes: 
			if (testing) {
				game.testMode(player, computer, discard);
			}
			
			System.out.println("\nGoing again...\n");
			
		} else { // if player said go fish or no 
			computer.addToHand(deck.drawCard());
			turnOver = true;
		}	
		
		return turnOver;
	}
	
	// method to make sure not duplicates in selected set
	public static boolean setHasDuplicates(int[] setNumbers) {
		boolean hasDuplicates = false;
		
		for (int i=0; i<setNumbers.length; i++) {
			for (int j=i+1; j<setNumbers.length; j++) {
				if (setNumbers[i] != 0
					&& setNumbers[i] == setNumbers[j]) {
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
