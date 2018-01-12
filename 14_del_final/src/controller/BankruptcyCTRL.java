package controller;

import model.*;

public class BankruptcyCTRL {
	
	Player[] players;
	Field[] fields;
	Toolbox toolbox;
	TradeCTRL trade;
	
	public BankruptcyCTRL (Player[] players, Field[] fields, Toolbox toolbox, TradeCTRL trade) {
		this.players = players;
		this.fields = fields;
		this.toolbox = toolbox;
		this.trade = trade;
	}
	
	
	public void payMoney(int currentPlayer, int toPlayer, int amount) {
		if (checkForEnoughMoneyToForcepay(currentPlayer, amount)==false) {
			if (raiseMoney(currentPlayer, amount) == false){
				bankruptcy(currentPlayer, toPlayer);
			}
		} else {						
			players[currentPlayer].removeMoney(amount);
			if (toPlayer > 0) {
				players[toPlayer].recieveMoney(amount);
			}
		}
	}

	//Check for om man har penge nok til at foretage en transaktion mellem to spillere.
	public boolean checkForEnoughMoneyToForcepay(int currentPlayer, int amount) {
		boolean returnValue = true;
		if ((players[currentPlayer].getBalance() - amount) < 0) {
			returnValue = false;
		}		
		return returnValue;
	}

	public boolean raiseMoney(int currentPlayer, int amountReached) {
		boolean returnValue=true;
		int numberOfHouses;
		int priceOfHouse;
		int priceOfProperty;
		int valueOfSale=0;
		int valueOfSale2=0;
		int playerbalance = players[currentPlayer].getBalance();
		int amountNeeded = amountReached - playerbalance;

		//Vi sælger huse
		for (int fieldCount = 0 ; fieldCount<=39;fieldCount++) {
			if(fields[fieldCount] instanceof PropertyFields && valueOfSale < amountNeeded) {
				if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
					numberOfHouses = toolbox.getHousesOnGroup(currentPlayer, fieldCount, fields);
					priceOfHouse = toolbox.getHousePrice(fieldCount, fields)/2;
					if(numberOfHouses > 0) {
						//Vi sælger husene 1 ad gangen
						for (int houseCount = 0; houseCount <= numberOfHouses; houseCount++ ) {
							valueOfSale = valueOfSale + priceOfHouse;
							trade.sellBuilding(currentPlayer, fieldCount, players, fields);
							if (valueOfSale >= amountNeeded) {
								break;
							}
						}
					}
				}
			}
		}
		if (valueOfSale < amountNeeded) {
			if (toolbox.checkPropertySaleValue(amountNeeded-valueOfSale, currentPlayer, fields)) {
				if(valueOfSale2 < amountNeeded) {
					for (int fieldCount2 = 0; fieldCount2<=39;fieldCount2++) {
						if(fields[fieldCount2] instanceof PropertyFields && valueOfSale2 < amountNeeded) {
							if (((PropertyFields)fields[fieldCount2]).getOwner() == currentPlayer && toolbox.getHousesOnProperty(fieldCount2, currentPlayer, fields)==0) {
								priceOfProperty = ((OwnerFields)fields[fieldCount2]).getPropertyValue();
								valueOfSale2 = valueOfSale + priceOfProperty;
								if (valueOfSale2 >= amountNeeded) {
									trade.sellProperty(currentPlayer, 0, players, fields, fieldCount2);
								}
							}
						}
					}
				}
			} else {
				returnValue = false;
			}
		}
		return returnValue;
	}

	public void bankruptcy(int currentPlayer, int toPlayer) {
		int numberOfBuildings;

		//Sælg alle bygninger.
		for (int fieldCount = 0 ; fieldCount <=39 ; fieldCount++) {//Gå brættet igennem.
			if(fields[fieldCount] instanceof OwnerFields) {//Er feltet et OwnerFields, så kan vi roligt Caste metoderne.
				numberOfBuildings = toolbox.getHousesOnProperty(fieldCount, currentPlayer, fields); //Returner antal bygninger hvis feltet ejes af currentPlayer		
				if (numberOfBuildings > 0) {//Hvis der er bygninger på grunden
					//Sælg bygninger
					for (int numberOfBuildingCount = 1; numberOfBuildingCount <= numberOfBuildings ; numberOfBuildingCount++) {
						trade.sellBuilding(fieldCount, currentPlayer, players, fields);
					}
				}
			}
		}
		trade.transferAssets(currentPlayer, toPlayer, players, fields);
		players[currentPlayer].setBroke(true);
	}	
}
