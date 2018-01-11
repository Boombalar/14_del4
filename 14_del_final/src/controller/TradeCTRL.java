package controller;

import model.*;

public class TradeCTRL {
	Toolbox toolbox;
	
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
	public void sellBuilding(int currentPlayer, int fieldNumber, Player[] players, Field[] fields) {
		int[] returnValue = new int[8];
		int numberOfHouses;
		int priceOfBuilding;

		numberOfHouses = toolbox.getHousesOnProperty(currentPlayer, fieldNumber, fields);
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

	//Skift ejerskab på en grund hvis den ejes af fromPlayer
	public void changeOwnerShip(int fromPlayer, int toPlayer, Field[] fields, int fieldNumber) {
		if(((OwnerFields)fields[fieldNumber]).getOwner() == fromPlayer) {
			((OwnerFields)fields[fieldNumber]).setOwner(toPlayer);
		}
	}


	
}
