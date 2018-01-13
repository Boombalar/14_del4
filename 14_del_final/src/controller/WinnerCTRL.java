package controller;

import model.*;
import java.util.concurrent.TimeUnit;
import view.*;

public class WinnerCTRL {

	public WinnerCTRL (){
	}

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
