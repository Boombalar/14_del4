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
		boolean returnValue=true;
		int numberOfHouses;
		int priceOfHouse;
		int priceOfProperty;
		int expectedSaleValue=0;
		int playerbalance = players[currentPlayer].getBalance();
		int amountNeeded = amountToPay - playerbalance;

		//Vi sælger huse
		for (int fieldCount = 0 ; fieldCount<=39;fieldCount++) {
			if(fields[fieldCount] instanceof PropertyFields &&  amountNeeded < 0) {					
				numberOfHouses = toolbox.getHousesOnProperty(currentPlayer, fieldCount);		
				//Vi sælger husene 1 ad gangen
				for (int houseCount = 1; houseCount <= numberOfHouses; houseCount++ ) {
					trade.sellBuilding(currentPlayer, fieldCount, players);
					priceOfHouse = toolbox.getHousePrice(fieldCount)/2;
					amountNeeded = amountNeeded - priceOfHouse;
					if (amountNeeded < 0) {
						break;
					}
				}
			}
		}

		if (amountNeeded > 0 && toolbox.checkPropertySaleValue(amountNeeded, currentPlayer)) {
			for (int fieldCount = 0; fieldCount<=39;fieldCount++) {
				if(fields[fieldCount] instanceof PropertyFields && amountNeeded > 0 && ((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
					priceOfProperty = ((OwnerFields)fields[fieldCount]).getPropertyValue();
					trade.sellProperty(currentPlayer, 0, fieldCount, players, fields);
					amountNeeded = amountNeeded - priceOfProperty;
					if (amountNeeded < 0) {
						break;
					}
				}
			}
		}
		return false;
	}

	public void bankruptcy(int currentPlayer, int toPlayer, Player[] players, Field[] fields, ViewCTRL view) {
		int numberOfBuildings;
		trade.transferAssets(currentPlayer, toPlayer, players, fields);
		players[currentPlayer].setBroke(true);
		view.writeText(players[currentPlayer].getPlayerName() + " er gået bankerot.");
		view.updatePlayerName(currentPlayer, players[currentPlayer].getPlayerName() + " [bankerot]");
	}	
}
