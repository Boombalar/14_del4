package main.model;

public class Account {
	private int balance = 30000; //Start værdi for hver spiller.
	
	/**
	 * getBalance som returnere den givne spillers nuværende pengebeholdning.
	 * @return Spillers nuværende pengebeholdning.
	 */
	public int getBalance() {
		return balance;
	}
	
	/**
	 * Withdraw(Udtræk) penge fra den givne spillers pengebeholdning.
	 * @param value Den mængde der skal trækkes ud af spillerens account.
	 */
	public void withdraw(int value) {
		this.balance -= value;
	}
	
	/**
	 * deposit(indsæt) penge til den givne spillers pengebeholdning.
	 * @param value Den mængde der skal lægges til spillerens account.
	 */
	public void deposit(int value) {
		this.balance += value;
	}
}