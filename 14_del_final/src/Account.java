
public class Account {
	private int balance = 30000;
	
	public int getBalance() {
		return balance;
	}
	
	public void withdraw(int value) {
		this.balance -= value;
	}
	public void deposit(int value) {
		this.balance += value;
	}
}