package minesweeper.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import minesweeper.controller.Minesweeper;

// handles the pause screen overlay
public class PauseScreen extends JPanel {

	private static PauseScreen instance;

	private PauseScreen() {

		// vertical center alignment copied from
		// https://stackoverflow.com/questions/897924/java-layout-manager-vertical-center
		this.setLayout(new GridBagLayout());
		// end of copy

		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

		JLabel pauseLabel = new JLabel("Pausiert");
		pauseLabel.setAlignmentX(CENTER_ALIGNMENT);

		JButton resumeButton = new JButton("Fortsetzen");
		resumeButton.setAlignmentX(CENTER_ALIGNMENT);
		resumeButton.addActionListener(e -> Minesweeper.getInstance().resumeGame());

		wrapper.add(pauseLabel);
		wrapper.add(Box.createRigidArea(new Dimension(0, 10))); // add vertical padding between elements
		wrapper.add(resumeButton);

		// part of vertical center alignment copy like above
		this.add(wrapper, new GridBagConstraints());
		// end of copy
	}

	// hello singleton
	public static PauseScreen getInstance() {
		if (instance == null) {
			instance = new PauseScreen();
		}
		return instance;
	}
}
