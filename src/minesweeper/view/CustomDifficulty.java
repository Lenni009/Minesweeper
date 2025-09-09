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
import minesweeper.enums.Panel;
import minesweeper.model.CustomDifficultyModel;

public class CustomDifficulty extends JPanel {

	private static CustomDifficulty instance;

	private SpinnerNumberModel minePercentageModel;
	private JLabel mineCountDisplay = new JLabel();

	private CustomDifficultyModel model = CustomDifficultyModel.getInstance();

	private CustomDifficulty() {

		JPanel wrapper = new JPanel();
		BoxLayout settingsLayout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(settingsLayout);

		// min size: 4*4
		// max size: 20*20
		SpinnerNumberModel lengthModel = new SpinnerNumberModel(model.getSideLength(), 4, 20, 1);
		JSpinner lengthSpinner = new JSpinner(lengthModel);

		lengthSpinner.addChangeListener(e -> {
			// copied from https://www.java-forum.org/thema/jspinner-getvalue.3079/
			int newSideLength = ((Number) lengthSpinner.getValue()).intValue();
			// end of copy
			model.setSideLength(newSideLength);

			// extract this into the controller
			int minMinePercentage = MineCalculator.getMinMinePercentage(newSideLength);
			int maxMinePercentage = MineCalculator.getMaxMinePercentage(newSideLength);

			minePercentageModel.setMinimum(minMinePercentage);
			minePercentageModel.setMaximum(maxMinePercentage);

			int minePercentage = model.getMinePercentage();

			// function pauses here to execute the actionListener on minePercentageSpinner
			// first (or runs on second thread, in any case, we cannot be sure that the
			// following code runs before that other listener)

			// handle potential out of bounds scenarios
			// do nothing if value is within allowed range, because race condition makes it
			// hard to get proper spinner values (see comment above)
			if (minePercentage < minMinePercentage) {
				// if smaller than minimum, set to minimum
				minePercentageModel.setValue(minMinePercentage);
			} else if (minePercentage > maxMinePercentage) {
				// if larger than maximum, set to maximum
				minePercentageModel.setValue(maxMinePercentage);
			}
		});

		int sideLength = model.getSideLength();

		int minInitialMinePercentage = MineCalculator.getMinMinePercentage(sideLength);
		int maxInitialMinePercentage = MineCalculator.getMaxMinePercentage(sideLength);
		model.setMinePercentage((maxInitialMinePercentage - minInitialMinePercentage) / 2);
		int minePercentage = model.getMinePercentage();
		model.setMineCount(MineCalculator.getMineCount(minePercentage, sideLength));
		int mineCount = model.getMineCount();
		setMineCountLabel(mineCount);
		minePercentageModel = new SpinnerNumberModel(minePercentage, minInitialMinePercentage, maxInitialMinePercentage,
				1);
		JSpinner minePercentageSpinner = new JSpinner(minePercentageModel);

		minePercentageSpinner.addChangeListener(e -> {
			// copied like above in changeListener
			model.setMinePercentage(((Number) minePercentageSpinner.getValue()).intValue());
			model.setMineCount(MineCalculator.getMineCount(minePercentage, sideLength));
			setMineCountLabel(mineCount);
		});

		JPanel lengthInput = createNumberInput("Seitenlänge", lengthSpinner);
		JPanel minePercentageInput = createNumberInput("Anteil Minen (%)", minePercentageSpinner);

		minePercentageInput.add(mineCountDisplay);

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
			Minesweeper.getInstance().startGame(sideLength, mineCount);
		});
		backButton.addActionListener(e -> {
			MsGUI view = MsGUI.getInstance();
			view.showPanel(Panel.MENU);
			view.resizeScreen();
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

	// singleton :3
	public static CustomDifficulty getInstance() {
		if (instance == null) {
			instance = new CustomDifficulty();
		}
		return instance;
	}

	private void setMineCountLabel(int mineCount) {
		mineCountDisplay.setText("(" + mineCount + " Minen)");
	}
}
