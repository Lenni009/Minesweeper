package minesweeper.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class CustomDifficulty extends JPanel {

	public CustomDifficulty() {
		BoxLayout settingsLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(settingsLayout);
		// this.setLayout(null);	// this is actually needed, otherwise it uses a flow layout (or some other layout that we don't want)
		
		
		
		JPanel lengthWrapper = new JPanel();
		lengthWrapper.setLayout(null);

		JSpinner length = new JSpinner();
		length.setBounds(0, 0, 50, 30);

		lengthWrapper.add(length);
		lengthWrapper.setAlignmentX(CENTER_ALIGNMENT);


		
		JPanel minePercentageWrapper = new JPanel();
		minePercentageWrapper.setLayout(null);

		JSpinner minePercentage = new JSpinner();
		minePercentage.setBounds(0, 0, 50, 30);

		minePercentageWrapper.add(minePercentage);
		minePercentageWrapper.setAlignmentX(CENTER_ALIGNMENT);

		
		
		JLabel lengthLabel = new JLabel("Seitenlänge");
		lengthLabel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel minePercentageLabel = new JLabel("Anteil Minen (%)");
		minePercentageLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		
		JButton playButton = new JButton("Spielen");
		playButton.setAlignmentX(CENTER_ALIGNMENT);
		
		JButton backButton = new JButton("Zurück");
		backButton.setAlignmentX(CENTER_ALIGNMENT);

		buttons.add(backButton);
		buttons.add(Box.createRigidArea(new Dimension(10, 0)));
		buttons.add(playButton);
		
		playButton.addActionListener(e -> {
			MwGUI.showPanel("game");
		});

		backButton.addActionListener(e -> {
			MwGUI.showPanel("menu");
		});

		this.add(lengthLabel);
		this.add(lengthWrapper);
		this.add(minePercentageLabel);
		this.add(minePercentageWrapper);
		this.add(buttons);
	}
}
