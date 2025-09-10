package minesweeper.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
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
	private static final int MIN_WIDTH_BUTTON = 60;
	private static final Rectangle SCREEN_SIZE = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getMaximumWindowBounds();

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
		initLocation();
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

	/**
	 * Default case (no arg is provided): resize screen to default values
	 */
	public void resizeScreen() {
		// resizeScreen clamps value, so we can provide a value < DEFAULT_SCREENSIZE
		resizeScreen(1);
	}

	/**
	 * Overloaded to accept argument, so the rezise is based on the needed width an height
	 * @param fieldLen
	 */
	public void resizeScreen(int fieldLen) {
		Dimension currentWindowSize = this.getSize();

		Dimension windowSize = getWindowSize(fieldLen);

		MsGUI.getInstance().setSize(windowSize.width, windowSize.height);

		updateLocation(windowSize, currentWindowSize);

	}

	/**
	 * Calculates the size of the new window, based on the number of buttons
	 * and the minimum width each buttons needs to display its text.
	 * Clamps the result, so it fits right on the screen.
	 * 
	 * @param fieldLen number of buttons in a row / col
	 * @return clamped windowsize
	 */
	private Dimension getWindowSize(int fieldLen) {
		int minWindowSize = MIN_WIDTH_BUTTON * fieldLen;

		int maxWindowWidth = SCREEN_SIZE.width;
		int maxWindowHeight = SCREEN_SIZE.height;

		int windowWidth = (int) Clamp.clamp(minWindowSize, DEFAULT_SCREENSIZE, maxWindowWidth);
		int windowHeight = (int) Clamp.clamp(minWindowSize, DEFAULT_SCREENSIZE, maxWindowHeight);

		Dimension windowSize = new Dimension(windowWidth, windowHeight);

		return windowSize;
	}

	/**
	 * Relocate window on the screen while trying to keep window as close as
	 * possible to the old position using the center of the old and new windows
	 * 
	 * @param newWindowSize size of the new window
	 * @param oldWindowSize size of the old window
	 */
	private void updateLocation(Dimension newWindowSize, Dimension oldWindowSize) {
		Point currentLocation = this.getLocation();

		int centerX = currentLocation.x + oldWindowSize.width / 2;
		int centerY = currentLocation.y + oldWindowSize.height / 2;

		int newLocationX = centerX - newWindowSize.width / 2;
		int newLocationY = centerY - newWindowSize.height / 2;

		// Bounds for new window on screen:
		// 0 (upper left corner) < newLocation < (screensize - windowsize)
		newLocationX = (int) Clamp.clamp(newLocationX, 0, SCREEN_SIZE.width - newWindowSize.width);
		newLocationY = (int) Clamp.clamp(newLocationY, 0, SCREEN_SIZE.height - newWindowSize.height);

		Logger.info("Set Location at [" + newLocationX + "/" + newLocationY + "]");

		this.setLocation(newLocationX, newLocationY);
	}

	/**
	 * This method should only be called once - at the start of loading minesweeper.
	 * This method will center the window an the screen.
	 */
	private void initLocation() {
		int locationWidth = (SCREEN_SIZE.width - DEFAULT_SCREENSIZE) / 2;
		int locationHeight = (SCREEN_SIZE.height - DEFAULT_SCREENSIZE) / 2;
		this.setLocation(locationWidth, locationHeight);
	}
}
