

public class TaxCard extends ChanceCard {
	
	private int housetax;
	private int hoteltax;
	

	public TaxCard(int number, int type, String description, int housetax, int hoteltax) {
		super(number, type, description);
		this.housetax=housetax;
		this.hoteltax=hoteltax;
	}

	public int getHouseTax() {
		return this.housetax;
	}
	
	public int getHotelTax() {
		return this.hoteltax;
	}
	 
}