package minesweeper.controller;

import java.util.Timer;

import minesweeper.model.GameTimerTask;
import minesweeper.view.GameTimerLabel;

// based on https://www.javaspring.net/java_util/mastering_the_java_timer_scheduleatfixedrate_method/
// timer to count the time a game takes
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

	/**
	 * stops all existing timers and starts a new timer
	 * 
	 * @param label label to display the timer in
	 */
	public void startTimer(GameTimerLabel label) {
		stopTimer();
		task = new GameTimerTask(label);
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	public void pauseTimer() {
		if (task != null) {
			task.setPaused(true);
		}
	}

	public void resumeTimer() {
		if (task != null) {
			task.setPaused(false);
		}
	}

	public void stopTimer() {
		if (task != null) {
			task.cancel(); // stop timer
		}

		// remove task from timer's task queue so the task can be garbage collected
		timer.purge();
	}

	/**
	 * gives a string showing the timer's current time
	 * 
	 * @return mm:ss time string
	 */
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

		return minutesString + ":" + secondsString;
	}

	/**
	 * gives the time string as it appears in the label
	 * 
	 * @return time string prefixed with "Zeit: " for display in the label
	 */
	public String getLabelTime() {
		String timeString = getTime();
		return "Zeit: " + timeString;
	}
}
