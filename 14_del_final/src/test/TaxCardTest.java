package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.TaxCard;

public class TaxCardTest {

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
	public void testTaxCard() {
		int[] expectedreturnValue = new int [2];
		int[] actualreturnValue = new int [2];
		
		TaxCard taxcard = new TaxCard(2,2,"description",4,5);
		int expectedNumber = 2;
		int expectedType = 2;
		String expectedDescription = "description";
		expectedreturnValue[0] = 4;
		expectedreturnValue[1] = 5;
		
		int actualNumber = taxcard.getNumber();
		int actualType = taxcard.getType();
		String actualDescription = taxcard.getDescription();
		actualreturnValue = taxcard.getReturnValue();
		
		assertEquals("getNumber virker ikke", expectedNumber, actualNumber);
		assertEquals("getType virker ikke", expectedType, actualType);
		assertEquals("getDescription virker ikke", expectedDescription, actualDescription);
		assertEquals("returnvalue value0 virker ikke", expectedreturnValue[0], actualreturnValue[0]);
		assertEquals("returnvalue value1 virker ikke", expectedreturnValue[1], actualreturnValue[1]);
		
	}

}
