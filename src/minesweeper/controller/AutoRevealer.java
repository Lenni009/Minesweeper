package minesweeper.controller;

import java.awt.Color;
import java.util.Set;

import minesweeper.enums.Flag;
import minesweeper.model.MsData;

public class AutoRevealer {

	private MsData data;
	private RevealChecker revealChecker;

	public AutoRevealer(MsData model) {
		this.data = model;
		revealChecker = new RevealChecker(data);
	}

	public Set<Field> revealField(Field field, Set<Field> changedFields) {
		// early return, if field is a mine or is already revealed
		if (field.isMine() || field.isRevealed()) {
			return changedFields;
		}

		// remove Flag from field, after it got revealed
		removePotentialFlag(field);

		// counts how many fields are revealed already
		revealChecker.incrementRevealedFields();

		int fieldX = field.getPosX();
		int fieldY = field.getPosY();

		// reveal field
		field.setRevealedStatus(true);
		data.countMineNeighbours(fieldX, fieldY);
		int mineNeighborCount = field.getMineNeighbourCount();
		if (mineNeighborCount > 0) {
			// scaling between 0x80 (orange) and 0xFF (yellow)
			int greenValue = 0xFF - (mineNeighborCount - 1) * 18;
			Color backgroundColor = new Color(0xFF, greenValue, 0);
			field.setBackgroundColor(backgroundColor);
		}
		field.setText(String.valueOf(mineNeighborCount));
		changedFields.add(field);

		// if the field has no mines as neighbours, call reveal for the direct
		// neighbours
		if (mineNeighborCount == 0) {
			updateNeighbours(changedFields, fieldX, fieldY);
		}

		return changedFields;
	}

	private void updateNeighbours(Set<Field> changedFields, int fieldX, int fieldY) {
		// relative positions of all 8 neighbours
		int[][] neighbours = { { fieldX - 1, fieldY - 1 }, { fieldX - 1, fieldY }, { fieldX - 1, fieldY + 1 }, // above
				{ fieldX, fieldY - 1 }, { fieldX, fieldY + 1 }, // left & right
				{ fieldX + 1, fieldY - 1 }, { fieldX + 1, fieldY }, { fieldX + 1, fieldY + 1 } }; // under

		// reveal all valid neighbours
		for (int[] fieldXY : neighbours) {
			if (isValidPosition(fieldXY[0], fieldXY[1])) {
				revealField(data.getField(fieldXY[0], fieldXY[1]), changedFields);
			}
		}
	}

	private void removePotentialFlag(Field field) {
		if (field.getFlagged() == Flag.FLAGGED) {
			// maintaining single source of truth
			data.setPlacedFlags(data.getPlacedFlags() - 1);
			field.setFlagged(Flag.NOFLAG);
		} else if (field.getFlagged() == Flag.QUESTIONMARK) {
			field.setFlagged(Flag.NOFLAG);
		}
	}

	private boolean isValidPosition(int posX, int posY) {
		int fieldLen = data.getFieldLen();
		return (0 <= posX && posX < fieldLen && 0 <= posY && posY < fieldLen);
	}

	public RevealChecker getRevealChecker() {
		return revealChecker;
	}
}
