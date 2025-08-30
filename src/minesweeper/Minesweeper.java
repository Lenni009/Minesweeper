package minesweeper;

import minesweeper.view.MainMenu;
import minesweeper.view.MsGUI;
  
  public class Minesweeper {
  
	  // private MsData model;
	  // private MsGUI view;
	  
	  public Minesweeper() {
	  
		  // model = new MsData(8,7);
		  // view = new MsGUI(model);
	  }
	  
	  public static void main(String[] args) {
		  MsGUI view = new MsGUI();
		  MainMenu mainMenu = new MainMenu();
	  
		  MsGUI.addPanel(mainMenu, "menu");
		  MsGUI.showPanel("menu");

		  // Minesweeper controller = new Minesweeper();
		  // controller.view.setVisible(true);
	}
}
 
