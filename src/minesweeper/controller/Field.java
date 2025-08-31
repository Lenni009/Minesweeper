package minesweeper.controller;

public class Field {
		
	/**
	 * true: ist eine Bombe
	 * false: ist keine Bombe
	 */
	private boolean isMine;
	
	/**
	 * NOFLAG = keine Flagge
	 * FLAGGED = Flagge gesetzt
	 * QUESTIONMARK = unsicher
	 */
	private Flag flagged;
	
	/**
	 * Anzahl Nachbarn, die Bomben sind
	 */
	private int bombNeighbourCount;

	// TODO better name
	/**
	 * true: kann noch geklickt werden -> zeige flagged
	 * false: aufgedeckt -> zeige Zahl (oder tot lol)
	 */
	private boolean isClickable;

	/**
	 * ID der Mine, wird aus MsData heraus generiert
	 */
	private int posX;
	private int posY;
	
	public String caption;
	
	public Field(int x, int y) {
		
		this.posX = x;
		this.posY = y;
		
		// bei init ist kein Feld eine mine, damit beim STart des Spiels das erste Aufdecken immer erfolgreich ist
		isMine = false;
		
		flagged = Flag.NOFLAG;
		
		bombNeighbourCount = 0;	
		
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

	public int getBombNeighbourCount() {
		return bombNeighbourCount;
	}
	
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}

	public void setBombNeighbourCount(int bombNeighbourCount) {
		this.bombNeighbourCount = bombNeighbourCount;
	}
	
	public void setText(String text) {
		// zeigt feld zahl oder flagging?
		if (this.isClickable) {
			this.caption = text;			
		}
		else if (bombNeighbourCount == 0) {
				this.caption = "";
		}
		else {
			this.caption = String.valueOf(bombNeighbourCount);				
		}
		// TODO: wenn linksclick zu bumm führt sollte nicht bombNeighbourCount gezeigt werden, sondern eine rote Bombe
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
