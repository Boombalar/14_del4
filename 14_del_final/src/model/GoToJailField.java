package model;

public class GoToJailField extends Field{

	private int[] returnValue = new int[1];

	/**
	 * Konstruktør til GoToJailField
	 * @param name Navnet på feltet, dette tilfælde 'DE FÆNGLES'
	 * @param type Typen på feltet, dette tilfælde 7
	 * @param number Nummeret feltet har på brættet, dette tilfælde 30.
	 * @param jail 
	 */
	public GoToJailField (String name, int type, int number, int jail) {
		super(name, type, number);
		this.returnValue[0] = jail;
	}
	/**
	 * Returnerer en int[1]
	 * @return Værdien er det felt der skal hoppes til.
	 */
	public int[] getReturnValue() {
		return this.returnValue;	
	}
}