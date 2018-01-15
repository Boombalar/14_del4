package controller;

import model.*;
import view.*;

public class ActionCTRL {
	private int numberOfPlayers;
	private AssetCTRL asset;
	private BankruptcyCTRL bankruptcy;
	private Board board;
	private CreatePlayers makePlayers;
	private ChanceDeckCTRL chancedeck;
	private DieCup dieCup;
	private DropdownCTRL dropdown;
	private Field[] fields;
	private JailCTRL jail;
	private LandOnFieldCTRL landonfield;
	private Player[] players;
	private TradeCTRL trade;
	private ViewCTRL view;
	private WinnerCTRL winner;

	public ActionCTRL() {
		initialiseGame();
		gameSequence();
	}

	/**
	 * Starter spillet ved at lave alle objekter, og lagre dem i de pladser vi har skabt oven over.
	 */
	private void initialiseGame() {
		chancedeck = new ChanceDeckCTRL(); //Lav chancekort CTRL.
		dieCup = new DieCup(); 		//Lav raflebæger.
		board = new Board();		 //Lav bræt model.
		fields = board.getFields();
		winner = new WinnerCTRL();
		asset = new AssetCTRL();
		trade = new TradeCTRL(asset);
		bankruptcy = new BankruptcyCTRL(asset, trade);
		jail = new JailCTRL(bankruptcy);
		landonfield = new LandOnFieldCTRL(asset, bankruptcy, chancedeck);
		//Opret bræt.
		view = new ViewCTRL(fields);

		//Hent antal spillere.
		String[] lines = {"2","3","4","5","6"};		
		numberOfPlayers = Integer.parseInt(view.getDropDownChoice("Vælg antal spillere 2-6", lines));
		makePlayers = new CreatePlayers(numberOfPlayers, view);  //Lav player array.
		players = makePlayers.getPlayers(); // Modtag player array.
		view.makeGuiPlayers(players); //Opret antal spillere på bræt.
		dropdown = new DropdownCTRL(landonfield, asset, trade);
		view.updateEntireBoard(fields, players); // updatering af boardet på gui, så test / fejlfinding kan blive nemmere.
	}

	/**
	 * Kører gamesekvens for en spiller.
	 */
	private void gameSequence() {
		int currentPlayer = 1; //Den første spiller instantieres til spiller 1

		while (!winner.checkWinner(numberOfPlayers, players)) { //Et while(true) loop der kører indtil vi har fundet 1 vinder

			while (true) {
				// Hvis en spiller er broke, så gå ud af loop
				if(players[currentPlayer].getBroke()) { //Metode der tjekker om en spiller er bankerot
					view.removePlayerCar(currentPlayer, players[currentPlayer].getPosition()); //Fjerner spillerens bil, hvis ovenstående er true
					currentPlayer++;
					break;				
				}

				// Lav Startmenu for spiller
				view.writeText("Det er " + players[currentPlayer].getPlayerName() + "'s tur nu");

				//Hvis spiller er i fængsel håndter det.
				jail.jailHandling(currentPlayer, players, fields, view);

				//Spillermenu
				String[] playerChoice = {"Slå terninger", "Køb hus og hotel","Sælg hus og hotel", "Sælg grund"};
				String choiceOfPlayer = view.getDropDownChoice(players[currentPlayer].getPlayerName() + " - vælg fra dropdown", playerChoice);

				//Håndtér valg fra menu
				//I java ver. 8 kan man switche på en streng :)
				switch(choiceOfPlayer) {

				// Slår terningerne, ændrer position i model lag og opdaterer view lag
				case "Slå terninger":
					dropdown.rollDice(currentPlayer, players, fields, view, dieCup);
					if (players[currentPlayer].getExtraTurn() == true) { //Tjekker om en spiller har slået 2 ens men en boolean.
						view.writeText(players[currentPlayer].getPlayerName() + " slog to ens, og har derfor en ekstra tur");
						players[currentPlayer].setExtraTurn(false); //Sætter en spillers extraTurn til false efter den blev sat til true.
						currentPlayer--;
					}
					break;

					// Køb huse og hoteller.
				case "Køb hus og hotel":
					dropdown.buyHousesAndHotel(currentPlayer, players, fields, view);
					if (dropdown.getBackToDropDown()) { //Metode der smider spilleren tilbage til "start" menuen, hvis en spiller valgte noget andet end slå terninger
						currentPlayer--;
					}
					break;

					//Sælg huse og hoteller.
				case "Sælg hus og hotel":
					dropdown.sellHousesAndHotels(currentPlayer, players, fields, view);
					if (dropdown.getBackToDropDown()) { //Metode der smider spilleren tilbage til "start" menuen, hvis en spiller valgte noget andet end slå terninger
						currentPlayer--;
					}
					break;

					//Sælg grund hvis man ejer den og der ikke er nogle huse på
				case "Sælg grund":
					dropdown.sellProperty(currentPlayer, players, fields, view);
					if (dropdown.getBackToDropDown()) { //Metode der smider spilleren tilbage til "start" menuen, hvis en spiller valgte noget andet end slå terninger
						currentPlayer--;
					}
					break;

				}
				//fortæller systemet af det er næsten spillers tur.
				currentPlayer++;
				//Når det har været sidste spillers tur bliver den sat til spiller 1 igen.
				if (currentPlayer == players.length){
					currentPlayer = 1; 		
				}

				if (winner.checkWinner(numberOfPlayers, players)) { //Boolean på om der er 1 spiller tilbage
					winner.printWinner(currentPlayer, numberOfPlayers, players, view); //Metode der printer vinderen, hvis ovenstående er sandt
					break;
				}
			}
		}
	}
	
}