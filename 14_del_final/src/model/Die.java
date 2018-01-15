package model;
import java.util.Random;

public class Die {

	private int eyes;
	private int sides = 6; //Antal sidder på terningen.

	/**
	 * roll()
	 * Finder et tilfældigt tal på Die-objektet.
	 */
	public void roll() {
		Random number = new Random();
		this.eyes = number.nextInt(this.sides)+1;
	}

	/**
	 * getEyes()
	 * Modtager øjnenenes værdi på Die-objektet.
	 * @return int - øjnenens værdi.
	 */
	public int getEyes() { 
		return this.eyes;
	}

}