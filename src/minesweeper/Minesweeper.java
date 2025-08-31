package minesweeper;

import minesweeper.model.MsData;
import minesweeper.view.MainMenu;
import minesweeper.view.MineField;
import minesweeper.view.MsGUI;
  
  public class Minesweeper {
	  
	  private static MsGUI view;
	  private MsData model;
	  private MineField mineField;
	  
	  public Minesweeper(int sideLength, int bombs) {
		  // TODO: reuse same class instance to avoid memory leaks and other issues with still drawn UI panels
		  model = new MsData(sideLength, bombs);
		  mineField = new MineField(model);
		  
		  view.addPanel(mineField, "game");
		  view.showPanel("game");
	  }
	  
	  public static void main(String[] args) {
		  view = MsGUI.getInstance();
		  MainMenu mainMenu = new MainMenu();
	  
		  view.addPanel(mainMenu, "menu");
		  view.showPanel("menu");
	}
}
 
