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
	//
	
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
	
	public Mine(String text) {
		super(text);
		
		mine = (zufall.nextInt(2) == 1);
		
		switch (zufall.nextInt(3)) {
		case 0: 
			flagged = Flag.NOFLAG;
			break;
		case 1:
			flagged = Flag.FLAGGED;
			break;
		case 2:
			flagged = Flag.QUESTIONMARK;
			break;
		}
		
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

	public void setBombNeighbourCount(int bombedNeighbours) {
		this.bombNeighbourCount = bombedNeighbours;
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
