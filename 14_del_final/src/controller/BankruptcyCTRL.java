package controller;

import model.*;
import view.*;

public class BankruptcyCTRL {

	AssetCTRL toolbox;
	TradeCTRL trade;

	public BankruptcyCTRL (AssetCTRL toolbox, TradeCTRL trade) {
		this.toolbox = toolbox;
		this.trade = trade;
	}


	public void payMoney(int currentPlayer, int toPlayer, int amount, Player[] players, Field[] fields, ViewCTRL view) {
		if (checkForEnoughMoneyOnAccount(currentPlayer, amount, players) == false) {
			if (raiseMoney(currentPlayer, toPlayer, amount, players, fields) == false){
				bankruptcy(currentPlayer, toPlayer, players, fields, view);
			}
		} else {						
			players[currentPlayer].removeMoney(amount);
			if (toPlayer > 0) {
				players[toPlayer].recieveMoney(amount);
			}
		}
		view.updateEntireBoard(fields, players);
	}

	//Check for om man har penge nok til at foretage en transaktion mellem to spillere.
	public boolean checkForEnoughMoneyOnAccount(int currentPlayer, int amount, Player[] players) {
		boolean returnValue = true;
		if ((players[currentPlayer].getBalance() - amount) < 0) {
			returnValue = false;
		}		
		return returnValue;
	}

	public boolean raiseMoney(int currentPlayer, int toPlayer, int amountToPay, Player[] players, Field[] fields) {
		int numberOfHouses = 0;

		//Vi sælger huse
		for (int fieldCount = 0 ; fieldCount<=39;fieldCount++) {
			//Er feltet et propertyfelt. Hvis vi ikke ejer feltet, så er numberOfHouses = 0
			if(fields[fieldCount] instanceof PropertyFields) {					
				numberOfHouses = toolbox.getHousesOnPropertyWithOwner(currentPlayer, fieldCount, fields);		
				//Er der huse på feltet ?
				if (numberOfHouses > 0) {
					//Vi sælger husene 1 ad gangen
					for (int houseCount = 1; houseCount <= numberOfHouses; houseCount++ ) {
						trade.sellBuilding(currentPlayer, fieldCount, players, fields);
						//Check for om salget af huset gav nok penge.
						if (checkForEnoughMoneyOnAccount(currentPlayer, amountToPay, players)) {
							trade.safeTransferMoney(currentPlayer, toPlayer, amountToPay, players);
							return true;
						}
					}
				}
			}
		}	


		int amountToRaise = amountToPay - players[currentPlayer].getBalance();
		// Hvis vi kan rejse de penge der mangler ved at sælge grunde
		if ((toolbox.checkPropertySaleValue(currentPlayer, amountToRaise, fields))==true) {

			//Vi begynder at sælge grunde indtil der er solgt nok grunde til at vi kan betale.
			for (int fieldCount = 0; fieldCount<=39;fieldCount++) {
				if(fields[fieldCount] instanceof OwnerFields && ((OwnerFields)fields[fieldCount]).getOwner() == currentPlayer  && (checkForEnoughMoneyOnAccount(currentPlayer, amountToPay, players) == false)) {
					trade.sellProperty(currentPlayer, 0, fieldCount, players, fields);
				}
			}
			trade.safeTransferMoney(currentPlayer, toPlayer, amountToPay, players);
			return true;
		}
		return false;
	}


	public void bankruptcy(int currentPlayer, int toPlayer, Player[] players, Field[] fields, ViewCTRL view) {
		trade.transferAssets(currentPlayer, toPlayer, players, fields);
		players[currentPlayer].setBroke(true);
		view.writeText(players[currentPlayer].getPlayerName() + " er gået bankerot.");
		view.updatePlayerName(currentPlayer, players[currentPlayer].getPlayerName() + " [bankerot]");
	}	
}
