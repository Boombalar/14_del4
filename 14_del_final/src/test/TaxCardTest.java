package test;
import static org.junit.Assert.*;
import org.junit.Test;
import model.TaxCard;

public class TaxCardTest {

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