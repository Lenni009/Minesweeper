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

		// early return, if field is a bomb or is already revealed
		if (field.isMine() || !field.isClickable()) {
			return changedFields;
		}
		
		int fieldX = field.getPosX();
		int fieldY = field.getPosY();
		
		// reveal field
		field.setClickable(false);
		data.countMineNeighbours(fieldX,fieldY);
		field.setText(String.valueOf(field.getBombNeighbourCount()));
		
		int[][] updateFields = {
				{fieldX-1, fieldY-1},{fieldX-1, fieldY},{fieldX-1, fieldY+1}, // above
				{fieldX, fieldY-1},{fieldX, fieldY+1}, // left & right
				{fieldX+1, fieldY-1},{fieldX+1, fieldY},{fieldX+1, fieldY+1}}; // under
				
		// if field has 0 Bomb as neighbours call reveal for direct neigbours
		if (field.getBombNeighbourCount() == 0) {
			for (int[] fieldXY : updateFields) {
				if (isValidPosition(fieldXY[0], fieldXY[1])) {
					changedFields.addAll(revealField(data.getField(fieldXY[0], fieldXY[1])));					
				}
			}
		}
		
		changedFields.add(field);
		
		return changedFields;
		
	}
	
	public boolean isValidPosition(int posX, int posY) {
		if (0 <= posX && posX < data.fieldLen && 0 <= posY && posY < data.fieldLen) {
			return true;
		}
		return false;
	}

}
