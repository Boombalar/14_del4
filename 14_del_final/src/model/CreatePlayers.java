package model;

import view.*;

public class CreatePlayers {

	private Player[] players; //Arrayet der indeholder alle objekterne på spillerne 2-6.
	private String playerName; //Navnet på den give spiller, opbevaret i en streng.
	
	/**
	 * Konstruktør til CreatePlayer, som tager imod den mængde spillere der er i spillet.
	 * Sørger for logikken bag at hver spiller hedder "Player 1/2/3/4" via et forloop, som får fra 1 til antal spillere.
	 * @param numberOfPlayers Den mængde spillere der er i spillet
	 */
	public CreatePlayers(int numberOfPlayers, ViewCTRL view) {
		players = new Player[numberOfPlayers+1];
		for (int x=1;x<=numberOfPlayers;x++) {
			playerName = view.getUserTextInput("Skriv spiller " + x + "'s navn");
			players[x]= new Player(playerName);
		}
	}
	
	/**
	 * Getter til at returnere en given spiller fra det array der indeholder alle Player objekterne.
	 * @return Playerobjektet på den give spiller, spilleren hentes ved smide spillerens tal ind i indexet.
	 */
	public Player[] getPlayers() {
		return players;
	}
}
