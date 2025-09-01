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
import minesweeper.controller.BombCalculator;
import minesweeper.utils.Logger;

public class CustomDifficulty extends JPanel {

	private static CustomDifficulty instance;
	private JSpinner lengthSpinner;
	private JSpinner minePercentageSpinner;

	private SpinnerNumberModel lengthModel;
	private SpinnerNumberModel minePercentageModel;

	private CustomDifficulty() {

		JPanel wrapper = new JPanel();
		BoxLayout settingsLayout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(settingsLayout);

		// min size: 4*4
		// max size: 20*20
		lengthModel = new SpinnerNumberModel(10, 4, 20, 1);
		lengthSpinner = new JSpinner(lengthModel);

		lengthSpinner.addChangeListener(evt -> {
			// copied from https://www.java-forum.org/thema/jspinner-getvalue.3079/
			int sideLength = ((Number) lengthSpinner.getValue()).intValue();
			// end of copy

			int minBombPercentage = BombCalculator.getMinBombPercentage(sideLength);
			int maxBombPercentage = BombCalculator.getMaxBombPercentage(sideLength);

			minePercentageModel.setMinimum(minBombPercentage);
			minePercentageModel.setMaximum(maxBombPercentage);
			int value = (maxBombPercentage - minBombPercentage) / 2;
			minePercentageModel.setValue(value);

			
			//minePercentageSpinner.setModel(minePercentageModel);
		});

		minePercentageModel = new SpinnerNumberModel(50, 0, 100, 1);
		minePercentageSpinner = new JSpinner(minePercentageModel);

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

		// TODO: dynamically calculate bomb count
		playButton.addActionListener(e -> {
			Minesweeper.getInstance().startGame(20, 100);
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
