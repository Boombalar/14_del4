package controller;

import model.*;
import view.*;

public class LandOnFieldCTRL {

	private AssetCTRL asset;
	private ChanceDeckCTRL chancedeck;
	private BankruptcyCTRL bankruptcy;

	/**
	 * Konstruktør til LandOnFieldCTRL
	 * @param asset - indtast objectnavn af typen Toolbox
	 * @param bankruptcy - indtast objectnavn af typen BankruptcyCTRL
	 * @param chancecarddeck - indtast objectnavn af typen ChanceCardDeckCTRL
	 */
	public LandOnFieldCTRL (AssetCTRL asset,BankruptcyCTRL bankruptcy, ChanceDeckCTRL chancedeck) {
		this.asset = asset;	
		this.chancedeck = chancedeck;
		this.bankruptcy = bankruptcy;
	}

	/**
	 * fieldRulesSwitch() - En metode som switcher på hvilket type felt man er landet på
	 * @param currentPlayer - spillernummeret i playerarrayet
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void ruleSwitch (int currentPlayer, Player[] players, Field[] fields, ViewCTRL view) {
		int fieldType = fields[players[currentPlayer].getPosition()].getType();
		int owner = 0;
		if ((fields[players[currentPlayer].getPosition()]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[players[currentPlayer].getPosition()]).getOwner());
		}

		switch (fieldType) {

		case 0:		
			propertyField(currentPlayer, owner, players, fields, view);
			break;
		case 1:
			//ShipFields
			shippingField(currentPlayer, owner, 1, players, fields, view);
			break;
		case 2:
			//BreweryFields
			breweryField(currentPlayer, owner, players, fields, view);
			break;
		case 3:
			//TaxFields
			taxField(currentPlayer, players, fields, view);
			break;
		case 4:
			//ChanceField			
			chanceField(currentPlayer, players, fields, view);
			break;
		case 5: 
			//StartField
			view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[players[currentPlayer].getPosition()].getName() + "'");
			break;
		case 6: 
			//NoActionField
			view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[players[currentPlayer].getPosition()].getName() + "'");
			break;
		case 7:
			//GoToJailField
			goToJailField(currentPlayer, players, fields, view);
			break;
		default:
			System.out.println("Felt-typen der pharses er ikke korrekt.");

		}
	}

	/**
	 * 
	 * @param oldPosition
	 * @param newPosition
	 * @return
	 */
	public boolean checkForPassingStart(int oldPosition, int newPosition) {
		Boolean didPassStart = false;
		if (newPosition < oldPosition) {
			didPassStart = true;
		}
		return didPassStart;
	}

	/**
	 * PropertyField()
	 * @param currentPlayer - aktuel spiller, der er i fokus (i int)
	 * @param owner - indtast ejeren af feltet, der er i fokus (i int)
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */


