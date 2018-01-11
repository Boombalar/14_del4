package controller;

import model.Field;
import model.OwnerFields;
import model.Player;
import model.PropertyFields;

public class BankruptcyCTRL {

	public void payMoney(int currentPlayer, int toPlayer, Player[] players, Field[] fields, int amount) {
		if (checkForEnoughMoneyToForcepay(currentPlayer, players, amount)==false) {
			if (raiseMoney(currentPlayer, players, fields, amount) == false){
				bankruptcy(currentPlayer, toPlayer, players, fields);
			}
		} else {						
			players[currentPlayer].removeMoney(amount);
			if (toPlayer > 0) {
				players[toPlayer].recieveMoney(amount);
			}
		}
	}

	//Check for om man har penge nok til at foretage en transaktion mellem to spillere.
	public boolean checkForEnoughMoneyToForcepay(int currentPlayer, Player[] players, int amount) {
		boolean returnValue = true;
		if ((players[currentPlayer].getBalance() - amount) < 0) {
			returnValue = false;
		}		
		return returnValue;
	}
	

	public boolean raiseMoney(int currentPlayer, Player[] players, Field[] fields, int amountReached) {
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
					numberOfHouses = getHousesOnProperty(currentPlayer, fields, fieldCount);
					priceOfHouse = getHousePrice(fieldCount, fields)/2;
					if(numberOfHouses > 0) {
						//Vi sælger husene 1 ad gangen
						for (int houseCount = 0; houseCount <= numberOfHouses; houseCount++ ) {
							valueOfSale = valueOfSale + priceOfHouse;
							sellBuilding(currentPlayer, players, fields, fieldCount);
							if (valueOfSale >= amountNeeded) {
								break;
							}
						}
					}
				}
			}
		}
		if (valueOfSale < amountNeeded) {
			if (checkPropertySaleValue(currentPlayer, fields, amountNeeded-valueOfSale)) {
				if(valueOfSale2 < amountNeeded) {
					for (int fieldCount2 = 0; fieldCount2<=39;fieldCount2++) {
						if(fields[fieldCount2] instanceof PropertyFields && valueOfSale2 < amountNeeded) {
							if (((PropertyFields)fields[fieldCount2]).getOwner() == currentPlayer && getHousesOnProperty(currentPlayer, fields, fieldCount2)==0) {
								priceOfProperty = ((OwnerFields)fields[fieldCount2]).getPropertyValue();
								valueOfSale2 = valueOfSale + priceOfProperty;
								if (valueOfSale2 >= amountNeeded) {
									sellProperty(currentPlayer, 0, players, fields, fieldCount2);
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

	

	public void bankruptcy(int currentPlayer, int toPlayer, Player[] players, Field[] fields) {
		int numberOfBuildings;

		//Sælg alle bygninger.
		for (int fieldCount = 0 ; fieldCount <=39 ; fieldCount++) {//Gå brættet igennem.
			if(fields[fieldCount] instanceof OwnerFields) {//Er feltet et OwnerFields, så kan vi roligt Caste metoderne.
				numberOfBuildings = getHousesOnProperty(currentPlayer, fields, fieldCount); //Returner antal bygninger hvis feltet ejes af currentPlayer		
				if (numberOfBuildings > 0) {//Hvis der er bygninger på grunden
					//Sælg bygninger
					for (int numberOfBuildingCount = 1; numberOfBuildingCount <= numberOfBuildings ; numberOfBuildingCount++) {
						sellBuilding(currentPlayer, players, fields, fieldCount);
					}
				}
			}
		}
		transferAssets(currentPlayer, toPlayer, players, fields);
		players[currentPlayer].setBroke(true);
	}	
}
