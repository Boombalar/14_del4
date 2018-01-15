package test;
import static org.junit.Assert.*;
import org.junit.Test;
import model.TransactionCard;

public class TransactionCardTest {

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
		actualreturnValue = transactioncard.getReturnValue();
		
		assertEquals("getNumber virker ikke", expectedNumber, actualNumber);
		assertEquals("getType virker ikke", expectedType, actualType);
		assertEquals("getDescription virker ikke", expectedDescription, actualDescription);
		assertEquals("returnvalue value0 virker ikke", expectedreturnValue[0], actualreturnValue[0]);
	}

}