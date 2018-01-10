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

	Toolbox toolbox;

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

		toolbox = new Toolbox();
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


					if (toolbox.getHousesOnProperty(currentPlayer, fields, fieldCount, returnValue)==0) {
						toolbox.sellProperty(currentPlayer, chosenPlayerNumber, players, fields, chosenFieldNumber);

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

	private void fieldRulesSwitch (int playerNumber) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int position = players[playerNumber].getPosition();
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
					view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
					PropertyFields wantedFieldChange = ((PropertyFields)fields[position]);
					wantedFieldChange.setOwner(playerNumber);
					view.updateOwnership(playerNumber, position);
					view.writeText("Du har købt " + fields[position].getName()+ " for " + propertyValue + " kr."); 
				}
			}
			if(owner != 0 && owner != playerNumber) {
				int propertyRent = getRentFromPropertyField(position);
				//transaktionmetode (currentplayer, owner, propertyRent)
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
				view.updatePlayerAccount(owner, players[owner].getBalance());
				view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + propertyRent + " til " + players[owner].getPlayerName());
			}
			if((owner == playerNumber)) { 
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
					view.updatePlayerAccount(playerNumber, shippingPropertyValue);
					view.updateOwnership(playerNumber, position);
					ShipFields wantedFieldChange = ((ShipFields)fields[position]);
					wantedFieldChange.setOwner(playerNumber);
					view.writeText("Du har købt " + fields[position].getName() + " for " + shippingPropertyValue + " kr" );
				}
			}

			if(owner != 0 && owner != playerNumber) {
				int shipRent = getRentFromShipField(position);
				//transaktionmetode (currentplayer, owner, shipRent)
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
				view.updatePlayerAccount(owner, players[owner].getBalance());
				view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + shipRent + " til " + players[owner].getPlayerName());
			}

			if(owner == playerNumber) {
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
					view.updatePlayerAccount(playerNumber, breweryPropertyValue);
					view.updateOwnership(playerNumber, position);
					BreweryFields wantedFieldChange = ((BreweryFields)fields[position]);
					wantedFieldChange.setOwner(playerNumber);
					view.writeText("Du har købt " + fields[position].getName() + " for " + breweryPropertyValue + " kr");
				}
			}

			if(owner != 0 && owner != playerNumber) {
				int breweryRent = (getRentFromBreweryField(position) * dieCup.getDiceValue());
				//transaktionmetode (currentplayer, owner, breweryRent)
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
				view.updatePlayerAccount(owner, players[owner].getBalance());
				view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + breweryRent + " til " + players[owner].getPlayerName());
			}

			if(owner == playerNumber) {
				view.writeText("Du er landet på " + fields[position].getName() + " du ejer selv dette bryggeri");
			}
			break;

		case 3:
			//Taxfields
			int[] taxValue = (((TaxField)fields[position]).getReturnValue());
			//transaktionmetode (currentplayer, 0, taxValue[0])
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			view.writeText("Du er landet på " + fields[position].getName() + " du skal betale " + taxValue[0] + " i skat");
			break;


		case 4:
			//Chancefield			
			view.writeText("Du er landet på 'Prøv lykken', du trækker et chance kort");
			chanceCard.draw();
			String chanceCardText = chanceCard.getDescription();
			view.showChanceCard(chanceCardText);
			chanceCardRules(playerNumber);
			break;

		case 7:
			//GoToJailField
			players[playerNumber].setPosition(11);
			players[playerNumber].setTurnsInJail(1);
			view.updatePlayerPosition(playerNumber, position, 11);
			view.writeText("Du er landet på " + fields[position].getName() + " du skal nu i fængsel");
			break;
		}
	}

	public int getRentFromPropertyField (int fieldNum) {
		int[] fieldRent = (((PropertyFields)fields[fieldNum]).returnValue());
		int returnValue = fieldRent[fieldRent[6]];
		int fieldOwner = (((PropertyFields)fields[fieldNum]).getOwner());

		if (toolbox.checkForGroupOwnership(fieldOwner, fields, fieldNum) == true) {
			returnValue = fieldRent[0] * 2;
		}
		return returnValue;
	}

	public int getRentFromShipField(int fieldNum) {
		int[] fieldRent = (((ShipFields)fields[fieldNum]).returnValue());
		int fieldOwner = (((ShipFields)fields[fieldNum]).getOwner());
		int numOfOwnedShipFields = (toolbox.checkNumOfOwnFieldsWithType(fieldOwner, fields, fieldNum, fields[fieldNum].getType()));
		return fieldRent[(numOfOwnedShipFields - 1)];
	}

	public int getRentFromBreweryField(int fieldNum) {
		int[] fieldRent = (((BreweryFields)fields[fieldNum]).returnValue());
		int fieldOwner = (((BreweryFields)fields[fieldNum]).getOwner());
		int numOfOwnedBrewFields = (toolbox.checkNumOfOwnFieldsWithType(fieldOwner, fields, fieldNum, fields[fieldNum].getType()));
		return fieldRent[(numOfOwnedBrewFields - 1)];
	}

	public void chanceCardRules (int playerNumber) {

		int chanceCardType = chanceCard.getType();
		int[] chanceCardValueArray = chanceCard.getReturnValue();

		switch (chanceCardType) {

		case 1: // TransactionCard
			int transactionValue = chanceCardValueArray[0];
			if (transactionValue < 0)
				players[playerNumber].removeMoney(transactionValue*(-1));
			else
				players[playerNumber].recieveMoney(transactionValue);
			break;

		case 2: // MoveToCards
			MoveToCardsRules(playerNumber); // logik og viewCTRL-kald ligger i denne metode.
			break;

		case 3: // ReleaseCards
			players[playerNumber].setTurnsInJail(1);;
			break;

		case 4: //TaxCards
			int numberofhouses = toolbox.getNumberOfHousesFromPlayer(playerNumber, fields);
			int numberofhotels = toolbox.getNumberOfHotelsFromPlayer(playerNumber, fields);
			players[playerNumber].removeMoney(chanceCardValueArray[1]*numberofhouses);
			players[playerNumber].removeMoney(chanceCardValueArray[2]*numberofhotels);
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			break;
		}
	}

	public void MoveToCardsRules (int playerNumber) {
		int[] chanceCardValueArray = chanceCard.getReturnValue();
		int oldPosition = players[playerNumber].getPosition();
		int moveToField = chanceCardValueArray[1];

		switch (chanceCardValueArray[2]){

		case 1:
			// Blot flyttekort til et bestemt felt.
			if((chanceCard.cardnumber == 19) || (chanceCard.cardnumber == 22)) {
				players[playerNumber].setPosition(moveToField);
				view.updatePlayerPosition(playerNumber, oldPosition, moveToField);
				players[playerNumber].setTurnsInJail(1);
			}
			else {
				players[playerNumber].setPosition(moveToField);
				view.updatePlayerPosition(playerNumber, oldPosition, moveToField);
			}
			break;

		case 2: // Et flyttekort, hvor man flytter til det nærmeste felt med redderi.			
			int[] shippingArray = new int[4];
			// Der oprettes et loop, som smider lokationenerne fra feltnumrene, ind i et array, hvis typen er "1" - som er shippingField.
			for(int i=0 ; i < 39 ; i++) {
				if (fields[i].getType() == 1) {
					shippingArray[i] = i;
				}
			}
			// Herefter kommer der et tjek om hvad det efterfølgende shippingField
			if( oldPosition < shippingArray[0]) {
				players[playerNumber].setPosition(shippingArray[0]);
				view.updatePlayerPosition(playerNumber, oldPosition, shippingArray[0]);
			}

			else if( oldPosition > shippingArray[0] && oldPosition < shippingArray[1]) {
				players[playerNumber].setPosition(shippingArray[1]);
				view.updatePlayerPosition(playerNumber, oldPosition, shippingArray[1]);
			}

			else if( oldPosition > shippingArray[1] && oldPosition < shippingArray[2]) {
				players[playerNumber].setPosition(shippingArray[2]);
				view.updatePlayerPosition(playerNumber, oldPosition, shippingArray[2]);
			}

			else if( oldPosition > shippingArray[2] && oldPosition < shippingArray[3]) {
				players[playerNumber].setPosition(shippingArray[3]);
				view.updatePlayerPosition(playerNumber, oldPosition, shippingArray[3]);
			}

			break;

		case 3: // Ryk tre felter tilbage.
			players[playerNumber].setPosition(oldPosition-3);
			view.updatePlayerPosition(playerNumber, oldPosition, oldPosition-3);
			break;
		}
	}
}