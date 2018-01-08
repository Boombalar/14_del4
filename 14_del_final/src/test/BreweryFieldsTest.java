package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.BreweryFields;

public class BreweryFieldsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
    
	/**
	 * BreweryFields klassen
	 * opretter et object af Ship med udfyldte parameter i parameterlisten. Og et forventet array og et Faktisk (det der er udfyldt i parameterliste) array
	 */
	@Test
	public void testBreweryFields() {
		BreweryFields breweryFields = new BreweryFields ("Tuborgvej",3,2,3000,1,4,100,200);
		int[] expectedReturnValue = new int[2];
		int[] actualReturnValue = new int[2];
		
		
		String expectedname = "Tuborgvej";
		int expectedType = 3;
		int expectedNumber = 2;
		int expectedPropertyValue = 3000;
		int expectedOwner = 1;
		int expectedGroupNumber = 4;
		expectedReturnValue[0] = 100;
		expectedReturnValue[1] = 200;
		
		
		String actualname = breweryFields.getName();
		int actualType = breweryFields.getType();
		int actualNumber = breweryFields.getNumber();
		int actualPropertyValue = breweryFields.getPropertyValue();
		int actualOwner = breweryFields.getOwner();
		int actualGroupNumber = breweryFields.getGroupNumber();
		actualReturnValue = breweryFields.getReturnValue(); 
		
		assertEquals("Name virker ikke",expectedname, actualname);
		assertEquals("Type virker ikke", expectedType, actualType);
		assertEquals("Number virker ikke", expectedNumber, actualNumber);
		assertEquals("PropertyValue virker ikke", expectedPropertyValue, actualPropertyValue);
		assertEquals("Owner virker ikke", expectedOwner, actualOwner);
		assertEquals("GroupNumber virker ikke", expectedGroupNumber, actualGroupNumber);
		for (int index = 0;index <= 1; index++) {
			assertEquals("returnvalue plads " + index + " virker ikke", expectedReturnValue[index], actualReturnValue[index]);
		}
		
		
	}

}
