package model;

public class PropertyFields extends OwnerFields {

	private int[] rent = new int[8];

	/**
	 * Konstruktør til propertyFields
	 * @param propertyValue	Hvad koster feltet
	 * @param owner			Hvem ejer felete
	 * @param groupNumber	Gruppenummer eks. hvidovrevej, og rødovrevej er gruppe 1
	 * @param value0	Rent 0 huse
	 * @param value1	Rent 1 huse
	 * @param value2	Rent 2 huse
	 * @param value3	Rent 3 huse
	 * @param value4	Rent 4 huse
	 * @param value5	Rent 5 huse/Hotel
	 * @param value6   Antal huse på felt.
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

	/**
	 * En getter til huslejen på et givet propertyField felt.
	 * @return Hvor mange huse en propertyField grund har
	 */
	public int[] getReturnValue() {
		return this.rent;
	}

	/**
	 * setNumberOfHouses() - Bestemmer hvor mange huse man har på den pågældende grund.
	 * @param returnValue - antal af huse der skal være på feltet.
	 */
	public void setNumberOfHouses(int returnValue) {
		this.rent[6] = returnValue;
	}
	
}