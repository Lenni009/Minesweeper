package minesweeper.view;

import java.awt.GridLayout;
import javax.swing.*;

import minesweeper.controller.GameTimer;
import minesweeper.controller.AutoRevealer;
import minesweeper.controller.MineDistributor;
import minesweeper.enums.FieldColor;
import minesweeper.enums.FieldLabel;
import minesweeper.enums.Flag;
import minesweeper.controller.Field;
import minesweeper.model.MsData;
import minesweeper.utils.Logger;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class MineField extends JPanel {

	private MsData data;

	private boolean isFirstClick = true;

	private JButton[][] buttons;

	private AutoRevealer autoRevealer;

	public MineField(MsData model) {
		this.data = model;
		autoRevealer = new AutoRevealer(data);

		int fieldLen = data.getFieldLen();

		this.setLayout(new GridLayout(fieldLen, fieldLen));

		data.generateMinefield(fieldLen);

		buttons = new JButton[fieldLen][fieldLen];

		for (int posX = 0; posX < fieldLen; posX++) {
			for (int posY = 0; posY < fieldLen; posY++) {
				JButton button = new JButton("");
				button.addMouseListener(createMouseListener(button, posX, posY));
				add(button);
				buttons[posX][posY] = button;
			}
		}

		MenuBar.getInstance()
				.setStatusText("Platzierte Flaggen: " + data.getPlacedFlags() + " / " + data.getAmountOfMines());
	}

	/**
	 * Adds MouseListener to JButton-Object
	 * 
	 * @param button the JButton-Object
	 * @param posX   X-Coord of corresponding Field-Object
	 * @param posY   Y-Coord of corresponding Field-Object
	 * @return
	 */
	private MouseListener createMouseListener(JButton button, int posX, int posY) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!button.isEnabled()) {
					return;
				}

				Field field = data.getField(posX, posY);

				if (SwingUtilities.isLeftMouseButton(e)) {
					leftclickEvent(button, field);
					
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					rightclickEvent(button, field);
				}
			}
		};
	}

	/**
	 * processes rightclick
	 * 
	 * @param button the clicked JButton
	 * @param field  the corresponding Field
	 */
	private void rightclickEvent(JButton button, Field field) {
		Flag flag = Flag.NOFLAG;
		FieldLabel label = FieldLabel.UNLABELLED;

		int amountOfMines = data.getAmountOfMines();

		if (field.getFlagged() == Flag.NOFLAG) { // there's no flag set
			if (data.getPlacedFlags() < amountOfMines) {
				// calling the getter inside the setter to maintain a single source of truth
				data.setPlacedFlags(data.getPlacedFlags() + 1);
				label = FieldLabel.FLAG;
				flag = Flag.FLAGGED;
			} else { // no flags left, put "?" instead
				label = FieldLabel.MAYBEFLAG;
				flag = Flag.QUESTIONMARK;
			}
		} else if (field.getFlagged() == Flag.FLAGGED) { // flag was already set
			label = FieldLabel.MAYBEFLAG;
			flag = Flag.QUESTIONMARK;
			data.setPlacedFlags(data.getPlacedFlags() - 1);
		}

		field.setText(label.toString());
		field.setFlagged(flag);

		MenuBar.getInstance().setStatusText("Platzierte Flaggen: " + data.getPlacedFlags() + " / " + amountOfMines);
		button.setText(field.getText());

		Logger.info("placed flags: " + data.getPlacedFlags());
	}

	/**
	 * processes leftclick
	 * 
	 * @param button the clicked JButton
	 * @param field  the corresponding Field
	 */
	private void leftclickEvent(JButton button, Field field) {

		// prevent leftclick: FLAGGED fields will not be revealed
		if (field.getFlagged() == Flag.FLAGGED) {
			Logger.info("FLAGGED field will not be revealed!");
			return;
		}

		// distribute mines after initial click
		if (isFirstClick) {
			isFirstClick = false;
			MineDistributor mineDistributor = new MineDistributor(data, field);
			mineDistributor.distributeMines();
			GameTimerLabel.getInstance().showTime();
			GameTimer.getInstance().startTimer(GameTimerLabel.getInstance());
		}

		// if mine is revealed the game ends
		if (field.isMine()) {
			Logger.info("Mine exploded - game over!");

			GameTimer.getInstance().stopTimer();
			revealMap(field);

			new EndScreen(false);
		}

		// reveal field
		else {
			revealField(field);
		}
	}

	private void revealField(Field field) {
		Set<Field> revealedFields = autoRevealer.revealField(field);

		if (!autoRevealer.revealChecker.isGameOver()) {
			updateButtonsGUI(revealedFields);
			MenuBar.getInstance()
					.setStatusText("Platzierte Flaggen: " + data.getPlacedFlags() + " / " + data.getAmountOfMines());
		} else {

			// Flag all mines, if game is won
			revealedFields.addAll(autoRevealer.revealChecker.revealMap());
			updateButtonsGUI(revealedFields);

			GameTimer.getInstance().stopTimer();

			new EndScreen(true);
		}
	}

	private void revealMap(Field field) {
		Set<Field> changedFields = new HashSet<Field>();

		for (Field[] fieldX : data.getMinefield()) {
			for (Field fieldY : fieldX) {
				revealMines(changedFields, fieldY);
			}
		}

		field.setText(FieldLabel.EXPLOSION.toString());
		field.setBackgroundColor(FieldColor.LIGHTRED);

		changedFields.add(field);

		updateButtonsGUI(changedFields);
	}

	private void revealMines(Set<Field> changedFields, Field fieldY) {
		// it was no mine, but was flagged
		if (!fieldY.isMine() && fieldY.getFlagged() == Flag.FLAGGED) {
			fieldY.setBackgroundColor(FieldColor.RED);
			changedFields.add(fieldY);
		}
		// it was a mine and got flagged with "?" -> counts, field doesn't change
		else if (fieldY.isMine() && fieldY.getFlagged() == Flag.QUESTIONMARK) {
			// fieldY.setText(FieldLabel.MINE.toString());
			// changedFields.add(fieldY);
		}
		// it was a mine and was not flagged
		else if (fieldY.isMine() && fieldY.getFlagged() == Flag.NOFLAG) {
			fieldY.setText(FieldLabel.MINE.toString());
			fieldY.setBackgroundColor(FieldColor.LIGHTGREY);
			changedFields.add(fieldY);
		}
	}

	private void updateButtonsGUI(Set<Field> revealedFields) {
		for (Field changedField : revealedFields) {
			int posX = changedField.getPosX();
			int posY = changedField.getPosY();

			JButton changedButton = buttons[posX][posY];

			changedButton.setText(changedField.getText());
			changedButton.setBackground(changedField.getBackgroundColor());
			changedButton.setEnabled(false);
			for (ActionListener listener : changedButton.getActionListeners()) {
				changedButton.removeActionListener(listener);
			}
		}
	}
}
