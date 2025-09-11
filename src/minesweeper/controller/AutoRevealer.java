package minesweeper.controller;

import java.awt.Color;
import java.util.Set;

import minesweeper.enums.Flag;
import minesweeper.model.MsData;

// automatically reveals fields when one field is clicked
public class AutoRevealer {

	private MsData data;
	private RevealChecker revealChecker;

	public AutoRevealer(MsData model) {
		this.data = model;
		revealChecker = new RevealChecker(data);
	}

	/**
	 * reveals a field in the data layer
	 * 
	 * @param field         field to reveal
	 * @param changedFields set of fields that need to be updated
	 */
	public void revealField(Field field, Set<Field> changedFields) {
		// early return if field is a mine or is already revealed
		if (field.isMine() || field.isRevealed()) {
			return;
		}

		// remove Flag from field after it got revealed
		removePotentialFlag(field);

		// count how many fields are already revealed (if all fields are revealed, the
		// game ends)
		revealChecker.incrementRevealedFields();

		int fieldX = field.getPosX();
		int fieldY = field.getPosY();

		// set field data to display
		field.setRevealedStatus(true);
		data.countMineNeighbours(fieldX, fieldY);
		int mineNeighbourCount = field.getMineNeighbourCount();
		if (mineNeighbourCount > 0) {
			// scaling between 0x80 (orange) and 0xFF (yellow)
			int maxPotentialNeighbours = 8;
			int minValue = 0x80;
			int maxValue = 0xFF;
			int increments = (maxValue - minValue) / (maxPotentialNeighbours - 1); // subtracting 1 because we only
																					// differentiate between 7 states
																					// (one state is 0)
			int greenValue = maxValue - (mineNeighbourCount - 1) * increments; // subtracting 1 so mineNeighbourCount of
																				// 1 results in 0xFF (bright yellow)
			Color backgroundColor = new Color(0xFF, greenValue, 0);
			field.setBackgroundColor(backgroundColor);
		}
		field.setText(String.valueOf(mineNeighbourCount));

		// add field to set of fields to change
		// the caller method keeps the set and uses it to update the UI
		// this works because set objects are passed by reference, not by value
		changedFields.add(field);

		// if the field has no mines as neighbours, call reveal for the direct
		// neighbours
		if (mineNeighbourCount == 0) {
			updateNeighbours(changedFields, fieldX, fieldY);
		}
	}

	/**
	 * reveals the neighbours of a given field
	 * 
	 * @param changedFields set of fields that need to be updated
	 * @param fieldX        x coordinate of the field
	 * @param fieldY        y coordinate of the field
	 */
	private void updateNeighbours(Set<Field> changedFields, int fieldX, int fieldY) {
		// relative positions of all 8 neighbours
		int[][] neighbours = { { fieldX - 1, fieldY - 1 }, { fieldX - 1, fieldY }, { fieldX - 1, fieldY + 1 }, // above
				{ fieldX, fieldY - 1 }, { fieldX, fieldY + 1 }, // left & right
				{ fieldX + 1, fieldY - 1 }, { fieldX + 1, fieldY }, { fieldX + 1, fieldY + 1 } }; // under

		// reveal all valid neighbours
		for (int[] fieldXY : neighbours) {
			// an invalid field would be found if the top left field is revealed (no
			// neighbours on top and left)
			if (isValidPosition(fieldXY[0], fieldXY[1])) {
				revealField(data.getField(fieldXY[0], fieldXY[1]), changedFields);
			}
		}
	}

	/**
	 * removes a flag if present
	 * 
	 * @param field field to remove the flag from
	 */
	private void removePotentialFlag(Field field) {
		if (field.getFlagged() == Flag.FLAGGED) {
			// maintaining single source of truth
			data.setPlacedFlags(data.getPlacedFlags() - 1);
			field.setFlagged(Flag.NOFLAG);
		} else if (field.getFlagged() == Flag.QUESTIONMARK) {
			field.setFlagged(Flag.NOFLAG);
		}
	}

	/**
	 * checks whether a given coordinate point is inside the playing field range
	 * 
	 * @param posX x coordinate of the field
	 * @param posY y coordinate of the field
	 * @return whether the the given point exists on the playing field
	 */
	private boolean isValidPosition(int posX, int posY) {
		int fieldLen = data.getFieldLen();
		return (0 <= posX && posX < fieldLen && 0 <= posY && posY < fieldLen);
	}

	public RevealChecker getRevealChecker() {
		return revealChecker;
	}
}
