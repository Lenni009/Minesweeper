package minesweeper.controller;

import minesweeper.model.MwData;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BombDistributor {
/*
 * siehe issue #3
 * Idee:
 * bei init der Feld-Objekte wird mine auf false gesetzt
 * Set anlegen; while set.size < anzahlMinen:
 * Random werden die bereits vorhandenen Feld-Objekte ausgewählt und mine auf true gesetzt.
 * Die minen werden in set gespeichert -> wenn mine ein 2. mal ausgewählt wird, wird set nicht erweitert
 * 
 * daraus folgt:
 * 1. die Fields dürfne nicht selbstständig entscheiden, ob sie eine Bombe sind
 * 2. Fields werden mit mine = false initialisiert
 * 3. 
 * 
 */
	private MwData data;
		
	public BombDistributor(MwData model) {
		this.data = model;
	}
	
	
	Set<Field> choosenMines = new HashSet<Field>();
	
	Random random = new Random();
	
	public void distributeMines() {
		while (choosenMines.size() < data.amountOfMines) {
			int fieldX = random.nextInt(data.fieldLen);
			int fieldY = random.nextInt(data.fieldLen);
			data.getField(fieldX, fieldY).setMine(true);
			choosenMines.add(data.getField(fieldX, fieldY));
		}
	}
}
