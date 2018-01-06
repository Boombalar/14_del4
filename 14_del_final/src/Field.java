
public class Field {
	protected String name;
	protected int type;
	protected int number;

	public Field(String name, int type, int number) {
		this.name = name;
		this.type = type;
		this.number = number;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getType() {
		return this.type;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public int[] returnValue() {
		int[] returnValue = {0};
		
		return returnValue;
	}
	
}
