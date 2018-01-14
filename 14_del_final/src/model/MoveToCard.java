package model;

public class MoveToCard extends ChanceCard{

	private int[] returnvalue = new int[2];

	/**
	 * Konstruktør til MoveToCard
	 * @param number Nummeret kortet har
	 * @param type Typen kortet har, i dette tilfælde 2.
	 * @param description En streng af beskrivelsen af kortet.
	 * @param fieldnumber Det felt der rykkes til, hvis det er bestemt
	 * @param movetotype Typen af typen MoveTo det er, om det er til nærmeste redderi, rådshuspladsen eller ryk 3 felter tilbage.
	 */
	public MoveToCard(int number, int type, String description, int fieldnumber, int movetotype) {
		super(number, type, description);
		this.returnvalue[0]=fieldnumber;
		this.returnvalue[1]=movetotype; 
	}

	/**
	 * En getter til MoveToCard
	 * @return Kortes nummer.
	 */
	public int[] getReturnValue() {
		return this.returnvalue;
	}
	
}