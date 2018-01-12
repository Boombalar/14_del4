package controller;

import model.*;
import view.*;

public class JailCTRL {
	Player[] players;
	Field[] fields;
	ViewCTRL view;
	Toolbox toolbox;
	TradeCTRL trade;
	BankruptcyCTRL bankruptcy;
	
	
	public JailCTRL(Player[] players, ViewCTRL view, BankruptcyCTRL bankruptcy) {
		this.players = players;
		this.view = view;
		this.bankruptcy = bankruptcy;
	}

	public void jailHandling(int currentPlayer) {

		if(players[currentPlayer].getTurnsInJail()==1) {
			if (players[currentPlayer].getReleaseCard() > 0) {

				String[] playerChoiceJail = {"Betal 1000 kr", "Brug releaseCard"};
				String choiceJailPlayer = view.getDropDownChoice("Vælg hvordan du kommer ud af fængsel", playerChoiceJail);

				if (choiceJailPlayer=="Brug releaseCard") {
					players[currentPlayer].setTurnsInJail(0);}

				if (choiceJailPlayer=="Betal 1000kr") {
					view.writeText("Betal 1000 kr for at komme ud af fængsel");
					bankruptcy.payMoney(currentPlayer, 0, 1000);
				}
			} else {					
				bankruptcy.payMoney(currentPlayer, 0, 1000);
			}
		}
	}
}

