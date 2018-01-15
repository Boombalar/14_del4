package model;

public abstract class ChanceCard {

	protected int number; //Nummeret på chancekortet
	protected int type; //Typen på chancekortet
	protected String description; //Beskrivelsen der printes, af chancekortet.

	/**
	 * Konstruktør til ChanceCard
	 * - Superklassen som nedarver til de forskellige typer af chancekort
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
	 * getNumber()
	 * En getter på det nummer chancekortet har i arryet ud fra de 30 kort.
	 * @return returnerer nummeret på chancekortet ud af 30 kort
	 */
	public int getNumber() { 
		return this.number;
	}

	/**
	 * setNumber()
	 * En setter på nummeret, tvinger chancekort nummeret til at være parameteren.
	 * @param number Det kortnummer der ønskes at trække.
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * getType()
	 * En getter på den type af chancekort der er trukkes ud fra de 4 vi har, goToJail, ReleaseCard, MoveTo og TransactionCard.
	 * @return returnerer typen af chancekortet
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * getDescription()
	 * En streng der returnere det der står på kortet.
	 * @return Returnerer beskrivelsen af chancekortet
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * getReturnValue()
	 * @return returnerer et array med to pladser, som bliver overwritet af de nedarvede klasser
	 */
	public int[] getReturnValue() {
		int[] returnvalue = {0,1};
		return returnvalue;
	}
	
}