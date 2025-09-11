package minesweeper.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import minesweeper.controller.Minesweeper;
import minesweeper.enums.Panel;

public class MainMenu extends JPanel {

	private static MainMenu instance;
	private CustomDifficulty customDifficultyView;

	private MainMenu() {

		GridLayout menuGrid = new GridLayout(2, 2); // 2x2 layout
		this.setLayout(menuGrid);

		JButton easy = generatePlayButton(8, 10);
		JButton medium = generatePlayButton(13, 25);
		JButton hard = generatePlayButton(20, 40);

		JButton custom = new JButton("Benutzerdefiniert");
		custom.addActionListener(e -> {
			MsGUI view = MsGUI.getInstance();
			view.removePanel(Panel.CUSTOM); // remove custom panel if it already exists
			customDifficultyView = CustomDifficulty.getInstance();
			view.addPanel(customDifficultyView, Panel.CUSTOM);
			view.showPanel(Panel.CUSTOM);
		});

		this.add(easy);
		this.add(medium);
		this.add(hard);
		this.add(custom);
	}

	/**
	 * generates a button that starts a new minesweeper game with the specified
	 * settings
	 * 
	 * @param sideLength length of one side of the playing field
	 * @param mines      total amount of mines to be put on the playing field
	 * @return button to start game
	 */
	private JButton generatePlayButton(int sideLength, int mines) {
		JButton playButton = new JButton(sideLength + " x " + sideLength + " (" + mines + " Minen)");
		playButton.addActionListener(e -> Minesweeper.getInstance().startGame(sideLength, mines));
		return playButton;
	}

	// another singleton :3
	public static MainMenu getInstance() {
		if (instance == null) {
			instance = new MainMenu();
		}
		return instance;
	}
}
