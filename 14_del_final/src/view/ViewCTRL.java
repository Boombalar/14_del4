package view;

import gui_main.GUI;

import java.awt.Color;
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
import gui_fields.GUI_Brewery;
import gui_fields.GUI_Tax;
import model.*
;


public class ViewCTRL {
	Player[] players;
	model.Field[] fields;

	GUI gui = new GUI();
	GUI_Field[] field;
	GUI_Player[] playerTEMP;
	private GUI_Street street;

	/**
	 * Kunstruktør til ViewCTRL
	 * @param players
	 * @param board
	 */
	public ViewCTRL(Player[] players, Board board) {
		this.players = players;
		this.fields = board.getFields();
		MakeBoard();
	}

	private void MakeBoard() {
		int fieldType;
		Color bgColor = Color.white;
		Color fgColor = Color.white;

		for (int i = 0; i < field.length; i++) {
			fieldType = this.fields[i].getType();

			switch(fieldType) {
			case 0: 
				int groupNumber = (((PropertyFields)fields[i]).getGroupNumber());
				switch(groupNumber) {
				case 1:
					bgColor = Color.blue;
					break;
				case 2:
					bgColor = Color.pink;
					break;
				case 3:
					bgColor = Color.green;
					break;
				case 4:
					bgColor = Color.gray;
					break;
				case 5:
					bgColor = Color.red;
					break;
				case 6:
					bgColor = Color.white;
					break;
				case 7:
					bgColor = Color.yellow;
					break;
				case 8:
					bgColor = Color.magenta;
					break;
	
				}
				field[i] = new GUI_Street(fields[i].getName(),"subText", "description", "100", bgColor, fgColor);
				break; //PropertyField
			case 1: 
				field[i] = new GUI_Shipping(fields[i].getName(),"subText","Description","4000","arg4",Color.cyan,Color.cyan);
				break; //ShipField
			case 2: 
				field[i] = new GUI_Brewery(fields[i].getName(),"subText","Description","3000","arg4",Color.orange,Color.orange);
				break; //BreweryField
			case 3: 
				field[i] = new GUI_Tax(fields[i].getName(),"subText","Description",Color.white, Color.white);
				break; //TaxField
			case 4: 
				field[i] = new GUI_Chance(fields[i].getName(),"subText","Description",Color.black, Color.black);
				break; //ChanceField
			case 5: 
				field[i] = new GUI_Start(fields[i].getName(),"subtext","description",Color.red, Color.red);
				break; //StartField
			
			case 6: 
				field[i] = new GUI_Refuge(fields[i].getName(), "Subtext", "description", "aeg3", Color.black, Color.black);
				break; //NoActionField
			
			case 7: 
				field[i] = new GUI_Refuge(fields[i].getName(), "Subtext", "description", "aeg3", Color.black, Color.black);

				break; //GoToJailField
				
			}
		}

	}
	/**
	 * Metode der giver mulighed for at lave en dropdown menu i GUI
	 * @param buttonText Teksten der kommer over dropdown menuen
	 * @param lines Array med den tekst der skal stå på linjerne
	 * @return Returnere GUIens dropdown menu
	 */
	public String getDropDownChoice(String buttonText, String[] lines) {
		return gui.getUserSelection(buttonText, lines);
	}

	/**
	 * Metode der viser en yes/no knap i GUIen
	 * @param buttonText Tekst over knapperne
	 * @param yesMessage Tekst der skal stå på TRUE knappen
	 * @param noMessage Tekst der skal stå på FALSE knappen
	 * @return Returnere GUIens yes/no menu
	 */
	public boolean getUserAnswer(String buttonText, String yesMessage, String noMessage) {	
		return gui.getUserLeftButtonPressed(buttonText, yesMessage, noMessage);
	}

	/**
	 * OK knappen der styrer flowet i spillet.
	 * @param buttonText Tekst der skal stå i knappen
	 * @param textMessage Tekst der skal stå over knappen
	 */
	public void getUserResponse(String buttonText, String textMessage) {
		gui.getUserButtonPressed(buttonText, textMessage);
	}

	/**
	 * Stiller spilleren et spørgsmål og returnerer svaret spilleren giver.
	 * @return
	 */
	public String getTextField(String question) {
		return gui.getUserString(question);
	}

	/**
	 * Viser knapperne i GUIen
	 * @param die1 Den ene terning
	 * @param die2 Den anden terning
	 */
	public void updateDice(int die1, int die2) {
		gui.setDice(die1, die2);
	}

	/**
	 * metode til at opdatere en spillers position.
	 * @param player Spillerens nummer
	 * @param oldPosition Den gamle position på spilleren.
	 * @param newPosition Den nye position på spilleren.
	 */
	public void updatePlayerPosition(int player, int oldPosition, int newPosition) {
		gui.getFields()[oldPosition].setCar(playerTEMP[player], false);
		gui.getFields()[newPosition].setCar(playerTEMP[player], true);
	}

	/**
	 * Metode der får GUIen til at vise en spillers account
	 * @param player Spillerens nummer
	 * @param amount mængden der skal vises i GUIen
	 */
	public void updatePlayerAccount(int player, int amount) {
		playerTEMP[player].setBalance(amount);
	}

	/**
	 * Opdatere ejerskabet af et felt til spilleren
	 * OBS: MANGLER LOKATIONEN PÅ DET FELT DER SKAL OPDATERES
	 * @param player Spillerens nummer
	 * @param fieldNumber Nummeret på det felt der skal opdateres
	 */
//	public void updateOwnership(int player, int fieldNumber) {
//		street.
//		street.setBorder(playerTEMP[player].getPrimaryColor());
//	}

	/**
	 * Opdatere et felt med huse, fra 0-5, og hvis der er "5" huse på feltet så placere den et hotel istedet.
	 * @param fieldNumber Nummeret på feltet der skal opdateres
	 * @param houses Mængden af huse der skal på feltet 0-5, hvor 0 er ingen huse og 5 er et hotel.
	 */
	public void updateBuildings(int fieldNumber, int houses) {
		boolean hasHotel = false;
		if (houses == 5) {
			hasHotel = true;
			street.setHotel(hasHotel);
		}
		else if (houses < 5 && houses > 0)
			street.setHouses(houses);
		else System.out.println("Fejl i updateBuildings metode");
	}

	/**
	 * Beskeden der står overst på skærmen, med ok knappen der styrer flowed på spillet
	 * @param text Den tekst der skal vises til spilleren.
	 */
	public void writeText (String text) {
		gui.showMessage(text);
	}

	/**
	 * Viser chanceCard teksten i midten af spillebrættet
	 * @param text Den tekst der skal stå i midten af spillebrættet.
	 */
	public void showChanceCard (String text) {
		gui.displayChanceCard(text);
	}
}
