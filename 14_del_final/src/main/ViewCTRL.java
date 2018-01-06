package main;

import gui_main.GUI;

import java.lang.reflect.Field;

import gui_fields.GUI_Board;
import gui_fields.GUI_Car;
import gui_fields.GUI_Car.Type;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Field;
import gui_fields.GUI_Jail;
import gui_fields.GUI_Player;
import gui_fields.GUI_Refuge;
import gui_fields.GUI_Shipping;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;

public class ViewCTRL {
	GUI gui = new GUI();
	GUI_Field field;
	GUI_Player[] playerTEMP;

	
	
	public ViewCTRL(Player[] players, Board board) {
		
	}
	
	public String getDropDownChoice(String buttonText, String[] lines) {
		return gui.getUserSelection(buttonText, lines);
	}
	
	public boolean getUserAnswer(String buttonText, String yesMessage, String noMessage) {	
		return gui.getUserLeftButtonPressed(buttonText, yesMessage, noMessage);
	}
	public void getUserResponse(String buttonText, String textMessage) {
		gui.getUserButtonPressed(buttonText, textMessage);
	}
	
	public String getTextField() {
		String enteredText="";
		
		return enteredText;
	}
	
	public void updateDice(int die1, int die2) {
		gui.setDice(die1, die2);
	}
	
	public void updatePlayerPosition(int player, int oldPosition, int newPosition) {
		gui.getFields()[oldPosition].setCar(Player[player], false);
		gui.getFields()[newPosition].setCar(Player[player], true);
	}
	
	public void updatePlayerAccount(int player, int amount) {
		Player[player].setBalance(amount);
	}
	
	public void updateOwnership(int player, int fieldnum) {
		
	}
	
	public void updateBuildings(int fieldnum, int houses, int hotel) {
		
	}
	
	public void writeText (String text) {
		
	}
	
	public void showChanceCard (String text) {
		
	}
}
