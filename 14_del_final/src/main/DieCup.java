package main;
public class DieCup {

	private int die1=0, die2=0;

	private Die die = new Die();

	public DieCup () {	
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

	public int getDie1Value () {
		return die1;
	}

	public int getDie2Value () {
		return die2;
	}
}
