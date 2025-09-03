package minesweeper.controller;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

// based on https://www.javaspring.net/java_util/mastering_the_java_timer_scheduleatfixedrate_method/
public class GameTimer {
	private final JLabel label;
	private int seconds = 0;
	private int minutes = 0;

	private Timer timer = new Timer();
	private TimerTask task;

	public GameTimer(JLabel label) {
		this.label = label;
	}

	public void startTimer() {
		seconds = 0;
		minutes = 0;
		task = new GameTimerTask();
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	public void stopTimer() {
		if (task != null)
			task.cancel();
		timer.purge();
		label.setText("");
	}

	public String getTime() {
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

	private class GameTimerTask extends TimerTask {
		@Override
		public void run() {
			seconds++;
			if (seconds >= 60) {
				seconds = 0;
				// this will lead to funny problems, if minutes get > 60
				minutes++;
			}
			label.setText(getTime());
		}
	};

}
