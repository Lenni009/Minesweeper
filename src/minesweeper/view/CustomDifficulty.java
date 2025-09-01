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

import minesweeper.Minesweeper;
import minesweeper.controller.MineCalculator;

public class CustomDifficulty extends JPanel {

	private static CustomDifficulty instance;
	private int sideLength = 10; // setting initial value
	private int minePercentage;

	private SpinnerNumberModel lengthModel;
	private SpinnerNumberModel minePercentageModel;

	private CustomDifficulty() {

		JPanel wrapper = new JPanel();
		BoxLayout settingsLayout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(settingsLayout);

		// min size: 4*4
		// max size: 20*20
		lengthModel = new SpinnerNumberModel(sideLength, 4, 20, 1);
		JSpinner lengthSpinner = new JSpinner(lengthModel);

		lengthSpinner.addChangeListener(e -> {
			// copied from https://www.java-forum.org/thema/jspinner-getvalue.3079/
			sideLength = ((Number) lengthSpinner.getValue()).intValue();
			// end of copy

			// extract this into the controller
			int minMinePercentage = MineCalculator.getMinMinePercentage(sideLength);
			int maxMinePercentage = MineCalculator.getMaxMinePercentage(sideLength);

			minePercentageModel.setMinimum(minMinePercentage);
			minePercentageModel.setMaximum(maxMinePercentage);

			if (minePercentage < minMinePercentage) {
				// if smaller than minimum, set to minimum
				minePercentageModel.setValue(minMinePercentage);
			} else if (minePercentage > maxMinePercentage) {
				// if larger than maximum, set maximum
				minePercentageModel.setValue(maxMinePercentage);
			} else {
				// if in allowed range, set to value that corresponds to previous exact mine
				// count
				int newMinePercentage = MineCalculator.getMinePercentageForSpecificMineCount(sideLength,
						minePercentage);
				minePercentageModel.setValue(newMinePercentage);
			}
		});

		int minInitialMinePercentage = MineCalculator.getMinMinePercentage(sideLength);
		int maxInitialMinePercentage = MineCalculator.getMaxMinePercentage(sideLength);
		minePercentage = (maxInitialMinePercentage - minInitialMinePercentage) / 2;
		minePercentageModel = new SpinnerNumberModel(minePercentage, minInitialMinePercentage, maxInitialMinePercentage,
				1);
		JSpinner minePercentageSpinner = new JSpinner(minePercentageModel);

		minePercentageSpinner.addChangeListener(e -> {
			// copied like above in changeListener
			minePercentage = ((Number) minePercentageSpinner.getValue()).intValue();
		});

		JPanel lengthInput = createNumberInput("Seitenlänge", lengthSpinner);
		JPanel minePercentageInput = createNumberInput("Anteil Minen (%)", minePercentageSpinner);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		JButton playButton = new JButton("Spielen");
		JButton backButton = new JButton("Zurück");

		// copied from https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
		buttons.add(backButton);
		buttons.add(Box.createRigidArea(new Dimension(10, 0))); // add padding between buttons
		buttons.add(playButton);
		// end of copy

		playButton.addActionListener(e -> {
			int mineCount = MineCalculator.getMineCount(sideLength, minePercentage);

			Minesweeper.getInstance().startGame(sideLength, mineCount);
		});
		backButton.addActionListener(e -> {
			MsGUI view = MsGUI.getInstance();
			view.showPanel("menu");
		});

		wrapper.add(lengthInput);
		wrapper.add(minePercentageInput);
		wrapper.add(Box.createRigidArea(new Dimension(0, 10))); // add vertical padding between elements
		wrapper.add(buttons);

		// using BorderLayout to group content at the top (GPT's idea)
		this.setLayout(new BorderLayout());
		this.add(wrapper, BorderLayout.NORTH);
	}

	/**
	 * generates a number input with label, wrapped in a JPanel
	 * 
	 * @param label   label to display next to the input
	 * @param spinner spinner with model, actionlisteners etc. already attached
	 */
	private JPanel createNumberInput(String label, JSpinner spinner) {
		JPanel inputWrapper = new JPanel();
		inputWrapper.setLayout(new FlowLayout());

		JLabel inputLabel = new JLabel(label);

		inputWrapper.add(inputLabel);
		inputWrapper.add(spinner);

		return inputWrapper;
	}

	public static CustomDifficulty getInstance() {
		if (instance == null) {
			instance = new CustomDifficulty();
		}
		return instance;
	}

}
