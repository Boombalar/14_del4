package main.model;

public class TaxCard extends ChanceCard {
	
	private int[] returnvalue = new int[2];
	

	public TaxCard(int number, int type, String description, int housetax, int hoteltax) {
		super(number, type, description);
		this.returnvalue[0]=housetax;
		this.returnvalue[1]=hoteltax;
	}
	
	public int[] returnValue() {
		return this.returnvalue;
	}
}