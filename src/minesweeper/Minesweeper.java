package minesweeper;

import minesweeper.model.MsData;
import minesweeper.view.MainMenu;
import minesweeper.view.MenuBar;
import minesweeper.view.MineField;
import minesweeper.view.MsGUI;

public class Minesweeper {

	private static MsGUI view;
	private static Minesweeper instance;
	private MsData model;
	private MineField mineField;

	private int sideLength;
	private int mines;

	private Minesweeper() {
	}

	// in case someone asks: this is the singleton pattern!
	public static Minesweeper getInstance() {
		if (instance == null) {
			instance = new Minesweeper();
		}
		return instance;
	}

	public void startGame(int sideLength, int mines) {
		this.sideLength = sideLength;
		this.mines = mines;

		view.removePanel("game");

		model = new MsData(sideLength, mines);
		mineField = new MineField(model);

		view.addPanel(mineField, "game");
		view.showPanel("game");

		MenuBar.getInstance().showGameMenu();
	}

	public void backToMainMenu() {
		view.showPanel("menu");
		MenuBar menuBar = MenuBar.getInstance();
		menuBar.setStatusText("");
		menuBar.hideGameMenu();
	}

	public static void main(String[] args) {
		view = MsGUI.getInstance();
		MainMenu mainMenu = MainMenu.getInstance();

		view.addPanel(mainMenu, "menu");
		view.showPanel("menu");
	}

	public int getSideLength() {
		return sideLength;
	}

	public int getMines() {
		return mines;
	}
}
