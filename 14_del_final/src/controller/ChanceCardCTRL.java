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
		shuffle();
	}

	private void shuffle() {
		int plads2=0;
		int plads=0;
		int tal=0;
		int[] shuffledDeck = new int[32];
		for (int x = 0;x<=31;x++) {
			shuffledDeck[x]=x;
		}

		for (int i = 0;i < 60000; i++) {
			Random random = new Random();
			plads = random.nextInt(chanceCard.length);
			tal = chanceCard[plads].getNumber();
//			tal = shuffledDeck[plads];
			while (plads2 != plads) 
			{
				plads2 = random.nextInt(chanceCard.length);
			}
			chanceCard[plads2].setNumber(chanceCard[plads].getNumber());
			chanceCard[plads].setNumber(tal);
//			shuffledDeck[plads]=shuffledDeck[plads2];
//			shuffledDeck[plads2]=tal;

		}
	
	for (int i = 0; i < chanceCard.length; i++) {
		System.out.println(chanceCard[i].getDescription());                       
	}
	
	System.out.println(chanceCard[2]);	

}

public void draw() {
	if (cardPointer > 31) {
		shuffle();
		cardPointer=0;
	}

	
	cardnumber = chanceCard[cardPointer].getNumber();
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
