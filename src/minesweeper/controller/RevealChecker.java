package minesweeper.controller;

import java.util.HashSet;
import java.util.Set;

import minesweeper.enums.FieldLabel;
import minesweeper.enums.Flag;
import minesweeper.model.MsData;
import minesweeper.utils.Logger;

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

	public boolean isGameOver() {
		return amountOfNormalFields == revealedFields;
	}

	public Set<Field> revealMap() {
		Set<Field> changedFields = new HashSet<>();

		for (Field[] fieldRow : data.getMinefield()) {
			for (Field field : fieldRow) {
				checkAndSetFlags(changedFields, field);
			}
		}
		return changedFields;
	}

	private void checkAndSetFlags(Set<Field> changedFields, Field field) {
		if (field.isMine() && field.getFlagged() == Flag.NOFLAG) {
			field.setText(FieldLabel.FLAG.toString());
			field.setFlagged(Flag.FLAGGED);
		}
		changedFields.add(field);
	}

}
