package controller;

import model.*;
import view.*;

public class ActionCTRL {
	Board board;
	Field[] fields;

	CreatePlayers makePlayers;
	Player[] players;

	ViewCTRL view;
	ChanceCardCTRL chanceCard;
	DieCup dieCup;

	public ActionCTRL() {
		initialiseGame();
	}

	public void initialiseGame() {
		int numberOfPlayers;

		//Lav bræt model.
		board = new Board();		
		fields = board.getFields();

		//Opret bræt.
		view = new ViewCTRL(fields);

		//Hent antal spillere.
		String[] lines = {"2","3","4","5","6"};
		numberOfPlayers = Integer.parseInt(view.getDropDownChoice("Vælg antal spillere 2-6", lines));

		//Lav player array.
		makePlayers = new CreatePlayers(numberOfPlayers); 
		players = makePlayers.getPlayers();

		//Opret antal spillere på bræt.
		view.makeGuiPlayers(players);

		//Lav chancekort CTRL.
		chanceCard = new ChanceCardCTRL();

		//Lav raflebæger.
		dieCup = new DieCup();
	}


	//	private void gameSequences();
	//	 	if (players[currentPlayer].checkBroke)
	//	 		i++
	//	//hele gameSequences while loop i et while loop i et forloop. 
	//	//metodee der har logikken bag alle spillers tur 
	//	}
	//	private void tansfer(int currentPlayer, int	receivePlayer, boolean direction, int amount) {
	//		if (direction == true);
	//		players[currentPlayer].removemoney(amount);
	//		players[receivePlayer].recievemoney(amount);
	//		
	//		//recievemoney og removemoney. 
	//		}else { (direction == false);
	//		players[currentPlayer].recieveMoney(amount);
	//		players[receivePlayer].removeMoney.(amount);	
	//}
	//	private void startGame();
	//	String[] playerAmount = {6, 5, 4, 3, 2};
	//	playernum = 

