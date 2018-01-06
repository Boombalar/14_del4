
public class TaxField extends Field{
	
	private int[] returnValue = new int[1];
	
	public TaxField (String name, int type, int number, int taxAmount) {
		super(name,type,number);
		this.returnValue[1] = taxAmount;
	}

	public int[] getReturnValue() {
		return this.returnValue;
	}
}
