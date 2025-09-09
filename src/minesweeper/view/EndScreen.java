package minesweeper.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import minesweeper.Minesweeper;
import minesweeper.controller.GameTimer;

public class EndScreen extends JDialog {

	public EndScreen(boolean isWon) {
		Minesweeper minesweeper = Minesweeper.getInstance();

		// set up dialog frame
		this.setModalityType(ModalityType.APPLICATION_MODAL); // make game window un-interactable
		this.setTitle("Spiel zuende");
		this.setSize(300, 300);

		MsGUI gui = MsGUI.getInstance();

		// position dialog in the middle of game window
		Dimension size = gui.getSize();
		Point location = gui.getLocation();
		int xAxis = size.width / 2 + location.x - this.getSize().width / 2;
		int yAxis = size.height / 2 + location.y - this.getSize().height / 2;

		this.setLocation(xAxis, yAxis);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// copied from
		// https://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
		WindowAdapter exitListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				minesweeper.backToMainMenu();
			}
		};
		this.addWindowListener(exitListener);
		// end of copy

		JPanel wrapper = new JPanel();
		BoxLayout layout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(layout);

		JButton mainMenu = new JButton("Hauptmenü");
		JButton restart = new JButton("Nochmal");

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		// copied from https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
		buttons.add(mainMenu);
		buttons.add(Box.createRigidArea(new Dimension(10, 0))); // add padding between buttons
		buttons.add(restart);
		// end of copy

		mainMenu.addActionListener(e -> {
			minesweeper.backToMainMenu();
			this.setVisible(false);
		});
		restart.addActionListener(e -> {
			minesweeper.restartGame();
			this.setVisible(false);
		});

		String elapsedTime = GameTimer.getInstance().getFormattedTime();
		JLabel timeDisplay = new JLabel(elapsedTime);
		timeDisplay.setAlignmentX(CENTER_ALIGNMENT);

		String winningStatusText = isWon ? "Gewonnen!" : "Verloren.";
		JLabel winningStatusDisplay = new JLabel(winningStatusText);
		winningStatusDisplay.setAlignmentX(CENTER_ALIGNMENT);
		Color winningStatusColor = isWon ? new Color(0x007F00) : new Color(0xFF0000); // isWon ? green : red
		winningStatusDisplay.setForeground(winningStatusColor);

		wrapper.add(winningStatusDisplay);
		wrapper.add(timeDisplay);

		wrapper.add(buttons);

		if (isWon) {
			LeaderBoard leaderBoard = new LeaderBoard();
			wrapper.add(leaderBoard);
		}

		this.add(wrapper);

		this.setVisible(true);

		minesweeper.endGame();
	}
}
