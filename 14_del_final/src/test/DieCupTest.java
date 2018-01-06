package test;

import static org.junit.Assert.*;
import org.junit.Test;

public class DieCupTest {

	main.model.DieCup diecup = new main.model.DieCup();

	@Test
	public void shakeTest() {
		int i = 0;

		while (i < 40000) {
			diecup.shake();
			assertFalse(diecup.getDie1Value() == 0);
			assertFalse(diecup.getDie2Value() == 0);
			i++;
		}
	}

	@Test
	public void getDiceValueTest() {
		int totalValue = diecup.getDie1Value() + diecup.getDie2Value();
		assertTrue(totalValue == diecup.getDiceValue());
	}

	@Test
	public void getEqualEyesTest() {
		boolean isEqualEyes = false;
		diecup.shake();
		if (diecup.getDie1Value() == diecup.getDie2Value()) {
			isEqualEyes = true;
			assertTrue(isEqualEyes == diecup.getEqualEyes());
		}
	}
}