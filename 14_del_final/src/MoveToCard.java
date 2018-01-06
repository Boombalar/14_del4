

public class MoveToCard extends ChanceCard{

	private int fieldnumber;
	private int movetotype;
	private int[] returnvalue = new int[2];
	
	public MoveToCard(int number, int type, String description, int fieldnumber, int movetotype) {
		super(number, type, description);
		this.returnvalue[0]=fieldnumber;
		this.returnvalue[1]=movetotype;
		
		
		 
	}
	
	public int getFieldNumber() {
		return this.fieldnumber;
	}
	
	public int getMoveToType() {
		return this.movetotype;
	}
	
	public int[] returnValue() {
		return this.returnvalue;
	}
}
