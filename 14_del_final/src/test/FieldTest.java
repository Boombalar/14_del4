package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Field;

public class FieldTest {

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
	public void testField() {
		Field field = new Field("start",1,2);
		
		String nameactual = field.getName();
		int actualtype = field.getType();
		int actualnumber = field.getNumber();
		String nameexpected = "start";
		int typexpected = 1;
		int numberexpected = 2;

		assertEquals("name virker ikke", nameexpected, nameactual);
		assertEquals("number virker ikke",numberexpected, actualnumber);
		assertEquals("type virker ikke",typexpected, actualtype);
	}
}
