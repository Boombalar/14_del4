package controller;

import model.Board;
import model.Player;
import view.ViewCTRL;
import model.DieCup;


public class ActionCTRL {
	model.Player[] players;
	model.Board board;
	view.ViewCTRL view;
	ChanceCardCTRL chanceCard;
	model.DieCup diecup;
	private int playernum;
	
	public ActionCTRL() {
	}
	
private void initialiseGame() {
	Player[] players = new Players();
	Board board = new Board();
	ViewCTRL viewCRTL = new ViewCTRL();
	ChanceCardCTRL chanceCardCRTL = new ChanceCardCRTL();
	DieCup dieCup = new DieCup();
	
	int Player[]

	private void startGame();
	String[] playerAmount = {6, 5, 4, 3, 2};
	playernum = 
	
private void gameSequences();
	if (players[currentPlayer].checkBroke)
		i++
//hele gameSequences while loop i et while loop i et forloop. 
//metodee der har logikken bag alle spillers tur 
}
private void tansfer(int currentPlayer, int	receivePlayer, boolean direction, int amount) {
	if (direction == true);
	players[currentPlayer].removemoney(amount);
	players[receivePlayer].recievemoney(amount);
	
	//recievemoney og removemoney. 
	}else { (direction == false);
	players[currentPlayer].recieveMoney(amount);
	players[receivePlayer].removeMoney.(amount);	
}
	
	
private void switchRules(Player[] players, int currentplayer) {

int fieldType;
fieldType = field[players[currentplayer].getPosition()].getType();

switch (fieldType) {

//ProbertyField
case 0:
	int position = players[currentplayer].getPosition();
	int owner = (((PropertyFields)field[position]).getOwner());	
	if (owner == 0) {
		boolean	answer = view.getUserAnswer("Vil du købe denne grund?", "ja", "nej");
		if(answer == true) {
			//indsæt transactionmetode

			//Her bliver feltet skiftet til currentplayer . OBS currentplayer skal skiftes når gamesekvens bliver lavet. 
			(((PropertyFields)field[position]).setOwner(currentplayer);
			// GUI kald, så spiller for en teskt om at han har købt feltet, message "Du har nu købt felt" + field[position].getName

		}else 

	}
	//ShipsFields
case 1:
	if(owner == 0) {

		}
	}
}

	//REGEL METODER HERUNDER.
}
