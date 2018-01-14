package model;

public class ChanceDeck {

	private ChanceCard[] chancecards = new ChanceCard[32]; //Danner et array med de 32 chancekort vi har.

	/**
	 * ChanceCardDeck() - Konstruktør til ChanceCardDeck
	 */
	public ChanceDeck() {
	//Opsætning er som følgende
	//De sidste pladser i arrayet bliver bestemt i de klasser der nedarves.
	//chancecards[<nr. i arrayet>] = new <klassen som chancekortet hører til> (<nr. på kortet>, <typen 1-4>, <beskrivelsen af kortet>, <eventuel betaling eller feltet der skal rykkes til>)
		chancecards[0] = new TransactionCard(0,1,"De har måttet vedtage en parkeringsbøde. Betal 200 kr.",-200);
		chancecards[1] = new TransactionCard(1,1,"Betal kr. 3.000 for reperation af Deres vogn",-3000);
		chancecards[2] = new TransactionCard(2,1,"Deres præmieobligation er kommet ud. De modtager kr. 1.000 af banken.",1000);
		chancecards[3] = new TransactionCard(3,1,"Kommunen har eftergivet et kvartals skat. Hæv i banken kr. 3.000",3000);
		chancecards[4] = new TransactionCard(4,1,"De har været en tur i udlandet og haft for mange cigaretter med hjem. Betal told kr. 200", -200);
		chancecards[5] = new TransactionCard(5,1,"Betal Deres bilforsikring kr. 1.000",-1000);
		chancecards[6] = new TransactionCard(6,1,"Deres præmieobligation er kommet ud. De modtager kr. 1.000 af banken.",1000);
		chancecards[7] = new TransactionCard(7,1,"Værdien af egen avl fra nyttehaven udgør kr. 200 som De modtager af banken", 200);
		chancecards[8] = new TransactionCard(8,1,"Betal kr. 3.000 for reperation af Deres vogn",-3000);
		chancecards[9] = new TransactionCard(9,1,"De har måttet vedtage en parkeringsbøde. Betal 200 kr.",-200);
		chancecards[10] = new TransactionCard(10,1,"De modtager Deres aktieudbytte. Modtag kr. 1.000 af banken.",1000);
		chancecards[11] = new TransactionCard(11,1,"Modtag udbytte af Deres aktier kr. 1.000",1000);
		chancecards[12] = new TransactionCard(12,1,"De har kørt frem for Fuld Stop. Betal kr. 1.000 i bøde",-1000);
		chancecards[13] = new TransactionCard(13,1,"De har vundet i Klasselotteriet. Modtag kr. 500",500);
		chancecards[14] = new TransactionCard(14,1,"De har modtaget Deres tandlægeregning. Betal kr. 2.000",-2000);
		chancecards[15] = new TransactionCard(15,1,"Modtag udbytte af Deres aktier kr. 1.000",1000);
		chancecards[16] = new TransactionCard(16,1,"Grundet dyrtiden har De fået gageforhøjelse. Modtag kr. 1.000",1000);
		chancecards[17] = new TransactionCard(17,1,"De har en række med elleve rigtige i tipning. Modtag kr. 1.000",1000);
		chancecards[18] = new MoveToCard(18,2,"Ryk frem til start",0,1);
		chancecards[19] = new MoveToCard(19,2,"Gå i fængsel. Ryk direkte til fængslet. Selvom De passerer start indkasserer De ikke kr. 4.000",10,3);
		chancecards[20] = new MoveToCard(20,2,"Ryk frem til Frederiksberg Allé. Hvis De passerer Start, indkassér kr. 4.000",11,1);
		chancecards[21] = new MoveToCard(21,2,"Tag ind på Rådhuspladsen",39,1);
		chancecards[22] = new MoveToCard(22,2,"Gå i fængsel. Ryk direkte til fængslet. Selvom De passerer start indkasserer De ikke kr. 4.000",10,3);
		chancecards[23] = new MoveToCard(23,2,"Ryk brikken frem til det nærmeste rederi og betal ejeren to gange den leje, han ellers er berettiget til. Hvis selskabet ikke ejes af nogen kan De købe det af banken.",0,2);// Her er moveto parametret ligegyldigt
		chancecards[24] = new MoveToCard(24,2,"Ryk brikken frem til det nærmeste rederi og betal ejeren to gange den leje, han ellers er berettiget til. Hvis selskabet ikke ejes af nogen kan De købe det af banken.",0,2);// Her er moveto parametret ligegyldigt
		chancecards[25] = new MoveToCard(25,2,"Ryk brikken frem til det nærmeste rederi og betal ejeren to gange den leje, han ellers er berettiget til. Hvis selskabet ikke ejes af nogen kan De købe det af banken.",0,2);// Her er moveto parametret ligegyldigt
		chancecards[26] = new MoveToCard(26,2,"Ryk frem 3 felter tilbage",3,4);
		chancecards[27] = new TaxCard(27,4,"Ejendomsskatterne er steget, ekstraudgifterne er: kr. 800 pr. hus og kr. 2.300 pr. hotel",800,2300);
		chancecards[28] = new TaxCard(28,4,"Oliepriserne er steget, og De skal betale: kr. 500 pr. hus og kr. 2.000 pr. hotel",500,2000);
		chancecards[29] = new ReleaseJailCard(29,3,"I anledning af kongens fødselsdag benådes De herved for fængsel. Dette kort kan opbevares, indtil De får brug for det.");
		chancecards[30] = new ReleaseJailCard(30,3,"I anledning af kongens fødselsdag benådes De herved for fængsel. Dette kort kan opbevares, indtil De får brug for det.");
		chancecards[31] = new MoveToCard(31,2,"Ryk frem til Grønningen. Hvis De passerer Start, Indkassér da kr. 4.000",24,1);
	}

	/**
	 * getChanceCards()
	 * Getter til et chancekort
	 * @return Arrayet der indeholder alle chancekortne.
	 */
	public ChanceCard[] getChanceCards() {
		return chancecards;
	}
}