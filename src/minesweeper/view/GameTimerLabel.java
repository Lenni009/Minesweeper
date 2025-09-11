package minesweeper.view;

import javax.swing.JLabel;

// label for displaying the timer
public class GameTimerLabel extends JLabel {

	private static GameTimerLabel instance;

	private GameTimerLabel() {
	}

	// all other singletons have comments explaining they are singletons, so this
	// one needs one as well I guess
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
