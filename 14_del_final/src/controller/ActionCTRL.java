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

	private void switchRules(Player[] players, int currentplayer) {
		int fieldType;
		fieldType = fields[players[currentplayer].getPosition()].getType();

		switch (fieldType) {

		//ProbertyField
		case 0:
			int position = players[currentplayer].getPosition();
			int owner = (((PropertyFields)fields[position]).getOwner());	
			if (owner == 0) {
				boolean	answer = view.getUserAnswer("Vil du købe denne grund?", "ja", "nej");
				if(answer == true) {
					//indsæt transactionmetode
					//Her bliver feltet skiftet til currentplayer . OBS currentplayer skal skiftes når gamesekvens bliver lavet. 
					(((PropertyFields)fields[position]).setOwner(currentplayer);
					//GUI kald, så spiller for en teskt om at han har købt feltet, message "Du har nu købt felt" + field[position].getName
				}
				if(owner != 0 && owner != currentplayer) {



				}
				//ShipsFields
			case 1:
				//				if(owner == 0) {
				//
				//				}else 

			}
			//Other
		case 2:
			if(owner == 0) {
				owner = 2;
			}
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
		}


		//REGEL METODER HERUNDER.