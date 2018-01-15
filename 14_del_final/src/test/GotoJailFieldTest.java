package test;
import static org.junit.Assert.*;

import org.junit.Test;

import model.GoToJailField;

public class GotoJailFieldTest {

	@Test
	public void testGoToJailField() {
		GoToJailField gotoJailField = new GoToJailField("Ryk direkte i fængsel",1,2,31);

		String expectedname ="Ryk direkte i fængsel";
		int expectedtype = 1;
		int expectednumber = 2;
		int[] expectedjailfield = {31};
		String actualname = gotoJailField.getName();
		int actualnumber = gotoJailField.getNumber();
		int actualtype = gotoJailField.getType();
		int[] actualjailfield = gotoJailField.getReturnValue();
		System.out.println( actualjailfield[0]);
		System.out.println(expectedjailfield[0]);

		assertEquals("Name virker ikke", expectedname, actualname);
		assertEquals("Number virker ikke", expectednumber, actualnumber);
		assertEquals("Type virker ikke", expectedtype, actualtype);
		assertEquals("taxAmount med arrays virker ikke",expectedjailfield[0], actualjailfield[0]);
	}

}
