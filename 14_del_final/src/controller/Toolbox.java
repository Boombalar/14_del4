package controller;

import gui_fields.GUI_Shipping;
import model.*;

public class Toolbox {

	public Toolbox() {
	}
	//Bruges til at overføre mellem 2 spillere hvor man på forhånd har bestemt
	//at ingen går.
	public void safeTransferMoney(int fromPlayer, int toPlayer, Player[] players, int amount) {
		players[fromPlayer].removeMoney(amount);
		players[toPlayer].recieveMoney(amount);
	}

	//Overfører aktiver fra 1 spiller til en anden.
	//hvis toPlayer er 0 så gives grundene tilbage til spillet
	//og der gives ikke penge til anden spiller.
	public void transferAssets(int fromPlayer, int toPlayer, Player[] players, Field[] fields) {
		for(int fieldCount = 0;fieldCount <=39;fieldCount++) {
			if (fields[fieldCount] instanceof OwnerFields) {
				changeOwnerShip(fromPlayer, toPlayer, fields, fieldCount);
			}
		}
		if (toPlayer != 0) {
			players[toPlayer].recieveMoney(players[fromPlayer].getBalance());
		}
		players[fromPlayer].removeMoney(players[fromPlayer].getBalance());
	}

	//Sælg en bygning til halv pris og overfør pengene til ejeren
	public void sellBuilding(int currentPlayer, Player[] players, Field[] fields, int fieldNumber) {
		int[] returnValue = new int[8];
		int numberOfHouses;
		int priceOfBuilding;

		numberOfHouses = getHousesOnProperty(currentPlayer, fields, fieldNumber);
		if (numberOfHouses > 0) {
			numberOfHouses = numberOfHouses - 1;
			returnValue[6] = numberOfHouses;
		}
		priceOfBuilding = returnValue[7]/2;
		players[currentPlayer].recieveMoney(priceOfBuilding);			
	}

	// sælg en grund til en spiller eller banken. Hvis toPlayer = 0 så overføres pengene ikke til nogen
	public void sellProperty(int currentPlayer, int toPlayer, Player[] players, Field[] fields, int fieldNumber) {
		int priceOfProperty;

		changeOwnerShip(currentPlayer, toPlayer, fields, fieldNumber);
		priceOfProperty = ((OwnerFields)fields[fieldNumber]).getPropertyValue();
		if (toPlayer == 0) {
			players[currentPlayer].recieveMoney(priceOfProperty);
		}else {
			safeTransferMoney(toPlayer, currentPlayer, players, priceOfProperty);
		}
	}

	//Håndterer en bankerot.

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

	//Skift ejerskab på en grund hvis den ejes af fromPlayer
	public void changeOwnerShip(int fromPlayer, int toPlayer, Field[] fields, int fieldNumber) {
		if(((OwnerFields)fields[fieldNumber]).getOwner() == fromPlayer) {
			((OwnerFields)fields[fieldNumber]).setOwner(toPlayer);
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

	//Check om man ejer alle grunde i en gruppe af PropertyFields.
	public boolean checkPropertyGroupOwnership(int fieldOwner, Field[] fields, int fieldNumber) {
		boolean returnValue = true;
		for (int fieldCount = 0;fieldCount <=39;fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields) {
				if (((PropertyFields)fields[fieldCount]).getGroupNumber() == ((PropertyFields)fields[fieldNumber]).getGroupNumber()) {//Hvis feltet man er nået til er samme ejer som feltet man checker ud fra
					if (((PropertyFields)fields[fieldCount]).getOwner() != fieldOwner) {//Hvis feltet ikke har samme ejer som det felt man checker ud fra.
						returnValue=false; //Så falsk
					}
				}
			}
		}
		return returnValue;
	}

	//Returner hvor mange huse der er på grunden, hvis man ejer den.
	public int getHousesOnProperty(int currentPlayer, Field[] fields, int fieldNumber) {
		int value = 0;
		if (((OwnerFields)fields[fieldNumber]).getOwner() == currentPlayer) {
			int[] returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
			value = returnValue[6];
		}
		return value;
	}

	//Returner hvor mange huse der er på en hel gruppe hvis man ejer gruppen
	public int getHousesOnGroup(int currentPlayer, Field[] fields, int fieldNumber) {
		int returnValue=0;
		int housesOnProperty = 0;

		for (int fieldCount = 0;fieldCount<= 39;fieldCount++) {
			if(fields[fieldCount] instanceof PropertyFields) {
				housesOnProperty = getHousesOnProperty(currentPlayer, fields, fieldNumber);
				if(housesOnProperty > 0) {
					returnValue = returnValue + housesOnProperty;
				}
			}
		}
		return returnValue;
	}

	public int getHousePrice(int fieldNumber, Field[] fields) {
		int[] returnValue;
		returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
		return returnValue[7];
	}

	public int getNumberOfOwnedPropertiesInGroup(int fieldNumber, Field[] fields, int playerOwner) {
		int returnValue=0;
		int groupNumber = ((OwnerFields)fields[fieldNumber]).getGroupNumber();
		for (int fieldCount=0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof OwnerFields) {
				if (((OwnerFields)fields[fieldCount]).getOwner()==playerOwner) {
					if (((OwnerFields)fields[fieldCount]).getGroupNumber()==groupNumber) {
						returnValue++;
					}
				}
			}
		}
		return returnValue;
	}

	public int getNumberOfHousesFromPlayer (int playerNumber, Field[] fields) {
		int numbOfHouses=0;
		for(int i=0 ; i < 39 ; i++) {
			if(fields[i] instanceof PropertyFields) {
				int numbOfHousesOnField = (((PropertyFields)fields[i]).getReturnValue()[6]);
				int fieldOwner = (((PropertyFields)fields[i]).getOwner());
				if ((fieldOwner == playerNumber) && (numbOfHousesOnField < 5)) {
					numbOfHouses += numbOfHousesOnField;
				}
			}
		}
		return numbOfHouses;
	}

	public int getNumberOfHotelsFromPlayer (int playerNumber, Field[] fields) {
		int numbOfHotels=0;
		for(int i=0 ; i < 39 ; i++) {
			if(fields[i] instanceof PropertyFields) {
				int numbOfHousesOnField = (((PropertyFields)fields[i]).getReturnValue()[6]);
				int fieldOwner = (((PropertyFields)fields[i]).getOwner());
				if ((fieldOwner == playerNumber) && (numbOfHousesOnField == 5)) { // hvis der er 5 huse, så er der et hotel
					numbOfHotels += 1;
				}
			}
		}
		return numbOfHotels;
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

	public boolean checkPropertySaleValue(int currentPlayer, Field[] fields, int amountNeeded) {
		boolean returnValue = false;
		int valueOfSale=0;
		int priceOfProperty;

		if(valueOfSale < amountNeeded) {
			for (int fieldCount = 0; fieldCount<=39;fieldCount++) {
				if(fields[fieldCount] instanceof PropertyFields && valueOfSale < amountNeeded) {
					if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer && getHousesOnProperty(currentPlayer, fields, fieldCount)==0) {
						priceOfProperty = ((OwnerFields)fields[fieldCount]).getPropertyValue();
						valueOfSale = valueOfSale + priceOfProperty;
						if (valueOfSale >= amountNeeded) {
							returnValue = true;
						}
					}
				}
			}
		}
		return returnValue;
	}

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

	public boolean CheckForPassingStart(int oldPosition, int newPosition) {
		Boolean checkForPassingStart = false;
		if (newPosition < oldPosition) {
			checkForPassingStart = true;
		}
		return checkForPassingStart;
	}	
}