package PJ4;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *      http://www.google.com/ig/directory?type=gadgets&url=www.labpixies.com/campaigns/videopoker/videopoker.xml
 *
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each playerHand. 
 * The player is dealt one five-card poker playerHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. Jacks or Better: a pair pays out only if the cards in the pair are Jacks, 
 * 	Queens, Kings, or Aces. Lower pairs do not pay out. 
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the main poker game class.
 * It uses Deck and Card objects to implement poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */



public class PokerGame {

    // default constant values
    private static final int startingBalance=100;
    private static final int numberOfCards=5;

    // default constant payout value and playerHand types
    private static final int[] multipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] goodHandTypes={ 
	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };
    private int myMultipliers;


    // must use only one deck
    private static final Deck oneDeck = new Deck(1);

    // holding current poker 5-card hand, balance, bet    
    private List<Card> playerHand;
    private int playerBalance;
    private int playerBet;

    /** default constructor, set balance = startingBalance */
    public PokerGame()
    {
	this(startingBalance);
    }

    /** constructor, set given balance */
    public PokerGame(int balance)
    {
	this.playerBalance= balance;
    }

    /** This display the payout table based on multipliers and goodHandTypes arrays */
    private void showPayoutTable()
    { 
	System.out.println("\n\n");
	System.out.println("Payout Table   	      Multiplier   ");
	System.out.println("=======================================");
	int size = multipliers.length;
	for (int i=size-1; i >= 0; i--) {
		System.out.println(goodHandTypes[i]+"\t|\t"+multipliers[i]);
	}
	System.out.println("\n\n");
    }

    /** Check current playerHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {

        // implement this method!
        int scoreValue = checkTheScore();
        String myWinning;
        if(scoreValue < 0) {
            System.out.println("Sorry, you lost.");
        }else {
            myMultipliers = multipliers[scoreValue];
            myWinning = goodHandTypes[scoreValue];
            System.out.println(myWinning);
        }

    }

    /*************************************************
     *   add new private methods here ....
     *************************************************/

    private int checkTheScore () {

        List sortedHand = (List)((ArrayList)playerHand).clone();

        int score = -1;

        if(isRoyalFlush(sortedHand)) {
            score = 8;
        }else if(isStraightFlush(sortedHand)) {
            score = 7;
        }else if(isFourKind(sortedHand)) {
            score = 6;
        }else if(isFullHouse(sortedHand)) {
            score = 5;
        }else if(isFlush(sortedHand)) {
            score = 4;
        }else if(isStraight(sortedHand)) {
            score = 3;
        }else if(isThreeKind(sortedHand)) {
            score = 2;
        }else if(isTwoPair(sortedHand)) {
            score = 1;
        }else if(isJackOrBetter(sortedHand)) {
            score = 0;
        }

        return score;
    }

    private class CardRankComparator implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.getRank() - o2.getRank() ;
        }
    }

    private class CardSuitComparator implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.getSuit() - o2.getSuit() ;
        }
    }
    private boolean isJackOrBetter(List currentHand) {
        boolean isJackOrBetter = false;

        //sort current hand in order of rank
        Collections.sort(currentHand, new CardRankComparator());

        for(int i = 0; i < currentHand.size()-1 ; i++) {

            Card currentCard = (Card)currentHand.get(i);
            Integer currentCardRank = currentCard.getRank();

            //if the card rank is Jack, Queen, King, or Ace.
            if(currentCardRank >= 11 || currentCardRank == 1) {
                Card nextCard = (Card)currentHand.get(i+1);
                if(currentCardRank == nextCard.getRank()) {
                    isJackOrBetter = true;
                    i++;
                }
            }
        }

        return isJackOrBetter;
    }

    private boolean isTwoPair(List currentHand) {

        int pair = 0;
        //sort current hand in natural order in rank
        Collections.sort(currentHand, new CardRankComparator());

        for(int i = 0; i < currentHand.size()-1; i++) {
            Card currentCard = (Card)currentHand.get(i);
            Card nextCard = (Card)currentHand.get(i+1);
            if(currentCard.getRank() == nextCard.getRank()) {
                pair++;
                i++;
            }
        }

        return pair == 2;
    }


    private boolean isThreeKind(List currentHand) {

        boolean isThreeKind = false;
        Collections.sort(currentHand, new CardRankComparator());

        for(int i = 0; i < currentHand.size()-2 && !isThreeKind ; i++) {
            Card firstCard = (Card)currentHand.get(i);
            Card secondCard = (Card)currentHand.get(i+1);
            Card thirdCard = (Card)currentHand.get(i+2);

            Integer firstCardRank = firstCard.getRank();
            Integer secondCardRank = secondCard.getRank();
            Integer thirdCardRank = thirdCard.getRank();

            if(firstCardRank == secondCardRank && secondCardRank == thirdCardRank) {
                isThreeKind = true;
            }

        }

        return isThreeKind;

    }

    private boolean isFourKind(List currentHand) {

        boolean isFourKind = false;
        Collections.sort(currentHand, new CardRankComparator());

        if(((Card)currentHand.get(0)).getRank() == ((Card)currentHand.get(1)).getRank() &&
                ((Card)currentHand.get(1)).getRank() == ((Card)currentHand.get(2)).getRank() &&
                ((Card)currentHand.get(2)).getRank() == ((Card)currentHand.get(3)).getRank() &&
                ((Card)currentHand.get(3)).getRank() != ((Card)currentHand.get(4)).getRank() ){
            isFourKind = true;
        }else if (((Card)currentHand.get(1)).getRank() == ((Card)currentHand.get(2)).getRank() &&
                ((Card)currentHand.get(2)).getRank() == ((Card)currentHand.get(3)).getRank() &&
                ((Card)currentHand.get(3)).getRank() == ((Card)currentHand.get(4)).getRank() &&
                ((Card)currentHand.get(0)).getRank() != ((Card)currentHand.get(1)).getRank()) {
            isFourKind = true;
        }


        return isFourKind;

    }
    private boolean isStraight(List currentHand) {

        boolean isStraight = true;
        Collections.sort(currentHand, new CardRankComparator());

        if(((Card)currentHand.get(0)).getRank() == 1) {

            for (int i = 1; i < currentHand.size() - 1; i++) {
                Card currentCard = (Card) currentHand.get(i);
                Card nextCard = (Card) currentHand.get(i + 1);
                Integer currentCardRank = currentCard.getRank();
                Integer nextCardRank = nextCard.getRank();

                if (currentCardRank + 1 != nextCardRank) {
                    isStraight = false;
                }
            }
        }else {

            for (int i = 0; i < currentHand.size() - 1; i++) {
                Card currentCard = (Card) currentHand.get(i);
                Card nextCard = (Card) currentHand.get(i + 1);
                Integer currentCardRank = currentCard.getRank();
                Integer nextCardRank = nextCard.getRank();

                if (currentCardRank + 1 != nextCardRank) {
                    isStraight = false;
                }
            }

        }
        return isStraight;
    }

    private boolean isFlush(List currentHand) {

        boolean isFlush = true;
        Collections.sort(currentHand, new CardSuitComparator());

        for(int i = 0; i < currentHand.size()-1; i++) {
            Card currentCard = (Card)currentHand.get(i);
            Integer currentCardSuit = currentCard.getSuit();

            if(currentCardSuit != ((Card)currentHand.get(i+1)).getSuit()) {
                isFlush = false;
            }
        }

        return isFlush;
    }

    private boolean isFullHouse(List currentHand) {

        boolean isFullHouse = false;

        if(isThreeKind(currentHand)) {

            Collections.sort(currentHand, new CardRankComparator());
            if (((Card) currentHand.get(0)).getRank() == ((Card) currentHand.get(1)).getRank()
                    && ((Card) currentHand.get(1)).getRank() != ((Card) currentHand.get(2)).getRank()) {
                isFullHouse = true;
            }
            if (((Card) currentHand.get(3)).getRank() == ((Card) currentHand.get(4)).getRank()
                    && ((Card) currentHand.get(2)).getRank() != ((Card) currentHand.get(3)).getRank()) {
                isFullHouse = true;
            }
        }

        return  isFullHouse;
    }

    private boolean isStraightFlush(List currentHand) {
        return isStraight(currentHand) && isFlush(currentHand);
    }

    private boolean isRoyalFlush(List currentHand) {

        Collections.sort(currentHand, new CardRankComparator());
        return ((Card)currentHand.get(0)).getRank() == 1 &&
                isStraight(currentHand) && isFlush(currentHand);
    }

    public void play() {
    /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for positions of cards to keep  
     *          get positions in one input line
     *		update cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */

        // implement this method!
        boolean showTable = true;
        boolean newGame = true;

        Scanner input = new Scanner(System.in);

        while(playerBalance >= 0) {
            List<Integer> positionList = new ArrayList<>();;
            myMultipliers = -1;

            if(showTable)
                showPayoutTable();

            System.out.println("Balance : $" + playerBalance);
            do {
                System.out.print("Enter bet : $");
                playerBet = input.nextInt();
                if(playerBet > playerBalance)
                    System.out.println("You don't have enough balance to bet, please enter lower bet.");
                if(playerBet == 0)
                    System.out.println("You have to bet more than $0.");
            }while(playerBet > playerBalance || playerBet == 0);

            playerBalance = playerBalance - playerBet;

            handlingDeck(oneDeck);
            input.nextLine();
            System.out.print("Enter positions of cards to keep (e.g. 1 4 5) :  ");
            String positions = input.nextLine();

            //System.out.println("positions ; " + positions);
            for(int i = 0; i < positions.length(); i++) {
                if((positions.charAt(i)) != ' ') {
                    positionList.add((int)(positions.charAt(i))- 48);
                }
            }
            //System.out.println(positionList);
            while(positionList.size() < 5) {
                for(int i = 1; i < 6; i++) {
                    if(!positionList.contains(i)) {
                        try {
                            Card newCard = (oneDeck.deal(1)).get(0);
                            playerHand.set(i-1, newCard);
                            positionList.add(i);
                        } catch (PlayingCardException er) {
                            er.getMessage();
                        }
                    }
                }
            }

            System.out.println("Hand : " + playerHand);
                checkHands();
            if(myMultipliers > 0) {
                playerBalance = playerBalance + (playerBet * myMultipliers);
            }
            if(playerBalance == 0 ) {
                System.out.println("\n\nYour balance is $ 0.\nBye!  " );
                break;
            }
            boolean validInputForNewGame = false;
            while(!validInputForNewGame) {
                System.out.print("\n\nYour balance : $" + playerBalance + ", One more game? (y or n) ");
                String newGameInput = (input.next()).toLowerCase();
                if(!checkValidInput(newGameInput)) {
                    System.out.println("Invalid Input, please enter either \"y\" or \"n\"");
                }else {
                    validInputForNewGame = true;
                    newGame =  newGameInput.equals("y") ? true : false;
                }
            }
            if(!newGame)
                break;

            boolean validInputForTable = false;
            while(!validInputForTable) {
                System.out.print("\nWant to see payout table ? (y or n) ");
                String newTableInput = (input.next()).toLowerCase();
                if(!checkValidInput(newTableInput)) {
                    System.out.println("Invalid Input, please enter either \"y\" or \"n\"");
                }else {
                    validInputForTable = true;
                    showTable =  newTableInput.equals("y") ? true : false;
                }

            }
            System.out.println("------------------------------------");
            System.out.println("\n");
        }


    }
    private boolean checkValidInput(String s) {
        s = s.toLowerCase();
        if(s.equals("y") || s.equals("n"))
            return true;

        return false;
    }
    /**
     * reset deck, shuffle deck, deal cards and display cards
     * @param myDeck
     */
    private void handlingDeck(Deck myDeck) {

         myDeck.reset();
        myDeck.shuffle();
        try {
            playerHand = myDeck.deal(5);
        } catch (PlayingCardException e) {
            e.printStackTrace();
        }
        System.out.println("Hand : " + playerHand);
    }


    /*************************************************
     *   Do not modify methods below
    /*************************************************


    /** testCheckHands() is used to test checkHands() method
     *  checkHands() should print your current hand type
     */
    public void testCheckHands()
    {
      	try {
    		playerHand = new ArrayList<Card>();

		// set Royal Flush
		playerHand.add(new Card(1,4));
		playerHand.add(new Card(10,4));
		playerHand.add(new Card(12,4));
		playerHand.add(new Card(11,4));
		playerHand.add(new Card(13,4));
		System.out.println(playerHand);

    		checkHands();
		System.out.println("-----------------------------------");
		// set Straight Flush
		playerHand.set(0,new Card(9,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight
		playerHand.set(4, new Card(8,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Flush

            playerHand.set(4, new Card(5,4));
		System.out.println(playerHand);

            checkHands();
		System.out.println("-----------------------------------");

		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

		// set Four of a Kind
		playerHand.clear();
		playerHand.add(new Card(8,4));
		playerHand.add(new Card(8,1));
		playerHand.add(new Card(12,4));
		playerHand.add(new Card(8,2));
		playerHand.add(new Card(8,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Three of a Kind
		playerHand.set(4, new Card(11,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Full House
		playerHand.set(2, new Card(11,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Two Pairs
		playerHand.set(1, new Card(9,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Royal Pair
		playerHand.set(0, new Card(3,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// non Royal Pair
		playerHand.set(2, new Card(3,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		System.out.println(e.getMessage());
      	}
    }

    /* Quick testCheckHands() */
    public static void main(String args[]) 
    {
	PokerGame pokergame = new PokerGame();
	pokergame.testCheckHands();
    }
}
