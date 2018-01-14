package controller;

import model.*;

import java.util.Random;

public class ChanceDeckCTRL {
	ChanceCard[] chanceCard;
	int cardPointer = 0;
	int cardNumber = 0;
	int[] shuffledDeck = new int[32];

	/**
	 *Opretter alle chancekortene. 
	 */
	public ChanceDeckCTRL () {
		ChanceDeck deck = new ChanceDeck();
		chanceCard = deck.getChanceCards();
		shuffle();
	}
/**
 *blander alle chancekortene
 */
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
/**
 *Trækker et chancekort.
 */
	public void draw() {

		if (cardPointer > 31) {
			shuffle();
			cardPointer=0;
		}
		cardNumber = shuffledDeck[cardPointer];
		cardPointer = cardPointer + 1;
	}
/**
 * 
 * @return
 */
	public int[] getReturnValue() {
		return chanceCard[cardNumber].getReturnValue();
	}
/**
 * 
 * @return
 */
	public String getDescription() {
		return chanceCard[cardNumber].getDescription();
	}
/**
 * Henter kort typen af chancekortet.
 * @return den retunere typen af chancekortet. 
 */
	public int getType() {
		return chanceCard[cardNumber].getType();
	}
}