package model;
public class OwnerFields extends Field {

	
	protected int propertyValue;
	protected int owner;
	protected int groupNumber;
	/**
	 * Konstruktør til OwnerFields
	 * @param name Navnet på feltet
	 * @param type Typen af feltet, ændres alt efter hvilket type owner field det er.
	 * @param number Nummeret feltet har på brættet.
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
		 * Hardset en ejer på et felt
		 * @param newOwner
		 */
		public void setOwner(int newOwner) {
			this.owner = newOwner;
			
		}
	    
		 /**
		  * 
		  * @return en getter metode, der returner hvilken gruppe grundkortet er i: groupNumber
		  */
		 public int getGroupNumber() {
	    	return this.groupNumber;
	    }
}
