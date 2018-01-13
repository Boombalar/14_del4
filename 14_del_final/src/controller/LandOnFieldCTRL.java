package controller;

import model.*;
import view.*;

public class LandOnFieldCTRL {

	private Toolbox toolbox;
	private ChanceCardDeckCTRL chancecarddeck;
	private BankruptcyCTRL bankruptcy;

	public LandOnFieldCTRL (Toolbox toolbox,BankruptcyCTRL bankruptcy, ChanceCardDeckCTRL chancecarddeck) {
		this.toolbox = toolbox;	
		this.chancecarddeck = chancecarddeck;
		this.bankruptcy = bankruptcy;
	}

	/**
	 * fieldRulesSwitch() - En metode som switcher på hvilket type felt man er landet på
	 * @param playerNumber
	 * @param players
	 * @param fields
	 * @param view
	 */
	public void ruleSwitch (int playerNumber, Player[] players, Field[] fields, ViewCTRL view) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int owner = 0;
		if ((fields[players[playerNumber].getPosition()]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[players[playerNumber].getPosition()]).getOwner());
		}

		switch (fieldType) {

		case 0:		
			propertyField(playerNumber, owner, players, fields, view);
			break;
		case 1:
			//ShipFields
			shippingField(playerNumber, 1, players, fields, view);
			break;
		case 2:
			//BreweryFields
			breweryField(playerNumber, owner, players, fields, view);
			break;
		case 3:
			//TaxFields
			taxField(playerNumber, owner, players, fields, view);
			break;
		case 4:
			//ChanceField			
			chanceField(playerNumber, players, fields, view);
			break;
		case 5: 
			//StartField
			view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[players[playerNumber].getPosition()].getName() + "'");
			break;
		case 6: 
			//NoActionField
			view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[players[playerNumber].getPosition()].getName() + "'");
			break;
		case 7:
			//GoToJailField
			goToJailField(playerNumber, players, fields, view);
			break;
		default:
			System.out.println("Felt-typen der pharses er ikke korrekt.");

		}
	}	

	public void propertyField(int playerNumber, int owner, Player[] players,Field[] fields,ViewCTRL view) {
		//ProbertyField
		int newPlayerPosition = players[playerNumber].getPosition();
		int propertyValue = (((PropertyFields)fields[newPlayerPosition]).getPropertyValue());
		int[] fieldRent = (((PropertyFields)fields[newPlayerPosition]).getReturnValue());
		int numberOfHouses = fieldRent[6];
		int propertyRent = fieldRent[numberOfHouses];
		if(owner == 0) {
			boolean	answer = view.getUserAnswer(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', vil du købe grunden ?", "ja", "nej");		
			if(answer == true) {
				view.writeText(players[playerNumber].getPlayerName() + " har købt '" + fields[newPlayerPosition].getName() + "' for " + propertyValue + " kr."); //gui tekst til spilleren
				PropertyFields wantedFieldChange = ((PropertyFields)fields[newPlayerPosition]);
				//Herunder bliver feltets ejer skiftet.
				wantedFieldChange.setOwner(playerNumber);
				bankruptcy.payMoney(playerNumber, owner, propertyValue, players, fields, view); 	//spiller køber grunden af brættet
			}
		}
		if(owner != 0 && owner != playerNumber) {
			if (toolbox.checkPropertyGroupOwnership(owner,newPlayerPosition,fields) && (fieldRent[6] == 0)) {
				propertyRent *= 2;
			}
			view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', du skal betale " + propertyRent + "kr. til " + players[owner].getPlayerName()); //Gui i tekst til spilleren
			bankruptcy.payMoney(playerNumber, owner, propertyRent, players, fields, view);			 //transaction mellem to spiller.
		}
		//Her lander den aktivespiller på et felt som han selv ejer. 
		if((owner == playerNumber)) {  
			view.writeText(players[playerNumber].getPlayerName() +  " er landet på '" + fields[newPlayerPosition].getName() + "', " + players[playerNumber].getPlayerName() + ", ejer selv denne grund");
		}

	}
	public void breweryField(int playerNumber,int owner,Player[] players,Field[] fields,ViewCTRL view) {
		int newPlayerPosition = players[playerNumber].getPosition();
		int breweryPropertyValue = (((BreweryFields)fields[newPlayerPosition]).getPropertyValue());
		int[] breweryFieldRent = (((BreweryFields)fields[newPlayerPosition]).getReturnValue());
		int numOfOwnedBrewFields = (toolbox.getNumberOfOwnedPropertiesInGroup(owner, newPlayerPosition));
		if(owner == 0) {
			boolean answer = view.getUserAnswer(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', vil du købe dette bryggeri ?", "ja", "nej");
			if(answer == true) {
				view.writeText(players[playerNumber].getPlayerName() + " har købt '" + fields[newPlayerPosition].getName() + "' for " + breweryPropertyValue + "kr.");
				OwnerFields wantedFieldChange = ((OwnerFields)fields[newPlayerPosition]);
				wantedFieldChange.setOwner(playerNumber);
				bankruptcy.payMoney(playerNumber, owner, breweryPropertyValue, players, fields, view);
			}
		}
		if(owner != 0 && owner != playerNumber) {
			int breweryRent = breweryFieldRent[numOfOwnedBrewFields-1];
			view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "' du skal betale " + breweryRent + "kr. til " + players[owner].getPlayerName());
			bankruptcy.payMoney(playerNumber, owner, breweryRent, players, fields, view);
		}

		if(owner == playerNumber) {
			view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', " + players[playerNumber].getPlayerName() + ",  ejer selv dette bryggeri");
		}
	}

	public void taxField(int playerNumber,int owner,Player[] players,Field[] fields,ViewCTRL view) {
		int newPlayerPosition = players[playerNumber].getPosition();
		int[] taxValue = (((TaxField)fields[newPlayerPosition]).getReturnValue());
		view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', du skal betale " + taxValue[0] + "kr. i skat"); // tekst til spilleren
		bankruptcy.payMoney(playerNumber, owner, taxValue[0], players, fields, view);// Transaction som sker på spilleren ud fra hvilket taxfield han lander på
	}

	public void chanceField(int playerNumber, Player[] players,Field[] fields,ViewCTRL view) {
		view.writeText(players[playerNumber].getPlayerName() + " er landet på 'Prøv lykken', du trækker et chance kort"); //Tekst fra gui 
		chancecarddeck.draw(); //ChanceCardCRTL trækker et kort	
		view.showChanceCard(chancecarddeck.getDescription());	 //Teksten fra Chancekortet vises i gui 
		chanceCardRules(playerNumber, players, fields, view); //kald af metode som fortæller hvilket slags kort man har trukket.
	}

	public void goToJailField(int playerNumber,Player[] players,Field[] fields,ViewCTRL view) {
		int newPlayerPosition = players[playerNumber].getPosition();
		view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', du skal nu i fængsel"); //tekst til spilleren.
		players[playerNumber].setPosition(10); // spilleren position bliver rykket til felt nr 10
		players[playerNumber].addTurnsInJail(); // Spilleren sidder i fængsel.
		view.updatePlayerPosition(playerNumber, newPlayerPosition, 10); //update af gui
	}

	/**
	 * shippingFieldRules() - En metode som bestemmer logikken for når man lander på et rederifelt og for hvor mange rederier man har
	 * @param playerNumber - modtager et spillernummer
	 * @param multiplier - Hvis feltet er ejet af en spiller ganges den totale leje med multiplier.
	 */
	public void shippingField(int playerNumber, int multiplier, Player[] players,Field[] fields,ViewCTRL view) {
		int newPlayerPosition = players[playerNumber].getPosition();
		int shippingPropertyValue = (((ShippingFields)fields[newPlayerPosition]).getPropertyValue());
		int owner = (((ShippingFields)fields[newPlayerPosition]).getOwner());
		int[] fieldRent = (((ShippingFields)fields[newPlayerPosition]).getReturnValue());
		int numOfOwnedShipFields = (toolbox.getNumberOfOwnedPropertiesInGroup(owner, newPlayerPosition));

		if(owner == 0) {
			boolean answer = view.getUserAnswer(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', vil du købe dette redderi ?", "ja", "nej"); //Spiller for mulighed for at købe grunden
			if(answer == true) {
				view.writeText(players[playerNumber].getPlayerName() + " har købt '" + fields[newPlayerPosition].getName() + "' for " + shippingPropertyValue + " kr" );	//Tekst til gui
				ShippingFields wantedFieldChange = ((ShippingFields)fields[newPlayerPosition]);
				wantedFieldChange.setOwner(playerNumber);												//Køberen bliver sat til ejer af feltet
				bankruptcy.payMoney(playerNumber, owner, shippingPropertyValue, players, fields, view);	//transaktionen forgår mellem spiller og bræt
			}
		}

		if(owner != 0 && owner != playerNumber) {
			int shipRent = ((fieldRent[numOfOwnedShipFields -1])*multiplier);
			if(multiplier == 1) {
				view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', du skal betale " + shipRent + "kr. til " + players[owner].getPlayerName());
			} else {
				view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', og du skal betale dobbelt leje, " + shipRent + "kr. til " + players[owner].getPlayerName());
			}
			bankruptcy.payMoney(playerNumber, owner, shipRent, players, fields, view);
		}

		if(owner == playerNumber) {
			view.writeText(players[playerNumber].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', " + players[playerNumber].getPlayerName() + ", ejer selv dette rederi");
		}
	}

	/**
	 * chanceCardRules - En metode som switcher på hvilken type ChanceCard man har trukket.
	 * @param currentPlayer - modtager et playernummer.
	 * @param  
	 */
	public void chanceCardRules (int currentPlayer, Player[] players,Field[] fields,ViewCTRL view) {
		int chanceCardType = chancecarddeck.getType();
		int[] chanceCardValueArray = chancecarddeck.getReturnValue();
		int owner = 0;
		if ((fields[players[currentPlayer].getPosition()]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[players[currentPlayer].getPosition()]).getOwner());
		}
		switch (chanceCardType) {

		case 1: // TransactionCard
			int transactionValue = chanceCardValueArray[0];
			if ((transactionValue) < 0) {
				int realvalue = (transactionValue * (-1)); 
				view.writeText("Der trækkes " + realvalue + "kr. fra " + players[currentPlayer].getPlayerName() + "'s konto.");
				bankruptcy.payMoney(currentPlayer, owner, (realvalue), players, fields, view);

			} else {
				view.writeText("Der tilføjes " + transactionValue + "kr. til " + players[currentPlayer].getPlayerName() + "'s konto.");
				players[currentPlayer].recieveMoney(transactionValue);
			}
			view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
			break;

		case 2: // MoveToCards
			moveToCardsRules(currentPlayer, players, fields, view); // logik og viewCTRL-kald ligger i denne metode.
			break;

		case 3: // ReleaseCards
			players[currentPlayer].addReleaseCard();
			view.writeText(players[currentPlayer].getPlayerName() + " har nu 1 løsladelseskort mere, og totalt " + players[currentPlayer].getReleaseCard() + "kort.");
			break;

		case 4: //TaxCards
			int numberofhouses = toolbox.getNumberOfHousesFromPlayer(currentPlayer);
			int numberofhotels = toolbox.getNumberOfHotelsFromPlayer(currentPlayer);
			int totalSum = (chanceCardValueArray[0] * numberofhouses)+(chanceCardValueArray[1] * numberofhotels);
			view.writeText("Der trækkes " + totalSum + "kr. fra " + players[currentPlayer].getPlayerName() + "'s konto.");
			bankruptcy.payMoney(currentPlayer, owner, (totalSum), players, fields, view);
			break;
		default:
			System.out.println("ChanceCard-typen der pharses er ikke korrekt.");
		}
	}

	/**
	 * moveToCardRules() - En Metode som switcher på hvilket type MoveToCard man har trukket.
	 * @param currentPlayer
	 * @param players
	 * @param fields
	 * @param view
	 */
	public void moveToCardsRules (int currentPlayer, Player[] players,Field[] fields, ViewCTRL view) {
		int playerPosition = players[currentPlayer].getPosition();
		int[] chanceCardValueArray = chancecarddeck.getReturnValue();
		int moveToField = chanceCardValueArray[0];
		int moveToType = chanceCardValueArray[1];

		switch (moveToType){

		case 1:
			// Blot flyttekort til et bestemt felt.
			view.writeText(players[currentPlayer].getPlayerName() + " flyttes til " + fields[moveToField].getName());
			if(toolbox.checkForPassingStart(playerPosition, moveToField)) {
				players[currentPlayer].setPosition(moveToField);
				view.updatePlayerPosition(currentPlayer, playerPosition, (moveToField));
			} else {
				players[currentPlayer].setPosition(moveToField);
				view.updatePlayerPosition(currentPlayer, playerPosition, moveToField);
			}
			ruleSwitch(currentPlayer, players, fields, view);
			break;

		case 2: // Et flyttekort, hvor man flytter til det nærmeste felt med rederi.	
			int oldPlayerPos = players[currentPlayer].getPosition();
			int newPlayerPos = players[currentPlayer].getPosition();

			while (fields[newPlayerPos].getType() != 1) {
				newPlayerPos++;
				if (newPlayerPos > 39) { 
					newPlayerPos =0;
				}
			}
			view.writeText(players[currentPlayer].getPlayerName() + " flyttes til " + fields[newPlayerPos].getName() + ", som er det nærmeste redderi");
			players[currentPlayer].setPosition(newPlayerPos);
			view.updatePlayerPosition(currentPlayer, oldPlayerPos, newPlayerPos);
			shippingField(currentPlayer, 2, players, fields, view);
			break;

		case 3:
			// Flyttekort til fængsel
			view.writeText(players[currentPlayer].getPlayerName() + " flyttes til " + fields[moveToField].getName());
			players[currentPlayer].addTurnsInJail();
			players[currentPlayer].setPosition(moveToField);
			players[currentPlayer].setExtraTurn(false);
			view.updatePlayerPosition(currentPlayer, playerPosition, moveToField);
			break;

		case 4: // Ryk tre felter tilbage.
			int actualMove = (playerPosition - moveToField);
			view.writeText(players[currentPlayer].getPlayerName() + " rykkes tre felter tilbage til " + fields[actualMove].getName());
			players[currentPlayer].setPosition(actualMove);
			view.updatePlayerPosition(currentPlayer, playerPosition, actualMove);
			ruleSwitch(currentPlayer, players, fields, view);
			break;

		default:
			System.out.println("MoveTo-typen der pharses er ikke korrekt.");
		}
}
}

