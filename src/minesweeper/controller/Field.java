package minesweeper.controller;

import minesweeper.enums.Flag;

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

	// TODO better name
	/**
	 * true: can still be clicked -> show flagged false: uncovered -> show number
	 * (or death lol)
	 */
	private boolean isClickable;

	/**
	 * ID of the mine, is generated from MsData
	 */
	private int posX;
	private int posY;

	public String caption;

	public Field(int x, int y) {

		this.posX = x;
		this.posY = y;

		// initially, no field is a mine so the initial reveal of a field is always
		// successful when starting the game
		isMine = false;

		flagged = Flag.NOFLAG;

		mineNeighbourCount = 0;

		caption = "";

		isClickable = true;
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
		// does field show number of flag?
		if (this.isClickable) {
			this.caption = text;
		} else if (mineNeighbourCount == 0) {
			this.caption = "";
		} else {
			this.caption = String.valueOf(mineNeighbourCount);
		}
		// TODO: if left click leads to boom, mineNeighbourCount should not be shown,
		// but a red mine
	}

	public String getText() {
		return this.caption;
	}

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}

}
