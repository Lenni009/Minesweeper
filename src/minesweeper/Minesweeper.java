package minesweeper;

import minesweeper.model.MwData;
import minesweeper.view.MsGUI;

public class Minesweeper {
	
	
	private MwData model;
	private MsGUI view;

	public Minesweeper() {
		model = new MwData(8,7);
		view = new MsGUI(model);
	}

	public static void main(String[] args) {
		Minesweeper controller = new Minesweeper();
		controller.view.setVisible(true);
	}

}
