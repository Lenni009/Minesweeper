package minesweeper;

import java.util.Random;

import javax.swing.JButton;

public class Mine extends JButton {
	
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
	private boolean clicklable;

	/**
	 * ID der Mine, wird aus GUI heraus generiert
	 */
	private int mineID;
	
	public Mine(int ID) {
		super(""); // Text muss an Standardkonstruktur uebergeben werden, dieser soll aber leer sein
		
		mineID = ID;
		
		mine = (zufall.nextInt(2) == 1);
		
		flagged = Flag.NOFLAG;
		
		bombNeighbourCount = 0;		
	}

	public Boolean getMine() {
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
	
	public int getID() {
		return mineID;
	}

	public void setBombNeighbourCount(int bombNeighbourCount) {
		this.bombNeighbourCount = bombNeighbourCount;
	}
	
	public void setVisibleText(String text) {
		// zeigt feld zahl oder flagging?
		if (this.clicklable) {
			this.setText(text);			
		}
		else {
			this.setText(String.valueOf(bombNeighbourCount));
		}
		// TODO: wenn linksclick zu bumm führt sollte nicht bombNeighbourCount gezeigt werden, sondern eine rote Bombe
	}
	

}
