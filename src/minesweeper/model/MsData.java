package minesweeper.model;

import minesweeper.controller.Field;
import minesweeper.utils.Logger;

public class MsData {

	public final int fieldLen;

	public final int amountOfMines;

	public int placedFlags = 0;

	/**
	 * Mine[x][y] with x .. Row y .. Column
	 */
	private Field[][] minefield;

	/**
	 * Constructor
	 * 
	 * @param fieldLen      Length of one site
	 * @param amountOfMines Concrete Number of Mines
	 */
	public MsData(int fieldLen, int amountOfMines) {
		this.fieldLen = fieldLen;
		minefield = new Field[fieldLen][fieldLen];
		this.amountOfMines = amountOfMines;
	}

	/**
	 * Generate Minefield with given Length
	 * 
	 * @param sideLength Length of one site
	 */
	public void generateMinefield(int sideLength) {
		// n*n-Spielfeld erstellen und mit Minen-Objekten befuellen
		for (int x = 0; x < sideLength; x++) {
			for (int y = 0; y < sideLength; y++) {
				Field field = new Field(x, y);
				minefield[x][y] = field;
			}
		}
	}

	/**
	 * Getter for the 2D Array
	 * 
	 * @return the 2D Array with all the Fields
	 */
	public Field[][] getMinefield() {
		return this.minefield;
	}

	/**
	 * Returns Field-Object, specified by X/Y-Choords
	 * 
	 * @param posX X-Choord of the Field
	 * @param posY Y-Choord of the Field
	 * @return Field
	 */
	public Field getField(int posX, int posY) {
		return minefield[posX][posY];
	}

	/**
	 * Count how many neighbours are mines and store result in MineNeighbourCount of
	 * Field-Obj
	 * 
	 * @param posX X-Choord of the Field
	 * @param posY Y-Choord of the Field
	 */
	public void countMineNeighbours(int posX, int posY) {
		// calculate neighbours
		/*
		 * +--+--+-----+-----+ |-1/-1|-1/00|-1/+1| +--+--+-----+-----+ |00/-1| X/Y
		 * |00/+1| +--+--+-----+-----+ |+1/-1|+1/00|+1/+1| +--+--+-----+-----+
		 */
		int[][] neighbourPos = { { posX - 1, posY - 1 }, { posX - 1, posY }, { posX - 1, posY + 1 }, { posX, posY - 1 },
				{ posX, posY + 1 }, { posX + 1, posY - 1 }, { posX + 1, posY }, { posX + 1, posY + 1 } };

		int counterMines = 0;

		for (int i = 0; i < neighbourPos.length; i++) {
			if (isValidNeighbourPos(neighbourPos, i) && getField(neighbourPos[i][0], neighbourPos[i][1]).isMine()) {
				counterMines++;
			}
		}
		Logger.info("" + counterMines);
		getField(posX, posY).setMineNeighbourCount(counterMines);
	}

	/**
	 * Returns true if position is valid
	 * 
	 * @param neighbourPos the 2D Array
	 * @param position     the Position
	 * @return true if valid, false otherwise
	 */
	private boolean isValidNeighbourPos(int[][] neighbourPos, int position) {
		return neighbourPos[position][0] >= 0 && neighbourPos[position][0] < fieldLen && neighbourPos[position][1] >= 0
				&& neighbourPos[position][1] < fieldLen;
	}

}
