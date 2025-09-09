package minesweeper.view;

import javax.swing.JLabel;

public class GameTimerLabel extends JLabel {

	private static GameTimerLabel instance;

	private GameTimerLabel() {
	}

	public static GameTimerLabel getInstance() {
		if (instance == null) {
			instance = new GameTimerLabel();
		}
		return instance;
	}

	public void hideTime() {
		this.setVisible(false);
	}

	public void showTime() {
		this.setVisible(true);
	}
}
