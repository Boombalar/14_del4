package model;

public class MoveToCard extends ChanceCard{

	private int[] returnvalue = new int[2];
	
	public MoveToCard(int number, int type, String description, int fieldnumber, int movetotype) {
		super(number, type, description);
		this.returnvalue[0]=fieldnumber;
		this.returnvalue[1]=movetotype; 
	}
	
	public int[] getReturnValue() {
		return this.returnvalue;
	}
}
