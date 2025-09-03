package minesweeper.controller;

import minesweeper.model.MsData;
import minesweeper.utils.Logger;
import minesweeper.view.EndScreen;

public class RevealChecker {

	private int revealedFields = 0;
	private int amountOfNormalFields;

	public RevealChecker(MsData model) {
		amountOfNormalFields = model.fieldLen * model.fieldLen - model.amountOfMines;
	}

	public void incrementRevealedFields() {
		revealedFields++;
		Logger.info("revealed fields: " + revealedFields);
		if (!isGameOver())
			return;

		new EndScreen();
	}

	private boolean isGameOver() {
		return amountOfNormalFields == revealedFields;
	}

}
