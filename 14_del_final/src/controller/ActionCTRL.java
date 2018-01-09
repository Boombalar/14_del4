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

	int numberOfPlayers;
	int lostPlayerCount;

	public ActionCTRL() {
		initialiseGame();
	}

	public void initialiseGame() {
		
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

	
	  private void gameSequences() {
		  int diceValue;
		  int oldPlayerPosition = 0;
		  int newPlayerPosition;
		  while (!checkWinner()) {
			  while (true) {
				  for (int j = 0; j < numberOfPlayers; j++) {
					  if (players[j].checkBroke()) {
						  j++;
						  lostPlayerCount++;
						}
					  view.getUserResponse("Fortsæt", "Det er spiller  " + j + "'s tur nu");
					  String[] playerChoice = {"Slå terninger", "Køb/sælg huse og hoteller", "Sælg grund"};
					  String choiceOfPlayer = view.getDropDownChoice("vælg", playerChoice);
					 switch(choiceOfPlayer) {
					 case "Slå terninger": 
						 dieCup.getDiceValue();
						 diceValue = dieCup.getDiceValue();
						 oldPlayerPosition += diceValue;
						 newPlayerPosition = oldPlayerPosition += dieCup.getDiceValue();
						 view.updateDice(dieCup.getDie1Value(), dieCup.getDie2Value());						  
						 view.updatePlayerPosition(j, oldPlayerPosition, newPlayerPosition);
						 if (newPlayerPosition > 40) {
							 newPlayerPosition -= 40;
							 players[j].recieveMoney(4000);
						 }
						 break;
					 case "Køb/sælg huse og hoteller":
						 view.getUserResponse("OK", "Du har valgt Køb/sælg huse og hoteller");
						// String[] fieldToUpgrade = {//Spillerens ejede grunde
					
							
					 }

					  
					  //Lav logik for spillers choice
			  }
			  
		  }
		  
	  	}
	  }
	  
	  private boolean checkWinner() {
		  int winner = lostPlayerCount-1;
		  if (winner < numberOfPlayers)
			  
			  
	  }
	// // private void tansfer(int currentPlayer, int receivePlayer, boolean
	// direction, int amount) {
	// // if (direction == true);
	// // players[currentPlayer].removemoney(amount);
	// // players[receivePlayer].recievemoney(amount);
	// //
	// // //recievemoney og removemoney.
		//}
		//else { (direction == false);
		//players[currentPlayer].recieveMoney(amount);
		//players[receivePlayer].removeMoney.(amount);
	//}
	//	
	//	
	//	private void switchRules() {
	//		
	//	}
	//	
	//	
	//	private void startGame();
	//	String[] playerAmount = {6, 5, 4, 3, 2};
	//	playernum = 

	//REGEL METODER HERUNDER.
	//}
}