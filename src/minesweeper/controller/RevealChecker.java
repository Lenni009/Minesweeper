package minesweeper.controller;

import java.util.HashSet;
import java.util.Set;

import minesweeper.enums.FieldLabel;
import minesweeper.enums.Flag;
import minesweeper.model.MsData;
import minesweeper.utils.Logger;

// handles end-of-game conditions
public class RevealChecker {

	private MsData data;

	private int revealedFields = 0;
	private int amountOfNormalFields;

	public RevealChecker(MsData model) {
		this.data = model;
		int fieldLen = data.getFieldLen();
		amountOfNormalFields = fieldLen * fieldLen - data.getAmountOfMines();
	}

	public void incrementRevealedFields() {
		revealedFields++;
		Logger.info("revealed fields: " + revealedFields);
	}

	public boolean isGameWon() {
		return amountOfNormalFields == revealedFields;
	}

	/**
	 * place flags on all non-flagged fields
	 * 
	 * @return set containing all changed fields
	 */
	public Set<Field> revealMap() {
		Set<Field> changedFields = new HashSet<>();

		for (Field[] fieldRow : data.getMinefield()) {
			for (Field field : fieldRow) {
				checkAndSetFlags(changedFields, field);
			}
		}
		return changedFields;
	}

	/**
	 * place flag on a field if it's a mine and not flagged
	 * 
	 * @param changedFields set containing all changed fields
	 * @param field         field to check
	 */
	private void checkAndSetFlags(Set<Field> changedFields, Field field) {
		if (field.isMine() && field.getFlagged() == Flag.NOFLAG) {
			field.setText(FieldLabel.FLAG.toString());
			field.setFlagged(Flag.FLAGGED);
			data.setPlacedFlags(data.getPlacedFlags() + 1);
		}
		changedFields.add(field);
	}

}
