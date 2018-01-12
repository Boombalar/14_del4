package controller;
 
import model.*;

import java.util.Random;
 
public class ChanceCardCTRL {
    ChanceCard[] chanceCard;
    int cardPointer = 0;
    int cardNumber = 0;
    int[] shuffledDeck = new int[32];
 
    public ChanceCardCTRL () {
        ChanceCardDeck deck = new ChanceCardDeck();
        chanceCard = deck.getChanceCards();
        shuffle();
    }
 
    private void shuffle() {
        int plads2=0;
        int plads=0;
        int tal=0;        
        
        for (int x = 0;x<=31;x++) {
            shuffledDeck[x]=x;
        }
 
 
        for (int i = 0;i < 60000; i++) {
            Random random = new Random();
            plads = random.nextInt(chanceCard.length);
 
            tal = shuffledDeck[plads];
            while (plads2 == plads)
            {
                plads2 = random.nextInt(chanceCard.length);
            }

            shuffledDeck[plads]=shuffledDeck[plads2];
            shuffledDeck[plads2]=tal;
        }
     }
 
    public void draw() {
 
        if (cardPointer > 31) {
            shuffle();
            cardPointer=0;
        }
        cardNumber = shuffledDeck[cardPointer];
        cardPointer = cardPointer + 1;
    }
 
    public int[] getReturnValue() {
        return chanceCard[cardNumber].returnValue();
    }
 
    public String getDescription() {
        return chanceCard[cardNumber].getDescription();
    }
 
    public int getType() {
        return chanceCard[cardNumber].getType();
    }
}