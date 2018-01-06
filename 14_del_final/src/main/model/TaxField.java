package main.model;
/**
 * 
 * @author kimsa
 *
 */
public class TaxField extends Field{
	
	private int[] returnValue = new int[1];
	
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
