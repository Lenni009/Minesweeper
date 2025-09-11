package minesweeper.controller;

import minesweeper.enums.Panel;
import minesweeper.model.MsData;
import minesweeper.view.GameTimerLabel;
import minesweeper.view.MainMenu;
import minesweeper.view.MenuBar;
import minesweeper.view.MineField;
import minesweeper.view.MsGUI;
import minesweeper.view.PauseScreen;

// main game controller class
public class Minesweeper {

	private static Minesweeper instance;
	private MsGUI view;
	private MsData model;

	private int sideLength;
	private int mines;

	private Minesweeper() {
		view = MsGUI.getInstance();
		MainMenu mainMenu = MainMenu.getInstance();

		view.addPanel(mainMenu, Panel.MENU);
		view.showPanel(Panel.MENU);

		view.resizeWindow();
		view.revalidate(); // this is needed because windows would otherwise show a blank screen
	}

	// in case someone asks: this is the singleton pattern!
	public static Minesweeper getInstance() {
		if (instance == null) {
			instance = new Minesweeper();
		}
		return instance;
	}

	/**
	 * starts a new game of Minesweeper
	 * 
	 * @param sideLength length of one side of the playing field
	 * @param mines      total amount of mines
	 */
	public void startGame(int sideLength, int mines) {
		this.sideLength = sideLength;
		this.mines = mines;

		// remove any old playing fields to reduce memory consumption
		view.removePanel(Panel.GAME);
		view.removePanel(Panel.PAUSE);

		model = new MsData(sideLength, mines);
		MineField mineField = new MineField(model);

		view.addPanel(mineField, Panel.GAME);
		view.showPanel(Panel.GAME);
		view.resizeWindow(sideLength); // adjust window size so the text in the buttons isn't truncated

		MenuBar menuBar = MenuBar.getInstance();
		menuBar.showGameMenu();
		menuBar.hidePauseButtons();
	}

	/**
	 * stops the timer and hides it
	 */
	public void endGame() {
		GameTimer.getInstance().stopTimer();
		GameTimerLabel.getInstance().hideTime();
	}

	/**
	 * pauses the timer and hides the playing field
	 */
	public void pauseGame() {
		GameTimer.getInstance().pauseTimer();

		PauseScreen pauseScreen = PauseScreen.getInstance();

		view.addPanel(pauseScreen, Panel.PAUSE);
		view.showPanel(Panel.PAUSE);

		MenuBar.getInstance().showPauseState();

	}

	/**
	 * shows the playing field and resumes the timer
	 */
	public void resumeGame() {
		view.showPanel(Panel.GAME);
		GameTimer.getInstance().resumeTimer();
		MenuBar.getInstance().showPlayingState();
	}

	/**
	 * restarts the game with the same parameters
	 */
	public void restartGame() {
		endGame();
		startGame(sideLength, mines);
	}

	/**
	 * shows the main menu
	 */
	public void backToMainMenu() {
		endGame();
		view.showPanel(Panel.MENU);
		view.resizeWindow();
		MenuBar menuBar = MenuBar.getInstance();
		menuBar.setStatusText("");
		menuBar.hideGameMenu();
	}

	public MsData getModel() {
		return model;
	}
}
