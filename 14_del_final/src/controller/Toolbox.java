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

	public void sellBuildings(int currentPlayer, Player[] players, Field[] fields, int fieldNumber) {
		int[] returnValue = new int[8];
		int numberOfHouses;
		int priceOfBuilding;
		
			if (((OwnerFields)fields[fieldNumber]).getOwner()==currentPlayer) {
				returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
				numberOfHouses = returnValue[6];
				if (numberOfHouses > 0) {
					numberOfHouses = numberOfHouses -1;
					returnValue[6] = numberOfHouses;
				}
			}
			priceOfBuilding = returnValue[7]/2;
			players[currentPlayer].recieveMoney(priceOfBuilding);
			
			
	}

	public void sellProperty(int currentPlayer, int toPlayer, Player[] players, Field[] fields, int fieldNumber) {

	}

	public void bankruptcy(int currentPlayer, int toPlayer, Player[] players, Field[] fields) {

	}

	public void checkForBankruptcy(int playerNumber, Player[] players, int amount) {

	}
}
