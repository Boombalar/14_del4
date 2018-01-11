package controller;

import model.*;

public class FieldRuleCTRL {
	landOnFieldCTRL landonfield;

	/**
	 * fieldRulesSwitch() - En metode som switcher på hvilket type felt man er landet på
	 * @param playerNumber - Modtager et spiller nummer
	 */
	public void fieldRulesSwitch (int playerNumber, int newPlayerPosition, Player[] players, Field[] fields) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int owner=0;
		if ((fields[newPlayerPosition]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[newPlayerPosition]).getOwner());
		}

		
		switch (fieldType) {

		case 0:			
			landonfield.propertyField();
			break;
		case 1:
			//ShipFields
			landonfield.shippingFieldRules(playerNumber, 1, newPlayerPosition);
			break;
		case 2:
			//Breweryfields
			landonfield.breweryField();
			break;

		case 3:
			//Taxfields
			landonfield.taxField();
			break;
		case 4:
			//Chancefield			
			landonfield.chanceField();
			break;
		case 7:
			//GoToJailField
			landonfield.goToJailField();
			break;
		}
	}	
}


