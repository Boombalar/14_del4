package controller;
import model.*;

public class Toolbox {



	public Toolbox() {

	}

	public void transferMoney(int fromPlayer, int toPlayer, Player[] players, int amount) {
		players[fromPlayer].removeMoney(amount);
		players[toPlayer].recieveMoney(amount);
	}

	public void transferAssets(int fromPlayer, int toPlayer, Player[] players, Field[] fields) {
		for(int fieldcount = 0;fieldcount <=39;fieldcount++) {
			if (fields[fieldcount] instanceof OwnerFields) {
				if (((OwnerFields)fields[fieldcount]).getOwner() == fromPlayer) {
					((OwnerFields)fields[fieldcount]).setOwner(toPlayer);	
				}
			}
		}
		players[toPlayer].recieveMoney(players[fromPlayer].getBalance());
		players[fromPlayer].removeMoney(players[fromPlayer].getBalance());
	}

	public void sellBuilding(int currentPlayer, Player[] players, Field[] fields, int fieldNumber) {
		int[] returnValue = new int[8];
		int numberOfHouses;
		int priceOfBuilding;

		numberOfHouses = getHousesOnProperty(currentPlayer, fields, fieldNumber, returnValue);
		if (numberOfHouses > 0) {
			numberOfHouses = numberOfHouses -1;
			returnValue[6] = numberOfHouses;
		}
		priceOfBuilding = returnValue[7]/2;
		players[currentPlayer].recieveMoney(priceOfBuilding);			
	}

	public void sellProperty(int currentPlayer, int toPlayer, Player[] players, Field[] fields, int fieldNumber) {
		int priceOfProperty;

		changeOwnerShip(toPlayer, fields, fieldNumber);
		priceOfProperty = ((OwnerFields)fields[fieldNumber]).getPropertyValue();
		if (toPlayer == 0) {
			players[currentPlayer].recieveMoney(priceOfProperty);
		}else {
			transferAssets(currentPlayer, toPlayer, players, fields);
		}

	}

	public void bankruptcy(int currentPlayer, int toPlayer, Player[] players, Field[] fields) {
		int[] returnValue;

		for (int fieldCount = 0;fieldCount <=39;fieldCount++) {
			if (((OwnerFields)fields[fieldCount]).getOwner() == currentPlayer) {
				returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
				int numberOfBuildings=returnValue[6];
				if (numberOfBuildings>0) {
					for (int numberOfBuildingSales = 1 ; numberOfBuildingSales <= numberOfBuildings ; numberOfBuildingSales++) {
						sellBuilding(currentPlayer, players, fields, fieldCount);
					}
				}
				changeOwnerShip(toPlayer, fields, fieldCount);	
			}
		}

		if (toPlayer==0) {
			players[currentPlayer].removeMoney(players[currentPlayer].getBalance());
		}else {
			transferMoney(currentPlayer, toPlayer, players, players[currentPlayer].getBalance());
		}
	}

	public void changeOwnerShip( int toPlayer, Field[] fields, int fieldNumber) {
		((OwnerFields)fields[fieldNumber]).setOwner(toPlayer);
	}

	public boolean checkForBankruptcy(int currentPlayer, Player[] players, int amount) {
		boolean returnValue = false;
		if (players[currentPlayer].getBalance() - amount < 0) {
			players[currentPlayer].setBroke(true);
			returnValue = true;
		}		
		return returnValue;
	}

	public boolean checkForGroupOwnership(int fieldOwner, Field[] fields, int fieldNumber) {
		boolean returnValue = true;
		for (int fieldCount = 0;fieldCount <=39;fieldCount++) {
			if (fields[fieldCount] instanceof OwnerFields) {
				if (((OwnerFields)fields[fieldCount]).getGroupNumber() == ((OwnerFields)fields[fieldNumber]).getGroupNumber()) {
					if (((OwnerFields)fields[fieldCount]).getOwner() != fieldOwner) {
						returnValue=false;
					}
				}
			}
		}
		return returnValue;
	}


	public int getHousesOnProperty(int currentPlayer, Field[] fields, int fieldNumber, int[] returnValue) {
		if (((OwnerFields)fields[fieldNumber]).getOwner()==currentPlayer) {
			returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
		}
		return returnValue[6];
	}

	public int getHousePrice(int fieldNumber, Field[] fields) {
		int[] returnValue;
		returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
		return returnValue[7];
	}

	public int raiseMoney(int currentplayer, Field[] fields, int amount) {
		int returnValue;


		return returnValue;
	}

}
