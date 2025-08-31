package minesweeper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CustomDifficulty extends JPanel {

	public CustomDifficulty() {

		// using BorderLayout to group content at the top
		this.setLayout(new BorderLayout());

		JPanel wrapper = new JPanel();
		BoxLayout settingsLayout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(settingsLayout);
		//this.setAlignmentX(CENTER_ALIGNMENT);
		// this.setLayout(null);	// this is actually needed, otherwise it uses a flow layout (or some other layout that we don't want)
		
		this.add(wrapper, BorderLayout.NORTH);


		
		
		
		JPanel lengthWrapper = new JPanel();
		lengthWrapper.setLayout(new FlowLayout());

		// TODO: implement dynamic limits
		// from 0 to 9, in 1.0 steps start value 5
		SpinnerNumberModel lengthModel = new SpinnerNumberModel(5.0, 0.0, 9.0, 1.0);
		JSpinner length = new JSpinner(lengthModel);
		lengthWrapper.add(length);


		
		JPanel minePercentageWrapper = new JPanel();
		minePercentageWrapper.setLayout(new FlowLayout());
		
		// from 0 to 9, in 1.0 steps start value 5
		SpinnerNumberModel PercentageModel = new SpinnerNumberModel(5.0, 0.0, 9.0, 1.0);
		JSpinner minePercentage = new JSpinner(PercentageModel);
		minePercentageWrapper.add(minePercentage);

		
		
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

		// copied from https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
		buttons.add(backButton);
		buttons.add(Box.createRigidArea(new Dimension(10, 0)));	// adds padding between buttons
		buttons.add(playButton);
		// end of copy
		
		playButton.addActionListener(e -> {
			MwGUI.showPanel("game");
		});

		backButton.addActionListener(e -> {
			MwGUI.showPanel("menu");
		});

		wrapper.add(lengthLabel);
		wrapper.add(lengthWrapper);
		//this.add(length);
		wrapper.add(minePercentageLabel);
		//this.add(minePercentage);
		wrapper.add(minePercentageWrapper);
		wrapper.add(buttons);
	}
}
