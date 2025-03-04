package com.example.project;

import java.util.ArrayList;

public class Game {

    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        //gets the rankings for both hands which means the higher the number the stronger the hand 
        int p1Rank = Utility.getHandRanking(p1Hand);
        int p2Rank = Utility.getHandRanking(p2Hand);
        //checks the handrankings 
        if (p1Rank > p2Rank) {
            return "Player 1 wins!";//player 1 wins if higher handranking 
        } else if (p2Rank > p1Rank) {
            return "Player 2 wins!";//player 2 wins if higher handranking 
        } else {//if hand rankings are same here is the result
            ArrayList<Card> p1Cards = new ArrayList<>(p1.getHand());
            p1Cards.addAll(communityCards);//combine the player hands with the community cards to check 
            ArrayList<Card> p2Cards = new ArrayList<>(p2.getHand());
            p2Cards.addAll(communityCards);
            //compares each card in both players 1 by 1 until some difference is found 
            for (int i = 0; i < p1Cards.size(); i++) {
                int p1CardRank = Utility.getRankValue(p1Cards.get(i).getRank());
                int p2CardRank = Utility.getRankValue(p2Cards.get(i).getRank());
                if (p1CardRank > p2CardRank) {
                    return "Player 1 wins!";//if the card rank of player 1 is higher they win 
                } else if (p2CardRank > p1CardRank) {
                    return "Player 2 wins!";//if the card rank of player 2 is higher they win 
                }
            }
            //if none of it works then it is a tie for both 
            return "Tie!";
        }
    }

    public static void play() {
       
}
}