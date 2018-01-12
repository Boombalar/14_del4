package controller;

import model.*;
import view.*;

public class Toolbox {

	Field[] fields;
	
	public Toolbox (Field[] fields) {
		this.fields = fields;
	}

	//Bruges til at overføre mellem 2 spillere hvor man på forhånd har bestemt
	//at ingen går.
	
	//Håndterer en bankerot.

	//Returner hvor mange huse der er på grunden, hvis man ejer den.
	public int getHousesOnProperty(int currentPlayer, int fieldNumber) {
		int value = 0;
		if (((OwnerFields)fields[fieldNumber]).getOwner() == currentPlayer) {
			int[] returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
			value = returnValue[6];
		}
		return value;
	}
	
	public void setHousesOnProperty(int amountOfHouses, int fieldNumber) {
		((PropertyFields)fields[fieldNumber]).setNumberOfHouses(amountOfHouses);
	}

	//Returner hvor mange huse der er på en hel gruppe hvis man ejer gruppen
	public int getHousesOnGroup(int currentPlayer, int fieldNumber) {
		int returnValue=0;
		int housesOnProperty = 0;

		for (int fieldCount = 0;fieldCount<= 39;fieldCount++) {
			if(fields[fieldCount] instanceof PropertyFields) {
				housesOnProperty = getHousesOnProperty(currentPlayer, fieldNumber);
				if(housesOnProperty > 0) {
					returnValue = returnValue + housesOnProperty;
				}
			}
		}
		return returnValue;
	}

	public int getHousePrice(int fieldNumber) {
		int[] returnValue;
		returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
		return returnValue[7];
	}

	public int getNumberOfOwnedPropertiesInGroup(int playerOwner, int fieldNumber) {
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

	public int getNumberOfHousesFromPlayer (int playerNumber) {
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

	public int getNumberOfHotelsFromPlayer (int playerNumber) {
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

	public boolean checkPropertySaleValue(int currentPlayer, int amountNeeded) {
		boolean returnValue = false;
		int valueOfSale=0;
		int priceOfProperty;

		if(valueOfSale < amountNeeded) {
			for (int fieldCount = 0; fieldCount<=39;fieldCount++) {
				if(fields[fieldCount] instanceof PropertyFields && valueOfSale < amountNeeded) {
					if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer && getHousesOnProperty(currentPlayer, fieldCount)==0) {
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

	public boolean CheckForPassingStart(int oldPosition, int newPosition) {
		Boolean checkForPassingStart = false;
		if (newPosition < oldPosition) {
			checkForPassingStart = true;
		}
		return checkForPassingStart;
	}	
	
	//Check om man ejer alle grunde i en gruppe af PropertyFields.
		public boolean checkPropertyGroupOwnership(int fieldOwner, int fieldNumber, Field[] fields) {
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
}