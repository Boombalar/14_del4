package main.model;
import java.util.Random;

public class Die {

	private int eyes;
	private int sides = 6;

	public Die () {
	}

	public void roll() {
		Random number = new Random();
		this.eyes = number.nextInt(this.sides)+1;
	}

	public int getEyes() { 
		return this.eyes;
	}

}
