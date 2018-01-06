package main;

public class Player {
	
	private String name; //Spillerens navn
	private int turnsInJail; //Antal forsøg der er brugt på at komme ud af jail
	private int equalEyes; //Antal gange en spiller har slået 2 ens.
	private int position; //Spillerens aktuelle lokation
	Account myAccount = new Account(); //En ny instans af Account(Pung)
	private boolean broke = false; //Tjekker om spilleren er bankerot.
	private boolean extraTurn; //Tjekker om spilleren skal have en tur til.
	
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
}
