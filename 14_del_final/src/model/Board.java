package model;

public class Board {

	private Field[] field = new Field[40]; //Danner et array med alle de 40 felter der er på brættet.

	public Board() {
		//opret felter

		//Opsætning er som følgende
		//field[<nummer i arrayet>] = new (<klassen som feltet hører til>, <navn>, <data som stammer fra hver af felternes klasses opbygning af arrayet>)
//
//		field[0] = new StartField("Start",5,0);
//		field[1] = new PropertyFields("Rødovrevej",0,1,1200,0,1,50,250,750,2250,4000,6000,0,1000);
//		field[2] = new ChanceField("?",4,2);
//		field[3] = new PropertyFields("Hvidovrevej",0,3,1200,0,1,50,250,750,2250,4000,6000,0,1000);
//		field[4] = new TaxField("Betal indkomst Skat",3,4,4000);
//		field[5] = new ShippingFields("SFL",1,5,4000,0,9,500,1000,2000,4000);
//		field[6] = new PropertyFields("Roskildevej",0,6,2000,0,2,100,600,1800,5400,8000,11000,0,1000);
//		field[7] = new ChanceField("?",4,7);
//		field[8] = new PropertyFields("Valby Langgade",0,8,2000,0,2,100,600,1800,5400,8000,11000,0,1000);
//		field[9] = new PropertyFields("Allégade",0,9,2400,0,2,100,600,1800,5400,8000,11000,0,1000);
//		field[10] = new NoActionField("Statsfængslet",6,10);
//		field[11] = new PropertyFields("Frederiksberg Allè",0,11,2800,0,3,200,1000,3000,9000,12500,15000,0,2000);
//		field[12] = new BreweryFields("Tuborg",2,12,3000, 0, 10,100,200);
//		field[13] = new PropertyFields("Bülowsvej",0,13,2800,0,3,200,1000,3000,9000,12500,15000,0,2000);
//		field[14] = new PropertyFields("Gl.Kongevej", 0,14,3200,0,3,250,1250,3750,10000,14000,18000,0,2000);
//		field[15] = new ShippingFields("Kalu & Årh",1,15,4000,0,9,500,1000,2000,4000);
//		field[16] = new PropertyFields("Bernstorffsvej",0,16,3600,0,4,300,1400,4000,11000,15000,19000,0,2000);
//		field[17] = new ChanceField("?",4,17);
//		field[18] = new PropertyFields("Hellerupvej",0,18,3600,0,4,300,1400,4000,11000,15000,19000,0,2000);
//		field[19] = new PropertyFields("Strandvej",0,19,4000,0,4,350,1600,4400,12000,16000,20000,0,2000);
//		field[20] = new NoActionField("Parkering", 6,20);
//		field[21] = new PropertyFields("Trianglen",0,21,4400,0,5,350,1800,5000,14000,17500,21000,0,3000);
//		field[22] = new ChanceField("?",4,22);
//		field[23] = new PropertyFields("Østerbrogade",0,23,4400,0,5,350,1800,5000,14000,17500,21000,0,3000);
//		field[24] = new PropertyFields("Grønningen",0,24,4800,0,5,400,2000,6000,15000,18500,22000,0,3000);
//		field[25] = new ShippingFields("DFDS",1,25,4000,0,9,500,1000,2000,4000);
//		field[26] = new PropertyFields("Bredgade",0,26,5200,0,6,450,2200,6600,16000,19500,23000,0,3000);
//		field[27] = new PropertyFields("Kgs.Nytorv",0,27,5200,0,6,450,2200,6600,16000,19500,23000,0,3000);
//		field[28] = new BreweryFields("Coca-Cola",2,28,3000, 0, 10,100,200);
//		field[29] = new PropertyFields("Østergade",0,29,5600,0,6,500,2400,7200,17000,20500,24000,0,3000);
//		field[30] = new GoToJailField("De fængsles",7,30,11);
//		field[31] = new PropertyFields("Amagertorv",0,31,6000,0,7,550,2600,7800,18000,22000,25000,0,4000);
//		field[32] = new PropertyFields("Vimmelskaftet",0,32,6000,0,7,550,2600,7800,18000,22000,25000,0,4000);
//		field[33] = new ChanceField("?",4,33);
//		field[34] = new PropertyFields("Nygade",0,34,6400,0,7,600,3000,9000,20000,24000,28000,0,4000);
//		field[35] = new ShippingFields("Hals & Knud",1,35,4000,0,9,500,1000,2000,4000);
//		field[36] = new ChanceField("?",4,36);
//		field[37] = new PropertyFields("Frederiksberggade",0,37,7000,0,8,700,3500,10000,22000,26000,30000,0,4000);
//		field[38] = new TaxField("Betal skat",3,38,2000);
//		field[39] = new PropertyFields("Rådhuspladsen",0,39,8000,0,8,1000,4000,12000,28000,34000,40000,0,4000);

		// Spiller 1 ejer alle de ejede felter.
		field[0] = new StartField("Start",5,0);
		field[1] = new PropertyFields("Rødovrevej",0,1,1200,1,1,50,250,750,2250,4000,6000,2,1000);
		field[2] = new ChanceField("?",4,2);
		field[3] = new PropertyFields("Hvidovrevej",0,3,1200,1,1,50,250,750,2250,4000,6000,1,1000);
		field[4] = new TaxField("Betal indkomst Skat",3,4,4000);
		field[5] = new ShippingFields("SFL",1,5,4000,1,9,500,1000,2000,4000);
		field[6] = new PropertyFields("Roskildevej",0,6,2000,1,2,100,600,1800,5400,8000,11000,1,1000);
		field[7] = new ChanceField("?",4,7);
		field[8] = new PropertyFields("Valby Langgade",0,8,2000,1,2,100,600,1800,5400,8000,11000,1,1000);
		field[9] = new PropertyFields("Allégade",0,9,2400,1,2,100,600,1800,5400,8000,11000,1,1000);
		field[10] = new NoActionField("Statsfængslet",6,10);
		field[11] = new PropertyFields("Frederiksberg Allè",0,11,2800,1,3,200,1000,3000,9000,12500,15000,1,2000);
		field[12] = new BreweryFields("Tuborg",2,12,3000, 1, 10,100,200);
		field[13] = new PropertyFields("Bülowsvej",0,13,2800,1,3,200,1000,3000,9000,12500,15000,1,2000);
		field[14] = new PropertyFields("Gl.Kongevej", 0,14,3200,1,3,250,1250,3750,10000,14000,18000,1,2000);
		field[15] = new ShippingFields("Kalu & Årh",1,15,4000,1,9,500,1000,2000,4000);
		field[16] = new PropertyFields("Bernstorffsvej",0,16,3600,1,4,300,1400,4000,11000,15000,19000,1,2000);
		field[17] = new ChanceField("?",4,17);
		field[18] = new PropertyFields("Hellerupvej",0,18,3600,1,4,300,1400,4000,11000,15000,19000,1,2000);
		field[19] = new PropertyFields("Strandvej",0,19,4000,1,4,350,1600,4400,12000,16000,20000,1,2000);
		field[20] = new NoActionField("Parkering", 6,20);
		field[21] = new PropertyFields("Trianglen",0,21,4400,1,5,350,1800,5000,14000,17500,21000,1,3000);
		field[22] = new ChanceField("?",4,22);
		field[23] = new PropertyFields("Østerbrogade",0,23,4400,1,5,350,1800,5000,14000,17500,21000,1,3000);
		field[24] = new PropertyFields("Grønningen",0,24,4800,1,5,400,2000,6000,15000,18500,22000,1,3000);
		field[25] = new ShippingFields("DFDS",1,25,4000,1,9,500,1000,2000,4000);
		field[26] = new PropertyFields("Bredgade",0,26,5200,1,6,450,2200,6600,16000,19500,23000,1,3000);
		field[27] = new PropertyFields("Kgs.Nytorv",0,27,5200,1,6,450,2200,6600,16000,19500,23000,1,3000);
		field[28] = new BreweryFields("Coca-Cola",2,28,3000, 1, 10,100,200);
		field[29] = new PropertyFields("Østergade",0,29,5600,1,6,500,2400,7200,17000,20500,24000,1,3000);
		field[30] = new GoToJailField("De fængsles",7,30,11);
		field[31] = new PropertyFields("Amagertorv",0,31,6000,1,7,550,2600,7800,18000,22000,25000,1,4000);
		field[32] = new PropertyFields("Vimmelskaftet",0,32,6000,1,7,550,2600,7800,18000,22000,25000,1,4000);
		field[33] = new ChanceField("?",4,33);
		field[34] = new PropertyFields("Nygade",0,34,6400,1,7,600,3000,9000,20000,24000,28000,1,4000);
		field[35] = new ShippingFields("Hals & Knud",1,35,4000,1,9,500,1000,2000,4000);
		field[36] = new ChanceField("?",4,36);
		field[37] = new PropertyFields("Frederiksberggade",0,37,7000,1,8,700,3500,10000,22000,26000,30000,1,4000);
		field[38] = new TaxField("Betal skat",3,38,2000);
		field[39] = new PropertyFields("Rådhuspladsen",0,39,8000,1,8,1000,4000,12000,28000,34000,40000,1,4000);

		// til at lave dine egne felter
//		for (int i = 0; i<=39 ; i++) {
//			field[i] = new ChanceField("?",4,36);
//		}
	}

	/**
	 * Getter til field arrayet
	 * @return Field array der indeholder alle felterne samt logik.
	 */
	public Field[] getFields() {
		return field;
	}
}

