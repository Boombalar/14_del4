package model;

public class DieCup {

	private int die1=0, die2=0; //Instansiere terningerne.
	private Die die = new Die(); // Laver et object af typen Die

	/**
	 * shake()
	 * - laver 2 rolls og gemmer i to værdier
	 * die1 og die2
	 */
	public void shake() {
		die.roll();
		this.die1 = this.die.getEyes();
		die.roll();
		this.die2 = this.die.getEyes();
	}

	/**
	 * getDiceValue()
	 * - får værdien fra die1 og die2
	 * @return - resultetet i int for die1+die2
	 */
	public int getDiceValue() {
		int dieresult = this.die1 + this.die2;
		return dieresult;
	}

	/**
	 * getEqualEyes()
	 * - Tjekker om die1 og die2 er de samme på Die objektet.
	 * Vær opmærksom på at die1 og die2's startværdier er 0.
	 * Så inden der laves shake, er denne true.
	 * @return boolean - true(de samme) / false(ikke de samme) 
	 */
	public boolean getEqualEyes() {
		boolean isEqual=false;
		if (this.die1 == this.die2) {
			isEqual = true;
		} return isEqual;
	}

	/**
	 * getDie1Value()
	 * @return int terning1 værdi.
	 */
	public int getDie1Value () {
		return die1;
	}

	/**
	 * getDie1Value()
	 * @return int terning2 værdi.
	 */
	public int getDie2Value () {
		return die2;
	}

}