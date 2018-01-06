public class OwnerFields extends Field {

	
	protected int propertyValue;
	protected int owner;
	protected int groupNumber;
	
    public OwnerFields(String name, int type, int number, int propertyValue, int owner, int groupNumber ) {
    	super(name, type, number);
    	
    	this.propertyValue = propertyValue;
		this.owner = owner;
		this.groupNumber = groupNumber;

        }
    
    	public int getPropertyValue() {
    		return this.propertyValue;
    	}
		
		public int getOwner(){
			return this.owner;
}
	    public int getGroupNumber() {
	    	return this.groupNumber;
	    }
}
