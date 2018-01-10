package controller;

import java.util.concurrent.TimeUnit;

import model.*;
import view.*;

public class ActionCTRL {
	private int numberOfPlayers;
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
		int amountOfProperties=0; //Den mængde grunde en given spiller ejer?
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
				String choiceOfPlayer = view.getDropDownChoice("Vælg", playerChoice);

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

					//Update view
					view.updateDice(dieCup.getDie1Value(), dieCup.getDie2Value());						  


					//Håndter om man kommer over start og får 4000.
					if (newPlayerPosition > 39) {
						newPlayerPosition -= 40;
						players[currentPlayer].recieveMoney(4000);
						view.writeText("Spiller " + currentPlayer + " har passeret start og får 4000 kroner");
					}

					players[currentPlayer].setPosition(newPlayerPosition);
					view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
					view.updatePlayerPosition(currentPlayer, oldPlayerPosition, newPlayerPosition);

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

					amountOfProperties=0;
					for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
						if (fields[fieldCount] instanceof PropertyFields) {
							if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
								returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
								if (toolbox.getHousesOnProperty(currentPlayer, fields, fieldCount)>0) {
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
								if (toolbox.getHousesOnProperty(currentPlayer, fields, fieldCount)>0) {
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
			view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
			currentPlayer++;
			if (currentPlayer > players.length-1){
				currentPlayer = 1;
			}

			if (checkWinner()) {
				printWinner();
				break;
			}
		}
	}

	private boolean checkWinner() {
		int numberOfPlayersBroke = 0;
		boolean winner = false;
		for (int i = 1; i <= numberOfPlayers; i++) {
			if (players[i].checkBroke())
				numberOfPlayersBroke++;
		}
		if (numberOfPlayersBroke == numberOfPlayers-1)
			winner = true;
		return winner;
	}

	private void printWinner() {
		if (checkWinner()) {
			for (int i = 1; i <= numberOfPlayers; i++) {
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

	/**
	 * fieldRulesSwitch() - En metode som switcher på hvilket type felt man er landet på
	 * @param playerNumber - Modtager et spiller nummer
	 */
	private void fieldRulesSwitch (int playerNumber) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int owner=0;
		if ((fields[this.newPlayerPosition]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[this.newPlayerPosition]).getOwner());
		}

		switch (fieldType) {

		case 0:	
			//ProbertyField
			int propertyValue = (((PropertyFields)fields[this.newPlayerPosition]).getPropertyValue());
			int[] fieldRent = (((PropertyFields)fields[this.newPlayerPosition]).getReturnValue());
			int numberOfHouses = fieldRent[6];
			int propertyRent = fieldRent[numberOfHouses];
			if(owner == 0) {
				boolean	answer = view.getUserAnswer("Vil du købe denne grund?", "ja", "nej");		
				if(answer == true) {
					toolbox.payMoney(playerNumber, owner, players, fields, propertyValue); 				//spiller køber grunden af brættet
					view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance()); 		//Update af gui.
					PropertyFields wantedFieldChange = ((PropertyFields)fields[this.newPlayerPosition]);
					//Herunder bliver feltets ejer skiftet.
					wantedFieldChange.setOwner(playerNumber);
					view.updateOwnership(playerNumber, this.newPlayerPosition);
					view.writeText(players[playerNumber].getPlayerName() + " har købt " + fields[this.newPlayerPosition].getName()+ " for " + propertyValue + " kr."); //gui tekst til spilleren
				}
			}
			if(owner != 0 && owner != playerNumber) {
				if ((toolbox.checkForGroupOwnership(owner, fields, this.newPlayerPosition) == true) && (fieldRent[6] == 0)) {
					propertyRent *= 2;
				}
				toolbox.payMoney(playerNumber, owner, players, fields, propertyRent);				 //transaction mellem to spiller.
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());			 //update af den aktive spillerens konto
				view.updatePlayerAccount(owner, players[owner].getBalance());						 //Update af den spiller som modtager penge
				view.writeText(players[playerNumber].getPlayerName() + " er landet på " + fields[this.newPlayerPosition].getName() + " du skal betale " + propertyRent + " til " + players[owner].getPlayerName()); //Gui i tekst til spilleren
			}
			//Her lander den aktivespiller på et felt som han selv ejer. 
			if((owner == playerNumber)) {  
				view.writeText(players[playerNumber].getPlayerName() +  "er landet på " + fields[this.newPlayerPosition].getName() + " du ejer selv denne grund");
			}

			break;

		case 1:
			//ShipFields
			shippingFieldRules(playerNumber, 1);
			break;

		case 2:
			//Breweryfields
			int breweryPropertyValue = (((BreweryFields)fields[this.newPlayerPosition]).getPropertyValue());
			int[] breweryFieldRent = (((BreweryFields)fields[this.newPlayerPosition]).returnValue());
			int numOfOwnedBrewFields = (toolbox.getNumberOfOwnedPropertiesInGroup(this.newPlayerPosition, fields, owner));

			if(owner == 0) {
				boolean answer = view.getUserAnswer(players[playerNumber].getPlayerName() + "er landet på " + fields[this.newPlayerPosition].getName() + "vil du købe grunden", "ja", "nej");
				if(answer == true) {
					toolbox.payMoney(playerNumber, owner, players, fields, breweryPropertyValue);
					view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
					view.updateOwnership(playerNumber, this.newPlayerPosition);
					BreweryFields wantedFieldChange = ((BreweryFields)fields[this.newPlayerPosition]);
					wantedFieldChange.setOwner(playerNumber);
					view.writeText(players[playerNumber].getPlayerName() + "har købt " + fields[this.newPlayerPosition].getName() + " for " + breweryPropertyValue + " kr");
				}
			}
			if(owner != 0 && owner != playerNumber) {
				int breweryRent = breweryFieldRent[numOfOwnedBrewFields];
				toolbox.payMoney(playerNumber, owner, players, fields, breweryRent);
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
				view.updatePlayerAccount(owner, players[owner].getBalance());
				view.writeText(players[playerNumber].getPlayerName() + "er landet på " + fields[this.newPlayerPosition].getName() + " du skal betale " + breweryRent + " til " + players[owner].getPlayerName());
			}

			if(owner == playerNumber) {
				view.writeText(players[playerNumber].getPlayerName() + "er landet på " + fields[this.newPlayerPosition].getName() + " du ejer selv dette bryggeri");
			}
			break;

		case 3:
			//Taxfields
			int[] taxValue = (((TaxField)fields[this.newPlayerPosition]).getReturnValue());
			toolbox.payMoney(playerNumber, owner, players, fields, taxValue[0]); // Transaction som sker på spilleren ud fra hvilket taxfield han lander på
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance()); // update af gui.
			view.writeText(players[playerNumber].getPlayerName() + "er landet på " + fields[this.newPlayerPosition].getName() + " du skal betale " + taxValue[0] + " i skat"); // tekst til spilleren
			break;

		case 4:
			//Chancefield			
			view.writeText(players[playerNumber].getPlayerName() + "er landet på 'Prøv lykken', du trækker et chance kort"); //Tekst fra gui 
			chanceCard.draw();   														//ChanceCardCRTL trækker et kort	
			view.showChanceCard(chanceCard.getDescription());							//Teksten fra Chancekortet vises i gui 
			chanceCardRules(playerNumber);												//kald af metode som fortæller hvilket slags kort man har trukket.
			break;

		case 7:
			//GoToJailField
			players[playerNumber].setPosition(10); // spilleren position bliver rykket til felt nr 10
			players[playerNumber].setTurnsInJail(1); // Spilleren sidder i fængsel.
			view.updatePlayerPosition(playerNumber, this.newPlayerPosition, 10); //update af gui
			view.writeText(players[playerNumber].getPlayerName() + "er landet på " + fields[this.newPlayerPosition].getName() + " du skal nu i fængsel"); //tekst til spilleren.
			break;
		}
	}

	/**
	 * shippingFieldRules() - En metode som bestemmer logikken for når man lander på et rederifelt og for hvor mange rederier man har
	 * @param playerNumber - modtager et spillernummer
	 * @param multiplier - Hvis feltet er ejet af en spiller ganges den totale leje med multiplier.
	 */
	public void shippingFieldRules(int playerNumber, int multiplier) {
		int shippingPropertyValue = (((ShipFields)fields[this.newPlayerPosition]).getPropertyValue());
		int owner = (((ShipFields)fields[this.newPlayerPosition]).getOwner());
		int[] fieldRent = (((ShipFields)fields[this.newPlayerPosition]).returnValue());
		int numOfOwnedShipFields = (toolbox.getNumberOfOwnedPropertiesInGroup(this.newPlayerPosition, fields, owner));

		if(owner == 0) {
			boolean answer = view.getUserAnswer(players[playerNumber].getPlayerName() + "er landet på " + fields[this.newPlayerPosition].getName() + " vil du købe grunden", "ja", "nej"); //Spiller for mulighed for at købe grunden
			if(answer == true) {
				toolbox.payMoney(playerNumber, owner, players, fields, shippingPropertyValue);				//transaktionen forgår mellem spiller og bræt
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());								//Update af spillerens konto i gui
				view.updateOwnership(playerNumber, this.newPlayerPosition);									//Update af spillerens ejerskab.
				ShipFields wantedFieldChange = ((ShipFields)fields[this.newPlayerPosition]);
				wantedFieldChange.setOwner(playerNumber);													//Køberen bliver sat til ejer af feltet
				view.writeText(players[playerNumber].getPlayerName() + "har købt " + fields[this.newPlayerPosition].getName() + " for " + shippingPropertyValue + " kr" );	//Tekst til gui
			}
		}

		if(owner != 0 && owner != playerNumber) {
			int shipRent = (fieldRent[numOfOwnedShipFields -1])*multiplier;
			toolbox.payMoney(playerNumber, owner, players, fields, shipRent);
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			view.updatePlayerAccount(owner, players[owner].getBalance());
			view.writeText(players[playerNumber].getPlayerName() + "er landet på " + fields[this.newPlayerPosition].getName() + " du skal betale " + shipRent + " til " + players[owner].getPlayerName());
		}

		if(owner == playerNumber) {
			view.writeText(players[playerNumber].getPlayerName() + "er landet på " + fields[this.newPlayerPosition].getName() + " du ejer selv dette rederi");
		}

	}

	/**
	 * chanceCardRules - En metode som switcher på hvilken type ChanceCard man har trukket.
	 * @param playerNumber - modtager et playernummer.
	 */

	public void chanceCardRules (int playerNumber) {
		int chanceCardType = chanceCard.getType();
		int[] chanceCardValueArray = chanceCard.getReturnValue();
		switch (chanceCardType) {

		case 1: // TransactionCard
			int transactionValue = chanceCardValueArray[0];
			if (transactionValue < 0) {
				toolbox.payMoney(playerNumber, 0, players, fields, transactionValue);
			} else {
				players[playerNumber].recieveMoney(transactionValue);
			}
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			break;

		case 2: // MoveToCards
			MoveToCardsRules(playerNumber); // logik og viewCTRL-kald ligger i denne metode.
			if(toolbox.CheckForPassingStart(this.oldPlayerPosition, this.newPlayerPosition) == true)
				view.updatePlayerPosition(playerNumber, this.oldPlayerPosition, this.newPlayerPosition);
			break;

		case 3: // ReleaseCards
			players[playerNumber].addReleaseCard();
			break;

		case 4: //TaxCards
			int numberofhouses = toolbox.getNumberOfHousesFromPlayer(playerNumber, fields);
			int numberofhotels = toolbox.getNumberOfHotelsFromPlayer(playerNumber, fields);
			int totalSum = (chanceCardValueArray[0] * numberofhouses)+(chanceCardValueArray[1] * numberofhotels);
			players[playerNumber].removeMoney(totalSum);
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			break;
		}
	}

	/**
	 * MoveToCardRules - En Metode som switcher på hvilket type MoveToCard man har trukket.
	 * @param playerNumber - modtager et playernummer.
	 */
	public void MoveToCardsRules (int playerNumber) {
		int[] chanceCardValueArray = chanceCard.getReturnValue();
		int moveToField = chanceCardValueArray[0];

		switch (chanceCardValueArray[1]){

		case 1:
			// Blot flyttekort til et bestemt felt.
			if((chanceCard.cardnumber == 19) || (chanceCard.cardnumber == 22)) {
				players[playerNumber].setPosition(moveToField);
				view.updatePlayerPosition(playerNumber,this.oldPlayerPosition, moveToField);
				players[playerNumber].setTurnsInJail(1);
			}
			else {
				players[playerNumber].setPosition(moveToField);
				view.updatePlayerPosition(playerNumber, this.oldPlayerPosition, moveToField);
				fieldRulesSwitch(playerNumber);
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
			// Herefter kommer der et tjek om hvilket efterfølgende shippingField er nærmest.
			if( this.oldPlayerPosition < shippingArray[0]) {
				players[playerNumber].setPosition(shippingArray[0]);
				view.updatePlayerPosition(playerNumber, this.oldPlayerPosition, shippingArray[0]);
				shippingFieldRules(playerNumber, 2);
			}

			else if( this.oldPlayerPosition > shippingArray[0] && this.oldPlayerPosition < shippingArray[1]) {
				players[playerNumber].setPosition(shippingArray[1]);
				view.updatePlayerPosition(playerNumber, this.oldPlayerPosition, shippingArray[1]);
				shippingFieldRules(playerNumber, 2);
			}

			else if( this.oldPlayerPosition > shippingArray[1] && this.oldPlayerPosition < shippingArray[2]) {
				players[playerNumber].setPosition(shippingArray[2]);
				view.updatePlayerPosition(playerNumber, this.oldPlayerPosition, shippingArray[2]);
				shippingFieldRules(playerNumber, 2);
			}

			else if( this.oldPlayerPosition > shippingArray[2] && this.oldPlayerPosition < shippingArray[3]) {
				players[playerNumber].setPosition(shippingArray[3]);
				view.updatePlayerPosition(playerNumber, this.oldPlayerPosition, shippingArray[3]);
				shippingFieldRules(playerNumber, 2);
			}
			break;

		case 3: // Ryk tre felter tilbage.
			players[playerNumber].setPosition(this.oldPlayerPosition-3);
			view.updatePlayerPosition(playerNumber, this.oldPlayerPosition, this.oldPlayerPosition-3);
			fieldRulesSwitch(playerNumber);
			break;
		}
	}
}