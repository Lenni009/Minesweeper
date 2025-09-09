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

import minesweeper.utils.Clamp;
import minesweeper.utils.Logger;

public class MsGUI extends JFrame {
	private static final int _DEFAULT_SCREENSIZE = 600;

	private static final int _MIN_WIDTH_BUTTON = 55;

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
	
	public void resizeScreen() {
		// standard width is 600 pixel
		// resizeScreen clamps value, so we can provide an value < _DEFAULT_SCREENSIZE
		resizeScreen(1);
	}
	
	public void resizeScreen(int FieldLen) {
		// one Button needs a width of at least 55 pixels
		int ScreenSize = _MIN_WIDTH_BUTTON * FieldLen;
		
		ScreenSize = (int) Clamp.clamp(ScreenSize, _DEFAULT_SCREENSIZE, Toolkit.getDefaultToolkit().getScreenSize().width);
		
		MsGUI.getInstance().setSize(ScreenSize, ScreenSize);
	}

}
