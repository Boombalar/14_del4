package model;

public class TransactionCard extends ChanceCard {

	private int[] returnvalue = new int[1];
	
	/**
	 * Konstruktør for TransactionCard
	 * @param number Nummeret kortet har i ChanceCard arrayet
	 * @param type Typen af chancekort det er
	 * @param description Beskrivelsen chancekortet har, til fremvisning på GUIen
	 * @param amount Den mængde en spiller mister når dette givne kort trækkes.
	 */
	public TransactionCard(int number, int type, String description, int amount) {
		super(number, type, description);
		this.returnvalue[0]=amount;
	}
	
	/**
	 * En getter til hvad returværdien skal være for hvad det koster at trække dette kort.
	 * @return prisen det koster at trække dette kort
	 */
	public int[] returnValue() {
		return this.returnvalue;
	}
}
