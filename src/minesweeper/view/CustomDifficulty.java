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

	JPanel wrapper = new JPanel();
	
	public CustomDifficulty() {

		BoxLayout settingsLayout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(settingsLayout);
		
		// using BorderLayout to group content at the top (GPT's idea)
		this.setLayout(new BorderLayout());
		this.add(wrapper, BorderLayout.NORTH);


		
		
		
		addNumberInput("Seitenlänge");


		
		JPanel minePercentageWrapper = new JPanel();
		minePercentageWrapper.setLayout(new FlowLayout());
		
		JLabel minePercentageLabel = new JLabel("Anteil Minen (%)");

		// from 0 to 9, in 1.0 steps start value 5
		SpinnerNumberModel PercentageModel = new SpinnerNumberModel(5.0, 0.0, 9.0, 1.0);
		JSpinner minePercentage = new JSpinner(PercentageModel);

		minePercentageWrapper.add(minePercentageLabel);
		minePercentageWrapper.add(minePercentage);
		
		
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		
		JButton playButton = new JButton("Spielen");
		JButton backButton = new JButton("Zurück");

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

		wrapper.add(lengthWrapper);
		wrapper.add(minePercentageWrapper);

		wrapper.add(Box.createRigidArea(new Dimension(0, 10)));	// adds vertical padding between elements
		wrapper.add(buttons);
	}

	/**
	 * adds a number input with label to the panel
	 * @param label label to display next to the input
	 * @param numberModel model to use for the input (initialValue, min, max, stepSize)
	 */
	private void addNumberInput(String label, SpinnerNumberModel numberModel) {
		JPanel inputWrapper = new JPanel();
		inputWrapper.setLayout(new FlowLayout());

		JLabel inputLabel = new JLabel(label);

		JSpinner numberInput = new JSpinner(numberModel);

		inputWrapper.add(inputLabel);
		inputWrapper.add(numberInput);
		
		wrapper.add(numberInput);
	}
}
