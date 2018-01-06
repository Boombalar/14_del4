
public class ShipFields extends OwnerFields {
	
	int[] rent = new int[4]; //array på 6
	
	public ShipFields (String name, int type, int number, int propertyValue, int owner, int groupNumber, int value0, int value1, int value2, int value3 ) {
		super(name, type, number, propertyValue, owner, groupNumber);
		rent[0] = value0;
		rent[1] = value1;
		rent[2] = value2;
		rent[3] = value3;
		
	}
	public int[] returnValue() {
		return this.rent;
}
}
