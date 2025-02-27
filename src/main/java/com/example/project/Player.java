
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
        allCards.addAll(communityCards);
        sortCards();
        return "Nothing"; // Placeholder, replace with actual logic
    }

    public void sortCards() {
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
        
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
