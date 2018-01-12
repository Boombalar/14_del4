package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Player;

public class PlayerTest {
	
	Player playerTest = new Player("Adam");

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

	/**
	 * Konstruktør skal ikke testes.
	 */
	@Test
	public void testPlayer() {
		///////////////fail("Not yet implemented");
	}

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
	 * Tester checkBroke, da en spiller ikke starter bankerot forventes false, og derefter trækkes 50000 fra spilleren konto
	 * og så forventes at checkBroke er true eftersom spillerens konto er under 0.
	 */
	@Test
	public void testCheckBroke() {
		boolean expected = false;
		assertFalse(expected);
		playerTest.removeMoney(50000);
		assertTrue(playerTest.getBroke());
		
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
