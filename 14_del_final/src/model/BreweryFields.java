package model;

public class BreweryFields extends OwnerFields {

	private int[] multiplier = new int[2]; //Hvis en spiller ejer begge bryggerierne, så er det dobbelt gange antal øjne, istedet for 1 gang.

	/**
	 * Konstruktør til BreweryFields
	 * @param name Navn på feltet.
	 * @param type Tallet der bruges til at switche til den rigtige felt regel i ActionCTRL.
	 * @param number Hvilket nummer de har i arrayet, det vil sige hvor de ligger på brættet.
	 * @param propertyValue Hvad feltet koster at købe for en spiller.
	 * @param owner Hvis feltet er ejet af en spiller, står ejerens nummer her. Default er 0.
	 * @param groupNumber Det tal der gør at vi kan se om vi alle i en gruppe, for brewery er det gruppe 10.
	 * @param Value0 Multiplieren på hvor mange gange en potentential "lejer" skal betale ejeren, value0 er 100
	 * @param Value1 Multiplieren på hvor mange gange en potentential "lejer" skal betale ejeren, value0 er 200
	 */
	public BreweryFields (String name, int type, int number, int propertyValue, int owner, int groupNumber, int Value0, int Value1) {
		super(name, type, number, propertyValue, owner, groupNumber);
		multiplier[0] = Value0;
		multiplier[1] = Value1;
	}

	/**
	 * getReturnValue()
	 * En getter på multiplieren, enten så er den 100 ellers så er den 200, hvis ejeren ejer begge breweries.
	 * @return 100/200 alt efter om ejeren ejer dem begge.
	 */
	public int[] getReturnValue() {
		return this.multiplier;
	}
}