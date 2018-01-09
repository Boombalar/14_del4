package controller;

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

	public ActionCTRL() {
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
	}

	
	//	private void gameSequences();
	//	 	if (players[currentPlayer].checkBroke)
	//	 		i++
	//	//hele gameSequences while loop i et while loop i et forloop. 
	//	//metodee der har logikken bag alle spillers tur 
	//	}
	//	private void tansfer(int currentPlayer, int	receivePlayer, boolean direction, int amount) {
	//		if (direction == true);
	//		players[currentPlayer].removemoney(amount);
	//		players[receivePlayer].recievemoney(amount);
	//		
	//		//recievemoney og removemoney. 
	//		}else { (direction == false);
	//		players[currentPlayer].recieveMoney(amount);
	//		players[receivePlayer].removeMoney.(amount);	
	//}
	//	
	//	
	//	private void switchRules() {
	//		
	//	}
	//	
	//	
	//	private void startGame();
	//	String[] playerAmount = {6, 5, 4, 3, 2};
	//	playernum = 

	//REGEL METODER HERUNDER.
	//}
}