
package com.example.project;
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; // the current community cards + hand
    private String[] suits = Utility.getSuits();
    private String[] ranks = Utility.getRanks();

    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public void addCard(Card c) {
        hand.add(c);
        allCards.add(c);
    }

    public String playHand(ArrayList<Card> communityCards) {
        allCards = new ArrayList<>(hand);
        allCards.addAll(communityCards);
        sortAllCards();

        ArrayList<Integer> rankFrequency = findRankingFrequency();
        ArrayList<Integer> suitFrequency = findSuitFrequency();

        // Check for Royal Flush (Straight Flush with Ace high)
        if (isStraightFlush(rankFrequency, suitFrequency) && allCards.get(allCards.size() - 1).getRank().equals("A")) {
            return "Royal Flush";
        }

        // Check for Straight Flush
        if (isStraightFlush(rankFrequency, suitFrequency)) {
            return "Straight Flush";
        }

        // Check for Four of a Kind
        if (rankFrequency.contains(4)) {
            return "Four of a Kind";
        }

        // Check for Full House (Three of a Kind + Pair)
        if (rankFrequency.contains(3) && rankFrequency.contains(2)) {
            return "Full House";
        }

        // Check for Flush (5 cards of the same suit)
        if (suitFrequency.contains(5)) {
            return "Flush";
        }

        // Check for Straight (5 consecutive ranks)
        if (isStraight()) {
            return "Straight";
        }

        // Check for Three of a Kind
        if (rankFrequency.contains(3)) {
            return "Three of a Kind";
        }

        // Check for Two Pair (at least two pairs)
        int pairCount = 0;
        for (int count : rankFrequency) {
            if (count == 2) pairCount++;
        }
        if (pairCount >= 2) { // Fix: Check for >= 2 pairs
            return "Two Pair";
        }

        // Check for A Pair
        if (rankFrequency.contains(2)) {
            return "A Pair";
        }

       
        if(highCardOrNothing()){
             return "High Card";
        }
       
        return "Nothing";
    }


    private boolean isStraightFlush(ArrayList<Integer> rankFrequency, ArrayList<Integer> suitFrequency) {
        return isStraight() && suitFrequency.contains(5);
    }
   
    private boolean isStraight() {
        for (int i = 0; i < allCards.size() - 4; i++) {
            int current = Utility.getRankValue(allCards.get(i).getRank());
            int next1 = Utility.getRankValue(allCards.get(i + 1).getRank());
            int next2 = Utility.getRankValue(allCards.get(i + 2).getRank());
            int next3 = Utility.getRankValue(allCards.get(i + 3).getRank());
            int next4 = Utility.getRankValue(allCards.get(i + 4).getRank());
            if (current + 1 == next1 && next1 + 1 == next2 && next2 + 1 == next3 && next3 + 1 == next4) {
                return true;
            }
        }
        return false;
    }

    private boolean highCardOrNothing(){
            Card highCard = allCards.get(allCards.size()-1);
            boolean hasHighCard =false;
            for(Card card: hand ){
                if(card.getRank().equals(highCard.getRank())){
                    hasHighCard =true;
                }
            }
            return hasHighCard;
    }
    


    public void sortAllCards() {
        for (int i = 0; i < allCards.size() - 1; i++) {
            for (int j = i + 1; j < allCards.size(); j++) {
                if (Utility.getRankValue(allCards.get(i).getRank()) > Utility.getRankValue(allCards.get(j).getRank())) {
                    Card temp = allCards.get(i);
                    allCards.set(i, allCards.get(j));
                    allCards.set(j, temp);
                }
            }
        }
    }

    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();
        for (String rank : ranks) {
            int count = 0;
            for (Card card : allCards) {
                if (card.getRank().equals(rank)) {
                    count++;
                }
            }
            frequency.add(count);
        }
        return frequency;
    }

    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();
        for (String suit : suits) {
            int count = 0;
            for (Card card : allCards) {
                if (card.getSuit().equals(suit)) {
                    count++;
                }
            }
            frequency.add(count);
        }
        return frequency;
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
