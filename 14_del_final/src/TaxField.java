
public class TaxField extends Field{
	int taxAmount;

	public TaxField (String name, int type, int number, int taxAmount) {
		super(name,type,number);
		this.taxAmount = taxAmount;
	}

	public int getTaxAmount() {
		return this.taxAmount;
	}
}