	private void fieldRulesSwitch(Player[] players, int currentplayer) {
		int fieldType = fields[players[currentplayer].getPosition()].getType();
		int position = players[currentplayer].getPosition();
		int owner = (((OwnerFields)fields[position]).getOwner());
		switch (fieldType) {

		case 0:	
			//ProbertyField
			int propertyValue = (((PropertyFields)fields[position]).getPropertyValue());
			if(owner == 0) {
				boolean	answer = view.getUserAnswer("Vil du købe denne grund?", "ja", "nej");
				if(answer == true) {
					//indsæt transactionmetode (currentplayer, owner, propertyValue);
					//Her bliver feltet skiftet til currentplayer . OBS currentplayer skal skiftes når gamesekvens bliver lavet. 
					view.updatePlayerAccount(currentplayer, players[currentplayer].getBalance());
					(((PropertyFields)fields[position]).setOwner(currentplayer);
					view.updateOwnership(currentplayer, position);
					view.writeText("Du har købt " + fields[position].getName()+ " for " + propertyValue + " kr."); 
				}
			}
			if(owner != 0 && owner != currentplayer) {
				int propertyRent = getRentFromPropertyField(position);
				//transaktionmetode (currentplayer, owner, propertyRent)
				view.updatePlayerAccount(currentplayer, players[currentplayer].getBalance());
				view.updatePlayerAccount(owner, players[owner].getBalance());
				view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + propertyRent + " til " + players[owner].getPlayerName());
			}
			if((owner == currentplayer)) { 
				view.writeText("Du er landet på " + fields[position].getName() + " du ejer selv denne grund");
			}

			break;
			
		case 1:
			//ShipFields
			int shippingPropertyValue = (((ShipFields)fields[position]).getPropertyValue());

			if(owner == 0) {
				boolean answer = view.getUserAnswer("Du er landet på" + fields[position].getName() + " vil du købe grunden", "ja", "nej");
				if(answer == true) {
					//transaktionmetode (currentplayer, owner, shippingPropertyValue)
					view.updatePlayerAccount(currentplayer, shippingPropertyValue);
					view.updateOwnership(currentplayer, position);
					(((ShipFields)fields[position]).setOwner(currentplayer);
					view.writeText("Du har købt " + fields[position].getName() + " for " + propertyValue + " kr" );
				}
			}
			if(owner != 0 && owner != currentplayer) {
				int shipRent = getRentFromShipField(position);
				//transaktionmetode (currentplayer, owner, shipRent)
				view.updatePlayerAccount(currentplayer, players[currentplayer].getBalance());
				view.updatePlayerAccount(owner, players[owner].getBalance());
				view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + shipRent + " til " + players[owner].getPlayerName());
			}
			if(owner == currentplayer) {
				view.writeText("Du er landet på " + fields[position].getName() + " du ejer selv dette rederi");
			}
			break;

		case 2:
			//Breweryfields
			int breweryPropertyValue = (((BreweryFields)fields[position]).getPropertyValue());
			if(owner == 0) {
				boolean answer = view.getUserAnswer("Du er landet på " + fields[position].getName() + "vil du købe grunden", "ja", "nej");
				if(answer == true) {
					//transaktionmetode(currentplayer, owner, breweryPropertyValue)
					view.updatePlayerAccount(currentplayer, breweryPropertyValue);
					view.updateOwnership(currentplayer, position);
					(((BreweryFields)fields[position]).setOwner(currentplayer);
					view.writeText("Du har købt " + fields[position].getName() + " for " + propertyValue + " kr");
				}
			}
			if(owner != 0 && owner != currentplayer) {
				int breweryRent = (getRentFromBreweryField(position) * dieCup.getDiceValue());
				//transaktionmetode (currentplayer, owner, breweryRent)
				view.updatePlayerAccount(currentplayer, players[currentplayer].getBalance());
				view.updatePlayerAccount(owner, players[owner].getBalance());
				view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + breweryRent + " til " + players[owner].getPlayerName());
			}
			if(owner == currentplayer) {
				view.writeText("Du er landet på " + fields[position].getName() + " du ejer selv dette bryggeri");
			}
			break;

		case 3:
			//Taxfields
			int[] taxValue = (((TaxField)fields[position]).getReturnValue());
			//transaktionmetode (currentplayer, 0, taxValue[0])
			view.updatePlayerAccount(currentplayer, players[currentplayer].getBalance());
			view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + taxValue[0] + " i skat");
			break;


		case 4:
			//Chancefield

			break;

		case 5:
			//Startfield - ingenting sker
			break;

		case 6:
			//NoActionField - ingenting sker
			break;

		case 7:
			//GoToJailField
			players[currentplayer].setPosition(11);
			players[currentplayer].setTurnsInJail(1);
			view.updatePlayerPosition(currentplayer, position, 11);
			view.writeText("Du er landet på " + fields[position].getName() + " du skal nu i fængsel");
			break;
		}
	}
	public int getRentFromPropertyField (int fieldNum) {
		int[] fieldRent = (((PropertyFields)fields[fieldNum]).returnValue());
		int fieldOwner = (((PropertyFields)fields[fieldNum]).getOwner());

		switch (fieldRent[6]) {

		case 0:
			int rentOnNoHouses = fieldRent[0];
			if (checkForGroupOwnership(fieldOwner, Field[] fields, fieldNum) == true) {
				rentOnNoHouses = rentOnNoHouses * 2;
			}
			return rentOnNoHouses;

		case 1:
			int rentOnOneHouse = fieldRent[1];
			return rentOnOneHouse;

		case 2: 
			int rentOnTwoHouse = fieldRent[2];
			return rentOnTwoHouse;

		case 3:
			int rentOnThreeHouse = fieldRent[3];
			return rentOnThreeHouse;

		case 4:
			int rentOnFourHouse = fieldRent[4];
			return rentOnFourHouse;

		case 5: 
			int rentOnOneHotel = fieldRent[5];
			return rentOnOneHotel;
		}

	}
	public int getRentFromShipField(int fieldNum) {
		int[] fieldRent = (((ShipFields)fields[fieldNum]).returnValue());
		int fieldOwner = (((ShipFields)fields[fieldNum]).getOwner());
		int numberOfOwnedShipFields = checkNumOfOwnedFields(fieldOwner, Field[] fields, fieldNum, fields[fieldNum].getType());

		switch (numberOfOwnedShipFields) {

		case 1:
			int rentOnShip = fieldRent[0];
			return rentOnShip;
		case 2:
			int rentOnTwoShip = fieldRent[1];
			return rentOnTwoShip;
		case 3:
			int rentOnThreeShip = fieldRent[2];
			return rentOnThreeShip;
		case 4:
			int rentOnFourShip = fieldRent[3];
			return rentOnFourShip;
		}
	}

	public int getRentFromBreweryField(int fieldNum) {
		int[] fieldRent = (((BreweryFields)fields[fieldNum]).returnValue());
		int fieldOwner = (((BreweryFields)fields[fieldNum]).getOwner());
		int numberOfOwnedBreweryFields = checkNumOfOwnedFields(fieldOwner, Field[] fields, fieldNum, fields[fieldNum].getType());

		switch (numberOfOwnedBreweryFields) {

		case 1:
			int rentOnOneBrewery = fieldRent[0];
			return rentOnOneBrewery;
		case 2:
			int rentOnTwoBrewery = fieldRent[1];
			return rentOnTwoBrewery;
		}
	}
	
}







