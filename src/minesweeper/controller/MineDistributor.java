package minesweeper.controller;

import minesweeper.model.MsData;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// handle distributing mines on first click
public class MineDistributor {

	private MsData data;

	private Field initField;

	public MineDistributor(MsData model, Field initField) {
		this.data = model;
		this.initField = initField;
	}

	/**
	 * Randomly chooses fields that will contain mines. The field initially clicked
	 * on and its neighbours won't be chosen.
	 */
	public void distributeMines() {

		int initX = initField.getPosX();
		int initY = initField.getPosY();

		// initiate field with all 8 neighbours
		int[][] freeFields = { { initX - 1, initY - 1 }, { initX - 1, initY }, { initX - 1, initY + 1 },
				{ initX, initY - 1 }, { initX, initY }, { initX, initY + 1 }, { initX + 1, initY - 1 },
				{ initX + 1, initY }, { initX + 1, initY + 1 } };

		Random random = new Random();
		Set<Field> chosenMines = new HashSet<>();
		int fieldLen = data.getFieldLen();

		// fill set until specified amount of mines has been reached
		while (chosenMines.size() < data.getAmountOfMines()) {
			int fieldX = random.nextInt(fieldLen);
			int fieldY = random.nextInt(fieldLen);
			if (isPosValid(fieldX, fieldY, freeFields)) {
				Field targetField = data.getField(fieldX, fieldY);
				targetField.setMine(true);
				chosenMines.add(targetField);
			}
		}
	}

	/**
	 * Checks whether a mine can be placed at generated coords.
	 * 
	 * @param randomX    randomly generated X-Coord
	 * @param randomY    randomly generated Y-Coord
	 * @param freeFields coords of the initially clicked field and its neighbours
	 * @return whether chosen position is valid
	 */
	private boolean isPosValid(int randomX, int randomY, int[][] freeFields) {

		for (int[] coord : freeFields) {
			if (coord[0] == randomX && coord[1] == randomY) {
				return false;
			}
		}

		return true;
	}
}
