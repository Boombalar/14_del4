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

	public void jailHandling(int currentPlayer, Player[] players, ViewCTRL view, Field[] fields) {

		if(players[currentPlayer].getTurnsInJail()==1) {
			if (players[currentPlayer].getReleaseCard() > 0) {

				String[] playerChoiceJail = {"Betal 1000 kr", "Brug releaseCard"};
				String choiceJailPlayer = view.getDropDownChoice("Vælg hvordan du kommer ud af fængsel", playerChoiceJail);

				if (choiceJailPlayer=="Brug releaseCard") {
					players[currentPlayer].setTurnsInJail(0);}

				if (choiceJailPlayer=="Betal 1000kr") {
					view.writeText("Betal 1000 kr for at komme ud af fængsel");
					bankruptcy.payMoney(currentPlayer, 0, 1000, players, fields);
				}
			} else {					
				bankruptcy.payMoney(currentPlayer, 0, 1000, players, fields);
			}
		}
	}
}

