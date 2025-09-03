package minesweeper.view;

import javax.swing.JLabel;

@SuppressWarnings("serial")
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
	
	public void hideTime() {
		this.setVisible(false);
	}

	public void showTime() {
		this.setVisible(true);
	}
}
