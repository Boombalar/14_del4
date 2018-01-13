package controller;

import model.*;
import view.*;

public class JailCTRL {
	Toolbox toolbox;
	TradeCTRL trade;
	BankruptcyCTRL bankruptcy;

	public JailCTRL(BankruptcyCTRL bankruptcy) {
		this.bankruptcy = bankruptcy;
	}

	public void jailHandling(int currentPlayer, Player[] players, Field[] fields, ViewCTRL view) {
		if(players[currentPlayer].getTurnsInJail()==1) {
			if (players[currentPlayer].getReleaseCard() > 0) {
				String[] playerChoiceJail = {"Brug løsladelseskort", "Betal 1000kr."};
				String choiceJailPlayer = view.getDropDownChoice("Vælg hvordan " + players[currentPlayer].getPlayerName() + " kommer ud af fængsel, der er " + players[currentPlayer].getReleaseCard() + " løsladelseskort tilgængeligt", playerChoiceJail);
				if (choiceJailPlayer=="Brug løsladelseskort") {
				}
				if (choiceJailPlayer=="Betal 1000kr.") {
					bankruptcy.payMoney(currentPlayer, 0, 1000, players, fields, view);
					view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
				}
			}else {					
				view.writeText(players[currentPlayer].getPlayerName() + " har intet løsladelseskort, og bliver derfor trukket 1000 kr., for at komme ud af fængslet.");
				bankruptcy.payMoney(currentPlayer, 0, 1000, players, fields, view);
				view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
			}
			players[currentPlayer].removeTurnsInJail();
		}
	}
}