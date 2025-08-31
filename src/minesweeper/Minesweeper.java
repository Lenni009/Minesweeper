package minesweeper;

import minesweeper.model.MwData;
import minesweeper.view.MainMenu;
import minesweeper.view.MineField;
import minesweeper.view.MwGUI;
  
  public class Minesweeper {
	  
	  private MwData model;
	  private MineField mineField;
	  
	  public Minesweeper(int sideLength, int bombs) {
		  // TODO: reuse same class instance to avoid memory leaks and other issues with still drawn UI panels
		  model = new MwData(sideLength, bombs);
		  mineField = new MineField(model);
		  
		  MwGUI.addPanel(mineField, "game");
		  MwGUI.showPanel("game");
	  }
	  
	  public static void main(String[] args) {
		  new MwGUI();
		  MainMenu mainMenu = new MainMenu();
	  
		  MwGUI.addPanel(mainMenu, "menu");
		  MwGUI.showPanel("menu");
	}
}
 
