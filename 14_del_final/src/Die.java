import java.util.Random;

public class Die {

	private int eyes;
	private int sides = 6;
	
	Die (int sides) {
		this.sides = sides;
	}
	
	public void roll() {
		Random number = new Random();
		this.eyes = number.nextInt(this.sides)+1;
	}
	
	public int getEyes() { 
		return this.eyes;
	}
	
}
