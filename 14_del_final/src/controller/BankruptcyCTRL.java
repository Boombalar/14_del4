package controller;

import model.*;
import view.*;

public class BankruptcyCTRL {

	Toolbox toolbox;
	TradeCTRL trade;

	public BankruptcyCTRL (Toolbox toolbox, TradeCTRL trade) {
		this.toolbox = toolbox;
		this.trade = trade;
	}


	public void payMoney(int currentPlayer, int toPlayer, int amount, Player[] players, Field[] fields, ViewCTRL view) {
		if (checkForAvailability(currentPlayer, amount, players) == false) {
			if (raiseMoney(currentPlayer, amount, players, fields) == false){
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
	public boolean checkForAvailability(int currentPlayer, int amount, Player[] players) {
		boolean returnValue = true;
		if ((players[currentPlayer].getBalance() - amount) < 0) {
			returnValue = false;
		}		
		return returnValue;
	}

	public boolean raiseMoney(int currentPlayer, int amountToPay, Player[] players, Field[] fields) {
		boolean returnValue = false;
		int numberOfHouses;
		int priceOfHouse;
		int priceOfProperty;

		//Vi sælger huse
		for (int fieldCount = 0 ; fieldCount<=39;fieldCount++) {
			if(fields[fieldCount] instanceof PropertyFields &&  checkForAvailability(currentPlayer, amountToPay, players)==false) {					
				numberOfHouses = toolbox.getHousesOnPropertyWithOwner(currentPlayer, fieldCount, fields);		
				//Vi sælger husene 1 ad gangen
				for (int houseCount = 1; houseCount <= numberOfHouses; houseCount++ ) {
					trade.sellBuilding(currentPlayer, fieldCount, players, fields);
					priceOfHouse = toolbox.getHousePrice(fieldCount, fields)/2;
					if (	checkForAvailability(currentPlayer, amountToPay, players)) {
						returnValue = true;
						break;
					}
				}
			}
			
		}

		int amountNeeded = amountToPay - players[currentPlayer].getBalance();
		if (checkForAvailability(currentPlayer, amountToPay, players)==false && (toolbox.checkPropertySaleValue(amountNeeded, currentPlayer, fields))==true) {
			for (int fieldCount = 0; fieldCount<=39;fieldCount++) {
				if(fields[fieldCount] instanceof OwnerFields && checkForAvailability(currentPlayer, amountToPay, players) && ((OwnerFields)fields[fieldCount]).getOwner() == currentPlayer) {
					priceOfProperty = ((OwnerFields)fields[fieldCount]).getPropertyValue();
					trade.sellProperty(currentPlayer, 0, fieldCount, players, fields);
					if (checkForAvailability(currentPlayer, amountToPay, players)) {
						returnValue = true;
						break;
					}
				}
			}
		}
		return returnValue;
	}

	public void bankruptcy(int currentPlayer, int toPlayer, Player[] players, Field[] fields, ViewCTRL view) {
		trade.transferAssets(currentPlayer, toPlayer, players, fields);
		players[currentPlayer].setBroke(true);
		view.writeText(players[currentPlayer].getPlayerName() + " er gået bankerot.");
		view.updatePlayerName(currentPlayer, players[currentPlayer].getPlayerName() + " [bankerot]");
	}	
}
