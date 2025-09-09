package minesweeper.view;

import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import minesweeper.Minesweeper;
import minesweeper.controller.GameTimer;

public class MenuBar extends JMenuBar {

	private static MenuBar instance;
	private JLabel statusLabel;
	private GameTimerLabel gameTimerLabel;
	private JMenu gameMenu;

	private MenuBar() {
		// Menu
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem exit = new JMenuItem("Beenden", KeyEvent.VK_B);
		gameMenu = new JMenu("Spiel");
		JMenuItem restart = new JMenuItem("Neustart", KeyEvent.VK_N);
		JMenuItem mainMenu = new JMenuItem("Hauptmenü", KeyEvent.VK_H);
		// JMenu onlineMenu = new JMenu("Online");
		JMenu helpMenu = new JMenu("Hilfe");
		JMenuItem rules = new JMenuItem("Regeln", KeyEvent.VK_R);
		JSeparator sep = new JSeparator();
		statusLabel = new JLabel("");
		gameTimerLabel = GameTimerLabel.getInstance();
		GameTimer.getInstance();

		exit.addActionListener(e -> System.exit(0));

		rules.addActionListener(e -> showHelpDialog());

		mainMenu.addActionListener(e -> Minesweeper.getInstance().backToMainMenu());
		restart.addActionListener(e -> Minesweeper.getInstance().restartGame());

		// assign everything
		this.add(fileMenu);
		this.add(gameMenu);
		// this.add(onlineMenu);
		this.add(helpMenu);
		fileMenu.add(exit);
		gameMenu.add(restart);
		gameMenu.add(mainMenu);
		helpMenu.add(rules);
		this.add(sep);
		this.add(statusLabel);
		this.add(Box.createHorizontalStrut(10));
		this.add(gameTimerLabel);

		hideGameMenu(); // initially hide game menu
	}

	// singleton <grin>
	public static MenuBar getInstance() {
		if (instance == null) {
			instance = new MenuBar();
		}
		return instance;
	}

	public void setStatusText(String text) {
		statusLabel.setText(text);
	}

	private void showHelpDialog() {
		// The text describing the ruleset was created by ChatGPT
		String infoText = "So wird gespielt:\nMinesweeper ist ein Logikspiel, bei dem der Spieler auf einem Raster Felder aufdeckt, ohne dabei Minen zu treffen.\nZiel ist es, alle sicheren Felder aufzudecken.\nBeim Anklicken eines Feldes zeigt eine Zahl an, wie viele Minen sich in den angrenzenden Feldern befinden.\nMit dieser Information kann der Spieler Rückschlüsse auf die Position der Minen ziehen.\nMinen können durch einen Richtsklick mit einer Flagge markiert werden.\nDas Spiel ist gewonnen, wenn alle nicht-minenhaltigen Felder aufgedeckt sind, und verloren, sobald eine Mine angeklickt wird.";
		JOptionPane.showMessageDialog(null, infoText, "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showGameMenu() {
		gameMenu.setVisible(true);
	}

	public void hideGameMenu() {
		gameMenu.setVisible(false);
	}

}
