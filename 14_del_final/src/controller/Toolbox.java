package controller;

import model.*;

public class Toolbox {

	/**
	 * Hvis playerNumber ejer feltet, så får man antal huse på feltet.
	 * @param owner spillernummeret der skal trækkes info på.
	 * @param fieldNumber feltnummeret hvor der skal trækkes info på.
	 * @param fields indtast objectnavn af typen Field[]
	 * @return Returner hvor mange huse der er på grunden, hvis man ejer den.
	 */
	public int getHousesOnPropertyWithOwner(int owner, int fieldNumber, Field[] fields) {
		int value = 0;
		if (((OwnerFields)fields[fieldNumber]).getOwner() == owner) {
			int[] returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
			value = returnValue[6];
		}
		return value;
	}

	/**
	 * Overskriver det antal huse der står på grunden.
	 * @param amountOfHouses Antal af huse der skal sættes til at være på ejerfeltet.
	 * @param fieldNumber hvilket feltnummer der skal sættes huse på.
	 * @param fields indtast objectnavn af typen Field[]
	 */
	public void setHousesOnProperty(int amountOfHouses, int fieldNumber, Field[] fields) {
		((PropertyFields)fields[fieldNumber]).setNumberOfHouses(amountOfHouses);
	}

	/**
	 * Hvis playerNumber er ejer, så træk info på hvor mange huse der er på en gruppe af grunde.
	 * @param owner ejeren man sammenligner info på, sammen med feltet.
	 * @param fieldNumber feltnummeret der skal undersøges.
	 * @param fields indtast objectnavn af typen Field[]
	 * @return Returner hvor mange huse der er på en hel gruppe, hvis man ejer gruppen.
	 */
	public int getHousesOnGroup(int owner, int fieldNumber, Field[] fields) {
		int returnValue=0;
		int housesOnProperty = 0;
		for (int fieldCount = 0;fieldCount<= 39;fieldCount++) {
			if(fields[fieldCount] instanceof PropertyFields) {
				housesOnProperty = getHousesOnPropertyWithOwner(owner, fieldNumber, fields);
				if(housesOnProperty > 0) {
					returnValue = returnValue + housesOnProperty;
				}
			}
		}
		return returnValue;
	}

	/**
	 * Få husprisen på et felt.
	 * @param fieldNumber feltet man skal undersøge
	 * @param fields indtast objectnavn af typen Field[]
	 * @return prisen på at bygge huse, på det pågældende felt.
	 */
	public int getHousePrice(int fieldNumber, Field[] fields) {
		int[] returnValue;
		returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
		return returnValue[7];
	}

	/**
	 * Finder hvor mange felter der ejes af owner, i en gruppe.
	 * @param owner ejeren der skal undersøges.
	 * @param fieldNumber feltnummeret, hvorfra man finder den ønskede gruppe.
	 * @param fields indtast objectnavn af typen Field[]
	 * @return antallet af ejede felter.
	 */
	public int getNumberOfOwnedPropertiesInGroup(int owner, int fieldNumber, Field[] fields) {
		int returnValue=0;
		int groupNumber = ((OwnerFields)fields[fieldNumber]).getGroupNumber();
		for (int fieldCount=0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof OwnerFields) {
				if (((OwnerFields)fields[fieldCount]).getOwner()==owner) {
					if (((OwnerFields)fields[fieldCount]).getGroupNumber()==groupNumber) {
						returnValue++;
					}
				}
			}
		}
		return returnValue;
	}

	/**
	 * 
	 * @param playerNumber
	 * @param fields indtast objectnavn af typen Field[]
	 * @return
	 */
	public int getNumberOfHousesFromPlayer(int playerNumber, Field[] fields) {
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

	/**
	 * 
	 * @param playerNumber
	 * @param fields - indtast objectnavn af typen Field[]
	 * @return
	 */
	public int getNumberOfHotelsFromPlayer(int playerNumber, Field[] fields) {
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

	/**
	 * 
	 * @param currentPlayer
	 * @param amountNeeded
	 * @param fields - indtast objectnavn af typen Field[]
	 * @return
	 */
	public boolean checkPropertySaleValue(int currentPlayer, int amountNeeded, Field[] fields) {
		boolean returnValue = false;
		int priceOfProperty;

		for (int fieldCount = 0; fieldCount<=39;fieldCount++) {
			if(fields[fieldCount] instanceof OwnerFields && amountNeeded > 0) {
				if (((OwnerFields)fields[fieldCount]).getOwner() == currentPlayer) {
					priceOfProperty = ((OwnerFields)fields[fieldCount]).getPropertyValue();
					amountNeeded = amountNeeded - priceOfProperty;
					if (amountNeeded < 0) {
						returnValue = true;
						break;
					}
				}
			}
		}
		return returnValue;
	}

	/**
	 * 
	 * @param oldPosition
	 * @param newPosition
	 * @return
	 */
	public boolean checkForPassingStart(int oldPosition, int newPosition) {
		Boolean didPassStart = false;
		if (newPosition < oldPosition) {
			didPassStart = true;
		}
		return didPassStart;
	}	

	//Check om man ejer alle grunde i en gruppe af PropertyFields.
	/**
	 * 
	 * @param fieldOwner
	 * @param fieldNumber
	 * @param fields - indtast objectnavn af typen Field[]
	 * @return
	 */
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