
public class PropertyFields extends OwnerFields {

	int[] rent = new int[8];
	//private int numberOfHouse;
	//private int priceOfHouse;
	
	public PropertyFields (String name, int type, int number, int popertyValue, int owner, int groupNumber, int[] rent) {
    	super(name, type, number, popertyValue, owner, groupNumber);
		
		
		this.rent[6] = numberOfHouse;
		this.rent[7] = priceOfHouse;	
		
	}
	public int[] getReturnValue() {
		return this.rent;
	}
	
	
}

//pris 0-5 huse = 6 pladser i array
//hvor mange huse der er på grunden = 1
//pris på hus