package minesweeper;

import minesweeper.view.MainMenu;
import minesweeper.view.MwGUI;
  
  public class Minesweeper {
  
	  // private MwData model;
	  // private MwGUI view;
	  
	  public Minesweeper() {
	  
		  // model = new MwData(8,7);
		  // view = new MwGUI(model);
	  }
	  
	  public static void main(String[] args) {
		  MwGUI view = new MwGUI();
		  MainMenu mainMenu = new MainMenu();
	  
		  MwGUI.addPanel(mainMenu, "menu");
		  MwGUI.showPanel("menu");

		  // Minesweeper controller = new Minesweeper();
		  // controller.view.setVisible(true);
	}
}
 
