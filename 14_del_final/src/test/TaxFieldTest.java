package test;
import static org.junit.Assert.*;
import org.junit.Test;
import model.TaxField;

public class TaxFieldTest {

	@Test
	public void testTaxField() {
		TaxField taxField = new TaxField("Betal Indkomstskat",1,2,4000);
		
		String expectedname ="Betal Indkomstskat";
		int expectedtype = 1;
		int expectednumber = 2;
		int[] expectedtaxAmount = {4000};
		String actualname = taxField.getName();
		int actualnumber = taxField.getNumber();
		int actualtype = taxField.getType();
		int[] actualtaxAmount = taxField.getReturnValue();
		System.out.println( actualtaxAmount[0]);
		System.out.println(expectedtaxAmount[0]);
		
		assertEquals("Name virker ikke", expectedname, actualname);
		assertEquals("Number virker ikke", expectednumber, actualnumber);
		assertEquals("Type virker ikke", expectedtype, actualtype);
		assertEquals("taxAmount med arrays virker ikke",expectedtaxAmount[0], actualtaxAmount[0]);
	}

}