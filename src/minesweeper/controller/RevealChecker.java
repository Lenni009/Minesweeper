package minesweeper.controller;

import java.util.HashSet;
import java.util.Set;

import minesweeper.model.MsData;
import minesweeper.utils.Logger;

public class RevealChecker {
	
	private MsData data;

	private int revealedFields = 0;
	private int amountOfNormalFields;

	public RevealChecker(MsData model) {
		this.data = model;
		amountOfNormalFields = data.fieldLen * data.fieldLen - data.amountOfMines;
	}

	public void incrementRevealedFields() {
		revealedFields++;
		Logger.info("revealed fields: " + revealedFields);
	}

	public boolean isGameOver() {
		return amountOfNormalFields == revealedFields;
	}
	
	public Set<Field> revealMap() {
		// TODO: use colors -> Related to Issue #4

		Set<Field> changedFields = new HashSet<Field>();

		for (Field[] fieldX : data.getMinefield()) {
			for (Field fieldY : fieldX) {
				checkAndSetFlags(changedFields, fieldY);
			}
		}
		return changedFields;
	}

	private void checkAndSetFlags(Set<Field> changedFields, Field fieldY) {
		if (fieldY.isMine() && fieldY.getFlagged() == Flag.NOFLAG) {
			fieldY.setText("/ ! \\");
			fieldY.setFlagged(Flag.FLAGGED);
		}
		changedFields.add(fieldY);
	}

}
