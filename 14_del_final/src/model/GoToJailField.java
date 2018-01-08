package model;

public class GoToJailField extends Field{
	
	private int[] returnValue = new int[1];
	
	public GoToJailField (String name, int type, int number, int jail) {
		super(name, type, number);
		this.returnValue[0] = jail;
	}
	/**
	 * Returnerer en int[1]
	 * @return    værdien er det felt der skal hoppes til.
	 */
	public int[] getReturnValue() {
		return this.returnValue;	
	}
	
}
