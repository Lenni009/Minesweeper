package minesweeper.controller;

import java.util.Timer;
import java.util.TimerTask;

import minesweeper.model.GameTimerTask;
import minesweeper.view.GameTimerLabel;

// based on https://www.javaspring.net/java_util/mastering_the_java_timer_scheduleatfixedrate_method/
public class GameTimer {
	private static GameTimer instance;

	private Timer timer = new Timer();
	private GameTimerTask task;

	private GameTimer() {
	}

	// singleton hehe
	public static GameTimer getInstance() {
		if (instance == null) {
			instance = new GameTimer();
		}
		return instance;
	}

	public void startTimer(GameTimerLabel label) {
		stopTimer();
		task = new GameTimerTask(label);
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	public void stopTimer() {
		if (task != null) {
			task.cancel();
		}
		timer.purge();
	}

	public String getTime() {
		int minutes = task.getMinutes();
		int seconds = task.getSeconds();

		String minutesString = Integer.toString(minutes);
		String secondsString = Integer.toString(seconds);

		if (minutes <= 9) {
			minutesString = "0" + minutes;
		}
		if (seconds <= 9) {
			secondsString = "0" + seconds;
		}

		return "Zeit: " + minutesString + ":" + secondsString;
	}
}
