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

				String[] playerChoiceJail = {"Betal 1000 kr.", "Brug løsladelseskort"};
				String choiceJailPlayer = view.getDropDownChoice("Vælg hvordan du kommer ud af fængsel", playerChoiceJail);

				if (choiceJailPlayer=="Brug løsladelseskort") {

				if (choiceJailPlayer=="Betal 1000kr.") {
					view.writeText("Betal 1000 kr for at komme ud af fængsel");
					bankruptcy.payMoney(currentPlayer, 0, 1000, players, fields);
					view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
				}
			} else {					
				view.writeText("Du har intet løsladelseskort, og bliver derfor trukket 1000 kr., for at komme ud af fængslet.");
				bankruptcy.payMoney(currentPlayer, 0, 1000, players, fields);
				view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
			}
			players[currentPlayer].addTurnsInJail(-1);}
		}
	}
}

