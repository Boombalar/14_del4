package controller;

import java.util.concurrent.TimeUnit;

import model.*;
import view.*;

public class ActionCTRL {
	private int numberOfPlayers;
	private int lostPlayerCount;
	private int oldPlayerPosition = 0; //En given spiller start position på en runde
	private int newPlayerPosition; //Den position en given spiller rykkes til når terningerne er slået
	private Board board;
	private Field[] fields;
	private CreatePlayers makePlayers;
	private Player[] players;
	private ViewCTRL view;
	private ChanceCardCTRL chanceCard;
	private DieCup dieCup;
	private Toolbox toolbox;

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

		toolbox = new Toolbox();
	}
	/**
	 * gameSequence
	 * kører gamesekvens for en spiller
	 * 
	 */
	private void gameSequence() {
		int currentPlayer = 1; //Den første spiller instaniseres til spiller 1
		int diceValue; //Den samlede mængde af terningerne
		int amountOfProperties; //Den mængde grunde en given spiller ejer?
		int index; //Index til hvad?
		String[] propertyArray; //Det String array som indeholder alle de grunde en given spiller ejer.
		String choice; //Det valg en given spiller vælger ud fra propertyString array.
		int[] returnValue; //Hvilken specifik returværdi det dette?s

		while (!checkWinner()) { //Et while(true) loop der kører indtil vi har fundet 1 vinder

			if(players[currentPlayer].getTurnsInJail()==1) {
				if (players[currentPlayer].getReleaseCard() > 0) {


					String[] playerChoiceJail = {"Betal 1000 kr", "Brug releaseCard"};
					String choiceJailPlayer = view.getDropDownChoice("Vælg hvordan du kommer ud af fængsel", playerChoiceJail);


					if (choiceJailPlayer=="Brug releaseCard") {
						players[currentPlayer].setTurnsInJail(0);}



					if (choiceJailPlayer=="Betal 1000kr") {
						view.writeText("Betal 1000 kr for at komme ud af fængsel");
						toolbox.payMoney(currentPlayer, 0, players, fields, 1000);
					}

				} else {					
					String playerChoiceRelease = "Betal 1000 kr for at komme ud af fængsel";
					toolbox.payMoney(currentPlayer, 0, players, fields, 1000);
				}
			}

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

			// Slår terningerne, ændrer position i model lag og opdaterer view lag
			// Håndterer om man kommer over start og får 4.000.
			case "Slå terninger": 
				//slå terninger
				dieCup.shake();
				diceValue = dieCup.getDiceValue();

				//skift position på spiller i model lag
				oldPlayerPosition = players[currentPlayer].getPosition();
				newPlayerPosition = oldPlayerPosition + diceValue;
				players[currentPlayer].setPosition(oldPlayerPosition + diceValue);

				//Update view
				view.updateDice(dieCup.getDie1Value(), dieCup.getDie2Value());						  
				view.updatePlayerPosition(currentPlayer, oldPlayerPosition, newPlayerPosition);

				//Håndter om man kommer over start og får 4000.
				if (newPlayerPosition > 39) {
					newPlayerPosition -= 40;
					players[currentPlayer].recieveMoney(4000);
					view.writeText("Spiller " + currentPlayer + " har passeret start og får 4000 kroner");
				}
				fieldRulesSwitch(currentPlayer);
				if (dieCup.getDie1Value() == dieCup.getDie2Value()) {
					currentPlayer--;
					/*
					players[currentPlayer].changeEqualEyes() ++;
					if(players[currentPlayer].changeEqualEyes() == 3) {
						jail();
						players[currentPlayer].changeEqualEyes(-3);
					}
					*/
					
				}
				break;

				// Køb huse og hoteller.
				// Finder de felter hvor spilleren ejer hele grupper. 
				// Giver mulighed for at bygge på de felter.
			case "Køb huse og hoteller":
				//Vi starter med at finde ud af hvor mange PropertyFields man ejer hvor man har hele gruppen
				//Således vi kan opbygge et array til dropdownlisten.

				amountOfProperties = 0;
				for(int fieldCount = 0;fieldCount<=39;fieldCount++) {//Går hele brættet igennem
					if (fields[fieldCount] instanceof PropertyFields) {
						if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer && toolbox.checkForGroupOwnership(currentPlayer, fields, fieldCount) == true) {
							amountOfProperties++;
						}
					}
				}

				//Vi laver Array til DropDown listen
				propertyArray = new String[amountOfProperties];
				index = 0;

				//Vi populerer Array
				//populer array med felt hvis man ejer det og har hele gruppen eks.
				//1. Hvidovrevej
				//3. Rødovrevej
				for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
					if (fields[fieldCount] instanceof PropertyFields) {
						if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer && toolbox.checkForGroupOwnership(currentPlayer, fields, fieldCount) == true) {
							propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
						}
					}
					index++;
				}

				//Vi modtager svar fra dropdown listen
				choice = view.getDropDownChoice("Vælg grund du vil bygge på", propertyArray);
				int chosenFieldNumber=Character.getNumericValue(choice.charAt(0));

				//Vi bygger hvis man ejer hele gruppen, og har råd
				if( toolbox.checkForGroupOwnership(currentPlayer, fields, chosenFieldNumber)) {
					if (players[currentPlayer].getBalance() > toolbox.getHousePrice(chosenFieldNumber, fields)) {
						returnValue = (((OwnerFields)fields[chosenFieldNumber]).returnValue());
						if (returnValue[6]<5) {//hvis der er mindre end 5 huse på feltet
							returnValue[6]++;
							players[currentPlayer].removeMoney(toolbox.getHousePrice(chosenFieldNumber, fields));
						}else {
							view.writeText("Du kan ikke bygge flere huse på denne grund");
						}
					}
				}
				break;


				//Sælg huse og hoteller.
				//Find de grunde hvor spilleren ejer huse
				//sælg et hus.
			case "Sælg huse og hoteller":

				//Find antal propertyfields med huse
				//Således vi kan lave Array til dropdown

				int[] returnValue;
				amountOfProperties=0;
				for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
					if (fields[fieldCount] instanceof PropertyFields) {
						if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
							returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
							if (toolbox.getHousesOnProperty(currentPlayer, fields, fieldCount, returnValue)>0) {
								amountOfProperties++;
							}	
						}
					}
				}

				//Lav array til dropdown
				propertyArray = new String[amountOfProperties];
				index=0;

				//populer array med felt hvis feltet har huse på sig eks.
				//1. Hvidovrevej
				//3. Rødovrevej
				for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
					if (fields[fieldCount] instanceof PropertyFields) {
						if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
							returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
							if (toolbox.getHousesOnProperty(currentPlayer, fields, fieldCount, returnValue)>0) {
								propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
							}
						}
					}
					index++;
				}

				//Sælg hus
				choice = view.getDropDownChoice("Vælg grund du vil sælge huse fra", propertyArray);
				chosenFieldNumber=Character.getNumericValue(choice.charAt(0));
				toolbox.sellBuilding(currentPlayer, players, fields, chosenFieldNumber);
				break;

				//Sælg grund hvis man ejer den og der ikke er nogle huse på
				//Man kan sælge til banken eller anden spiller
			case "Sælg grund":

				//Find ud af hvor mange grunde man ejer som ikke har huse på sin gruppe til array
				for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
					if (fields[fieldCount] instanceof PropertyFields && toolbox.getHousesOnGroup(currentPlayer, fields, fieldCount)==0) {
						if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
							amountOfProperties++;
						}
					}
				}

				//Lav Array
				propertyArray = new String[amountOfProperties];
				index=0;

				//populer array med felt hvis man ejer det og der ikke er huse på gruppen
				for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
					if (fields[fieldCount] instanceof PropertyFields && toolbox.getHousesOnGroup(currentPlayer, fields, fieldCount)==0) {
						if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
							propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
						}
					}
					index++;
				}

				//vælge grund i dropdown
				choice = view.getDropDownChoice("Vælg hvilken grund du vil sælge", propertyArray);
				chosenFieldNumber=Character.getNumericValue(choice.charAt(0));

				//Vælge hvilken spiller man vil sælge til 
				//0 er banken.

				//Lav array og populer
				String[] playerCountArray = new String[players.length+1];
				for (int playerCount = 0;playerCount <= players.length;playerCount++) {
					playerCountArray[playerCount]= Integer.toString(playerCount);
				}

				//Vælg spiller eller bank
				String playerSellChoice = view.getDropDownChoice("Hvilken spiller vil du sælge til? 0 er til banken", playerCountArray);
				int chosenPlayerNumber=Character.getNumericValue(playerSellChoice.charAt(0));

				//Sælg grund.
				returnValue = ((PropertyFields)fields[chosenFieldNumber]).getReturnValue();
				toolbox.sellProperty(currentPlayer, chosenPlayerNumber, players, fields, chosenFieldNumber);
			}

			break;
			//Lav logik for spillers choice
		}

		currentPlayer++;
		if (currentPlayer > players.length)
			currentPlayer = 1;

		if (checkWinner()) {
			printWinner();
			break;
		}
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

	private void updateEntireBoard(Field[] fields, Player[] players, ViewCTRL view) {

		//Updater fields ownership på bræt
		for (int fieldCount = 0;fieldCount <= 39; fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields) {
				view.updateOwnership(((PropertyFields)fields[fieldCount]).getOwner(), fieldCount);
			}
		}
		for (int playerCount = 1 ; playerCount <= players.length; playerCount++) {
			if (players[playerCount].getBroke()) {
				view.turnOffPlayer(playerCount);
			}
		}
	}

// indsæt fieldruleswitch og alle metoder.