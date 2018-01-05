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
		
		
		public int hentOnwner(){
			return this.owner;
}
	    public int hentGroupNumber() {
	    	return this.groupNumber;
	    }
}
