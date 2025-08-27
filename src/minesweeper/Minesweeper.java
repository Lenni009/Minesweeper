package minesweeper;

import minesweeper.model.MsData;
import minesweeper.view.MsGUI;

public class Minesweeper {
	
	
	private MsData model;
	private MsGUI view;

	public Minesweeper() {
		model = new MsData(8,7);
		view = new MsGUI(model);
	}

	public static void main(String[] args) {
		Minesweeper controller = new Minesweeper();
		controller.view.setVisible(true);
	}

}
