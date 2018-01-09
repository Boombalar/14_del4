package model;

public class CreatePlayers {

	private Player[] players;
	private String playerName;
	
	public CreatePlayers(int numberOfPlayers) {
		players = new Player[numberOfPlayers+1];
		for (int x=1;x<=numberOfPlayers;x++) {
			playerName = "Player "+x;
			players[x]= new Player(playerName);
		}
	}
	
	public Player[] getPlayers() {
		return players;
	}
}
