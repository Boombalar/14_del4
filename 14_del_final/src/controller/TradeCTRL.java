package controller;

import model.*;

public class TradeCTRL {
	AssetCTRL asset;

	/**
	 * Konstruktør til TradeCTRL
	 * @param toolbox - indtast objectnavn af typen Toolbox
	 */
	public TradeCTRL (AssetCTRL asset) {
		this.asset = asset;
	}

	/**
	 * safeTransferMoney() - overfører penge fra 1 konto til en anden.
	 * @param fromPlayer - spillernummer på den der skal trækkes i penge.
	 * @param toPlayer - spillernummer på den der skal modtage penge.
	 * @param amount - beløbet der skal overføres.
	 * @param players - indtast objectnavn af typen Player[]
	 */
	public void safeTransferMoney(int fromPlayer, int toPlayer, int amount, Player[] players) {
		players[fromPlayer].removeMoney(amount);
		players[toPlayer].recieveMoney(amount);
	}

	/**
	 * transferAssets() - Overfører aktiver fra 1 spiller til en anden.
	 * Hvis toPlayer er 0 så gives grundene tilbage til spillet, og der gives ikke penge til anden spiller.
	 * @param fromPlayer - spillernummer på den der skal trækkes i penge.
	 * @param toPlayer - spillernummer på den der skal modtage penge.
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 */
	public void transferAssets(int fromPlayer, int toPlayer, Player[] players, Field[] fields) {
		for(int fieldCount = 0;fieldCount <=39;fieldCount++) {
			if (fields[fieldCount] instanceof OwnerFields) {
				changeOwnerShip(fromPlayer, toPlayer, fieldCount, fields);
			}
		}
		if (toPlayer != 0) {
			players[toPlayer].recieveMoney(players[fromPlayer].getBalance());
		}
		players[fromPlayer].removeMoney(players[fromPlayer].getBalance());
	}

	/**
	 * buyBuilding() - køber bygning til et felt.
	 * @param currentPlayer - den spiller der skal købe.
	 * @param fieldNumber - feltet der skal bygges på
	 * @param players - indtast objectnavn af typen Player[]
	 */
	public void buyBuilding(int currentPlayer, int fieldNumber, Player[] players, Field[] fields) {
		int[] returnValue = new int[8];
		int numberOfHouses;

		numberOfHouses = asset.getHousesOnPropertyWithOwner(currentPlayer, fieldNumber, fields);
		if (numberOfHouses < 5) {
			returnValue[6]++;
		}
	}

	/**
	 * sellBuilding() - Sælg en bygning til halv pris og overfør pengene til ejeren
	 * @param currentPlayer - den spiller der skal sælge.
	 * @param fieldNumber - feltet hvor der skal fjernes et hus
	 * @param players - indtast objektnavn af typen Player[]
	 */
	public void sellBuilding(int currentPlayer, int fieldNumber, Player[] players, Field[] fields) {
		int numberOfHouses;
		int priceOfBuilding;

		numberOfHouses = asset.getHousesOnPropertyWithOwner(currentPlayer, fieldNumber, fields);
		if (numberOfHouses > 0) {
			asset.setHousesOnProperty(numberOfHouses-1, fieldNumber, fields);
			priceOfBuilding = asset.getHousePrice(fieldNumber, fields)/2;
			players[currentPlayer].recieveMoney(priceOfBuilding);		
		}
	}

	// sælg en grund til en spiller eller banken. Hvis toPlayer = 0 så overføres pengene ikke til nogen
	/**
	 * 
	 * @param currentPlayer - den spiller der skal sælge
	 * @param toPlayer - den spiller der skal modtage salget.
	 * @param fieldNumber - feltet hvor der skal fjernes et hus
	 * @param players - indtast objektnavn af typen Player[]
	 * @param fields - indtast objektnavn af typen Field[]
	 */
	public void sellProperty(int currentPlayer, int toPlayer, int fieldNumber, Player[] players, Field[] fields) {
		int priceOfProperty;

		changeOwnerShip(currentPlayer, toPlayer, fieldNumber, fields);
		priceOfProperty = ((OwnerFields)fields[fieldNumber]).getPropertyValue();
		if (toPlayer == 0) {
			players[currentPlayer].recieveMoney(priceOfProperty);
		}else {
			safeTransferMoney(toPlayer, currentPlayer, priceOfProperty, players);
		}
	}

	//Skift ejerskab på en grund hvis den ejes af fromPlayer
	public void changeOwnerShip(int fromPlayer, int toPlayer, int fieldNumber, Field[] fields) {
		if(((OwnerFields)fields[fieldNumber]).getOwner() == fromPlayer) {
			((OwnerFields)fields[fieldNumber]).setOwner(toPlayer);
		}
	}
}