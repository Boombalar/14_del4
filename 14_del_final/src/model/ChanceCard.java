package model;


public class ChanceCard {

	protected int number;
	protected int type;
	protected String description;
	
	/**
	 * Superklassen som nedarver til de forskellige typer af chancekort
	 * @param number er nummeret på chancekortet ud af 30 kort
	 * @param type er typen af chancekortet
	 * @param description er beskrivelsen af chancekortet
	 */
	public ChanceCard(int number,int type,String description) {
		this.number=number;
		this.type=type;
		this.description=description;
		
	}
	
	/**
	 * 
	 * @return returnerer nummeret på chancekortet ud af 30 kort
	 */
	public int getNumber() { 
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * 
	 * @return returnerer typen af chancekortet
	 */
	public int getType() {
		return this.type;
	}
	
	/**
	 * 
	 * @return Returnerer beskrivelsen af chancekortet
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * 
	 * @return returnerer et array med to pladser, som bliver overwritet af de nedarvede klasser
	 */
	public int[] returnValue() {
		int[] returnvalue = {0,1};
		return returnvalue;
	}
}
 