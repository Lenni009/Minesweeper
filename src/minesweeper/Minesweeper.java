package minesweeper;

import minesweeper.model.MsData;
import minesweeper.view.MainMenu;
import minesweeper.view.MineField;
import minesweeper.view.MsGUI;
  
  public class Minesweeper {
	  
	  private MsData model;
	  private MineField mineField;
	  
	  public Minesweeper(int sideLength, int bombs) {
		  // TODO: reuse same class instance to avoid memory leaks and other issues with still drawn UI panels
		  model = new MsData(sideLength, bombs);
		  mineField = new MineField(model);
		  
		  MsGUI.addPanel(mineField, "game");
		  MsGUI.showPanel("game");
	  }
	  
	  public static void main(String[] args) {
		  new MsGUI();
		  MainMenu mainMenu = new MainMenu();
	  
		  MsGUI.addPanel(mainMenu, "menu");
		  MsGUI.showPanel("menu");
	}
}
 
