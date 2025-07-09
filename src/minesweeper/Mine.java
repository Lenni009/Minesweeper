package minesweeper;

import java.util.Random;

import javax.swing.JButton;

public class Mine {
	
	Random zufall = new Random();
	
	/**
	 * true: ist eine Bombe
	 * false: ist keine Bombe
	 */
	private boolean mine;
	
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
	private boolean clickable;

	/**
	 * ID der Mine, wird aus MwData heraus generiert
	 */
	private int posX;
	private int posY;
	
	public String caption;
	
	public Mine(int x, int y) {
		
		this.posX = x;
		this.posY = y;
		
		mine = (zufall.nextInt(2) == 1);
		
		flagged = Flag.NOFLAG;
		
		bombNeighbourCount = 0;	
		
		caption = "";
		
		clickable = true;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(Boolean mine) {
		this.mine = mine;
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
		if (this.clickable) {
			this.caption = text;			
		}
		else {
			this.caption = String.valueOf(bombNeighbourCount);
		}
		// TODO: wenn linksclick zu bumm führt sollte nicht bombNeighbourCount gezeigt werden, sondern eine rote Bombe
	}
	
	public String getText() {
		return this.caption;
	}
	

}
