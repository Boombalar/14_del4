package model;

public class TransactionCard extends ChanceCard {

	private int[] returnvalue = new int[1];
	
	public TransactionCard(int number, int type, String description, int amount) {
		super(number, type, description);
		this.returnvalue[0]=amount;
	}
	
	public int[] returnValue() {
		return this.returnvalue;
	}
}
