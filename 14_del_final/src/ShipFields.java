
public class ShipFields extends OwnerFields {
	
	int[] rent = new int[4]; //array på 6
	
	public ShipFields (String name, int type, int number, int popertyValue, int owner, int groupNumber) {
		super(name, type, number, popertyValue, owner, groupNumber);
	
		
	}
	public int[] returnValue() {
		return this.rent;
}
}
