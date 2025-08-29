package minesweeper.controller;

import java.util.HashSet;
import java.util.Set;

import minesweeper.model.MsData;

public class AutoRevealer {
	
	private MsData data;
	
	public AutoRevealer(MsData model) {
		this.data = model;
	}
	
	public Set<Field> revealField(Field clickedField) {
		
		Set<Field> changedFields = new HashSet<Field>();

		// early return, if field is a bomb
		if (clickedField.isMine() ) {
			return changedFields;
		}
		
		// reveal field
		clickedField.setClickable(false);
		data.countMineNeighbours(clickedField.getPosX(),clickedField.getPosY());
		clickedField.setText(String.valueOf(clickedField.getBombNeighbourCount()));
		
		int[][] updateFields = {
				{clickedField.getPosX()-1, clickedField.getPosY()}, // above
				{clickedField.getPosX(), clickedField.getPosY()-1}, // left
				{clickedField.getPosX(), clickedField.getPosY()+1}, // right
				{clickedField.getPosX()+1, clickedField.getPosY()}}; // under
				
		// if field has 0 Bomb as neighbours call reveal for direct neigbours
		// TODO: also call indirect neighbours ("all 8")?
		if (clickedField.getBombNeighbourCount() == 0) {
			for (int[] fieldXY : updateFields) {
				// TODO: kann man natürlich nur aufrufen, wenn Position valide ist
				if (isValidPosition(fieldXY[0], fieldXY[1])) {
					// DAS HIER IST EIN STACKOVERFLOW:
					//changedFields.addAll(revealField(data.getField(fieldXY[0], fieldXY[1])));					
				}
			}
		}
		
		changedFields.add(clickedField);
		
		return changedFields;
		
	}
	
	public boolean isValidPosition(int posX, int posY) {
		if (0 <= posX && posX < data.fieldLen && 0 <= posY && posY < data.fieldLen) {
			return true;
		}
		return false;
	}

}
