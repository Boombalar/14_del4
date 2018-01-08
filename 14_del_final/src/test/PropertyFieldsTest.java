package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.PropertyFields;

public class PropertyFieldsTest {

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

	@Test
	public void testPropertyFields() {
		PropertyFields propertyfield = new PropertyFields("Hvidovrevej",1,4,3000,0,1,50,250,750,2250,4000,6000,0,1000);
		int[] expectedReturnValue = new int[8];
		int[] actualReturnValue = new int[8];
		
		String expectedname = "Hvidovrevej";
		int expectedType = 1;
		int expectedNumber = 4;
		int expectedPropertyValue = 1200;
		int expectedOwner = 0;
		int expectedGroupNumber = 1;
		expectedReturnValue[0] = 50;
		expectedReturnValue[1] = 250;
		expectedReturnValue[2] = 750;
		expectedReturnValue[3] = 2250;
		expectedReturnValue[4] = 4000;
		expectedReturnValue[5] = 6000;
		expectedReturnValue[6] = 0;
		expectedReturnValue[7] = 1000;
		
		String actualname = propertyfield.getName();
		int actualType = propertyfield.getType();
		int actualNumber = propertyfield.getNumber();
		int actualPropertyValue = propertyfield.getPropertyValue();
		int actualOwner = propertyfield.getOwner();
		int actualGroupNumber = propertyfield.getGroupNumber();
		actualReturnValue = propertyfield.getReturnValue(); //returnerer i dette tilfælde et int array der er 8 langt med det værdier der er lagt ind i det.
		
		
		assertEquals("Name virker ikke",expectedname, actualname);
		assertEquals("Type virker ikke", expectedType, actualType);
		assertEquals("Number virker ikke", expectedNumber, actualNumber);
		assertEquals("PropertyValue virker ikke", expectedPropertyValue, actualPropertyValue);
		assertEquals("Owner virker ikke", expectedOwner, actualOwner);
		assertEquals("GroupNumber virker ikke", expectedGroupNumber, actualGroupNumber);
		for (int index = 0;index <= 7; index++) {
			assertEquals("returnvalue plads " + index + " virker ikke", expectedReturnValue[index], actualReturnValue[index]);
		}

		
		
//		actualReturnValue[0] = 50;
//		actualReturnValue[1] = 250;
//		actualReturnValue[2] = 750;
//		actualReturnValue[3] = 2250;"
//		actualReturnValue[4] = 4000;
//		actualReturnValue[5] = 6000;
//		actualReturnValue[6] = 0;
//		actualReturnValue[7] = 1000;
//		
		
		
		
	}

}
