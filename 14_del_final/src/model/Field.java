package model;

public abstract class Field {
	protected String name; //Navnet på det givne felt
	protected int type; //Typen på feltet.
	protected int number; //Nummeret feltet har på brættet.

	/**
	 * Konstruktør til Field - Superklasse for alle felterne.
	 * @param name  navn på felt eks. "Hvidovrevej"
	 * @param type  type på felt eks. 1
	 * @param number feltnummer fra 1 til 40. 
	 */
	public Field(String name, int type, int number) {
		this.name = name;
		this.type = type;
		this.number = number;
	}

	/**
	 * Getter til navnet på feltet
	 * @return Navnet på feltet.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Typen feltet har
	 * @return Kommer an på hvilket felt der hentes.
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Feltets plads på brættet
	 * @return Nummeret fletet har på brættet.
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * Bliver overskrevet af subklasserne.
	 * @return Kommer an på subklassen.
	 */
	public int[] returnValue() {
		int[] returnValue = {0};
		return returnValue;
	}
}