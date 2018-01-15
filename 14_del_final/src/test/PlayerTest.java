package test;

import static org.junit.Assert.*;
import org.junit.Test;
import model.Player;

public class PlayerTest {

	Player playerTest = new Player("Adam");

	/**
	 * Tester getBalance, da en spiller instansieres med 30000 på kontoen forventes at getBalance returnere 30000.
	 */
	@Test
	public void testGetBalance() {
		int expected = 30000;
		int actual = playerTest.getBalance();
		assertEquals(expected, actual);
	}

	/**
	 * Tester recieveMoney ved at lægge 5000 til spillerens konto og derefter køres en getBalance for at se om spilleren har 35000.
	 */
	@Test
	public void testRecieveMoney() {
		playerTest.recieveMoney(5000);
		int expected = 35000;
		assertEquals(expected, playerTest.getBalance());

	}

	/**
	 * Tester removeMoney, først trækkes 5000 fra spillerens pengebeholdning på de 30000, og derefter bruges getBalance til at se om 
	 * spillerens pengeholdning er på de 25000 som kommer fra 30000-5000.
	 */
	@Test
	public void testRemoveMoney() {
		playerTest.removeMoney(5000);
		int expected = 25000;
		assertEquals(expected, playerTest.getBalance());
	}

}