	public void propertyField(int currentPlayer, int owner, Player[] players,Field[] fields, ViewCTRL view) {

		//ProbertyField
		int newPlayerPosition = players[currentPlayer].getPosition();
		int propertyValue = (((PropertyFields)fields[newPlayerPosition]).getPropertyValue());
		int[] fieldRent = (((PropertyFields)fields[newPlayerPosition]).getReturnValue());
		int numberOfHouses = fieldRent[6];
		int propertyRent = fieldRent[numberOfHouses];
		
		//Hvis banken ejer feltet, og man har råd til at købe
		//skulle have stået <=
		if(owner == 0 && propertyValue < players[currentPlayer].getBalance()) {
			boolean	answer = view.getUserAnswer(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', vil du købe grunden ?", "ja", "nej");		
			//Hvis man svarer ja til at købe
			if(answer == true) {
				view.writeText(players[currentPlayer].getPlayerName() + " har købt '" + fields[newPlayerPosition].getName() + "' for " + propertyValue + " kr."); //gui tekst til spilleren
				//Herunder bliver feltets ejer skiftet.
				((PropertyFields)fields[newPlayerPosition]).setOwner(currentPlayer);
				players[currentPlayer].removeMoney(propertyValue);; 	//spiller køber grunden af banken.
				view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
				view.updateOwnership(currentPlayer, newPlayerPosition);
			}
		}

		//Hvis ejer ikke er bank og owner ikke er aktiv spiller.
		if(owner != 0 && owner != currentPlayer) {
			if (asset.checkPropertyGroupOwnership(owner,newPlayerPosition,fields) && (fieldRent[6] == 0)) {
				propertyRent *= 2;
			}
			view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', du skal betale " + propertyRent + "kr. til " + players[owner].getPlayerName()); //Gui i tekst til spilleren
			bankruptcy.payMoney(currentPlayer, owner, propertyRent, players, fields, view);	 //transaction mellem to spiller.

		}
		//Her lander den aktivespiller på et felt som han selv ejer. 
		if((owner == currentPlayer)) {  
			view.writeText(players[currentPlayer].getPlayerName() +  " er landet på '" + fields[newPlayerPosition].getName() + "', " + players[currentPlayer].getPlayerName() + ", ejer selv denne grund");
		}
	}

	/**
	 * shippingField()
	 * @param currentPlayer - aktuel spiller, der er i fokus (i int)
	 * @param multiplier - multiplier, der bestemmer multipliciteten af lejen, når man lander, og det er ejet i forvejen.
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void shippingField(int currentPlayer, int owner, int multiplier, Player[] players,Field[] fields,ViewCTRL view) {
		int newPlayerPosition = players[currentPlayer].getPosition();
		int shippingPropertyValue = (((ShippingFields)fields[newPlayerPosition]).getPropertyValue());
		int[] fieldRent = (((ShippingFields)fields[newPlayerPosition]).getReturnValue());
		int numOfOwnedShipFields = (asset.getNumberOfOwnedPropertiesInGroup(owner, newPlayerPosition, fields));
		//Hvis det er ejet af banken
		//Her skulle der også have været check om man havde råd til at købe feltet.
		if(owner == 0) {
			//Så får man muligheden for at købe den.
			boolean answer = view.getUserAnswer(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', vil du købe dette redderi ?", "ja", "nej"); //Spiller for mulighed for at købe grunden
			if(answer == true) {
				view.writeText(players[currentPlayer].getPlayerName() + " har købt '" + fields[newPlayerPosition].getName() + "' for " + shippingPropertyValue + " kr" );	//Tekst til gui
				//ville ikke skrive det sådan her. istedet for skal der stå.  
				//((ShippingFields)fields[newPlayerPosition]).setOwner(currentPlayer);
				ShippingFields wantedFieldChange = ((ShippingFields)fields[newPlayerPosition]);
				wantedFieldChange.setOwner(currentPlayer);												//Køberen bliver sat til ejer af feltet
				bankruptcy.payMoney(currentPlayer, owner, shippingPropertyValue, players, fields, view);	//transaktionen forgår mellem spiller og bræt
			}
		}
		//Hvis ejer ikke er 0 eller currentPlayer
		if(owner != 0 && owner != currentPlayer) {
			//multiplier er hvis man trækker et chancekort der beder en om at rykke til nærmeste
			//og betale 2 gange lejen på feltet
			int shipRent = ((fieldRent[numOfOwnedShipFields -1])*multiplier);
			if(multiplier == 1) {
				view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', du skal betale " + shipRent + "kr. til " + players[owner].getPlayerName());
			} else {
				view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', og du skal betale dobbelt leje, " + shipRent + "kr. til " + players[owner].getPlayerName());
			}
			//Man skal ikke betale med payMoney, men med safeTransfer.
			bankruptcy.payMoney(currentPlayer, owner, shipRent, players, fields, view);
		}

		if(owner == currentPlayer) {
			view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', " + players[currentPlayer].getPlayerName() + ", ejer selv dette rederi");
		}
	}

	/**
	 * breweryField ()
	 * @param currentPlayer - aktuel spiller, der er i fokus (i int)
	 * @param owner - indtast ejeren af feltet, der er i fokus (i int)
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void breweryField(int currentPlayer,int owner,Player[] players,Field[] fields,ViewCTRL view) {
		int newPlayerPosition = players[currentPlayer].getPosition();
		int breweryPropertyValue = (((BreweryFields)fields[newPlayerPosition]).getPropertyValue());
		int[] breweryFieldRent = (((BreweryFields)fields[newPlayerPosition]).getReturnValue());
		int numOfOwnedBrewFields = (asset.getNumberOfOwnedPropertiesInGroup(owner, newPlayerPosition, fields));
		//Hvis feltet er ejet af banken
		//Her skulle der også have været check om man havde råd til at købe feltet.
		if(owner == 0) {
			boolean answer = view.getUserAnswer(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', vil du købe dette bryggeri ?", "ja", "nej");
			//Så kan man købe den.
			if(answer == true) {
				view.writeText(players[currentPlayer].getPlayerName() + " har købt '" + fields[newPlayerPosition].getName() + "' for " + breweryPropertyValue + "kr.");
				//ville ikke skrive det sådan her. istedet for skal der stå.  
				//((OwnerFields)fields[newPlayerPosition]).setOwner(currentPlayer);
				OwnerFields wantedFieldChange = ((OwnerFields)fields[newPlayerPosition]);
				wantedFieldChange.setOwner(currentPlayer);
				//Man skal ikke betale med payMoney, men med safeTransfer.
				bankruptcy.payMoney(currentPlayer, owner, breweryPropertyValue, players, fields, view);
			}
		}
		//Hvis ejeren ikke er banken, eller en selv
		if(owner != 0 && owner != currentPlayer) {
			int breweryRent = breweryFieldRent[numOfOwnedBrewFields-1];
			view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "' du skal betale " + breweryRent + "kr. til " + players[owner].getPlayerName());
			bankruptcy.payMoney(currentPlayer, owner, breweryRent, players, fields, view);
		}

		if(owner == currentPlayer) {
			view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', " + players[currentPlayer].getPlayerName() + ",  ejer selv dette bryggeri");
		}
	}

	/**
	 * taxField()
	 * @param currentPlayer - aktuel spiller, der er i fokus (i int)
	 * @param owner - indtast ejeren af feltet, der er i fokus (i int)
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void taxField(int currentPlayer, Player[] players,Field[] fields,ViewCTRL view) {
		int newPlayerPosition = players[currentPlayer].getPosition();
		int[] taxValue = (((TaxField)fields[newPlayerPosition]).getReturnValue());
		view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', du skal betale " + taxValue[0] + "kr. i skat"); // tekst til spilleren
		bankruptcy.payMoney(currentPlayer, 0, taxValue[0], players, fields, view);// Transaction som sker på spilleren ud fra hvilket taxfield han lander på
	}

	/**
	 * chanceField()
	 * @param currentPlayer - aktuel spiller, der er i fokus (i int)
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void chanceField(int currentPlayer, Player[] players,Field[] fields,ViewCTRL view) {
		view.writeText(players[currentPlayer].getPlayerName() + " er landet på 'Prøv lykken', du trækker et chance kort"); //Tekst fra gui 
		chancedeck.draw(); //ChanceCardCRTL trækker et kort	
		view.showChanceCard(chancedeck.getDescription());	 //Teksten fra Chancekortet vises i gui 
		//Udfør regel på modellag
		chanceCardRules(currentPlayer, players, fields, view); //kald af metode som fortæller hvilket slags kort man har trukket.
	}

	/**
	 * goToJailField()
	 * @param currentPlayer - aktuel spiller, der er i fokus (i int)
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void goToJailField(int currentPlayer,Player[] players,Field[] fields,ViewCTRL view) {
		int newPlayerPosition = players[currentPlayer].getPosition();
		view.writeText(players[currentPlayer].getPlayerName() + " er landet på '" + fields[newPlayerPosition].getName() + "', du skal nu i fængsel"); //tekst til spilleren.
		players[currentPlayer].setPosition(10); // spilleren position bliver rykket til felt nr 10
		players[currentPlayer].addTurnsInJail(); // Spilleren sidder i fængsel.
		view.updatePlayerPosition(currentPlayer, newPlayerPosition, 10); //update af gui
	}

	/**
	 * chanceCardRules () - Switcher på hvilken type ChanceCard man har trukket, og udfører alt handling.
	 * @param currentPlayer - aktuel spiller, der er i fokus (i int)
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void chanceCardRules (int currentPlayer, Player[] players,Field[] fields,ViewCTRL view) {
		int chanceCardType = chancedeck.getType();
		int[] chanceCardValueArray = chancedeck.getReturnValue();

		//Den her er overflødig da vi på dette sted altid står på et chancekort som er ejet af spillet.

		int owner = 0;
		if ((fields[players[currentPlayer].getPosition()]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[players[currentPlayer].getPosition()]).getOwner());
		}
		switch (chanceCardType) {
		
		case 1: // TransactionCard
			int transactionValue = chanceCardValueArray[0];
			//Hvis tallet er negativt
			if ((transactionValue) < 0) {
				//vi laver nu tallet om til et positivt tal så vi skriver et positivt tal ud og kan skelne
				//mellem removeMoney og recieveMoney i player.

				int realvalue = (transactionValue * (-1)); 
				view.writeText("Der trækkes " + realvalue + "kr. fra " + players[currentPlayer].getPlayerName() + "'s konto.");
				bankruptcy.payMoney(currentPlayer, owner, realvalue, players, fields, view);

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
			int numberofhouses = asset.getNumberOfHousesFromPlayer(currentPlayer, fields);
			int numberofhotels = asset.getNumberOfHotelsFromPlayer(currentPlayer, fields);
			int totalSum = (chanceCardValueArray[0] * numberofhouses)+(chanceCardValueArray[1] * numberofhotels);
			view.writeText("Der trækkes " + totalSum + "kr. fra " + players[currentPlayer].getPlayerName() + "'s konto.");
			bankruptcy.payMoney(currentPlayer, owner, (totalSum), players, fields, view);
			break;
		default:
			System.out.println("ChanceCard-typen der pharses er ikke korrekt.");
		}
	}

	/**
	 * moveToCardRules() - Switcher på hvilket type af MoveToCard man har trukket.
	 * @param currentPlayer - aktuel spiller, der er i fokus (i int)
	 * @param players - indtast objectnavn af typen Player[]
	 * @param fields - indtast objectnavn af typen Field[]
	 * @param view - indtast objectnavn af typen ViewCTRL
	 */
	public void moveToCardsRules (int currentPlayer, Player[] players,Field[] fields, ViewCTRL view) {
		int playerPosition = players[currentPlayer].getPosition();
		int[] chanceCardValueArray = chancedeck.getReturnValue();
		int moveToField = chanceCardValueArray[0];
		int moveToType = chanceCardValueArray[1];

		switch (moveToType){
		case 1:
			// Blot flyttekort til et bestemt felt.
			if(checkForPassingStart(playerPosition, moveToField)) {
				view.writeText(players[currentPlayer].getPlayerName() + " flyttes til " + fields[moveToField].getName() + ", men passerer samtidigt også start og får 4000 kr.");
				players[currentPlayer].recieveMoney(4000);
				players[currentPlayer].setPosition(moveToField);
				view.updatePlayerPosition(currentPlayer, playerPosition, (moveToField));
			} else {
				view.writeText(players[currentPlayer].getPlayerName() + " flyttes til " + fields[moveToField].getName() + ", men passerer samtidigt også start og får 4000 kr.");
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
			int owner=((OwnerFields)fields[newPlayerPos]).getOwner();
			shippingField(currentPlayer, owner, 2, players, fields, view);
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