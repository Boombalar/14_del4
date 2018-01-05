
public class DieCup {

	private int die1=1, die2=1;
	private int numberOfSides;

	private Die die;

	public DieCup (int numberOfsides) {	
		this.numberOfSides = numberOfsides;
		die = new Die(this.numberOfSides);
	}

	public void shake() {
		die.roll();
		this.die1 = this.die.getEyes();
		die.roll();
		this.die2 = this.die.getEyes();
	}

	public int getDiceValue() {
		int dieresult = this.die1 + this.die2;
		return dieresult;
	}

	public boolean getEqualEyes() {
		boolean isEqual=false;
		if (this.die1 == this.die2) {
			isEqual = true;
		}
		return isEqual;
	}
}
