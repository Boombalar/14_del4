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
	//	
	//	
	//	
	//	
	//	private void startGame();
	//	String[] playerAmount = {6, 5, 4, 3, 2};
	//	playernum = 

	private void fieldRulesSwitch(Player[] players, int currentplayer) {

		int fieldType = fields[players[currentplayer].getPosition()].getType();
		int position = players[currentplayer].getPosition();
		int owner = (((OwnerFields)fields[position]).getOwner());
		switch (fieldType) {

		//ProbertyField
		case 0:	
			int propertyValue = (((PropertyFields)fields[position]).getPropertyValue());

			if (owner == 0) {
				boolean	answer = view.getUserAnswer("Vil du købe denne grund?", "ja", "nej");
				if(answer == true) {
					//indsæt transactionmetode (currentplayer, owner, propertyValue);
					//Her bliver feltet skiftet til currentplayer . OBS currentplayer skal skiftes når gamesekvens bliver lavet. 
					view.updatePlayerAccount(currentplayer, players[currentplayer].getBalance());
					(((PropertyFields)fields[position]).setOwner(currentplayer);
					view.updateOwnership(currentplayer, position);
					view.writeText("Du har købt " + fields[position].getName()+ " for " + propertyValue + " kr."); 

					// SKAL DER VÆRE SPILLESKIFT HER EFTER ??????
				}

				if(owner != 0 && owner != currentplayer) {

					int propertyRent = getRentFromPropertyField(position);
					//transaktionmetode (currentplayer, owner, propertyRent)
					view.updatePlayerAccount(currentplayer, players[currentplayer].getBalance());
					view.updatePlayerAccount(owner, players[owner].getBalance());
					view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + propertyRent + " til " + players[owner].getPlayerName());

				}
				if(owner == currentplayer) {
					view.writeText("Du er landet på " + fields[position].getName() + " du ejer selv denne grund");

				}


				//ShipsFields
			case 1:
				int shippingValue = (((ShipFields)fields[position]).getPropertyValue());

				if(owner == 0) {
					boolean answer = view.getUserAnswer("Du er landet på" + fields[position].getName() + " vil du købe grunden", "ja", "nej");
					if(answer == true) {
						//transaktionmetode ( currentplayer, owner, shippingvalue)
						view.updatePlayerAccount(currentplayer, shippingValue);
						view.updateOwnership(currentplayer, position);
						(((ShipFields)fields[position]).setOwner(currentplayer);
						view.writeText("Du har købt " + fields[position].getName() + " for " + propertyValue + " kr" );

					} 
				}



				//Breweryfields
			case 2:
				int breweryValue = (((BreweryFields)fields[position]).getPropertyValue());

				if(owner == 0) {
					boolean answer = view.getUserAnswer("Du er landet på " + fields[position].getName() + "vil du købe grunden", "ja", "nej");
					if(answer == true) {
						//transaktionmetode(currentplayer, owner, shippingvalue)
						view.updatePlayerAccount(currentplayer, breweryValue);
						view.updateOwnership(currentplayer, position);
						(((BreweryFields)fields[position]).setOwner(currentplayer);
						view.writeText("Du har købt " + fields[position].getName() + " for " + propertyValue + " kr");
						if(owner != 0 && owner != currentplayer) {

						}

					}
				}
				//Taxfields
			case 3:

				//Chancefield
			case 4:

				//Startfield
			case 5:

				//NoActionField
			case 6:

				//GoToJailField
			case 7:

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

			switch (fieldRent[3]) {

			case 0:
				int rentOnShip = fieldRent[0];
				if (checkForGroupOwnership(fieldOwner, Field[] fields, fieldNum) == true) {
					return rentOnShip;
					
				}
			case 1:
			case 2:
			case 3:
			}
			
				

		}
	}