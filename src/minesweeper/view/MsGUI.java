package minesweeper.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;

import minesweeper.enums.Panel;
import minesweeper.utils.Clamp;
import minesweeper.utils.Logger;

public class MsGUI extends JFrame {
	private static final int DEFAULT_SCREENSIZE = 600;
	private static final int MIN_WIDTH_BUTTON = 55;

	private static MsGUI instance;

	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	private Map<String, JPanel> panels = new HashMap<>();

	private Panel currentlyActivePanel;

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

	public void addPanel(JPanel panel, Panel identifier) {
		String idString = identifier.toString();
		if (panels.containsKey(idString)) {
			Logger.error("Panel Key " + idString + " already exists!");
			return;
		}

		cardPanel.add(panel, idString);
		panels.put(idString, panel);
	}

	public void showPanel(Panel identifier) {
		cardLayout.show(cardPanel, identifier.toString());
		currentlyActivePanel = identifier;
	}

	public void removePanel(Panel identifier) {
		String idString = identifier.toString();
		if (!panels.containsKey(idString)) {
			Logger.error("Panel Key " + idString + " doesn't exist!");
			return;
		}

		JPanel panelToRemove = panels.get(idString);
		cardPanel.remove(panelToRemove);
		panels.remove(idString);
	}

	public Panel getCurrentlyActivePanel() {
		return currentlyActivePanel;
	}

	public void resizeScreen() {
		// standard width is 600 pixel
		// resizeScreen clamps value, so we can provide a value < DEFAULT_SCREENSIZE
		resizeScreen(1);
	}

		public void resizeScreen(int fieldLen) {
		// one Button needs a width of at least 55 pixels
		int windowSize = MIN_WIDTH_BUTTON * fieldLen;
		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int minScreenDimension = Math.min(screenSize.width, screenSize.height);

		windowSize = (int) Clamp.clamp(windowSize, DEFAULT_SCREENSIZE, minScreenDimension);

		MsGUI.getInstance().setSize(windowSize, windowSize);

		updateLocation(windowSize, screenSize, minScreenDimension);

	}

	private void updateLocation(int windowSize, Rectangle screenSize, int minScreenDimension) {

		int locationWidth = (screenSize.width - windowSize) / 2;
		int locationHeight = (screenSize.height - windowSize) / 2;

		this.setLocation(locationWidth, locationHeight);
	}
}
