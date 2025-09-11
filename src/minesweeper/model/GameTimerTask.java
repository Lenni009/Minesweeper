package minesweeper.model;

import java.util.TimerTask;

import minesweeper.controller.GameTimer;
import minesweeper.view.GameTimerLabel;

// this would be much easier with a nested private class, but the clean code guidelines dictate that each class must be a separate file
public class GameTimerTask extends TimerTask {
	private int seconds = 0;
	private int minutes = 0;

	private GameTimerLabel label;
	private boolean isPaused = false;

	public GameTimerTask(GameTimerLabel label) {
		this.label = label;
	}

	@Override
	public void run() {
		if (isPaused) {
			return;
		}

		seconds++;
		if (seconds >= 60) {
			seconds = 0;
			// this will lead to funny problems if minutes get > 60
			// common case should be games < 60 minutes, it's not worth taking these edge
			// cases into account and add complexity just for them
			minutes++;
		}

		// not great, but it works
		// would be nicer if this was be in the view, not the data
		String timeString = GameTimer.getInstance().getLabelTime();
		label.setText(timeString);
	}

	public int getSeconds() {
		return seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
}
