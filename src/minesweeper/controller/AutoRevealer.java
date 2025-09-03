package minesweeper.controller;

import java.util.HashSet;
import java.util.Set;

import minesweeper.model.MsData;

public class AutoRevealer {

	private MsData data;

	public AutoRevealer(MsData model) {
		this.data = model;
	}

	public Set<Field> revealField(Field field) {

		Set<Field> changedFields = new HashSet<Field>();

		// early return, if field is a mine or is already revealed
		if (field.isMine() || !field.isClickable()) {
			return changedFields;
		}
		
		if (field.getFlagged() == Flag.FLAGGED) {
			data.placedFlags--;
			field.setFlagged(Flag.NOFLAG);
		}
		else if (field.getFlagged() == Flag.QUESTIONMARK){
			field.setFlagged(Flag.NOFLAG);
		}

		int fieldX = field.getPosX();
		int fieldY = field.getPosY();

		// reveal field
		field.setClickable(false);
		data.countMineNeighbours(fieldX, fieldY);
		field.setText(String.valueOf(field.getMineNeighbourCount()));

		int[][] neighbours = { { fieldX - 1, fieldY - 1 }, { fieldX - 1, fieldY }, { fieldX - 1, fieldY + 1 }, // above
				{ fieldX, fieldY - 1 }, { fieldX, fieldY + 1 }, // left & right
				{ fieldX + 1, fieldY - 1 }, { fieldX + 1, fieldY }, { fieldX + 1, fieldY + 1 } }; // under

		// if field has 0 mines as neighbours call reveal for direct neighbours
		if (field.getMineNeighbourCount() == 0) {
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
		return (0 <= posX && posX < data.fieldLen && 0 <= posY && posY < data.fieldLen);
	}

}
