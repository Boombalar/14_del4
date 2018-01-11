package controller;

import model.*;
import view.*;

public class JailCTRL {
	BankruptcyCTRL bankrupt;

	public void jailHandling(int currentPlayer, Player[] players, Field[] fields, ViewCTRL view, Toolbox toolbox, TradeCTRL trade ) {

		if(players[currentPlayer].getTurnsInJail()==1) {
			if (players[currentPlayer].getReleaseCard() > 0) {

				String[] playerChoiceJail = {"Betal 1000 kr", "Brug releaseCard"};
				String choiceJailPlayer = view.getDropDownChoice("Vælg hvordan du kommer ud af fængsel", playerChoiceJail);

				if (choiceJailPlayer=="Brug releaseCard") {
					players[currentPlayer].setTurnsInJail(0);}

				if (choiceJailPlayer=="Betal 1000kr") {
					view.writeText("Betal 1000 kr for at komme ud af fængsel");
					bankrupt.payMoney(currentPlayer, 0, 1000, players, fields, toolbox,  trade);
				}
			} else {					
				bankrupt.payMoney(currentPlayer, 0, 1000, players, fields, toolbox, trade);
			}
		}
	}
}

