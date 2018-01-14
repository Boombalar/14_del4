package model;

public class ReleaseJailCard extends ChanceCard{

	/**
	 * Konstruktør til ReleaseJailCard chancekort
	 * @param number Nummeret kortet har i arryet
	 * @param type Typen af chancekort det er
	 * @param description Beskrivelsen der skal vises, hvis dette kort trækkes.
	 */
	public ReleaseJailCard(int number, int type, String description) {
		super(number, type, description);
	}
}