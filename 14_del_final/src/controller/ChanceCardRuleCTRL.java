package controller;

import model.*;
import view.*;

public class ChanceCardRuleCTRL {

	Toolbox toolbox;
	BankruptcyCTRL bankruptcy;
	ChanceCardDeckCTRL chancecarddeck;
	LandOnFieldCTRL landonfield;

	public ChanceCardRuleCTRL (Toolbox toolbox,BankruptcyCTRL bankruptcy, ChanceCardDeckCTRL chancecarddeck, LandOnFieldCTRL landonfield) {
		this.toolbox = toolbox;	
		this.bankruptcy = bankruptcy;
		this.chancecarddeck = chancecarddeck;
		this.landonfield = landonfield;
	}

	/**
	 * chanceCardRules - En metode som switcher på hvilken type ChanceCard man har trukket.
	 * @param currentPlayer - modtager et playernummer.
	 * @param  
	 */
	public void chanceCardRules (int currentPlayer, Player[] players,Field[] fields,ViewCTRL view) {
		int chanceCardType = chancecarddeck.getType();
		int[] chanceCardValueArray = chancecarddeck.getReturnValue();
		int owner = 0;
		if ((fields[players[currentPlayer].getPosition()]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[players[currentPlayer].getPosition()]).getOwner());
		}
		switch (chanceCardType) {

		case 1: // TransactionCard
			int transactionValue = chanceCardValueArray[0];
			if ((transactionValue) < 0) {
				int realvalue = (transactionValue * (-1)); 
				view.writeText("Der trækkes " + realvalue + "kr. fra " + players[currentPlayer].getPlayerName() + "'s konto.");
				bankruptcy.payMoney(currentPlayer, owner, (realvalue), players, fields, view);

			} else {
				view.writeText("Der tilføjes " + transactionValue + "kr. til " + players[currentPlayer].getPlayerName() + "'s konto.");
				players[currentPlayer].recieveMoney(transactionValue);
			}
			view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
			break;

		case 2: // MoveToCards
			moveToCardsRules(currentPlayer, players, fields, view); // logik og viewCTRL-kald ligger i denne metode.
			break;

		case 3: // ReleaseCards
			players[currentPlayer].addReleaseCard();
			view.writeText(players[currentPlayer].getPlayerName() + " har nu 1 løsladelseskort mere, og totalt " + players[currentPlayer].getReleaseCard() + "kort.");
			break;

		case 4: //TaxCards
			int numberofhouses = toolbox.getNumberOfHousesFromPlayer(currentPlayer);
			int numberofhotels = toolbox.getNumberOfHotelsFromPlayer(currentPlayer);
			int totalSum = (chanceCardValueArray[0] * numberofhouses)+(chanceCardValueArray[1] * numberofhotels);
			view.writeText("Der trækkes " + totalSum + "kr. fra " + players[currentPlayer].getPlayerName() + "'s konto.");
			bankruptcy.payMoney(currentPlayer, owner, (totalSum), players, fields, view);
			break;
		default:
			System.out.println("ChanceCard-typen der pharses er ikke korrekt.");
		}
	}

	/**
	 * moveToCardRules() - En Metode som switcher på hvilket type MoveToCard man har trukket.
	 * @param currentPlayer
	 * @param players
	 * @param fields
	 * @param view
	 */
	public void moveToCardsRules (int currentPlayer, Player[] players,Field[] fields, ViewCTRL view) {
		int playerPosition = players[currentPlayer].getPosition();
		int[] chanceCardValueArray = chancecarddeck.getReturnValue();
		int moveToField = chanceCardValueArray[0];
		int moveToType = chanceCardValueArray[1];

		switch (moveToType){

		case 1:
			// Blot flyttekort til et bestemt felt.
			view.writeText(players[currentPlayer].getPlayerName() + " flyttes til " + fields[moveToField].getName());
			if(playerPosition > moveToField) {
				players[currentPlayer].setPosition(moveToField-40);
				view.updatePlayerPosition(currentPlayer, playerPosition, (moveToField-40));
			} else {
				players[currentPlayer].setPosition(moveToField);
				view.updatePlayerPosition(currentPlayer, playerPosition, moveToField);
			}
			landonfield.ruleSwitch(currentPlayer, players, fields, view);
			break;

		case 2: // Et flyttekort, hvor man flytter til det nærmeste felt med rederi.	
			int oldPlayerPos = players[currentPlayer].getPosition();
			int newPlayerPos = players[currentPlayer].getPosition();

			while (fields[newPlayerPos].getType() != 1) {
				newPlayerPos++;
				if (newPlayerPos > 39) { 
					newPlayerPos =0;
				}
			}
			view.writeText(players[currentPlayer].getPlayerName() + " flyttes til " + fields[newPlayerPos].getName() + ", som er det nærmeste redderi");
			players[currentPlayer].setPosition(newPlayerPos);
			view.updatePlayerPosition(currentPlayer, oldPlayerPos, newPlayerPos);
			landonfield.shippingField(currentPlayer, 2, players, fields, view);
			break;

		case 3:
			// Flyttekort til fængsel
			view.writeText(players[currentPlayer].getPlayerName() + " flyttes til " + fields[moveToField].getName());
			players[currentPlayer].addTurnsInJail();
			players[currentPlayer].setPosition(moveToField);
			players[currentPlayer].setExtraTurn(false);
			view.updatePlayerPosition(currentPlayer, playerPosition, moveToField);
			break;

		case 4: // Ryk tre felter tilbage.
			int actualMove = (playerPosition - moveToField);
			view.writeText(players[currentPlayer].getPlayerName() + " rykkes tre felter tilbage til " + fields[actualMove].getName());
			players[currentPlayer].setPosition(actualMove);
			view.updatePlayerPosition(currentPlayer, playerPosition, actualMove);
			landonfield.ruleSwitch(currentPlayer, players, fields, view);
			break;

		default:
			System.out.println("MoveTo-typen der pharses er ikke korrekt.");
		}
	}
}
