package minesweeper.view;

import java.awt.GridLayout;
import javax.swing.*;

import java.awt.Font;
import minesweeper.controller.GameTimer;
import minesweeper.controller.AutoRevealer;
import minesweeper.controller.MineDistributor;
import minesweeper.controller.RevealChecker;
import minesweeper.enums.FieldColor;
import minesweeper.enums.FieldLabel;
import minesweeper.enums.Flag;
import minesweeper.controller.Field;
import minesweeper.model.MsData;
import minesweeper.utils.FontLoader;
import minesweeper.utils.Logger;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

// handles displaying the playing field
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

		Font font = FontLoader.getInstance().getFont();

		// create buttons and store them for later use
		for (int posX = 0; posX < fieldLen; posX++) {
			for (int posY = 0; posY < fieldLen; posY++) {
				JButton button = new JButton("");
				button.addMouseListener(createMouseListener(button, posX, posY));
				button.setFont(font);
				this.add(button);
				buttons[posX][posY] = button;
			}
		}

		updatePlacedFlagLabel();
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
					leftclickEvent(field);
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

		updatePlacedFlagLabel();
		button.setText(field.getText());

		Logger.info("placed flags: " + data.getPlacedFlags());
	}

	/**
	 * processes leftclick
	 * 
	 * @param field the field that was clicked
	 */
	private void leftclickEvent(Field field) {

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
			GameTimerLabel timerLabel = GameTimerLabel.getInstance();
			timerLabel.showTime();
			GameTimer.getInstance().startTimer(timerLabel);
			MenuBar.getInstance().showPlayingState(); // display pause button in the menubar
		}

		// if mine is revealed the game ends
		if (field.isMine()) {
			Logger.info("Mine exploded - game over!");

			revealMap(field);

			new EndScreen(false);
		} else {
			revealField(field);
		}
	}

	/**
	 * reveal a field in the UI
	 * 
	 * @param field field to reveal
	 */
	private void revealField(Field field) {
		Set<Field> revealedFields = new HashSet<>();
		autoRevealer.revealField(field, revealedFields); // recursively fills up revealedFields

		RevealChecker revealChecker = autoRevealer.getRevealChecker();

		if (revealChecker.isGameWon()) {
			// Flag all mines if game is won
			revealedFields.addAll(revealChecker.revealMap());

			// using invokeLater so the UI is updated before it is
			// locked and we don't have to duplicate code
			SwingUtilities.invokeLater(() -> new EndScreen(true));
		}

		// this would have no effect without the invokeLater in the if statement
		updateButtonsGUI(revealedFields);
		updatePlacedFlagLabel();
	}

	/**
	 * updates the placed flags label with the current data
	 */
	private void updatePlacedFlagLabel() {
		MenuBar.getInstance()
				.setStatusText("Platzierte Flaggen: " + data.getPlacedFlags() + " / " + data.getAmountOfMines());
	}

	/**
	 * reveal all mines on the map
	 * 
	 * @param field field that triggered this method
	 */
	private void revealMap(Field field) {
		Set<Field> changedFields = new HashSet<>();

		for (Field[] fieldX : data.getMinefield()) {
			for (Field fieldY : fieldX) {
				revealMines(changedFields, fieldY);
			}
		}

		// give triggered mine different icon and background colour
		field.setText(FieldLabel.EXPLOSION.toString());
		field.setBackgroundColor(FieldColor.LIGHTRED);

		changedFields.add(field);

		updateButtonsGUI(changedFields);
	}

	/**
	 * reveals a field if it is a mine, or changes the background colour if the
	 * field was incorrectly flagged
	 * 
	 * @param changedFields set with all fields that need to be updated
	 * @param field         field to reveal
	 */
	private void revealMines(Set<Field> changedFields, Field field) {
		// it was no mine, but was flagged
		if (!field.isMine() && field.getFlagged() == Flag.FLAGGED) {
			field.setBackgroundColor(FieldColor.RED);
			changedFields.add(field);
		}
		// it was a mine and was not flagged
		else if (field.isMine() && field.getFlagged() == Flag.NOFLAG) {
			field.setText(FieldLabel.MINE.toString());
			field.setBackgroundColor(FieldColor.LIGHTGREY);
			changedFields.add(field);
		}
	}

	/**
	 * update a set of buttons in the UI
	 * 
	 * @param revealedFields set of buttons to update
	 */
	private void updateButtonsGUI(Set<Field> revealedFields) {
		for (Field changedField : revealedFields) {
			int posX = changedField.getPosX();
			int posY = changedField.getPosY();

			JButton changedButton = buttons[posX][posY];

			changedButton.setText(changedField.getText());
			changedButton.setBackground(changedField.getBackgroundColor());
			changedButton.setEnabled(false);

			// there could be multiple listeners on the buttons, we want to remove them all
			// so no code is executed when they are clicked again
			for (ActionListener listener : changedButton.getActionListeners()) {
				changedButton.removeActionListener(listener);
			}
		}
	}
}
