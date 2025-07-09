package minesweeper;

import java.util.Map;

public class MwData {
	
	private Map<Integer, Mine> minefield;
	
	public MwData() {
		minefield = new Map<Integer, Mine>(); 
	}
	
	/* Nachbarn:
	 * +--+--+--+
	 * |-9|-8|-7|
	 * +--+--+--+
	 * |-1|ID|+1|
	 * +--+--+--+
	 * |+7|+8|+9|
	 * +--+--+--+
	 */

}
