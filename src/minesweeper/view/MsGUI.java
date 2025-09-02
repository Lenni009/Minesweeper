package minesweeper.view;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import minesweeper.utils.Logger;

public class MsGUI extends JFrame {
	static MsGUI instance;

	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	private Map<String, JPanel> panels = new HashMap<>();
	private JLabel statusLabel;

	private MsGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// JFrame config
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");

		// TODO: extract menubar to make more dynamic
		// Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem exit = new JMenuItem("Beenden");
		JMenu onlineMenu = new JMenu("Online");
		JMenu helpMenu = new JMenu("Hilfe");
		JSeparator sep = new JSeparator();
		statusLabel = new JLabel("");

		exit.addActionListener(e -> System.exit(0));

		// TODO: [Optional] add Shortcut with function keys or Alt+KEY
		// help.setMnemonic(KeyEvent.VK_H);
		helpMenu.addActionListener(e -> showHelpDialog());

		// assign everything
		menuBar.add(fileMenu);
		menuBar.add(onlineMenu);
		menuBar.add(helpMenu);
		fileMenu.add(exit);
		menuBar.add(sep);
		menuBar.add(statusLabel);

		// add to window
		this.setJMenuBar(menuBar);

		this.add(cardPanel);

		this.setVisible(true);
	}

	private void showHelpDialog() {
		// The Text describing the ruleset was created by ChatGPT
		String infoText = "So wird gespielt:\nMinesweeper ist ein Logikspiel, bei dem der Spieler auf einem Raster Felder aufdeckt, ohne dabei Minen zu treffen.\nZiel ist es, alle sicheren Felder aufzudecken.\nBeim Anklicken eines Feldes zeigt eine Zahl an, wie viele Minen sich in den angrenzenden Feldern befinden.\nMit dieser Information kann der Spieler Rückschlüsse auf die Position der Minen ziehen.\nMinen können durch einen Richtsklick mit einer Flagge markiert werden.\nDas Spiel ist gewonnen, wenn alle nicht-minenhaltigen Felder aufgedeckt sind, und verloren, sobald eine Mine angeklickt wird.";
		JOptionPane.showMessageDialog(null, infoText, "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
	}

	// in case someone asks: this is the singleton pattern!
	public static MsGUI getInstance() {
		if (instance == null) {
			instance = new MsGUI();
		}
		return instance;
	}

	public void addPanel(JPanel panel, String identifier) {
		if (panels.containsKey(identifier)) {
			Logger.error("Panel Key already exists!");
		}

		cardPanel.add(panel, identifier);
		panels.put(identifier, panel);
	}

	public void showPanel(String identifier) {
		cardLayout.show(cardPanel, identifier);
	}

	public void removePanel(String identifier) {
		if (!panels.containsKey(identifier)) {
			Logger.error("Panel Key doesn't exist!");
		}

		JPanel panelToRemove = panels.get(identifier);
		if (panelToRemove == null) {
			return;
		}

		cardPanel.remove(panelToRemove);
		panels.remove(identifier);
	}

	public void setStatusText(String text) {
		statusLabel.setText(text);
	}
}
