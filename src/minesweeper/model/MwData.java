package minesweeper.model;

import minesweeper.controller.Field;

/**
 * @args int fieldLen n*n
 * @args int amountOfMines konkrete Anzahl an Minen
 */
public class MwData {
	
	public final int fieldLen;
	
	public final int amountOfMines;
	
	/**
	 * Mine[x][y] mit
	 * x .. Zeile
	 * y .. Spalte
	 */
	private Field[][] minefield;
			
	public MwData(int fieldLen, int amountOfMines) {
		this.fieldLen = fieldLen;
		minefield = new Field[fieldLen][fieldLen];
		this.amountOfMines = amountOfMines;
	}
	
	public void generateMinefield(int fieldLen) {
		// n*n-Spielfeld erstellen und mit Minen-Objekten befuellen
		for (int x = 0; x < (fieldLen); ++x) {
			for (int y = 0; y < (fieldLen); ++y) {
				Field m = new Field(x,y);
				minefield[x][y] = m;
			}
		}
	}
	
	public Field[][] getMinefield() {
		return this.minefield;
	}

	public Field getMine(int x, int y) {
		/*try {
			Mine mine = minefield[x][y]; 
			return mine;
		}
		catch (Exception e) {
			return null;
		}*/
		return minefield[x][y];
	}
	
	public void countMineNeighbours(int posX, int posY) {
		// Nachbarn ermitteln
		/* 
		 * +--+--+-----+-----+
		 * |-1/-1|-1/00|-1/+1|
		 * +--+--+-----+-----+
		 * |00/-1| X/Y |00/+1|
		 * +--+--+-----+-----+
		 * |+1/-1|+1/00|+1/+1|
		 * +--+--+-----+-----+
		 */
		int[][] neighbourPos = {
				{posX-1,posY-1},{posX-1,posY},{posX-1,posY+1},
				{posX,posY-1},{posX,posY+1},
				{posX+1,posY-1},{posX+1,posY},{posX+1,posY+1}};
		
		int counterMines = 0;
		
		for (int i=0; i<8; i++) {
			if (isValidNeighbourPos(neighbourPos, i) && getMine(neighbourPos[i][0],neighbourPos[i][1]).isMine()) {
				counterMines++;
			}
		}
		System.out.println("" + counterMines);
		getMine(posX, posY).setBombNeighbourCount(counterMines);
	}

	private boolean isValidNeighbourPos(int[][] neighbourPos, int i) {
		return neighbourPos[i][0] >= 0 && neighbourPos[i][0] < fieldLen && neighbourPos[i][1] >= 0 && neighbourPos[i][1] < fieldLen;
	}
	
	
	


}
