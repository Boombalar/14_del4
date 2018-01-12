package controller;

import model.*;
import view.ViewCTRL;

public class FieldRuleCTRL {
	
	Player[] players;
	Field[] fields;
	Toolbox toolbox;
	TradeCTRL trade;
	ViewCTRL view;
	ChanceCardCTRL chancecard;
	LandOnFieldCTRL landonfield;
	BankruptcyCTRL bankruptcy;
	FieldRuleCTRL fieldRuleSwitch;
	
	public FieldRuleCTRL (Player[] players, Field[] fields, Toolbox toolbox, ViewCTRL view, BankruptcyCTRL bankruptcy, TradeCTRL trade, LandOnFieldCTRL landonfield, ChanceCardCTRL chancecard, FieldRuleCTRL fieldRuleSwitch) {
		this.players = players;
		this.fields = fields;
		this.toolbox = toolbox;
		this.view = view;
		this.chancecard = chancecard;
		this.trade = trade;
		this.landonfield = landonfield;
		this.bankruptcy = bankruptcy;
		this.fieldRuleSwitch = fieldRuleSwitch;
	}
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
	public void ruleSwitch (int playerNumber, int newPlayerPosition) {
		int fieldType = fields[players[playerNumber].getPosition()].getType();
		int owner=0;
		if ((fields[newPlayerPosition]) instanceof OwnerFields) {
			owner = (((OwnerFields)fields[newPlayerPosition]).getOwner());
		}

		switch (fieldType) {
		
		case 0:			
			landonfield.propertyField(playerNumber, newPlayerPosition, owner);
			break;
		case 1:
			//ShipFields
			landonfield.shippingFieldRules(playerNumber, 1, newPlayerPosition);
			break;
		case 2:
			//Breweryfields
			landonfield.breweryField(playerNumber, newPlayerPosition, owner);
			break;
		case 3:
			//Taxfields
			landonfield.taxField(playerNumber, newPlayerPosition, owner);
			break;
		case 4:
			//Chancefield			
			landonfield.chanceField(playerNumber, newPlayerPosition, owner);
			break;
		case 7:
			//GoToJailField
			landonfield.goToJailField(playerNumber, newPlayerPosition);
			break;
		}
	}	
}

