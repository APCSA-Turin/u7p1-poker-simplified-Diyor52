package com.example.project;
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; // the current community cards + hand
    private String[] suits = Utility.getSuits();
    private String[] ranks = Utility.getRanks();

    public Player() {//constuctor 
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {//getter method to get something 
        return hand;
    }

    public ArrayList<Card> getAllCards() {//getting method to get all your cards not just hand 
        return allCards;
    }

    public void addCard(Card c) {//setter method as it is void and adds
        hand.add(c);
        allCards.add(c);
    }

    public String playHand(ArrayList<Card> communityCards) {
        allCards = new ArrayList<>(hand);//adds the community cards to the hands cards
        allCards.addAll(communityCards);//community cards added
        sortAllCards();//sorts them out in increasing order 

        ArrayList<Integer> rankFrequency = findRankingFrequency();
        ArrayList<Integer> suitFrequency = findSuitFrequency();

        // Check for Royal Flush (Straight Flush with Ace high)
        if (isStraightFlush(rankFrequency, suitFrequency) && allCards.get(allCards.size() - 1).getRank().equals("A")) {
            return "Royal Flush";//used the straightflush method to get the suits then manually checked the last one to see if it is a Ace
        }

        // Check for Straight Flush
        if (isStraightFlush(rankFrequency, suitFrequency)) {
            return "Straight Flush";//called to the boolean I made and if it is true then it returns the straight flush
        }
        
        // Check for Four of a Kind
        boolean hasFourOfAKind = false;
        for (int count : rankFrequency) {//checks through the ranks and see that if it has a 4 of the same rank then it returns four of a kind
            if (count == 4) {//checks for the 4 of the same rank 
                hasFourOfAKind = true;
                break;
            }
        }
        if (hasFourOfAKind) {
            return "Four of a Kind";
        }
        
        // Check for Full House (Three of a Kind + Pair)
        boolean hasThreeOfAKind = false;// checks for the full house so I made 2 booleans called three of a king and a pair. 
        boolean hasPair = false;
        for (int count : rankFrequency) {//checking through the rank frequency to get the same rank 3 times and if so then returns true
            if (count == 3) {
                hasThreeOfAKind = true;
            }
            if (count == 2) {//then it checks for 2 of the same rank in order to get the additional pair 
                hasPair = true;
            }
        }
        if (hasThreeOfAKind && hasPair) {//if both are present then it returns fullhouse and if one is false then it does not return fullhouse 
            return "Full House";
        }
        
        // Check for Flush (5 cards of the same suit)
        boolean hasFlush = false;
        for (int count : suitFrequency) {//checking through the suit now since flush is 5 of the same suit 
            if (count == 5) {//if the suit contains a count of 5 then returns flush
                hasFlush = true;
                break;
            }
        }
        if (hasFlush) {
            return "Flush";
        }
        
        // Check for Straight (5 consecutive ranks)
        if (isStraight()) {//made  a private variable for this as it was longer
            return "Straight";
        }
        
        // Check for Three of a Kind
        boolean hasThreeOfAKindAgain = false;//this checks for individual three of a kind instead of a full house 
        for (int count : rankFrequency) {//checks the ranks to be 3 of the same rank 
            if (count == 3) {
                hasThreeOfAKindAgain = true;//if so returns true and then returns three of a kind 
                break;
            }
        }
        if (hasThreeOfAKindAgain) {
            return "Three of a Kind";
        }
        
        // Check for Two Pair (at least two pairs)
        int pairCount = 0;// checks for 2 pairs now so 2 of the same rank twice
        for (int count : rankFrequency) {
            if (count == 2) {
                pairCount++;//made a pairCount to check if it going to be a two pair 
            }
        }
        if (pairCount >= 2) {//if 2 or more then 2 pair
            return "Two Pair";
        }
        
        // Check for A Pair
        boolean hasPairAgain = false;//checks for pair like the previous one so I made another boolean value 
        for (int count : rankFrequency) {
            if (count == 2) {//got 1 pair so it returns true and returns pair which is 2 cards of the same rank 
                hasPairAgain = true;
                break;
            }
        }
        if (hasPairAgain) {
            return "A Pair";
        }
        
        // Check for High Card or Nothing
        if (highCardOrNothing()) {//Made another private boolean in order to check this 
            return "High Card";//if true return true
        }
        
        return "Nothing";//else returns false
        
    }


    private boolean isStraightFlush(ArrayList<Integer> rankFrequency, ArrayList<Integer> suitFrequency) {
        boolean hasFiveSameSuit = false;
        //This checks to see if any suit appears 5 times
        for (int suitCount : suitFrequency) {
            if (suitCount == 5) {
                hasFiveSameSuit = true;//if same suit appears five times and the ranks are consecutive then returns straight flush
                break;
            }
        }
        return isStraight() && hasFiveSameSuit;//checks both the straight boolean and the  5 suit to make straightflush 
    }
    //Checks if the hand contains 5 conssecutive ranks
    private boolean isStraight() {
        //in sorted order checks the next 4 cards
        for (int i = 0; i < allCards.size() - 4; i++) {
            int current = Utility.getRankValue(allCards.get(i).getRank());
            int next1 = Utility.getRankValue(allCards.get(i + 1).getRank());
            int next2 = Utility.getRankValue(allCards.get(i + 2).getRank());
            int next3 = Utility.getRankValue(allCards.get(i + 3).getRank());
            int next4 = Utility.getRankValue(allCards.get(i + 4).getRank());
            //checks if the 4 cards are in consecutive order by adding 1 to the next 
            if (current + 1 == next1 && next1 + 1 == next2 && next2 + 1 == next3 && next3 + 1 == next4) {
                return true;
            }
        }
        return false;
    }

    //Checks to see the highest ranked card from allCards as this is the last one remaining
    private boolean highCardOrNothing(){
            //gets highest card
            Card highCard = allCards.get(allCards.size()-1);
            boolean hasHighCard =false;
            //checks if any card matches the highestcard rank
            for(Card card: hand ){
                if(card.getRank().equals(highCard.getRank())){
                    hasHighCard =true;
                }
            }
            return hasHighCard;
    }
    

    //Sorts allcards by rank 
    public void sortAllCards() {
        //checks each index starting from 0 to the size of all cards -1
        for (int i = 0; i < allCards.size() - 1; i++) {
            //checks the next indexes to compare rank values and then swaps if is needed 
            for (int j = i + 1; j < allCards.size(); j++) {
                if (Utility.getRankValue(allCards.get(i).getRank()) > Utility.getRankValue(allCards.get(j).getRank())) {
                    Card temp = allCards.get(i);//swapping process here 
                    allCards.set(i, allCards.get(j));
                    allCards.set(j, temp);
                }
            }
        }
    }

    //Checks for the frequency for the rank of the cards
    public ArrayList<Integer> findRankingFrequency() {
        //made a new arraylist here too
        ArrayList<Integer> frequency = new ArrayList<>();
        //checks through the possible occurances of ranks 
        for (String rank : ranks) {
            int count = 0;
            //checks the allcards to see if any of the cards match up with the rank given from the above loop 
            for (Card card : allCards) {
                if (card.getRank().equals(rank)) {//if card matches up the rank adds 1 to count 
                    count++;
                }
            }
            frequency.add(count);//adds the amount of each rank to the list 
        }
        return frequency;
    }

    //Checks for the frequency for the suit of the cards
    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();//made new arraylist 
        //checks through the suits list to see the count occurances for each possible suit 
        for (String suit : suits) {
            int count = 0;
            //checks the cards from allcards to see if it matches up with the suit and if it does it adds 1 to count
            for (Card card : allCards) {
                if (card.getSuit().equals(suit)) {
                    count++;
                }
            }
            frequency.add(count);//adds the amount of  each suit to the list 
        }
        return frequency;
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
