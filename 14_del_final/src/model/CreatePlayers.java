package model;

public class CreatePlayers {

	private Player[] players;
	private String playerName;
	
	public CreatePlayers(int numberOfPlayers) {
		for (int x=0;x<=numberOfPlayers;x++) {
			playerName = "Player "+x;
			players[x]= new Player(playerName);
		}
	}
	
	public Player[] getPlayers() {
		return players;
	}
}
