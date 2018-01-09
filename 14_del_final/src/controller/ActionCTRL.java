package controller;

import java.util.concurrent.TimeUnit;

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
		
		gameSequence();
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

	
	  private void gameSequence() {
		  int diceValue;
		  int oldPlayerPosition = 0;
		  int newPlayerPosition;
		  while (!checkWinner()) {
			  while (true) {
				  for (int j = 0; j < numberOfPlayers; j++) {
					  if (players[j].checkBroke()) {
						  j++;
						}
					  view.writeText("Det er spiller  " + j + "'s tur nu");
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
						// String[] fieldToUpgrade = {//Spillerens ejede grunde
						 //Lav logik for spillers choice
					
							
					 }
			  }
		  }
	  	}
		  if (checkWinner())
			  printWinner();
	  }
 
	  private boolean checkWinner() {
		  int numberOfPLayersBroke = 0;
		  boolean winner = false;
		 for (int i = 0; i < numberOfPlayers; i++) {
			if (players[i].checkBroke())
				numberOfPLayersBroke++;
		}
		 if (numberOfPLayersBroke == numberOfPlayers-1)
			  winner = true;
		return winner;
			  
	  }
	  private void printWinner() {
		  if (checkWinner()) {
			  for (int i = 0; i < numberOfPlayers; i++) {
				  boolean checkPlayerBroke = players[i].checkBroke();
				if (!checkPlayerBroke) {
					view.getUserResponse("Afslut spil", "Spiller " + i + " har vundet!!");
					try {
						TimeUnit.SECONDS.sleep(1);
						System.exit(0);
					}
					catch (InterruptedException e) {
						System.out.println("Fejl i sleep når program lukker");
						e.printStackTrace();
					}
				}
				
			}
		  }
	  }
}