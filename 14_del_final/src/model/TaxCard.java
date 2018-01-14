package model;

public class TaxCard extends ChanceCard {

	private int[] returnvalue = new int[2]; //Et array som på plads 0 indeholder prisen for 1 hus og på plads 2 indeholder prisen for 1 hotel.

	/**
	 * Konstruktør til TaxCard kortet
	 * @param number Nummeret kortet har i ChanceCard arrayet.
	 * @param type Typen af chanceKort det er
	 * @param description Beskrivelsen af chancekortet, der skal vises i GUIen
	 * @param housetax Prisen for hvad dette kort koster per hus
	 * @param hoteltax Prisen for hvad dette kort koster per hotel
	 */
	public TaxCard(int number, int type, String description, int housetax, int hoteltax) {
		super(number, type, description);
		this.returnvalue[0]=housetax;
		this.returnvalue[1]=hoteltax;
	}

	/**
	 * En getter til hvad returværdien skal være for hvis dette kort trækkes.
	 */
	public int[] getReturnValue() {
		return this.returnvalue;
	}
}