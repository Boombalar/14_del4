package test;

import org.junit.Assert;
import org.junit.Test;

public class DieTest {

	model.Die die = new model.Die();

	@Test
	public void testRoll() {
		int i=0;

		while (i < 40000) {
			die.roll();
			Assert.assertTrue("Dice faceValue to low", this.die.getEyes() >= 1);
			Assert.assertTrue("Dice faceValue to high", this.die.getEyes() <= 6);
			i++;
		}

	}
}
