package minesweeper.controller;

import minesweeper.model.MsData;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MineDistributor {

	private MsData data;
	
	private Field initField;
		
	public MineDistributor(MsData model, Field initField) {
		this.data = model;
		this.initField = initField;
	}
	
	
	Set<Field> choosenMines = new HashSet<Field>();
	
	Random random = new Random();
	
	/**
	 * Randomly chooses fields that will contain mines. The field initially clicked on and its neighbours won't be chosen.
	 */
	public void distributeMines() {
		
		int initX = initField.getPosX();
		int initY = initField.getPosY();
		
		// initiale field with all 8 neighbours
		int[][] freeFields = {
				{initX-1,initY-1},{initX-1,initY},{initX-1,initY+1},
				{initX,initY-1},{initX,initY},{initX,initY+1},
				{initX+1,initY-1},{initX+1,initY},{initX+1,initY+1}};
		
		while (choosenMines.size() < data.amountOfMines) {
			int fieldX = random.nextInt(data.fieldLen);
			int fieldY = random.nextInt(data.fieldLen);
			if (isPosValid(fieldX, fieldY, freeFields)) {
				data.getField(fieldX, fieldY).setMine(true);
				choosenMines.add(data.getField(fieldX, fieldY));
			}
		}
	}

	/**
	 * Checks wether a mine can be placed at generated coords.
	 * @param randomX randomly generated X-Coord
	 * @param randomY randomly generated Y-Coord
	 * @param freeFields coords of the initially clicked field and its neighbours
	 * @return true, if chosen position is valid
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
