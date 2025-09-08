package minesweeper.controller;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import minesweeper.enums.Flag;
import minesweeper.model.MsData;

public class AutoRevealer {

	private MsData data;
	public RevealChecker revealChecker;

	public AutoRevealer(MsData model) {
		this.data = model;
		revealChecker = new RevealChecker(data);
	}

	public Set<Field> revealField(Field field) {

		Set<Field> changedFields = new HashSet<Field>();

		// early return, if field is a mine or is already revealed
		if (field.isMine() || !field.isClickable()) {
			return changedFields;
		}

		if (field.getFlagged() == Flag.FLAGGED) {
			// maintaining single source of truth
			data.setPlacedFlags(data.getPlacedFlags() - 1);
			field.setFlagged(Flag.NOFLAG);
		} else if (field.getFlagged() == Flag.QUESTIONMARK) {
			field.setFlagged(Flag.NOFLAG);
		}

		revealChecker.incrementRevealedFields();

		int fieldX = field.getPosX();
		int fieldY = field.getPosY();

		// reveal field
		field.setClickable(false);
		data.countMineNeighbours(fieldX, fieldY);
		int mineNeighborCount = field.getMineNeighbourCount();
		if (mineNeighborCount > 0) {
			// scaling between 0x80 (orange) and 0xFF (yellow)
			int greenValue = 0xFF - (mineNeighborCount - 1) * 18;
			Color backgroundColor = new Color(0xFF, greenValue, 0);
			field.setBackgroundColor(backgroundColor);
		}
		field.setText(String.valueOf(mineNeighborCount));

		int[][] neighbours = { { fieldX - 1, fieldY - 1 }, { fieldX - 1, fieldY }, { fieldX - 1, fieldY + 1 }, // above
				{ fieldX, fieldY - 1 }, { fieldX, fieldY + 1 }, // left & right
				{ fieldX + 1, fieldY - 1 }, { fieldX + 1, fieldY }, { fieldX + 1, fieldY + 1 } }; // under

		// if field has 0 mines as neighbours call reveal for direct neighbours
		if (mineNeighborCount == 0) {
			for (int[] fieldXY : neighbours) {
				updateField(changedFields, fieldXY);
			}
		}

		changedFields.add(field);

		return changedFields;

	}

	private void updateField(Set<Field> changedFields, int[] fieldXY) {
		if (isValidPosition(fieldXY[0], fieldXY[1])) {
			changedFields.addAll(revealField(data.getField(fieldXY[0], fieldXY[1])));
		}
	}

	private boolean isValidPosition(int posX, int posY) {
		int fieldLen = data.getFieldLen();
		return (0 <= posX && posX < fieldLen && 0 <= posY && posY < fieldLen);
	}

}
