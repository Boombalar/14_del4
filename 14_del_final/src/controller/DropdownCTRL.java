package controller;

import model.*;
import view.*;

public class DropdownCTRL {



	public void rollDice (int currentPlayer, DieCup dieCup, Player[] players, Field[] fields, ViewCTRL view, FieldRuleCTRL fieldRuleCTRL ) {

		//slå terninger
		dieCup.shake();
		int diceValue = dieCup.getDiceValue();

		//skift position på spiller i model lag
		int oldPlayerPosition = players[currentPlayer].getPosition();
		int newPlayerPosition = oldPlayerPosition + diceValue;

		//Update view
		view.updateDice(dieCup.getDie1Value(), dieCup.getDie2Value());						  


		//Håndter om man kommer over start og får 4000.
		if (newPlayerPosition > 39) {
			newPlayerPosition -= 40;
			players[currentPlayer].recieveMoney(4000);
			view.writeText("Spiller " + currentPlayer + " har passeret start og får 4000 kroner");
		}

		players[currentPlayer].setPosition(newPlayerPosition);
		view.updatePlayerPosition(currentPlayer, oldPlayerPosition, newPlayerPosition);
		view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());

		fieldRuleCTRL.fieldRulesSwitch(currentPlayer, newPlayerPosition, players, fields);
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
	}

	public void buyHousesAndHotel(int currentPlayer, Player[] players, Field[] fields, ViewCTRL view, TradeCTRL tradectrl, Toolbox toolbox) {
		
		int amountOfProperties = 0;
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {//Går hele brættet igennem
			if (fields[fieldCount] instanceof PropertyFields) {
				if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer && toolbox.checkPropertyGroupOwnership(currentPlayer, fieldCount, fields) == true) {
					amountOfProperties++;
				}
			}
		}

		//Vi laver Array til DropDown listen
		String [] propertyArray = new String[amountOfProperties];
		int index = 0;
		String choice;
		int[] returnValue;
	

		//Vi populerer Array
		//populer array med felt hvis man ejer det og har hele gruppen eks.
		//1. Hvidovrevej
		//3. Rødovrevej
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields) {
				if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer && toolbox.checkPropertyGroupOwnership(currentPlayer, fieldCount,fields) == true) {
					index = toolbox.getNumberOfOwnedPropertiesInGroup(currentPlayer, fieldCount, fields );
					propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
				}
			}
			index++;
		}

		//Vi modtager svar fra dropdown listen
		if (propertyArray.length != 0) {
			choice = view.getDropDownChoice("Vælg grund du vil bygge på", propertyArray);
			int chosenFieldNumber=Character.getNumericValue(choice.charAt(0));

			//Vi bygger hvis man ejer hele gruppen, og har råd
			if( toolbox.checkPropertyGroupOwnership(currentPlayer,chosenFieldNumber, fields)) {
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
		}
		else {
			view.writeText("Du ejer ikke nogle grunde");
			currentPlayer--;
		}


	}
	public void sellHousesAndHotels(int currentPlayer, Player[] players, Field[] fields, ViewCTRL view, Toolbox toolbox, TradeCTRL tradectrl) {
		int amountOfProperties=0;
		int[] returnValue;
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields) {
				if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
					returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
					if (toolbox.getHousesOnProperty(currentPlayer, fieldCount, fields)>0) {
						amountOfProperties++;
					}	
				}
			}
		}

		//Lav array til dropdown
		String[] propertyArray = new String[amountOfProperties];
		int index=0;
		String choice;

		//populer array med felt hvis feltet har huse på sig eks.
		//1. Hvidovrevej
		//3. Rødovrevej
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields) {
				if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
					returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
					if (toolbox.getHousesOnProperty(currentPlayer, fieldCount, fields)>0) {
						propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
					}
				}
			}
			index++;
		}

		//Sælg hus
		if (propertyArray.length != 0) {
			choice = view.getDropDownChoice("Vælg grund du vil sælge huse fra", propertyArray);
			int chosenFieldNumber=Character.getNumericValue(choice.charAt(0));
			tradectrl.sellBuilding(currentPlayer, chosenFieldNumber, players, fields);
		}
		else 
		{
			view.writeText("Du har ikke nogle grunde at sælge huse på");
			currentPlayer--;
		}
	}
	public void sellProperty(int currentPlayer, Player[] players, Field[] fields, ViewCTRL view, Toolbox toolbox, TradeCTRL trade ) {
		int amountOfProperties=0;
		int[] returnValue;
		//Find ud af hvor mange grunde man ejer som ikke har huse på sin gruppe til array
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields && toolbox.getHousesOnGroup(currentPlayer, fieldCount,fields)==0) {
				if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
					amountOfProperties++;
				}
			}
		}
		//Lav Array
		String[] propertyArray = new String[amountOfProperties];
		int index=0;
		String choice;

		//populer array med felt hvis man ejer det og der ikke er huse på gruppen
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields && toolbox.getHousesOnGroup(currentPlayer, fieldCount,fields)==0) {
				if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
					propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + ". " + fields[fieldCount].getName(); 
				}
			}
			index++;
		}

		//vælge grund i dropdown
		if (propertyArray.length != 0) {
			choice = view.getDropDownChoice("Vælg hvilken grund du vil sælge", propertyArray);
			int chosenFieldNumber=Character.getNumericValue(choice.charAt(0));

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
			trade.sellProperty(currentPlayer, chosenPlayerNumber, players, fields, chosenFieldNumber);
		}
		else {
			view.writeText("Du ejer ikke nogle grunde, som du kan sælge");
			currentPlayer--;
		}
	}
}


