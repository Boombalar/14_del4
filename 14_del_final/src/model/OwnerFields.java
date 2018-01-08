package model;
public class OwnerFields extends Field {

	
	protected int propertyValue;
	protected int owner;
	protected int groupNumber;
	/**
	 * 
	 * @param name
	 * @param type
	 * @param number
	 * @param propertyValue Værdien af grunden
	 * @param owner Hvilken spiller ejer feltet
	 * @param groupNumber Nummeret for den gruppe af grunde med samme farve.
	 */
    public OwnerFields(String name, int type, int number, int propertyValue, int owner, int groupNumber ) {
    	super(name, type, number);
    	
    	this.propertyValue = propertyValue;
		this.owner = owner;
		this.groupNumber = groupNumber;

        }
        
        /**
         * 
         * @return en getter metode der returner hvad grunden er værd i variablen: propertyValue
         */
    	public int getPropertyValue() {
    		return this.propertyValue;
    	}
		/**
		 * 
		 * @return en getter metode, der returner hvem der ejer grundkortet: owner
         */
		 
		public int getOwner(){
			return this.owner;
        }
	    
		 /**
		  * 
		  * @return en getter metode, der returner hvilken gruppe grundkortet er i: groupNumber
		  */
		 public int getGroupNumber() {
	    	return this.groupNumber;
	    }
}
