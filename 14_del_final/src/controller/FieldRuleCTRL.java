package controller;

import model.*;
import view.ViewCTRL;

public class FieldRuleCTRL {
	
	Toolbox toolbox;
	TradeCTRL trade;
	ChanceCardCTRL chancecard;
	LandOnFieldCTRL landonfield;
	BankruptcyCTRL bankruptcy;
	FieldRuleCTRL fieldRuleSwitch;
	
	public FieldRuleCTRL (Toolbox toolbox,BankruptcyCTRL bankruptcy, TradeCTRL trade, LandOnFieldCTRL landonfield, ChanceCardCTRL chancecard, FieldRuleCTRL fieldRuleSwitch) {
		this.toolbox = toolbox;
		this.chancecard = chancecard;
		this.trade = trade;
		this.landonfield = landonfield;
		this.bankruptcy = bankruptcy;
		this.fieldRuleSwitch = fieldRuleSwitch;
	}
	/**
	 * fieldRulesSwitch() - En metode som switcher på hvilket type felt man er landet på
	 * @param playerNumber - Modtager et spiller numer
	 * @param view 
	 * @param toolbox 
	 * @param bankruptcy 
	 * @param trade 
	 * @param chancecard 
	 * @param fieldRuleSwitch 
	 */
	public void ruleSwitch (int playerNumber, int newPlayerPosition, Player[] players, Field[] fields, ViewCTRL view) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int owner=0;
		if ((fields[newPlayerPosition]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[newPlayerPosition]).getOwner());
		}

		switch (fieldType) {
		
		case 0:			
			landonfield.propertyField(playerNumber, newPlayerPosition, owner, players, fields, view);
			break;
		case 1:
			//ShipFields
			landonfield.shippingFieldRules(playerNumber, 1, newPlayerPosition, players, fields, view);
			break;
		case 2:
			//Breweryfields
			landonfield.breweryField(playerNumber, newPlayerPosition, owner, players, fields, view);
			break;
		case 3:
			//Taxfields
			landonfield.taxField(playerNumber, newPlayerPosition, owner, players, fields, view);
			break;
		case 4:
			//Chancefield			
			landonfield.chanceField(playerNumber, newPlayerPosition, owner, players, fields, view);
			break;
		case 7:
			//GoToJailField
			landonfield.goToJailField(playerNumber, newPlayerPosition, players, fields, view);
			break;
		}
	}	
}

