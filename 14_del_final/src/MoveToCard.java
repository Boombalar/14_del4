

public class MoveToCard extends ChanceCard{

	private int fieldnumber;
	private int movetotype;
	
	public MoveToCard(int number, int type, String description, int fieldnumber, int movetotype) {
		super(number, type, description);
		this.fieldnumber=fieldnumber;
		this.movetotype=movetotype;
		 
	}
	
	public int getFieldNumber() {
		return this.fieldnumber;
	}
	
	public int getMoveToType() {
		return this.movetotype;
	}
	
}
