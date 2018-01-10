package model;
/**
 * 
 * @author kimsa
 *
 */
public class TaxField extends Field{
	
	private int[] returnValue = new int[1]; //Er nødt til at være i et array, så det passer ind i strukturen på resten af felterne
	
	/**
	 * Konstruktør på TaxField 
	 * @param name Navnet på feltet
	 * @param type Typen af feltet, kan ses i board klassen.
	 * @param number Nummeret feltet har på brættet, kan ses i board klassen
	 * @param taxAmount Den mængde en spiller skal betale i skat, hvis spilleren lander på dette felt.
	 */
	public TaxField (String name, int type, int number, int taxAmount) {
		super(name, type, number);
		this.returnValue[0] = taxAmount;
	}
	/**
	 * getReturnValue returnerer et int[1]
	 * @return værdien er den skat der skal betales
	 */
	public int[] getReturnValue() {
		return this.returnValue;
	}
	
	
}
