package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.model.MoveToCard;

public class MoveToCardTest {

	
	
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
	public void testMoveToCard() {
		int[] expectedreturnValue = new int [2];
		int[] actualreturnValue = new int [2];
			
		MoveToCard movetocard = new MoveToCard(1,1,"description",2,3);
		int expectedNumber = 1;
		int expectedType = 1;
		String expectedDescription = "description";
		expectedreturnValue[0] = 2;
		expectedreturnValue[1] = 3;

		
		int actualNumber = movetocard.getNumber();
		int actualType = movetocard.getType();
		String actualDescription = movetocard.getDescription();
		actualreturnValue=movetocard.getReturnValue();
		
		assertEquals("getNumber virker ikke", expectedNumber, actualNumber);
		assertEquals("getType virker ikke", expectedType, actualType);
		assertEquals("getDescription virker ikke", expectedDescription, actualDescription);
		assertEquals("returnvalue value0 virker ikke", expectedreturnValue[0], actualreturnValue[0]);
		assertEquals("returnvalue value1 virker ikke", expectedreturnValue[1], actualreturnValue[1]);
		
		
		
		
		
		
		
			
	
	}

}
