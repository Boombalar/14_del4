package test;
import static org.junit.Assert.*;

import org.junit.Test;

import model.MoveToCard;

public class MoveToCardTest {

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