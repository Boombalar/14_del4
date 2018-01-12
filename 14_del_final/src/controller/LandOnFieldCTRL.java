package controller;

import model.*;
import view.*;

public class LandOnFieldCTRL {
	
	Toolbox toolbox;
	TradeCTRL trade;
	ChanceCardCTRL chancecard;
	BankruptcyCTRL bankruptcy;
	
	public LandOnFieldCTRL (Toolbox toolbox,BankruptcyCTRL bankruptcy, TradeCTRL trade, ChanceCardCTRL chancecard) {
		this.toolbox = toolbox;	
		this.chancecard = chancecard;
		this.trade = trade;
		this.bankruptcy = bankruptcy;
	}
	
	/**
	 * fieldRulesSwitch() - En metode som switcher på hvilket type felt man er landet på
	 * @param playerNumber - Modtager et spiller numer
	 * @param view 
	 * @param toolbox 
	 * @param bankruptcy 
	 * @param trade 
	 * @param chancecard 
	 * @param fieldRuleSwitch 
	 */
	public void ruleSwitch (int playerNumber, int newPlayerPosition, Player[] players, Field[] fields, ViewCTRL view) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int owner=0;
		if ((fields[newPlayerPosition]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[newPlayerPosition]).getOwner());
		}

		switch (fieldType) {
		
		case 0:			
			propertyField(playerNumber, newPlayerPosition, owner, players, fields, view);
			break;
		case 1:
			//ShipFields
			shippingFieldRules(playerNumber, 1, newPlayerPosition, players, fields, view);
			break;
		case 2:
			//Breweryfields
			breweryField(playerNumber, newPlayerPosition, owner, players, fields, view);
			break;
		case 3:
			//Taxfields
			taxField(playerNumber, newPlayerPosition, owner, players, fields, view);
			break;
		case 4:
			//Chancefield			
			chanceField(playerNumber, newPlayerPosition, owner, players, fields, view);
			break;
		case 7:
			//GoToJailField
			goToJailField(playerNumber, newPlayerPosition, players, fields, view);
			break;
		}
	}	
	
	public void propertyField(int playerNumber, int newPlayerPosition, int owner, Player[] players,Field[] fields,ViewCTRL view) {
		//ProbertyField
		int propertyValue = (((PropertyFields)fields[newPlayerPosition]).getPropertyValue());
		int[] fieldRent = (((PropertyFields)fields[newPlayerPosition]).getReturnValue());
		int numberOfHouses = fieldRent[6];
		int propertyRent = fieldRent[numberOfHouses];
		if(owner == 0) {
			boolean	answer = view.getUserAnswer(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " vil du købe grunden", "ja", "nej");		
			if(answer == true) {
				view.writeText(players[playerNumber].getPlayerName() + " har købt " + fields[newPlayerPosition].getName() + " for " + propertyValue + " kr."); //gui tekst til spilleren
				bankruptcy.payMoney(playerNumber, owner, propertyValue, players, fields); //spiller køber grunden af brættet
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance()); 		//Update af gui.
				PropertyFields wantedFieldChange = ((PropertyFields)fields[newPlayerPosition]);
				//Herunder bliver feltets ejer skiftet.
				wantedFieldChange.setOwner(playerNumber);
				view.updateOwnership(playerNumber, newPlayerPosition);
			}
		}
		if(owner != 0 && owner != playerNumber) {
			if ((toolbox.checkPropertyGroupOwnership(owner,newPlayerPosition,fields) == true) && (fieldRent[6] == 0)) {
				propertyRent *= 2;
			}
			view.writeText(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " du skal betale " + propertyRent + " til " + players[owner].getPlayerName()); //Gui i tekst til spilleren
			bankruptcy.payMoney(playerNumber, owner, propertyRent, players, fields);			 //transaction mellem to spiller.
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());			 //update af den aktive spillerens konto
			view.updatePlayerAccount(owner, players[owner].getBalance());						 //Update af den spiller som modtager penge
		}
		//Her lander den aktivespiller på et felt som han selv ejer. 
		if((owner == playerNumber)) {  
			view.writeText(players[playerNumber].getPlayerName() +  " er landet på " + fields[newPlayerPosition].getName() + " du ejer selv denne grund");
		}

	}
	
	public void breweryField(int newPlayerPosition, int playerNumber,int owner,Player[] players,Field[] fields,ViewCTRL view) {
		int breweryPropertyValue = (((OwnerFields)fields[newPlayerPosition]).getPropertyValue());
		int[] breweryFieldRent = (((OwnerFields)fields[newPlayerPosition]).returnValue());
		int numOfOwnedBrewFields = (toolbox.getNumberOfOwnedPropertiesInGroup(newPlayerPosition, owner));
		if(owner == 0) {
			boolean answer = view.getUserAnswer(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " vil du købe grunden", "ja", "nej");
			if(answer == true) {
				view.writeText(players[playerNumber].getPlayerName() + " har købt " + fields[newPlayerPosition].getName() + " for " + breweryPropertyValue + " kr");
				bankruptcy.payMoney(playerNumber, owner, breweryPropertyValue, players, fields);
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
				view.updateOwnership(playerNumber, newPlayerPosition);
				OwnerFields wantedFieldChange = ((OwnerFields)fields[newPlayerPosition]);
				wantedFieldChange.setOwner(playerNumber);
			}
		}
		if(owner != 0 && owner != playerNumber) {
			int breweryRent = breweryFieldRent[numOfOwnedBrewFields-1];
			view.writeText(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " du skal betale " + breweryRent + " til " + players[owner].getPlayerName());
			bankruptcy.payMoney(playerNumber, owner, breweryRent, players, fields);
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			view.updatePlayerAccount(owner, players[owner].getBalance());
		}

		if(owner == playerNumber) {
			view.writeText(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " du ejer selv dette bryggeri");
		}
	}
	
	public void taxField(int playerNumber, int newPlayerPosition,int owner,Player[] players,Field[] fields,ViewCTRL view) {
		int[] taxValue = (((TaxField)fields[newPlayerPosition]).getReturnValue());
		view.writeText(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " du skal betale " + taxValue[0] + " i skat"); // tekst til spilleren
		bankruptcy.payMoney(playerNumber, owner, taxValue[0], players, fields);// Transaction som sker på spilleren ud fra hvilket taxfield han lander på
		view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance()); // update af gui.
	}
	
	public void chanceField(int playerNumber, int newPlayerPosition, int toPlayer,Player[] players,Field[] fields,ViewCTRL view) {
		view.writeText(players[playerNumber].getPlayerName() + " er landet på 'Prøv lykken', du trækker et chance kort"); //Tekst fra gui 
		chancecard.draw(); //ChanceCardCRTL trækker et kort	
		view.showChanceCard(chancecard.getDescription());	 //Teksten fra Chancekortet vises i gui 
		chanceCardRules(playerNumber, newPlayerPosition, toPlayer, players, fields, view); //kald af metode som fortæller hvilket slags kort man har trukket.
	}
	
	public void goToJailField(int playerNumber, int newPlayerPosition,Player[] players,Field[] fields,ViewCTRL view) {
		view.writeText(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " du skal nu i fængsel"); //tekst til spilleren.
		players[playerNumber].setPosition(10); // spilleren position bliver rykket til felt nr 10
		players[playerNumber].setTurnsInJail(1); // Spilleren sidder i fængsel.
		view.updatePlayerPosition(playerNumber, newPlayerPosition, 10); //update af gui
	}

	/**
	 * shippingFieldRules() - En metode som bestemmer logikken for når man lander på et rederifelt og for hvor mange rederier man har
	 * @param playerNumber - modtager et spillernummer
	 * @param multiplier - Hvis feltet er ejet af en spiller ganges den totale leje med multiplier.
	 */
	public void shippingFieldRules(int playerNumber, int multiplier, int newPlayerPosition,Player[] players,Field[] fields,ViewCTRL view) {
		int shippingPropertyValue = (((ShipFields)fields[newPlayerPosition]).getPropertyValue());
		int owner = (((ShipFields)fields[newPlayerPosition]).getOwner());
		int[] fieldRent = (((ShipFields)fields[newPlayerPosition]).getReturnValue());
		int numOfOwnedShipFields = (toolbox.getNumberOfOwnedPropertiesInGroup(owner, newPlayerPosition));

		if(owner == 0) {
			boolean answer = view.getUserAnswer(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " vil du købe grunden", "ja", "nej"); //Spiller for mulighed for at købe grunden
			if(answer == true) {
				view.writeText(players[playerNumber].getPlayerName() + " har købt " + fields[newPlayerPosition].getName() + " for " + shippingPropertyValue + " kr" );	//Tekst til gui
				bankruptcy.payMoney(playerNumber, owner, shippingPropertyValue, players, fields);				//transaktionen forgår mellem spiller og bræt
				view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());								//Update af spillerens konto i gui
				view.updateOwnership(playerNumber, newPlayerPosition);									//Update af spillerens ejerskab.
				ShipFields wantedFieldChange = ((ShipFields)fields[newPlayerPosition]);
				wantedFieldChange.setOwner(playerNumber);													//Køberen bliver sat til ejer af feltet
			}
		}

		if(owner != 0 && owner != playerNumber) {
			int shipRent = ((fieldRent[numOfOwnedShipFields -1])*multiplier);
			view.writeText(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " du skal betale " + shipRent + " til " + players[owner].getPlayerName());
			bankruptcy.payMoney(playerNumber, owner, shipRent, players, fields);
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			view.updatePlayerAccount(owner, players[owner].getBalance());
		}

		if(owner == playerNumber) {
			view.writeText(players[playerNumber].getPlayerName() + " er landet på " + fields[newPlayerPosition].getName() + " du ejer selv dette rederi");
		}

	}

	/**
	 * chanceCardRules - En metode som switcher på hvilken type ChanceCard man har trukket.
	 * @param playerNumber - modtager et playernummer.
	 * @param owner 
	 * @param  
	 */

	public void chanceCardRules (int playerNumber, int newPlayerPosition, int owner,Player[] players,Field[] fields,ViewCTRL view) {
		int chanceCardType = chancecard.getType();
		int[] chanceCardValueArray = chancecard.getReturnValue();
		switch (chanceCardType) {

		case 1: // TransactionCard
			int transactionValue = chanceCardValueArray[0];
			if (transactionValue < 0) {
				bankruptcy.payMoney(playerNumber, owner, transactionValue, players, fields);
				view.writeText(players[playerNumber].getPlayerName() + "");

			} else {
				players[playerNumber].recieveMoney(transactionValue);
				view.writeText(players[playerNumber].getPlayerName() + "");
			}
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			break;

		case 2: // MoveToCards
			MoveToCardsRules(playerNumber, newPlayerPosition, owner, players, fields, view); // logik og viewCTRL-kald ligger i denne metode.
			break;

		case 3: // ReleaseCards
			players[playerNumber].addReleaseCard();
			break;

		case 4: //TaxCards
			int numberofhouses = toolbox.getNumberOfHousesFromPlayer(playerNumber);
			int numberofhotels = toolbox.getNumberOfHotelsFromPlayer(playerNumber);
			int totalSum = (chanceCardValueArray[0] * numberofhouses)+(chanceCardValueArray[1] * numberofhotels);
			players[playerNumber].removeMoney(totalSum);
			view.updatePlayerAccount(playerNumber, players[playerNumber].getBalance());
			break;
		}
	}

	/**
	 * MoveToCardRules - En Metode som switcher på hvilket type MoveToCard man har trukket.
	 * @param playerNumber - modtager et playernummer.
	 * @param chancecard 
	 */
	public void MoveToCardsRules (int playerNumber, int newPlayerPosition,int toPlayer,Player[] players,Field[] fields,ViewCTRL view) {
		int[] chanceCardValueArray = chancecard.getReturnValue();
		int moveToField = chanceCardValueArray[0];
		
		if(toolbox.CheckForPassingStart(newPlayerPosition, moveToField) == true)
			view.updatePlayerPosition(playerNumber, newPlayerPosition, moveToField);

		switch (chanceCardValueArray[1]){

		case 1:
			// Blot flyttekort til et bestemt felt.
			if((chancecard.cardNumber == 19) || (chancecard.cardNumber == 22)) {
				players[playerNumber].setPosition(moveToField);
				view.updatePlayerPosition(playerNumber,newPlayerPosition, moveToField);
				players[playerNumber].setTurnsInJail(1);
			}
			else {
				players[playerNumber].setPosition(moveToField);
				view.updatePlayerPosition(playerNumber, newPlayerPosition, moveToField);
				ruleSwitch(playerNumber, newPlayerPosition, players, fields, view);
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
			if( newPlayerPosition < shippingArray[0]) {
				players[playerNumber].setPosition(shippingArray[0]);
				view.updatePlayerPosition(playerNumber, newPlayerPosition, shippingArray[0]);
				shippingFieldRules(playerNumber, 2, shippingArray[0], players, fields, view);
			}

			else if( newPlayerPosition > shippingArray[0] && newPlayerPosition < shippingArray[1]) {
				players[playerNumber].setPosition(shippingArray[1]);
				view.updatePlayerPosition(playerNumber, newPlayerPosition, shippingArray[1]);
				shippingFieldRules(playerNumber, 2, shippingArray[1], players, fields, view);
				}

			else if( newPlayerPosition > shippingArray[1] && newPlayerPosition < shippingArray[2]) {
				players[playerNumber].setPosition(shippingArray[2]);
				view.updatePlayerPosition(playerNumber, newPlayerPosition, shippingArray[2]);
				shippingFieldRules(playerNumber, 2, shippingArray[2], players, fields, view);
				}

			else if( newPlayerPosition > shippingArray[2] && newPlayerPosition < shippingArray[3]) {
				players[playerNumber].setPosition(shippingArray[3]);
				view.updatePlayerPosition(playerNumber, newPlayerPosition, shippingArray[3]);
				shippingFieldRules(playerNumber, 2, shippingArray[3], players, fields, view);
				}
			break;

		case 3: // Ryk tre felter tilbage.
			players[playerNumber].setPosition(newPlayerPosition - 3);
			view.updatePlayerPosition(playerNumber, newPlayerPosition, newPlayerPosition - 3);
			ruleSwitch(playerNumber, newPlayerPosition, players, fields, view);
			break;
		}
	}
}


