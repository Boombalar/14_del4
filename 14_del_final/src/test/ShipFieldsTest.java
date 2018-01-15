package test;
import static org.junit.Assert.*;
import org.junit.Test;
import model.ShippingFields;

public class ShipFieldsTest {	

	/**
	 * opretter et object af Ship med udfyldte parameter i parameterlisten. Og et forventet array og et Faktisk (det der er udfyldt i parameterliste) array
	 */
	@Test
	public void testShipFields() {
		ShippingFields shipFields = new ShippingFields ("Kalundborg/aarhus",3,2,5000,1,2,500,1000,2000,4000);
		int[] expectedReturnValue = new int[4];
		int[] actualReturnValue = new int[4];

		//hvis ikke jeg kan modtage parameterlisten vil nedarvningen ikke fungere

		String expectedname = "Kalundborg/aarhus";
		int expectedType = 3;
		int expectedNumber = 2;
		int expectedPropertyValue = 5000;
		int expectedOwner = 1;
		int expectedGroupNumber = 2;
		expectedReturnValue[0] = 500;
		expectedReturnValue[1] = 1000;
		expectedReturnValue[2] = 2000;
		expectedReturnValue[3] = 4000;


		String actualname = shipFields.getName();
		int actualType = shipFields.getType();
		int actualNumber = shipFields.getNumber();
		int actualPropertyValue = shipFields.getPropertyValue();
		int actualOwner = shipFields.getOwner();
		int actualGroupNumber = shipFields.getGroupNumber();
		actualReturnValue = shipFields.getReturnValue(); 
		//returner i dette tilfælde et int array der er 4 langt med det værdier der er lagt ind i det.


		assertEquals("Name virker ikke",expectedname, actualname);
		assertEquals("Type virker ikke", expectedType, actualType);
		assertEquals("Number virker ikke", expectedNumber, actualNumber);
		assertEquals("PropertyValue virker ikke", expectedPropertyValue, actualPropertyValue);
		assertEquals("Owner virker ikke", expectedOwner, actualOwner);
		assertEquals("GroupNumber virker ikke", expectedGroupNumber, actualGroupNumber);
		for (int index = 0;index <= 3; index++) {
			assertEquals("returnvalue plads " + index + " virker ikke", expectedReturnValue[index], actualReturnValue[index]);


		}

	}
}
