
public class TransactionCard extends ChanceCard {

	private int amount;
	private int[] returnvalue = new int[2];
	
	public TransactionCard(int number, int type, String description, int amount) {
		super(number, type, description);
		this.returnvalue[0]=amount;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public int[] returnValue() {
		return this.returnvalue;
	}
}
