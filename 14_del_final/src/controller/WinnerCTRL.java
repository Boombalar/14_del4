package controller;

import model.*;
import java.util.concurrent.TimeUnit;
import view.*;

public class WinnerCTRL {

	public boolean checkWinner(int numberOfPlayers, Player[] players) {
		int numberOfPlayersBroke = 0;
		boolean winner = false;

		for (int i = 1; i <= numberOfPlayers; i++) {
			if (players[i].checkBroke())
				numberOfPlayersBroke++;
		}
		if (numberOfPlayersBroke == numberOfPlayers-1)
			winner = true;
		return winner;
	}

	public void printWinner(int numberOfPlayers, Player[] players, ViewCTRL view) {
		if (checkWinner(numberOfPlayers, players)) {
			for (int i = 1; i <= numberOfPlayers; i++) {
				boolean checkPlayerBroke = players[i].checkBroke();
				if (!checkPlayerBroke) {
					view.getUserResponse("Afslut spil", "Spiller " + i + " har vundet!!");
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
