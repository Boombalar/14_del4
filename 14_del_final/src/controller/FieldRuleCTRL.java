package controller;

import model.*;
import view.ViewCTRL;

public class FieldRuleCTRL {
	/**
	 * fieldRulesSwitch() - En metode som switcher på hvilket type felt man er landet på
	 * @param playerNumber - Modtager et spiller nummer
	 * @param view 
	 * @param toolbox 
	 * @param bankruptcy 
	 * @param trade 
	 * @param chancecard 
	 * @param fieldRuleSwitch 
	 */
	public void fieldRulesSwitch (int playerNumber, int newPlayerPosition, Player[] players, Field[] fields, LandOnFieldCTRL landonfield, ViewCTRL view, Toolbox toolbox, BankruptcyCTRL bankruptcy, TradeCTRL trade, ChanceCardCTRL chancecard, FieldRuleCTRL fieldRuleSwitch) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int owner=0;
		if ((fields[newPlayerPosition]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[newPlayerPosition]).getOwner());
		}

		switch (fieldType) {
		
		case 0:			
			landonfield.propertyField(playerNumber, newPlayerPosition, owner, players, fields, view, toolbox, bankruptcy, trade);
			break;
		case 1:
			//ShipFields
			landonfield.shippingFieldRules(playerNumber, 1, newPlayerPosition, view, toolbox, players, fields, bankruptcy, trade);
			break;
		case 2:
			//Breweryfields
			landonfield.breweryField(playerNumber, newPlayerPosition, owner, players, fields, view, toolbox, bankruptcy, trade);
			break;
		case 3:
			//Taxfields
			landonfield.taxField(playerNumber, newPlayerPosition, owner, players, fields, view, toolbox, bankruptcy, trade);
			break;
		case 4:
			//Chancefield			
			landonfield.chanceField(playerNumber, newPlayerPosition, owner, players, fields, view, chancecard, toolbox, bankruptcy, fieldRuleSwitch, landonfield, trade);
			break;
		case 7:
			//GoToJailField
			landonfield.goToJailField(playerNumber, newPlayerPosition, players, fields, view);
			break;
		}
	}	
}


