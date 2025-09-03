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

	@Override
	public void setText(String text) {
		super.setText(text);
	}

}
