import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChanceCardTest {

	ChanceCard chancecard = new ChanceCard(1,1,"description");
	
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
	public void testGetNumber() {
		int expected = 1;
		int actual = chancecard.number;
		assertEquals("getNumber metoden virker ikke", expected, actual);
		
	}
		
	@Test
	public void testGetType() {
		int expected = 1;
		int actual = chancecard.getType();
		assertEquals("getType metoden virker ikke", expected, actual);
		
	}
	
	@Test
	public void testGetDescription() {
		String expected = "description";
		String actual = chancecard.getDescription();
		
	}
	 

		

}
