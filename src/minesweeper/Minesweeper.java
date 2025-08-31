package minesweeper;

import minesweeper.model.MsData;
import minesweeper.view.MainMenu;
import minesweeper.view.MineField;
import minesweeper.view.MsGUI;

public class Minesweeper {

	private static MsGUI view;
	private static Minesweeper instance;
	private MsData model;
	private MineField mineField;

	private Minesweeper() {
	}

	// in case someone asks: this is the singleton pattern!
	public static Minesweeper getInstance() {
		if (instance == null) {
			instance = new Minesweeper();
		}
		return instance;
	}

	public void startGame(int sideLength, int bombs) {
		mineField.getParent().remove(mineField);

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
