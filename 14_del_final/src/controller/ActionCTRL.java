package controller;

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
	
	private void switchRules() {
		
	}
	
	//REGEL METODER HERUNDER.
}
