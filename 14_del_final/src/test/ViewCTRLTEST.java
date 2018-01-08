package test;
import view.*;

import java.util.Random;

import controller.*;
import model.*;

public class ViewCTRLTEST {
	Board board;
	Field[] fields;

	CreatePlayers makePlayers;
	Player[] players;

	ViewCTRL view;
	ChanceCardCTRL chanceCard;
	DieCup dieCup;

	public ViewCTRLTEST() {
		initialiseGame();
	}

	public void initialiseGame() {
		int numberOfPlayers;

		//Lav bræt model.
		board = new Board();		
		fields = board.getFields();

		//Opret bræt.
		view = new ViewCTRL(fields);

		//Hent antal spillere.
		String[] lines = {"2","3","4","5","6"};
		numberOfPlayers = Integer.parseInt(view.getDropDownChoice("Vælg antal spillere 2-6", lines)); //DropDown testet.
		view.writeText(Integer.toString(numberOfPlayers));
		System.out.println("Dropdown og writeText testet");

		//Lav player array.
		makePlayers = new CreatePlayers(6); 
		players = makePlayers.getPlayers();

		//Opret antal spillere på bræt.
		view.makeGuiPlayers(players);

		//Lav chancekort CTRL.
		chanceCard = new ChanceCardCTRL();
		view.showChanceCard(chanceCard.getDescription());// Vis et chancekort er testet
		System.out.println("Chancekort er testet");

		//Lav raflebæger.
		dieCup = new DieCup();
		dieCup.shake();
		view.updateDice(dieCup.getDie1Value(), dieCup.getDie2Value());//Terninger er testet
		System.out.println("terninger er testet");

		//test flyt alle 6 spillere
		for(int x=1;x<=6;x++) {
			Random number = new Random();
			int ranNum = number.nextInt(6)+1;
			view.updatePlayerPosition(x,0,ranNum);
			view.updatePlayerAccount(x, ranNum);
		} //updatePlayerPosition og updatePlayerAccount er testet
		System.out.println("updatePlayerPosition og updatePlayerAccount er testet");
		
		view.updateBuildings(1, 1);
		view.updateBuildings(3, 2);
		view.updateBuildings(6, 3);
		view.updateBuildings(8, 4);
		view.updateBuildings(9, 5);
		System.out.println("updateBuildings er testet");
		
		view.updateOwnership(1, 1);
		view.updateOwnership(3, 1);
		view.updateOwnership(6, 1);
		view.updateOwnership(8, 1);
		view.updateOwnership(9, 1);
		System.out.println("updateOwnership er testet");
		

	}
}
