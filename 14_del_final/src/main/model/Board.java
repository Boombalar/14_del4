package main.model;

public class Board {

	private Field[] field;





	public int getFieldType(int type) {
		Field[type].getType;
		return type;
	}


	public Board() {
		//opret felter



		field[0] = new StartField("Start", 4, 1);

		int[] rent = {50,250,750,2250,4000,6000};
		field[1] = new PropertyFields("Rødovrevej",1,2,rent,0,0);

		field[2] = new ChanceField("Prøv Lykken",4,3);

		int[] rent1 = {50,250,750,2250,4000,6000};
		field[3] = new PropertyFields("Hvidovrevej",1,4,rent1,0,0);

		int[] tax = {4000};
		field[4] = new TaxField("Betal indkomst Skat",3,5,tax);

		int[] rent2 = {500,1000,2000,4000};
		field[5] = new ShipFields("SFL",1,6,rent2);

		int[] rent3 = {100,600,1800,5400,8000,11000};
		field[6] = new PropertyFields("Roskildevej",1,7, rent3,0,0);

		field[7] = new ChanceField("Prøv lykken", 4, 8);

		int[] rent4 = {100,600,1800,5400,8000,11000};
		field[8] = new PropertyFields("Valby Langgade",1,9, rent4,0,0);

		int[] rent5 = {150,800,2000,6000,9000,12000};
		field[9] = new PropertyFields("Allégade",1,10,rent5,0,0);

		field[10] = new NoActionField("På Besøg",4,11);

		int[] rent6 = {200,1000,3000,9000,12500,15000};
		field[11] = new PropertyFields("Frederiksberg Allè",1,12,rent6,0,0);

		int[] rent7 = {100,200};
		field[12] = new BreweryFields("Tuborg",1,13,rent7);

		int[] rent8 = {200,1000,3000,9000,12500,15000};
		field[13] = new PropertyFields("Bülowsvej",1,14,0,0);

		int[] rent9 = {250,1250,3750,10000,14000,18000};
		field[14] = new PropertyFields("Gl.Kongevej", 1,15,rent9,0,0);

		int[] rent10 = {500,1000,2000,4000};
		field[15] = new ShipFields("Kalundborg/Århus",1,16,rent10);

		int[] rent11 = {300,1400,4000,11000,15000,19000};
		field[16] = new PropertyFields("Bernstorffsvej",1,17,rent11,0,0);

		field[17] = new ChanceField("Prøv Lykken",4,18);

		int[] rent12 = {300,1400,4000,11000,15000,19000};
		field[18] = new PropertyFields("Hellerupvej",1,19,rent12,0,0);

		int[] rent13 = {350,1600,4400,12000,16000,20000};
		field[19] = new PropertyFields("Strandvej",1,20,rent13,0,0);

		field[20] = new NoActionField("Parkering", 4,21);

		int[] rent14 = {350,1800,5000,14000,17500,21000};
		field[21] = new PropertyFields("Trianglen",1,22,rent14,0,0);

		field[22] = new ChanceField("Prøv Lykken", 4,23);

		int[] rent15 = {350,1800,5000,14000,17500,21000};
		field[23] = new PropertyFields("Østerbrogade",1,24,rent15,0,0);

		int[] rent16 = {400,2000,6000,15000,18500,22000};
		field[24] = new PropertyFields("Grønningen",1,25,rent16,0,0);

		int[] rent17 = {500,1000,2000,4000};
		field[25] = new ShipFields("DFDS Seaways",1,26,rent17);

		int[] rent18 = {450,2200,6600,16000,19500,23000};
		field[26] = new PropertyFields("Bredgade",1,27,rent18,0,0);

		int[] rent19 = {450,2200,6600,16000,19500,23000};
		field[27] = new PropertyFields("Kgs.Nytorv",1,28,rent19,0,0);

		int[] rent20 = {100,200};
		field[28] = new BreweryFields("Coca-Cola",1,29,rent20);

		int[] rent21 = {500,2400,7200,17000,20500,24000};
		field[29] = new PropertyFields("Østergade",1,30,rent21,0,0);

		field[30] = new GoToJailField("De Fængsles",2,31,11);

		int[] rent22 = {550,2600,7800,18000,22000,25000};
		field[31] = new PropertyFields("Amagertorv",1,32,rent22,0,0);

		int[] rent23 = {550,2600,7800,18000,22000,25000};
		field[32] = new PropertyFields("Vimmelskaftet",1,33,rent23,0,0);

		field[33] = new ChanceField("PrøvLykken",4,34);

		
		int[] rent24 = {600,3000,9000,20000,24000,28000};
		field[34] = new PropertyFields("Nygade",1,35,rent24,0,0);

		int[] rent25 = {500,1000,2000,4000};
		field[35] = new ShipFields("Halsskov/Knudshoved",1,36,rent25);

		field[36] = new NoActionField("Prøv Lykken",4,37);

		int[] rent26 = {700,3500,10000,22000,26000,30000};
		field[37] = new PropertyFields("Frederiksberggade",1,38,rent26,0,0);

		int[] rent27 = {2000};
		field[38] = new TaxField("Betal skat",3,39,rent27);

		int[] rent28 = {1000,4000,12000,28000,34000,40000};
		field[39] = new PropertyFields("Rådhuspladsen",1,40,rent28,0,0);

	}
}

