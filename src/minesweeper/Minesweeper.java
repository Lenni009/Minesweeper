package minesweeper;

import minesweeper.controller.GameTimer;
import minesweeper.enums.Panel;
import minesweeper.model.MsData;
import minesweeper.view.GameTimerLabel;
import minesweeper.view.MainMenu;
import minesweeper.view.MenuBar;
import minesweeper.view.MineField;
import minesweeper.view.MsGUI;
import minesweeper.view.PauseScreen;

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
		view.removePanel("pause");

		model = new MsData(sideLength, mines);
		mineField = new MineField(model);

		view.addPanel(mineField, Panel.GAME);
		view.showPanel(Panel.GAME);
		view.resizeScreen(sideLength);

		MenuBar.getInstance().showGameMenu();
	}

	public void endGame() {
		GameTimer.getInstance().stopTimer();
		GameTimerLabel.getInstance().hideTime();
	}

	public void pauseGame() {
		GameTimer.getInstance().pauseTimer();

		PauseScreen pauseScreen = PauseScreen.getInstance();

		view.addPanel(pauseScreen, Panel.PAUSE);
		view.showPanel(Panel.PAUSE);
	}

	public void resumeGame() {
		view.showPanel(Panel.GAME);
		GameTimer.getInstance().resumeTimer();
	}

	public void restartGame() {
		endGame();
		startGame(sideLength, mines);
	}

	public void backToMainMenu() {
		endGame();
		view.showPanel(Panel.MENU);
		view.resizeScreen();
		MenuBar menuBar = MenuBar.getInstance();
		menuBar.setStatusText("");
		menuBar.hideGameMenu();
	}

	public static void main(String[] args) {
		view = MsGUI.getInstance();
		MainMenu mainMenu = MainMenu.getInstance();

		view.addPanel(mainMenu, Panel.MENU);
		view.showPanel(Panel.MENU);

		view.resizeScreen();
		view.revalidate();
	}

	public MsData getModel() {
		return model;
	}
}
