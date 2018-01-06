
public class TaxField extends Field{
	
	private int[] returnvalue = new int[1];
	
	public TaxField (String name, int type, int number, int taxAmount) {
		super(name,type,number);
		this.returnvalue[1] = taxAmount;
	}

	public int[] getReturnValue() {
		return this.returnvalue;
	}
}
