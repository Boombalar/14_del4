package model;

/**
 * Ingen returfelt
 * Danner chancekort feltet, via konstruktør, med et navn, type og nummer, hvilket nedarves fra klassen Field.
 * @author kimsa
 */
public class ChanceField extends Field {
	public ChanceField(String name, int type, int number) {
		super(name, type, number);
		
	}

}
