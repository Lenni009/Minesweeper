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

public class CustomDifficulty extends JPanel {

	private static CustomDifficulty instance;
	
	private CustomDifficulty() {

		JPanel wrapper = new JPanel();
		BoxLayout settingsLayout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(settingsLayout);

		// TODO: do proper limit calculations
		JPanel lengthInput = createNumberInput("Seitenlänge", new SpinnerNumberModel(5.0, 0.0, 9.0, 1.0));
		JPanel minePercentageInput = createNumberInput("Anteil Minen (%)", new SpinnerNumberModel(5.0, 0.0, 9.0, 1.0));
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		
		JButton playButton = new JButton("Spielen");
		JButton backButton = new JButton("Zurück");

		// copied from https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
		buttons.add(backButton);
		buttons.add(Box.createRigidArea(new Dimension(10, 0)));	// add padding between buttons
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
		wrapper.add(Box.createRigidArea(new Dimension(0, 10)));	// add vertical padding between elements
		wrapper.add(buttons);

		// using BorderLayout to group content at the top (GPT's idea)
		this.setLayout(new BorderLayout());
		this.add(wrapper, BorderLayout.NORTH);
	}

	/**
	 * generates a number input with label, wrapped in a JPanel
	 * @param label label to display next to the input
	 * @param numberModel model to use for the input (initialValue, min, max, stepSize)
	 */
	private JPanel createNumberInput(String label, SpinnerNumberModel numberModel) {
		JPanel inputWrapper = new JPanel();
		inputWrapper.setLayout(new FlowLayout());

		JLabel inputLabel = new JLabel(label);

		JSpinner numberInput = new JSpinner(numberModel);
		
		numberInput.addChangeListener(e -> {
			
		});
		
		inputWrapper.add(inputLabel);
		inputWrapper.add(numberInput);
		
		return inputWrapper;
	}
	
	public static CustomDifficulty getInstance() {
		if (instance == null) {
			instance = new CustomDifficulty();
		}
		return instance;
	}

}
