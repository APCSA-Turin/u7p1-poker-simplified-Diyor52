package com.example.project;

import java.util.ArrayList;

public class Game {

    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        int p1Rank = Utility.getHandRanking(p1Hand);
        int p2Rank = Utility.getHandRanking(p2Hand);

        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p2Rank > p1Rank) {
            return "Player 2 wins!";
        } else {
            ArrayList<Card> p1Cards = new ArrayList<>(p1.getHand());
            p1Cards.addAll(communityCards);
            ArrayList<Card> p2Cards = new ArrayList<>(p2.getHand());
            p2Cards.addAll(communityCards);
            for (int i = 0; i < p1Cards.size(); i++) {
                int p1CardRank = Utility.getRankValue(p1Cards.get(i).getRank());
                int p2CardRank = Utility.getRankValue(p2Cards.get(i).getRank());
                if (p1CardRank > p2CardRank) {
                    return "Player 1 wins!";
                } else if (p2CardRank > p1CardRank) {
                    return "Player 2 wins!";
                }
            }

            return "Tie!";
        }
    }

    public static void play() {
       
}
}