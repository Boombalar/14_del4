package controller;

import model.*;
import view.*;

public class DropdownCTRL {
	DieCup dieCup;
	LandOnFieldCTRL landonfield;
	Toolbox toolbox;
	BankruptcyCTRL bankruptcy;
	TradeCTRL trade;
	ChanceCardCTRL chanceCard;

	public DropdownCTRL(DieCup dieCup, LandOnFieldCTRL landonfield, Toolbox toolbox, BankruptcyCTRL bankruptcy, TradeCTRL trade, ChanceCardCTRL chanceCard) {
		this.dieCup = dieCup;
		this.landonfield = landonfield;
		this.toolbox = toolbox;
		this.bankruptcy = bankruptcy;
		this.trade = trade;
		this.chanceCard = chanceCard;
	}
	boolean backToDropdown = false;


	public void rollDice (int currentPlayer, Player[] players, Field[] fields, ViewCTRL view) {

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
			view.writeText(players[currentPlayer].getPlayerName() + " har passeret start, og får 4000 kr.");
		}

		players[currentPlayer].setPosition(newPlayerPosition);
		view.updatePlayerPosition(currentPlayer, oldPlayerPosition, newPlayerPosition);
		view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());

		landonfield.ruleSwitch(currentPlayer, players, fields, view);

		if (dieCup.getDie1Value() == dieCup.getDie2Value()) {
			players[currentPlayer].setExtraTurn(true);
			/*
		players[currentPlayer].changeEqualEyes() ++;
		if(players[currentPlayer].changeEqualEyes() == 3) {
			jail();
			players[currentPlayer].changeEqualEyes(-3);
		}
			 */

		}
	}

	public void buyHousesAndHotel(int currentPlayer, Player[] players, Field[] fields, ViewCTRL view) {

		backToDropdown = true;
		//Find ud af hvor mange proporties man ejer hvor man har hele gruppen til array.
		int amountOfProperties = 0;
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {//Går hele brættet igennem
			if (fields[fieldCount] instanceof PropertyFields) {
				//check om man ejer gruppen
				if (toolbox.checkPropertyGroupOwnership(currentPlayer, fieldCount, fields) == true && toolbox.getHousesOnProperty(currentPlayer, fieldCount) != 5) {
					amountOfProperties++;
				}
			}
		}
		//Vi laver Array til DropDown listen
		String [] propertyArray = new String[amountOfProperties];
		int index = 0;
		int[] returnValue;

		//Vi populer array med felt hvis man ejer det og har hele gruppen eks.
		//1-Hvidovrevej
		//3-Rødovrevej
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields) {
				if (toolbox.checkPropertyGroupOwnership(currentPlayer, fieldCount,fields) == true && toolbox.getHousesOnProperty(currentPlayer, fieldCount) != 5) {
					propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + "-" + fields[fieldCount].getName(); 
					index++;
				}
			}
		}
		//Vi modtager svar fra dropdown listen
		if (propertyArray.length != 0) {
			//Vælg hvilken grund du vil købe hus på
			String[] choice = view.getDropDownChoice("Vælg hvilken grund til vil købe hus på", propertyArray).split("-");
			int chosenFieldNumber=Integer.parseInt(choice[0]);

			//Vi bygger hvis man har råd
				if (players[currentPlayer].getBalance() > toolbox.getHousePrice(chosenFieldNumber)) {
					returnValue = ((PropertyFields)fields[chosenFieldNumber]).getReturnValue();
					if (returnValue[6] < 6) {//hvis der er mindre end 6 huse på feltet
						returnValue[6]++;
						//BYG FOR FANDEN!!
						players[currentPlayer].removeMoney(toolbox.getHousePrice(chosenFieldNumber));
						toolbox.setHousesOnProperty(returnValue[6], chosenFieldNumber);
						view.updateBuildings(chosenFieldNumber, returnValue[6]);
					}else {
						view.writeText("Du har ikke råd til at bygge");
					}
				}
			
		}
		else {
			view.writeText("Du ejer ikke nogle grunde");
		}
	}
	
	public void sellHousesAndHotels(int currentPlayer, Player[] players, Field[] fields, ViewCTRL view) {
		int amountOfProperties=0;
		int[] returnValue;
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			if (fields[fieldCount] instanceof PropertyFields) {
				if (((PropertyFields)fields[fieldCount]).getOwner() == currentPlayer) {
					returnValue = (((PropertyFields)fields[fieldCount]).getReturnValue());
					if (toolbox.getHousesOnProperty(currentPlayer, fieldCount)>0) {
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
					if (toolbox.getHousesOnProperty(currentPlayer, fieldCount)>0) {
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
			trade.sellBuilding(currentPlayer, chosenFieldNumber, players);
		}
		else 
		{
			view.writeText("Du har ikke nogle grunde at sælge huse på");

		}
		backToDropdown = true;
	}
	
	
	public void sellProperty(int currentPlayer, Player[] players, Field[] fields, ViewCTRL view) {
		int amountOfProperties=0;
		int[] returnValue;
		//Find ud af hvor mange grunde man ejer som ikke har huse på sin gruppe til array
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			//Hvis feltet er enten et ejet OwnerField eller et ejet PropertyField uden huse.
			if ((fields[fieldCount] instanceof OwnerFields && ((OwnerFields)fields[fieldCount]).getOwner()==currentPlayer) || (fields[fieldCount] instanceof PropertyFields && toolbox.getHousesOnGroup(currentPlayer, fieldCount)==0) && (((PropertyFields)fields[fieldCount]).getOwner()==currentPlayer)) {
				amountOfProperties++;//tæl op
			}
		}

		//Lav Array
		String[] propertyArray = new String[amountOfProperties];
		int index=0;

		//Populer array med grunde hvor man enten ejer OwnerField eller ejer PropertyField uden huse.
		for(int fieldCount = 0;fieldCount<=39;fieldCount++) {
			if ((fields[fieldCount] instanceof OwnerFields && ((OwnerFields)fields[fieldCount]).getOwner()==currentPlayer) || (fields[fieldCount] instanceof PropertyFields && toolbox.getHousesOnGroup(currentPlayer, fieldCount)==0) && (((PropertyFields)fields[fieldCount]).getOwner()==currentPlayer)) {
				propertyArray[index] = Integer.toString(fields[fieldCount].getNumber()) + "-" + fields[fieldCount].getName(); 
				index++;	
			}
		}

		//vælge grund i dropdown
		if (propertyArray.length != 0) {

			//Vælge hvilken spiller man vil sælge til 
			//0 er banken.
			String[] choice = view.getDropDownChoice("Vælg hvilken grund du vil sælge", propertyArray).split("-");
			int chosenFieldNumber=Integer.parseInt(choice[0]);

			//Lav array og populer
			String[] playerCountArray = new String[players.length];
			for (int playerCount = 0;playerCount <= players.length-1;
					playerCount++) {
				playerCountArray[playerCount]= Integer.toString(playerCount);
			}

			//Vælg spiller eller bank
			String playerSellChoice = view.getDropDownChoice("Hvilken spiller vil du sælge til? 0 er til banken", playerCountArray);
			int chosenPlayerNumber=Character.getNumericValue(playerSellChoice.charAt(0));

			//Sælg grund.
			trade.sellProperty(currentPlayer, chosenPlayerNumber,chosenFieldNumber, players, fields);
			view.updateOwnership(chosenPlayerNumber, chosenFieldNumber);
			view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
			view.updatePlayerAccount(chosenPlayerNumber, players[chosenPlayerNumber].getBalance());
		}
		else {
			view.writeText("Du ejer ikke nogle grunde, som du kan sælge");
			backToDropdown = true;
		}
	}

	public boolean getBackToDropDown() {
		return backToDropdown;
	}
}


