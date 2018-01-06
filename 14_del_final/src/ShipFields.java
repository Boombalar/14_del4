
public class ShipFields extends OwnerFields {
	
	int[] rent = new int[4]; //array på 6
	
	public ShipFields (String name, int type, int number, int propertyValue, int owner, int groupNumber, int[] rent) {
		super(name, type, number, propertyValue, owner, groupNumber);
	
		
	}
	public int[] returnValue() {
		return this.rent;
}
}
