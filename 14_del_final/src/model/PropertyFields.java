package model;

public class PropertyFields extends OwnerFields {

	private int[] rent = new int[8];
	/*private int numberOfHouse;
	private int priceOfHouse;
	Er lagt in i det sidste to pladser i arrayet. 
	*/
	
	/**
	 * 
	 * @param propertyValue	hvad koster feltet
	 * @param owner			hvem ejer felete
	 * @param groupNumber	Gruppenummer eks. hvidovrevej, og rødovrevej er gruppe 1
	 * @param value0	rent 0 huse
	 * @param value1	rent 1 huse
	 * @param value2	rent 2 huse
	 * @param value3	rent 3 huse
	 * @param value4	rent 4 huse
	 * @param value5	rent 5 huse/Hotel
	 * @param value6   antal huse på felt.
	 * @param value7   Pris for at bygge hus.
	 */
	public PropertyFields (String name, int type, int number, int propertyValue, int owner, int groupNumber, int value0, int value1, int value2, int value3, int value4, int value5, int value6, int value7) {
    	super(name, type, number, propertyValue, owner, groupNumber);
		rent[0] = value0; 
		rent[1] = value1;
		rent[2] = value2;
		rent[3] = value3;
		rent[4] = value4;
		rent[5] = value5;
		rent[6] = value6; 
		rent[7] = value7;
				
	}
	public int[] getReturnValue() {
		return this.rent;
	}
	
	
}

//pris 0-5 huse = 6 pladser i array
//hvor mange huse der er på grunden = 1
//pris på hus