package main.model;

public class Board {

	Private field[40] Field;


	public Board() {
		//opret felter
		

	}


	public int getFieldType(int type) {
		Field[type].getType;
		return type;
	}

	Field[0] = new Start("Start", 4, 1);

	int[] rent = {50,250,750,2250,4000,6000};
	Field[1] = new Proberty("Rødovrevej",1,2,rent,0,0);

	Field[2] = new NoActionField("Prøv Lykken",4,3);

	int[] rent1 = {50,250,750,2250,4000,6000};
	Field[3] = new Proberty("Hvidovrevej",1,4,rent1,0,0);

	int[] tax = {4000};
	Field[4] = new TaxField("Betal indkomst Skat",3,5,tax);

	int[] rent2 = {500,1000,2000,4000};
	Field[5] = new ShipField("SFL",1,6,rent2);

	int[] rent3 = {100,600,1800,5400,8000,11000};
	Field[6] = new Proberty("Roskildevej",1,7, rent3,0,0);

	Field[7] = new NoActionField("Prøv lykken", 4, 8);

	int[] rent4 = {100,600,1800,5400,8000,11000};
	Field[8] = new Proberty("Valby Langgade",1,9, rent4,0,0);

	int[] rent5 = {150,800,2000,6000,9000,12000};
	Field[9] = new Proberty("Allégade",1,10,rent5,0,0);

	Field[10] = new NoActionField("På Besøg",4,11);

	int[] rent6 = {200,1000,3000,9000,12500,15000};
	Field[11] = new Proberty("Frederiksberg Allè",1,12,rent6,0,0);

	int[] rent7 = {100,200};
	Field[12] = new Proberty("Tuborg",1,13,rent7);

	int[] rent8 = {200,1000,3000,9000,12500,15000};
	Field[13] = new Proberty("Bülowsvej",1,14,0,0);

	int[] rent9 = {250,1250,3750,10000,14000,18000};
	Field[14] = new Proberty("Gl.Kongevej", 1,15,rent9,0,0);

	int[] rent10 = {500,1000,2000,4000};
	Field[15] = new Proberty("Kalundborg/Århus",1,16,rent10);

	int[] rent11 = {300,1400,4000,11000,15000,19000};
	Field[16] = new Proberty("Bernstorffsvej",1,17,rent11,0,0);

	Field[17] = new NoActionField("Prøv Lykken",4,18);

	int[] rent12 = {300,1400,4000,11000,15000,19000};
	Field[18] = new Proberty("Hellerupvej",1,19,rent12,0,0);

	int[] rent13 = {350,1600,4400,12000,16000,20000};
	Field[19] = new Proberty("Strandvej",1,20,rent13,0,0);

	Field[20] = new NoActionField("Parkering", 4,21);

	int[] rent14 = {350,1800,5000,14000,17500,21000};
	Field[21] = new Proberty("Trianglen",1,22,rent14,0,0);

	Field[22] = new NoActionField("Prøv Lykken", 4,23);

	int[] rent15 = {350,1800,5000,14000,17500,21000};
	Field[23] = new Proberty("Østerbrogade",1,24,rent15,0,0);

	int[] rent16 = {400,2000,6000,15000,18500,22000};
	Field[24] = new Proberty("Grønningen",1,25,rent16,0,0);

	int[] rent17 = {500,1000,2000,4000};
	Field[25] = new Proberty("DFDS Seaways",1,26,rent17);

	int[] rent18 = {450,2200,6600,16000,19500,23000};
	Field[26] = new Proberty("Bredgade",1,27,rent18,0,0);

	int[] rent19 = {450,2200,6600,16000,19500,23000};
	Field[27] = new Proberty("Kgs.Nytorv",1,28,rent19,0,0);

	int[] rent20 = {100,200};
	Field[28] = new Proberty("Coca-Cola",1,29,rent20);

	int[] rent21 = {500,2400,7200,17000,20500,24000};
	Field[29] = new Proberty("Østergade",1,30,rent21,0,0);

	Field[30] = new GoToJail("De Fængsles",2,31,11);

	int[] rent22 = {550,2600,7800,18000,22000,25000};
	Field[31] = new Proberty("Amagertorv",1,32,rent22,0,0);

	int[] rent23 = {550,2600,7800,18000,22000,25000};
	Field[32] = new Proberty("Vimmelskaftet",1,33,rent23,0,0);

	Field[33] = new NoActionField("PrøvLykken",4,34);

	int[] rent24 = {600,3000,9000,20000,24000,28000};
	Field[34] = new Proberty("Nygade",1,35,rent24,0,0);

	int[] rent25 = {500,1000,2000,4000};
	Field[35] = new Proberty("Halsskov/Knudshoved",1,36,rent25);

	Field[36] = new NoActionField("Prøv Lykken",4,37);

	int[] rent26 = {700,3500,10000,22000,26000,30000};
	Field[37] = new Proberty("Frederiksberggade",1,38,rent26,0,0);

	int[] rent27 = {2000};
	Field[38] = new TaxField("Betal skat",3,39,rent27);

	int[] rent28 = {1000,4000,12000,28000,34000,40000};
	Field[39] = new Proberty("Rådhuspladsen",1,40,rent28,0,0);


}
}
