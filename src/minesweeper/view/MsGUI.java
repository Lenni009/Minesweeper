package minesweeper.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;

import minesweeper.utils.Logger;

@SuppressWarnings("serial")
public class MsGUI extends JFrame {
	private static MsGUI instance;

	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	private Map<String, JPanel> panels = new HashMap<>();

	private MsGUI() {
		// set text colour of disabled buttons to black for better contrast
		UIManager.put("Button.disabledText", Color.BLACK);

		// JFrame config
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JMenuBar menuBar = MenuBar.getInstance();

		// add to window
		this.setJMenuBar(menuBar);

		this.add(cardPanel);

		this.setVisible(true);
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

}
