

public class ChanceCard {

	protected int number;
	protected int type;
	protected String description;
	
	
	public ChanceCard(int number,int type,String description) {
		this.number=number;
		this.type=type;
		this.description=description;
		
	}
	
	public int getNumber() { 
		return this.number;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int[] returnValue() {
		int[] returnvalue = {0,1};
		return returnvalue;
	}
}
 