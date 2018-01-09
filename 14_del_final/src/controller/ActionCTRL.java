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
		int currentPlayer = 1; //Den første spiller instaniseres til spiller 1
		int diceValue; //Den samlede mængde af terningerne
		int oldPlayerPosition = 0; //En given spiller start position på en runde
		int newPlayerPosition; //Den position en given spiller rykkes til når terningerne er slået
		while (!checkWinner()) { //Et while(true) loop der kører indtil vi har fundet 1 vinder
			/*
			if(players[currentPlayer].checkTurnInJail()==1)
				if(players[currentPlayer].getPosition() == 11)
					if (players.releaseCard >=1); {
						String[] playerChoiceJail = {"Betal 1000 kr", "Brug releaseCard"};
						String ChoiceJailPlayer = view.getDropDownChoice("Vælg", playerChoiceJail);
						view.updatePlayerAccount(player, amount);
					}
							else {
								String playerChoiceRelease = "Betal 1000 kr for at komme ud af fængsel";
								//raisemoney
								players[currentPlayer].removeMoney(1000);
							}

						}
			 */
			while (true) {
				// Hvis en spiller er broke, så gå ud af loop
				if(players[currentPlayer].checkBroke())
					break;
				// Lav Startmenu for spiller
				view.writeText("Det er spiller  " + currentPlayer + "'s tur nu");
				String[] playerChoice = {"Slå terninger", "Køb huse og hoteller","Sælg huse og hoteller", "Sælg grund"};
				String choiceOfPlayer = view.getDropDownChoice("vælg", playerChoice);
				
				//Håndtér valg fra menu
				switch(choiceOfPlayer) {
				
				// Slå terninger
				// Slår terningerne, ændrer position i model lag og opdaterer view lag
				// Håndterer om man kommer over start og får 4.000.
				case "Slå terninger": 
					dieCup.getDiceValue();
					diceValue = dieCup.getDiceValue();
					oldPlayerPosition = players[currentPlayer].getPosition();
					newPlayerPosition = oldPlayerPosition + diceValue;
					players[currentPlayer].setPosition(oldPlayerPosition + diceValue);
					view.updateDice(dieCup.getDie1Value(), dieCup.getDie2Value());						  
					view.updatePlayerPosition(currentPlayer, oldPlayerPosition, newPlayerPosition);
					if (newPlayerPosition > 39) {
						newPlayerPosition -= 40;
						players[currentPlayer].recieveMoney(4000);
						view.writeText("Spiller " + currentPlayer + " har passeret start og får 4000 kroner");
					}
					break;
					
				// Køb huse og hoteller.
				// Finder de felter hvor spilleren ejer hele grupper. 
				// Giver mulighed for at bygge på de felter.
				case "Køb huse og hoteller":
					//Vi starter med at finde ud af hvor mange
					
					int amountOfProperties;
					for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
						if (fields[fieldCount] instanceof PropertyFields) {
							if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
								amountOfProperties++;
							}
						}
					}
					String[] propertyArray = new String[amountOfProperties];
					int index;

					for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
						if (fields[fieldCount] instanceof PropertyFields) {
							if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
								propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
							}
						}
					}
					String choice = view.getDropDownChoice("Vælg grund du vil bygge på", propertyArray);
					int chosenFieldNumber=Character.getNumericValue(choice.charAt(0));
					if( Toolbox.checkForGroupOwnership(currentPlayer, fields, chosenFieldNumber)) {
						if (players[currentPlayer].getBalance() > Toolbox.getHousePrice(chosenFieldNumber, fields)) {
							int[] returnValue =	(((OwnerFields)fields[chosenFieldNumber]).returnValue());
							if (returnValue[6]<5) {//hvis der er mindre end 5 huse på feltet
								returnValue[6]++;
								players[currentPlayer].removeMoney(Toolbox.getHousePrice(chosenFieldNumber, fields));
							}else {
								view.writeText("Du kan ikke bygge flere huse på denne grund");
							}
						}
					}
					break;

				case "Sælg huse og hoteller":
					int amountOfProperties;
					int[] returnValue;
					for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
						if (fields[fieldCount] instanceof PropertyFields) {
							if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
								returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
								if (Toolbox.getHOusesOnProperty(currentPlayer, fields, fieldCount, returnValue)>0) {
									amountOfProperties++;
								}
							}
						}
					}
					String[] propertyArray = new String[amountOfProperties];
					int index;

					for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
						if (fields[fieldCount] instanceof PropertyFields) {
							if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
								returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
								if (Toolbox.getHOusesOnProperty(currentPlayer, fields, fieldCount, returnValue)>0) {
									propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
								}
							}
						}
					}
					String choice = view.getDropDownChoice("Vælg grund du vil sælge huse fra", propertyArray);
					int chosenFieldNumber=Character.getNumericValue(choice.charAt(0));
					players[currentPlayer].recieveMoney(Toolbox.sellBuilding(currentPlayer, players, fields, chosenFieldNumber));
					break;

				case "Sælg grund":
					int amountOfProperties;
					for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
						if (fields[fieldCount] instanceof PropertyFields) {
							if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
								amountOfProperties++;
							}
						}
					}
					String[] propertyArray = new String[amountOfProperties];
					int index;

					for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
						if (fields[fieldCount] instanceof PropertyFields) {
							if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
								propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
							}
						}
					}
					String choice = view.getDropDownChoice("Vælg hvilken grund du vil sælge", propertyArray);
					int chosenFieldNumber=Character.getNumericValue(choice.charAt(0));
					
					String[] playerCountArray = new String[players.length+1];
					for (int playerCount = 0;playerCount <= players.length;playerCount++) {
						playerCountArray[playerCount]= Integer.toString(playerCount);
					}
					String playerSellChoice = view.getDropDownChoice("Hvilken spiller vil du sælge til? 0 er til banken", playerCountArray);
					int chosenPlayerNumber=Character.getNumericValue(playerSellChoice.charAt(0));
					
					
					if (Toolbox.getHOusesOnProperty(currentPlayer, fields, fieldCount, returnValue)==0) {
						Toolbox.sellProperty(currentPlayer, chosenPlayerNumber, players, fields, chosenFieldNumber);
						
					}
					else {
						view.writeText("Du kan ikke sælge grunden for der er huse på");
					}
					break;
					
					//Lav logik for spillers choice


				}
			}
			currentPlayer++;
			if (currentPlayer > players.length)
				currentPlayer = 1;
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