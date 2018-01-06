package main.model;

public class BreweryFields extends OwnerFields {
	
	int[] multiplier = new int[2];
	
	public BreweryFields (String name, int type, int number, int propertyValue, int owner, int groupNumber, int Value0, int Value1) {
		super(name, type, number, propertyValue, owner, groupNumber);
		multiplier[0] = Value0;
		multiplier[1] = Value1;
	}

    public int[] getReturnValue() {
	return this.multiplier;
			
}

}
