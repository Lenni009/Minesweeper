package minesweeper.view;

import java.awt.GridLayout;
import javax.swing.*;

import minesweeper.controller.Flag;
import minesweeper.controller.AutoRevealer;
import minesweeper.controller.MineDistributor;
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

	public MineField(MsData model) {
		this.data = model;

		this.setLayout(new GridLayout(data.fieldLen, data.fieldLen));

		data.generateMinefield(data.fieldLen);

		buttons = new JButton[data.fieldLen][data.fieldLen];

		for (int posX = 0; posX < data.fieldLen; posX++) {
			for (int posY = 0; posY < data.fieldLen; posY++) {
				JButton button = new JButton("");
				button.addMouseListener(createMouseListener(button, posX, posY));
				add(button);
				buttons[posX][posY] = button;
			}
		}

		MenuBar.getInstance().setStatusText("Platzierte Flaggen: " + data.placedFlags + " / " + data.amountOfMines);
	}

	/**
	 * Adds MouseListener to JButton-Object
	 * 
	 * @param button the JButton-Object
	 * @param posX   X-Coord of corresponding Field-Object
	 * @param posY   Y-Coord of corresponding Field-Object
	 * @return
	 */
	private MouseListener createMouseListener(JButton button, int posX, int posY) { // TODO auslagern in Data
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!button.isEnabled()) {
					return;
				}

				Field field = data.getField(posX, posY);

				if (SwingUtilities.isLeftMouseButton(e)) {
					leftclickEvent(button, field);
					Logger.info("links");
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					rightclickEvent(button, field);
					Logger.info("rechts");
				}
				Logger.info(field.getFlagged().toString());
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
		String text = "";

		if (field.getFlagged() == Flag.NOFLAG) { // there's no flag set
			if (data.placedFlags < data.amountOfMines) {
				data.placedFlags++;
				text = "/ ! \\";
				flag = Flag.FLAGGED;
			} else { // no flags left, put "?" instead
				text = "/ ? \\";
				flag = Flag.QUESTIONMARK;
			}
		} else if (field.getFlagged() == Flag.FLAGGED) { // flag was already set
			text = "/ ? \\";
			flag = Flag.QUESTIONMARK;
			data.placedFlags--;
		}

		field.setText(text);
		field.setFlagged(flag);

		MenuBar.getInstance().setStatusText("Platzierte Flaggen: " + data.placedFlags + " / " + data.amountOfMines);
		button.setText(field.getText());

		Logger.info("# platzierte Flaggen: " + data.placedFlags);
	}

	/**
	 * processes leftclick
	 * 
	 * @param button the clicked JButton
	 * @param field  the corresponding Field
	 */
	private void leftclickEvent(JButton button, Field field) {

		AutoRevealer ar = new AutoRevealer(data);

		// prevent leftclick: FLAGGED fields will not be revealed
		if (field.getFlagged() == Flag.FLAGGED) {
			Logger.info("FLAGGED Feld wird nicht aufgedeckt");
			return;
		}

		// distribute mines after initial click
		if (isFirstClick) {
			isFirstClick = false;
			MineDistributor bd = new MineDistributor(data, field);
			bd.distributeMines();
			MenuBar.getInstance().startTimer();
		}

		// if mine is revealed the game ends
		if (field.isMine()) {
			Logger.info("BOOOOOOOM");

			MenuBar.getInstance().stopTimer();
			revealMap(field);

			new EndScreen();
		}

		// reveal field
		else {
			Set<Field> revealedFields = ar.revealField(field);
			updateButtonsGUI(revealedFields);
		}
	}

	private void revealMap(Field field) {
		// TODO: use colors -> Related to Issue #4
		
		// Placeholders:
		// D ... dark background
		// R ... red background
		// B ... color the bomb red
		
		Set<Field> changedFields = new HashSet<Field>();
		
		for (Field[] fieldX : data.getMinefield()) {
			for (Field fieldY : fieldX) {
				revealMines(changedFields, fieldY);
			}
		}
		
		field.setText("B"); // Placeholder to mark clicked bomb
		changedFields.add(field);
		
		updateButtonsGUI(changedFields);
	}

	private void revealMines(Set<Field> changedFields, Field fieldY) {
		// it was no mine, but was flagged
		if (!fieldY.isMine() && fieldY.getFlagged() == Flag.FLAGGED) {
			fieldY.setText("R");
			changedFields.add(fieldY);
		}
		// it was not a mine, but was flagged with "?"
		else if (!fieldY.isMine() && fieldY.getFlagged() == Flag.QUESTIONMARK) {
			// nothing to do, but the documentation in the issue described this case
			
			// optional: falsly "?"-flagged fields could also be marked red:
			// fieldY.setText("R");
			// changedFields.add(fieldY);
		}
		// it was a mine and got flagged with "?" -> counts
		else if (fieldY.isMine() && fieldY.getFlagged() == Flag.QUESTIONMARK) {
			fieldY.setText("D");
			changedFields.add(fieldY);
		}
		// it was a mine and got flagged with "!"
		else if (fieldY.isMine() && fieldY.getFlagged() == Flag.NOFLAG) {
			fieldY.setText("D");
			changedFields.add(fieldY);
		}
	}

	private void updateButtonsGUI(Set<Field> revealedFields) {
		for (Field changedField : revealedFields) {
			int posX = changedField.getPosX();
			int posY = changedField.getPosY();

			JButton changedButton = buttons[posX][posY];

			changedButton.setText(changedField.getText());
			changedButton.setEnabled(false);
			// [0] is not the wanted listener (but
			// javax.swing.plaf.basic.BasicButtonListener)
			// [1] is the wanted listener
			changedButton.removeMouseListener(changedButton.getMouseListeners()[1]);
		}
	}
}
