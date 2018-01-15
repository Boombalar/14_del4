package controller;

import model.*;
import java.util.concurrent.TimeUnit;
import view.*;

public class WinnerCTRL {

	/**
	 * checkWinner() - tjekker om der findes en vinder, ved at gennemløbe spillerarrayet for 'broke-status'
	 * @param numberOfPlayers - antal af spillere.
	 * @param players - indtast objectnavn af typen Player[]
	 * @return - boolean bestemmer om der er en vinder eller ej.
	 */
	public boolean checkWinner(int numberOfPlayers, Player[] players) {
		int numberOfPlayersBroke = 0;
		boolean winner = false;
		for (int i = 1; i <= numberOfPlayers; i++) {
			if (players[i].getBroke())
				numberOfPlayersBroke++;
		}
		if (numberOfPlayersBroke == numberOfPlayers-1)
			winner = true;
		return winner;
	}

	/**
	 * printWinner() - printer vinderen på skærmen. - finder først om der er en vinder med checkWinner()
	 * @param currentPlayer - spillernummeret i playerarrayet
	 * @param numberOfPlayers - antal af spillere.
	 * @param players - indtast objectnavn af typen Player[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void printWinner(int currentPlayer, int numberOfPlayers, Player[] players, ViewCTRL view) {
		if (checkWinner(numberOfPlayers, players)) {
			for (int i = 1; i <= numberOfPlayers; i++) {
				boolean checkPlayerBroke = players[i].getBroke();
				if (!checkPlayerBroke) {
					view.showChanceCard(players[currentPlayer].getPlayerName() + " har vundet spillet!! - ønsker du at spille igen, skal du starte spillet igen.");
					view.getUserResponse("Spillet slutter efter du trykker på 'Afslut spil'", "Afslut spil");
					try {
						TimeUnit.SECONDS.sleep(1);
						System.exit(0);
					}
					catch (InterruptedException e) {
						System.out.println("Fejl i sleep når program lukker");
						e.printStackTrace();
					}
				}
			}
		}
	}

}