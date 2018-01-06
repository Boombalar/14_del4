package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.model.TransactionCard;

public class TransactionCardTest {

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
	public void testTransactionCard() {
		int[] expectedreturnValue = new int [2];
		int[] actualreturnValue = new int [2];
		
		TransactionCard transactioncard = new TransactionCard(1,1,"description",6);
		int expectedNumber = 1;
		int expectedType = 1;
		String expectedDescription = "description";
		expectedreturnValue[0] = 6;
	
		
		int actualNumber = transactioncard.getNumber();
		int actualType = transactioncard.getType();
		String actualDescription = transactioncard.getDescription();
		actualreturnValue = transactioncard.returnValue();
		
		assertEquals("getNumber virker ikke", expectedNumber, actualNumber);
		assertEquals("getType virker ikke", expectedType, actualType);
		assertEquals("getDescription virker ikke", expectedDescription, actualDescription);
		assertEquals("returnvalue value0 virker ikke", expectedreturnValue[0], actualreturnValue[0]);
				
		
	}

}
