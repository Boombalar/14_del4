package controller;

import model.*;
import view.*;

public class ActionCTRL {
	private int numberOfPlayers;
	private Board board;
	private Field[] fields;
	private CreatePlayers makePlayers;
	private Player[] players;
	private ViewCTRL view;
	private ChanceCardCTRL chanceCard;
	private DieCup dieCup;
	private Toolbox toolbox;
	private JailCTRL jail;
	private DropdownCTRL dropdown;
	private LandOnFieldCTRL landonfield;
	private TradeCTRL trade;
	private WinnerCTRL winner;
	private BankruptcyCTRL bankruptcy;
	private FieldRuleCTRL fieldRuleCTRL;

	public ActionCTRL() {
		initialiseGame();
		gameSequence();
	}

	public void initialiseGame() {
		board = new Board();		 //Lav bræt model.
		fields = board.getFields();
		view = new ViewCTRL(fields);//Opret bræt.
		String[] lines = {"2","3","4","5","6"};		//Hent antal spillere.
		numberOfPlayers = Integer.parseInt(view.getDropDownChoice("Vælg antal spillere 2-6", lines));
		makePlayers = new CreatePlayers(numberOfPlayers);  		//Lav player array.
		players = makePlayers.getPlayers();
		view.makeGuiPlayers(players); //Opret antal spillere på bræt.
		chanceCard = new ChanceCardCTRL(); 		//Lav chancekort CTRL.
		dieCup = new DieCup(); 		//Lav raflebæger.
		toolbox = new Toolbox(fields);
		jail = new JailCTRL(players, view, bankruptcy);
		dropdown = new DropdownCTRL(dieCup, players, fields, view, fieldRuleCTRL, landonfield, toolbox, bankruptcy, trade, chanceCard);
		landonfield = new LandOnFieldCTRL(players, fields, toolbox, view, bankruptcy, trade, landonfield, chanceCard, fieldRuleCTRL);
		trade = new TradeCTRL(players, fields, toolbox);
		winner = new WinnerCTRL(players, view);
		bankruptcy = new BankruptcyCTRL(players, fields, toolbox, trade);
	}
	/**
	 * gameSequence
	 * kører gamesekvens for en spiller
	 * 
	 */
	private void gameSequence() {
		int oldPlayerPosition = 0; //En given spiller start position på en runde
		int newPlayerPosition; //Den position en given spiller rykkes til når terningerne er slået
		int currentPlayer = 1; //Den første spiller instantieres til spiller 1
		int diceValue; //Den samlede mængde af terningerne
		int amountOfProperties=0; //Den mængde grunde en given spiller ejer?
		int index;
		String[] propertyArray; //Det String array som indeholder alle de grunde en given spiller ejer.
		String choice; //Det valg en given spiller vælger ud fra propertyString array.
		int[] returnValue; //Hvilken specifik returværdi det dette?s

		while (!winner.checkWinner(numberOfPlayers)) { //Et while(true) loop der kører indtil vi har fundet 1 vinder

			jail.jailHandling(currentPlayer);

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
					dropdown.rollDice(currentPlayer);
					break;

					// Køb huse og hoteller.
					// Finder de felter hvor spilleren ejer hele grupper. 
					// Giver mulighed for at bygge på de felter.
				case "Køb huse og hoteller":
					//Vi starter med at finde ud af hvor mange PropertyFields man ejer hvor man har hele gruppen
					//Således vi kan opbygge et array til dropdownlisten.

					dropdown.buyHousesAndHotel(currentPlayer);

					break;

					//Sælg huse og hoteller.
					//Find de grunde hvor spilleren ejer huse
					//sælg et hus.
				case "Sælg huse og hoteller":

					//Find antal propertyfields med huse
					//Således vi kan lave Array til dropdown


					dropdown.sellHousesAndHotels(currentPlayer);
					break;

					//Sælg grund hvis man ejer den og der ikke er nogle huse på
					//Man kan sælge til banken eller anden spiller
				case "Sælg grund":

					dropdown.sellProperty(currentPlayer);
					break;
					//Lav logik for spillers choice
				}
				//			view.updatePlayerAccount(currentPlayer, players[currentPlayer].getBalance());
				currentPlayer++;
				if (currentPlayer > players.length-1){
					currentPlayer = 1;
				}

				if (winner.checkWinner(numberOfPlayers)) {
					winner.printWinner(numberOfPlayers);
					break;
				}
			}
		}
	}
}