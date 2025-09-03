package minesweeper.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import minesweeper.Minesweeper;
import minesweeper.controller.GameTimer;

public class EndScreen extends JDialog {

	public EndScreen() {
		Minesweeper minesweeper = Minesweeper.getInstance();
		
		// set up dialog frame
		this.setModalityType(ModalityType.APPLICATION_MODAL); // make game window un-interactable
		this.setTitle("Gewonnen|Verloren (Spiel zuende?)"); // TODO: proper title
		this.setSize(100, 100);
		MsGUI gui = MsGUI.getInstance();
		Dimension size = gui.getSize();
		Point location = gui.getLocation();
		int xAxis = size.width/2 + location.x - this.getSize().width/2;
		int yAxis = size.height/2 + location.y - this.getSize().height/2;
		
		this.setLocation(xAxis, yAxis);
		//this.setLocation();
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

		mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		restart.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainMenu.addActionListener(e -> {
			minesweeper.backToMainMenu();
			this.setVisible(false);
		});
		restart.addActionListener(e -> {
			minesweeper.restartGame();
			this.setVisible(false);
		});

		String elapsedTime = GameTimer.getInstance().getTime();
		JLabel timeDisplay = new JLabel(elapsedTime);
		timeDisplay.setAlignmentX(CENTER_ALIGNMENT);

		// TODO: Bestenliste

		wrapper.add(timeDisplay);
		wrapper.add(restart);
		wrapper.add(mainMenu);

		this.add(wrapper);

		this.setVisible(true);
		
		minesweeper.endGame();
	}
}
