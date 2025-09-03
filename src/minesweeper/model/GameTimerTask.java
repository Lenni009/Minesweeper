package minesweeper.model;

import java.util.TimerTask;

public class GameTimerTask extends TimerTask {
	private int seconds = 0;
	private int minutes = 0;

	@Override
	public void run() {
		seconds++;
		if (seconds >= 60) {
			seconds = 0;
			// this will lead to funny problems, if minutes get > 60
			minutes++;
		}
		System.out.println(seconds);
	}

	public int getSeconds() {
		return seconds;
	}

	public int getMinutes() {
		return minutes;
	}

}
