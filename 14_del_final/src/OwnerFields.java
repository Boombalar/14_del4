public class OwnerFields extends Field {

	
	protected int popertyValue;
	protected int owner;
	protected int groupNumber;
	
    public OwnerFields(String name, int type, int number, int popertyValue, int owner, int groupNumber ) {
    	super(name, type, number);
    	
    	this.popertyValue = popertyValue;
		this.owner = owner;
		this.groupNumber = groupNumber;

        }
    
    	public int getPopertyValue() {
    		return this.owner;
    	}
		
		public int getOwner(){
			return this.owner;
}
	    public int getGroupNumber() {
	    	return this.groupNumber;
	    }
}
