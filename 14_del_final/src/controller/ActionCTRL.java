package controller;

import model.Player;

public class ActionCTRL {
	model.Player[] players;
	model.Board board;
	view.ViewCTRL view;
	ChanceCardCTRL chanceCard;
	model.DieCup diecup;

	public ActionCTRL() {
		view.ViewCTRL view = new view.ViewCTRL(this.players, this.board);
	}

	private void initialiseGame() {

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
				boolean answer = view.getUserAnswer("Vil du købe redderiet", "ja", "nej");
				
			}
		}




	}

	//REGEL METODER HERUNDER.
}
