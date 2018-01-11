package controller;

import model.OwnerFields;

public class FieldRuleSwitch {
	/**
	 * fieldRulesSwitch() - En metode som switcher på hvilket type felt man er landet på
	 * @param playerNumber - Modtager et spiller nummer
	 */
	private void fieldRulesSwitch (int playerNumber, int newPlayerPosition) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int owner=0;
		if ((fields[newPlayerPosition]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[newPlayerPosition]).getOwner());
		}

		switch (fieldType) {

		case 0:			
			propertyField
			break;
		case 1:
			//ShipFields
			shippingFieldRules(playerNumber, 1, newPlayerPosition);
			break;
		case 2:
			//Breweryfields
			breweryField
			break;

		case 3:
			//Taxfields
			taxField
			break;
		case 4:
			//Chancefield			
			chanceField.
			break;
		case 7:
			//GoToJailField
			gotoJailField.
			break;
		}
	}	

}
