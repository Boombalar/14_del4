package controller;

import gui_fields.GUI_Shipping;
import model.*;

public class Toolbox {

	public Toolbox() {
	}

	public void safeTransferMoney(int fromPlayer, int toPlayer, Player[] players, int amount) {
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

		numberOfHouses = getHousesOnProperty(currentPlayer, fields, fieldNumber);
		if (numberOfHouses > 0) {
			numberOfHouses = numberOfHouses - 1;
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
			safeTransferMoney(currentPlayer, toPlayer, players, priceOfProperty);
		}
	}

	public void bankruptcy(int currentPlayer, int toPlayer, Player[] players, Field[] fields) {
		int[] returnValue;

		for (int fieldCount = 0 ; fieldCount <=39 ; fieldCount++) {

			if(fields[fieldCount] instanceof OwnerFields) {
				if (((OwnerFields)fields[fieldCount]).getOwner() == currentPlayer) {
					returnValue = (((OwnerFields)fields[fieldCount]).returnValue());
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
				safeTransferMoney(currentPlayer, toPlayer, players, players[currentPlayer].getBalance());
			}
		}
	}

	public void changeOwnerShip( int toPlayer, Field[] fields, int fieldNumber) {
		((OwnerFields)fields[fieldNumber]).setOwner(toPlayer);
	}

	public boolean checkForBankruptcy(int currentPlayer, Player[] players, int amount) {
		boolean returnValue = false;
		if ((players[currentPlayer].getBalance() - amount) < 0) {
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

	public int getHousesOnProperty(int currentPlayer, Field[] fields, int fieldNumber) {
		int value = 0;
		if (((OwnerFields)fields[fieldNumber]).getOwner()==currentPlayer) {
			int[] returnValue = ((PropertyFields)fields[fieldNumber]).getReturnValue();
			value = returnValue[6];
		}
		return value;
	}

	public int getHousesOnGroup(int currentPlayer, Field[] fields, int fieldNumber) {
		int returnValue=0;
		int[] returnField = null;

		for (int fieldCount = 0;fieldCount<= 39;fieldCount++) {
			if(fields[fieldCount] instanceof PropertyFields) {
				returnField = ((PropertyFields)fields[fieldNumber]).getReturnValue();
				if(((OwnerFields)fields[fieldCount]).getOwner()==currentPlayer && getHousesOnProperty(currentPlayer, fields, fieldNumber)>0) {
					returnValue = returnValue + getHousesOnProperty(currentPlayer, fields, fieldNumber);
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
		if (checkForBankruptcy(currentPlayer, players, amount)) {
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