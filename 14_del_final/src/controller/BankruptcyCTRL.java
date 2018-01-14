package controller;

import model.*;
import view.*;

public class BankruptcyCTRL {

	private AssetCTRL asset;
	private TradeCTRL trade;

	public BankruptcyCTRL (AssetCTRL asset, TradeCTRL trade) {
		this.asset = asset;
		this.trade = trade;
	}

	/**
	 * En metode som laver en transaktion mellem to spillere. 
	 * @param currentPlayer modtager en int som den aktivespiller.
	 * @param toPlayer modtager en int som er til den spiller der skal betales til.
	 * @param amount modtager en int som er det beløb der skal betales. 
	 * @param players Det er et objekt af typen Player[]
	 * @param fields Det er et objekt af typen Fields[]
	 * @param view Det er et objekt af ViewCTRL.
	 */ 
	public void payMoney(int currentPlayer, int toPlayer, int amount, Player[] players, Field[] fields, ViewCTRL view) {
		if (checkForEnoughMoneyOnAccount(currentPlayer, amount, players) == false) {
			if (raiseMoney(currentPlayer, toPlayer, amount, players, fields) == false){
				bankruptcy(currentPlayer, toPlayer, players, fields, view);
			}
		} else {						
			players[currentPlayer].removeMoney(amount);
			if (toPlayer > 0) {
				players[toPlayer].recieveMoney(amount);
			}
		}
		view.updateEntireBoard(fields, players);
	}

	/**
	 * En metode der tjekke for om man kan foretage en transaktion mellem to spiller.
	 * @param currentPlayer modtager en int som er den aktive spiller.
	 * @param amount modtager en int som er det beløb der skal betales. 
	 * @param players Det er et objekt af typen Player[]
	 * @return Retunere true, hvis der er penge nok ellers false. 
	 */
	//Check for om man har penge nok til at foretage en transaktion mellem to spillere.
	public boolean checkForEnoughMoneyOnAccount(int currentPlayer, int amount, Player[] players) {
		boolean returnValue = true;
		if ((players[currentPlayer].getBalance() - amount) < 0) {
			returnValue = false;
		}		
		return returnValue;
	}

	/**
	 * En metode som samler penge sammen ved salg af aktiver, hvis man ikke har nok penge på kontoen, 
	 * @param currentPlayer modtager en int som er den aktive spiller.
	 * @param toPlayer Modtager en int som er den spiller som skal modtage pengene. 
	 * @param amountToPay Modtager en int som er det beløb der skal betales. 
	 * @param players Det er et objekt af players[]
	 * @param fields Det er et objekt af Field[]
	 * @return - Retunere true hvis der er samlet nok penge sammen ved salg af huse eller grunde, ellers false. 
	 */
	public boolean raiseMoney(int currentPlayer, int toPlayer, int amountToPay, Player[] players, Field[] fields) {
		int numberOfHouses = 0;

		//Vi sælger huse
		for (int fieldCount = 0 ; fieldCount<=39;fieldCount++) {
			//Er feltet et propertyfelt. Hvis vi ikke ejer feltet, så er numberOfHouses = 0
			if(fields[fieldCount] instanceof PropertyFields) {					
				numberOfHouses = asset.getHousesOnPropertyWithOwner(currentPlayer, fieldCount, fields);		
				//Er der huse på feltet ?
				if (numberOfHouses > 0) {
					//Vi sælger husene 1 ad gangen
					for (int houseCount = 1; houseCount <= numberOfHouses; houseCount++ ) {
						trade.sellBuilding(currentPlayer, fieldCount, players, fields);
						//Check for om salget af huset gav nok penge.
						if (checkForEnoughMoneyOnAccount(currentPlayer, amountToPay, players)) {
							trade.safeTransferMoney(currentPlayer, toPlayer, amountToPay, players);
							return true;
						}
					}
				}
			}
		}

		int amountToRaise = amountToPay - players[currentPlayer].getBalance();
		// Hvis vi kan rejse de penge der mangler ved at sælge grunde
		if ((asset.checkPropertySaleValue(currentPlayer, amountToRaise, fields))==true) {

			//Vi begynder at sælge grunde indtil der er solgt nok grunde til at vi kan betale.
			for (int fieldCount = 0; fieldCount<=39;fieldCount++) {
				if(fields[fieldCount] instanceof OwnerFields && ((OwnerFields)fields[fieldCount]).getOwner() == currentPlayer  && (checkForEnoughMoneyOnAccount(currentPlayer, amountToPay, players) == false)) {
					trade.sellProperty(currentPlayer, 0, fieldCount, players, fields);
				}
			}
			trade.safeTransferMoney(currentPlayer, toPlayer, amountToPay, players);
			return true;
		}
		return false;
	}

	/**
	 * bankruptcy() - En metode der overfører alle aktiver, "penge og grunde" til den spiller man er gået bankerot eller banken og giver en tekst til spiller at han er gået bankerot.
	 * @param currentPlayer - Modtager en int som er den aktivespiller.
	 * @param toPlayer - modtager en int som er den spiller han skal overfører til.
	 * @param players - Det er et objekt af typen Player[]
	 * @param fields - Det er et objekt af typen Field[]
	 * @param view - Det er et objekt af ViewCTRL.
	 */
	public void bankruptcy(int currentPlayer, int toPlayer, Player[] players, Field[] fields, ViewCTRL view) {
		trade.transferAssets(currentPlayer, toPlayer, players, fields);
		players[currentPlayer].setBroke(true);
		view.writeText(players[currentPlayer].getPlayerName() + " er gået bankerot.");
		view.updatePlayerName(currentPlayer, players[currentPlayer].getPlayerName() + " [bankerot]");
	}	

}
