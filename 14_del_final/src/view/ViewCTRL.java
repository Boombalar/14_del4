package view;

import gui_main.GUI;
import java.awt.Color;

//import gui_fields.GUI_Board;
import gui_fields.GUI_Car;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Field;
//import gui_fields.GUI_Jail;
import gui_fields.GUI_Player;
import gui_fields.GUI_Refuge;
import gui_fields.GUI_Shipping;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_fields.GUI_Brewery;
import gui_fields.GUI_Tax;
import model.*;


public class ViewCTRL {
	private Player[] players;
	private Field[] fields;

	private GUI gui;
	private GUI_Field[] guiFields = new GUI_Field[40];
	private GUI_Player[] guiPlayer;
	private Color[] carColor = new Color[7];
	private GUI_Car[] guiCar = new GUI_Car[7];


	/**
	 * Kunstruktør til ViewCTRL. Opretter board og spillere.
	 * @param players
	 * @param board
	 */
	public ViewCTRL(Field[] fields) {
		this.fields = fields;
		MakeBoard();
	}

	private void MakeBoard() {
		int fieldType;
		Color bgColor = Color.white;
		Color fgColor = Color.black;
		String price = "";

		//Generer felter til board på baggrund af Model guiFields
		for (int i = 0; i < fields.length; i++) {
			fieldType = this.fields[i].getType();

			//Bestem farve til grupperne
			switch(fieldType) {
			

			case 0: //PropertyField
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
				fgColor = Color.black;
				price = Integer.toString(((PropertyFields)fields[i]).getPropertyValue()) + " kr.";
				guiFields[i] = new GUI_Street(fields[i].getName(),price, "", "100", bgColor, fgColor);
				break; 

			case 1: //ShipField
				guiFields[i] = new GUI_Shipping(fields[i].getName(),"subText","","4000","arg4",Color.cyan,Color.black);
				break; 

			case 2: //BreweryField
				price = Integer.toString(((BreweryFields)fields[i]).getPropertyValue()) + " kr.";
				guiFields[i] = new GUI_Brewery(fields[i].getName(),price,"","","arg4",Color.orange,Color.black);
				break; 

			case 3: //TaxField
				int[] taxint = ((TaxField)fields[i]).getReturnValue();
				String tax = Integer.toString(taxint[0]) + " kr."; 
				guiFields[i] = new GUI_Tax(fields[i].getName(),tax,"",Color.white, Color.black);
				break; 

			case 4: //ChanceField
				guiFields[i] = new GUI_Chance(fields[i].getName(),"","",Color.black, Color.white);
				break; 

			case 5: //StartField
				guiFields[i] = new GUI_Start(fields[i].getName(),"Når du passerer start får du 4000 kr.","",Color.red, Color.black);
				break; 

			case 6: //NoActionField
				guiFields[i] = new GUI_Refuge(fields[i].getName(), "Subtext", "", "aeg3", Color.black, Color.white);
				break; 

			case 7: //GoToJailField
				guiFields[i] = new GUI_Refuge(fields[i].getName(), "Subtext", "", "aeg3", Color.black, Color.white);
				break; 
			}
		}
		gui = new GUI(guiFields);
	}

	public void makeGuiPlayers(Player[] players) {
		
		this.players = players;
		
		//Opretter 6 farver på bilerne
		carColor[1] = new Color(70,250,0);
		carColor[2] = new Color(10,160,230);
		carColor[3] = new Color(200,200,200);
		carColor[4] = new Color(0,0,0);	
		carColor[5] = new Color(50,150,100);
		carColor[6] = new Color(75,100,220);

		//Opret spillere på bræt.
		for (int counter = 1 ; counter < players.length ; counter++) {
			guiCar[counter] = new GUI_Car();
			guiCar[counter].setPrimaryColor(carColor[counter]);
			guiPlayer[counter] = new GUI_Player(players[counter].getPlayerName(),players[counter].getBalance(),guiCar[counter]);
			gui.addPlayer(guiPlayer[counter]);
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
			gui.getFields()[oldPosition].setCar(guiPlayer[player], false);
			gui.getFields()[newPosition].setCar(guiPlayer[player], true);
		}

		/**
		 * Metode der får GUIen til at vise en spillers account
		 * @param player Spillerens nummer
		 * @param amount mængden der skal vises i GUIen
		 */
		public void updatePlayerAccount(int player, int amount) {
			guiPlayer[player].setBalance(amount);
		}

		/**
		 * Opdatere ejerskabet af et felt til spilleren
		 * @param player Spillerens nummer
		 * @param fieldNumber Nummeret på det felt der skal opdateres
		 */
		public void updateOwnership(int player, int fieldNumber) {
			((GUI_Street)guiFields[fieldNumber]).setBorder(guiPlayer[player].getPrimaryColor());
		}

		/**
		 * Opdatere et felt med huse, fra 0-5, og hvis der er "5" huse på feltet så placere den et hotel istedet.
		 * @param fieldNumber Nummeret på feltet der skal opdateres
		 * @param houses Mængden af huse der skal på feltet 0-5, hvor 0 er ingen huse og 5 er et hotel.
		 */
		public void updateBuildings(int fieldNumber, int houses) {
			boolean hasHotel = false;
			if (houses == 5) {
				hasHotel = true;
				((GUI_Street)guiFields[fieldNumber]).setHotel(hasHotel);
			}
			else if (houses < 5 && houses >= 0)
				((GUI_Street)guiFields[fieldNumber]).setHouses(houses);
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
