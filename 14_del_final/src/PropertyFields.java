
public class PropertyFields extends OwnerFields {

	int[] rent = new int[7];
	private int numberOfHouse;
	private int priceOfHouse;
	
	public PropertyFields (String name, int type, int number, int popertyValue, int owner, int groupNumber, int rent, int numberOfHouse, int priceOfHouse ) {
    	super(name, type, number, popertyValue, owner, groupNumber);
		
		//this.rent = rent; //skal den være der 
		this.numberOfHouse = numberOfHouse;
		this.priceOfHouse = numberOfHouse;	
		
	}
	public int getNumberOfHouse(){
		return this.numberOfHouse;
	}
	
	public int getPriceOfHouse() {
		return this.priceOfHouse;
	}
	
	
}

