
public class BreweryFields extends OwnerFields {
	
	int[] multiplyer = new int[2];
	
	public BreweryFields (String name, int type, int number, int popertyValue, int owner, int groupNumber) {
		super(name, type, number, popertyValue, owner, groupNumber);
	

	}

    public int[] returnValue() {
	return this.multiplyer;
			
}

}
