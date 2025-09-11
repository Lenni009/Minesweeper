package minesweeper.controller;

import java.awt.Color;

import minesweeper.enums.FieldColor;
import minesweeper.enums.FieldLabel;
import minesweeper.enums.Flag;

// a field object in the UI
public class Field {

	/**
	 * true: is a mine false: is not a mine
	 */
	private boolean isMine;

	/**
	 * NOFLAG = no flag FLAGGED = flag set QUESTIONMARK = not sure
	 */
	private Flag flagged;

	/**
	 * amount of neighbours that are mines
	 */
	private int mineNeighbourCount;

	/**
	 * if field is revealed (true) it can't be clicked on anymore if field is not
	 * revealed (false) it can still be clicked on
	 */
	private boolean isRevealed;

	/**
	 * ID of the mine, is generated from MsData
	 */
	private int posX;
	private int posY;

	// text to display
	private String caption;

	// backgroundcolor
	private Color backgroundColor;

	public Field(int x, int y) {

		this.posX = x;
		this.posY = y;

		// initially, no field is a mine so the initial reveal of a field is always
		// successful when starting the game
		isMine = false;

		flagged = Flag.NOFLAG;

		mineNeighbourCount = 0;

		caption = FieldLabel.UNLABELLED.toString();

		isRevealed = false;

		setBackgroundColor(FieldColor.WHITE);
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(Boolean mine) {
		this.isMine = mine;
	}

	public Flag getFlagged() {
		return flagged;
	}

	public void setFlagged(Flag flagged) {
		this.flagged = flagged;
	}

	public int getMineNeighbourCount() {
		return mineNeighbourCount;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setMineNeighbourCount(int mineNeighbourCount) {
		this.mineNeighbourCount = mineNeighbourCount;
	}

	public void setText(String text) {
		// does field show number or flag?
		if (!this.isRevealed) { // field is not revealed by now
			this.caption = text;
		} else if (mineNeighbourCount == 0) { // field is revealed, has 0 mines -> display nothing
			this.caption = "";
		} else { // display number of mine neighbours
			this.caption = String.valueOf(mineNeighbourCount);
		}
	}

	public String getText() {
		return this.caption;
	}

	public boolean isRevealed() {
		return isRevealed;
	}

	public void setRevealedStatus(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(FieldColor backgroundColor) {
		setBackgroundColor(backgroundColor.getColor());
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
