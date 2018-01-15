package model;

public class ShippingFields extends OwnerFields {

	private int[] rent = new int[4]; //array på de 5 forskellige priser det kan koste at lande på et ejet redderi.

	/**
	 * Konstruktør til ShipFields(Redderi)
	 * @param name Navnet på redderiet
	 * @param type Typen OwnerFields det er
	 * @param number Hvilket nummer det har på brættet.
	 * @param propertyValue Hvad det koster for en spiller at købe grunden af 'banken'
	 * @param owner Tallet på ejeren af grunden, defaualt ingen ejer er null/0.
	 * @param groupNumber Nummeret på den gruppe ShipFields hører til.
	 * @param value0 Hvad det koster hvis en spiller ejer 1
	 * @param value1 Hvad det koster hvis en spiller ejer 2
	 * @param value2 Hvad det koster hvis en spiller ejer 3
	 * @param value3 Hvad det koster hvis en spiller ejer 4
	 */
	public ShippingFields (String name, int type, int number, int propertyValue, int owner, int groupNumber, int value0, int value1, int value2, int value3 ) {
		super(name, type, number, propertyValue, owner, groupNumber);
		rent[0] = value0;
		rent[1] = value1;
		rent[2] = value2;
		rent[3] = value3;

	}

	/**
	 * En getter på hvad det koster for en spiller at lande på et ejet felt, alt efter hvor mange ShipFields ejeren har.
	 * @return Hvad det koster for en spiller er lande på dette felt, hvis det er ejet.
	 */
	public int[] getReturnValue() {
		return this.rent;
	}
	
}