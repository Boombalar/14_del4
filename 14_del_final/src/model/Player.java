package model;

public class Player {
	
	private String name; //Spillerens navn
	private int turnsInJail=0; //Antal forsøg der er brugt på at komme ud af jail
//	private int equalEyes=0; //Antal gange en spiller har slået 2 ens.
	private int position=0; //Spillerens aktuelle lokation
	private Account myAccount = new Account(); //En ny instans af Account(Pung)
	private boolean broke = false; //Tjekker om spilleren er bankerot.
	private boolean extraTurn=false; //Tjekker om spilleren skal have en tur til.
	private int releaseCard=0; //Antal kort der giver spilleren mulighed for at komme smertefrit ud at fængsel
	
	/**
	 * Konstruktør på spilleren
	 * @param name Navnet på hvad spilleren skal hedde.
	 */
	public Player(String name) {
		this.name = name;
	}
	
	/**
	 * En getter til spillerens balance som går ind i den instans af account og tjekker hvad den får at getter som tjekker den variabel som er spillerens pengebeholdning.
	 * @return Returnere getBalance metoden fra klassen Account.
	 */
	public int getBalance() {
		return myAccount.getBalance();
	}
	
	/**
	 * Tjekker om spilleren er bankerot
	 * @return false hvis spilleren ikke er og true hvis hvis spilleren er.
	 */
	public boolean checkBroke() {
		if (getBalance() <= 0)
			broke = true;
		return broke;
	}
	
	/**
	 * En hardsetter på hvad en given spillers position skal være.
	 * @param position Position på hvor spilleren skal stå henne.
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * En getter på spillerens position
	 * @return Spillerens position.
	 */
	public int getPosition() {
		return this.position;
	}
	
	/**
	 * recieveMoney giver spilleren penge igennem deposit metoden i Account
	 * @param value Den mængde der skal lægges til spillerens pengebeholdning
	 */
	public void recieveMoney(int value) {
		myAccount.deposit(value);
	}
	
	/**
	 * removeMoney trækker penge fra spillerens pung via metoden withdraw fra klasse Account
	 * @param value Den mængde der trækkes fra spillerens pengebeholdning.
	 */
	public void removeMoney(int value) {
		myAccount.withdraw(value);
	}
	
	/**
	 * En getter på spillerens navn
	 * @return Spillerens navn
	 */
	public String getPlayerName() {
		return this.name;
	}
	
	/**
	 * En boolean på om spilleren skal have en extra tur, hvis han slår 2 ens.
	 * @return False, vil altid returnere false, medmindre den er specifikt sat til at være true med setteren, og da vil den altid returnere true.
	 */
	public boolean getExtraTurn() {
		return this.extraTurn;
	}
	
	/**
	 * Setter en spiller extraTurn til parameteren, og da forbliver spillerens extraTurn boolean til dette, indtil ændret.
	 * @param extraTurn Hvis en spiller skal have en extra tur, sæt da true.
	 */
	public void setExtraTurn(boolean extraTurn) {
		this.extraTurn=extraTurn;
	}

	/**
	 * En getter på hvor mange turer en spiller har siddet i fængsel.
	 * @return En integer på hvor mange turer en spiller har siddet i fængsel
	 */
	public int getTurnsInJail() {
		return turnsInJail;
	}
	
	/**
	 * Hardsetter en spillers tur i fængsel.
	 * @param turnsInJail Antal turer det ønskes at sætte en spillers tur i fængsel.
	 */
	public void setTurnsInJail(int turnsInJail) {
		this.turnsInJail = turnsInJail;
	}

	/**
	 * Endnu en metode der returnere om en spiller er bankerot.
	 * @return Returnerer om den tidligere metode giver true/false.
	 */
	public boolean getBroke() {
		return this.broke;
	}
	
	/**
	 * En setter til om en spiller har tabt.
	 * @param broke Boolean, true så er spilleren bankerot.
	 */
	public void setBroke(boolean broke) {
		this.broke = broke;
	}
	
	/**
	 * En getter på hvor mange releaseCards en given spiller har
	 * @return Antal release cards, defualt 0.
	 */
	public int getReleaseCard() {
		return this.releaseCard;
	}
	
	/**
	 * Køres denne metode tilføjes 1 til antal releaseCards.
	 */
	public void addReleaseCard() {
		this.releaseCard++;
	}
	
	/**
	 * En camougeret getter til antal releaseCards en spiller har.
	 * @return Antal kort en spiller har.
	 */
	public int useReleaseCard() {
		return this.releaseCard;
	}
}
