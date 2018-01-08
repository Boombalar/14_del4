package test;

import static org.junit.Assert.*;
import org.junit.Test;

public class DieCupTest {

	main.model.DieCup dieCup = new main.model.DieCup();

	/**
	 * shakeTest()
	 * - tester om shake() laver en ændring på die1 og die2 på Die objektet.
	 */
	@Test
	public void shakeTest() {
			dieCup.shake();
			assertFalse(dieCup.getDie1Value() == 0);
			assertFalse(dieCup.getDie2Value() == 0);
	}

	/**
	 * getDiceValueTest()
	 * - tester om værdien er den samme.
	 * Man trækker info fra de to terningslagringspladser, vs. bruge metoden dicecup.getDiceValue
	 */
	@Test
	public void getDiceValueTest() {
		int totalValue = dieCup.getDie1Value() + dieCup.getDie2Value();
		assertTrue(totalValue == dieCup.getDiceValue());
	}

	/**
	 * getEqualEyesTest()
	 * - tester om værdien af de to lagringspladser til terningerne er de samme.
	 * Man trækker info omkring begge terninger vs. man trækker info fra metoden objektet.getEqualEyes.
	 */
	@Test
	public void getEqualEyesTest() {
		boolean isEqualEyes = false;
		dieCup.shake();
		if (dieCup.getDie1Value() == dieCup.getDie2Value()) {
			isEqualEyes = true;
			assertTrue(isEqualEyes == dieCup.getEqualEyes());
		}
	}
	
}