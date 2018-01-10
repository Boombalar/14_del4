package model;

public class Player {
	
	private String name; //Spillerens navn
	private int turnsInJail=0; //Antal forsøg der er brugt på at komme ud af jail
//	private int equalEyes=0; //Antal gange en spiller har slået 2 ens.
	private int position=0; //Spillerens aktuelle lokation
	private Account myAccount = new Account(); //En ny instans af Account(Pung)
	private boolean broke = false; //Tjekker om spilleren er bankerot.
	private boolean extraTurn=false; //Tjekker om spilleren skal have en tur til.
	private int releaseCard=0;
	
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
		if (getBalance() < 0)
			broke = true;
		return broke;
			
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	
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
	
	public String getPlayerName() {
		return this.name;
	}
	
	public boolean getExtraTurn() {
		return this.extraTurn;
	}
	
	public void setExtraTurn(boolean extraTurn) {
		this.extraTurn=extraTurn;
	}

	public int getTurnsInJail() {
		return turnsInJail;
	}
	public void setTurnsInJail(int turnsInJail) {
		this.turnsInJail = turnsInJail;
	}

	public boolean getBroke() {
		return this.broke;
	}
	
	public void setBroke(boolean broke) {
		this.broke = broke;
	}
	
	public int getReleaseCard() {
		return this.releaseCard;
	}
	
	public void addReleaseCard() {
		this.releaseCard++;
	}
	
	public int useReleaseCard() {
		
		return this.releaseCard;
	}
}
