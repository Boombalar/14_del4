package controller;

import model.ChanceCardDeck;
import model.ChanceCard;
import java.util.Random;

public class ChanceCardCTRL {
	ChanceCard[] chanceCard;
	int cardPointer = 0;
	int cardnumber = 0;
	
	
	public ChanceCardCTRL () {
		ChanceCardDeck deck = new ChanceCardDeck();
		chanceCard = deck.getChanceCards();
	}
	
	private void shuffle() {
	        for (int i = 0;i < 60000; i++) {
	            Random random = new Random();
	            int plads = random.nextInt(chanceCard.length);
	            int tal = chanceCard[plads].getNumber();
	            int plads2 = random.nextInt(chanceCard.length);
	            chanceCard[plads]= chanceCard[plads2];
	            chanceCard[plads2].setNumber(tal);
	        }
	        for (int i = 0; i < chanceCard.length; i++) {
	            System.out.println(chanceCard[i]);                       
	        }
	        System.out.println(chanceCard[2]);	
	}
	
	public void draw() {
		if (cardPointer > 31) {
			shuffle();
			cardPointer = 0;
		}
		
		cardnumber = chanceCard[cardPointer].getType();
		cardPointer = cardPointer +1;
	}
	
	public int[] getReturnValue() {
		return chanceCard[cardnumber].returnValue();
	}
	
	public String getDescription() {
		return chanceCard[cardnumber].getDescription();
	}

	public int getType() {
		return chanceCard[cardnumber].getType();
	}
	
	
}
