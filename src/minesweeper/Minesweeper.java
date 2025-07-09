package minesweeper;


public class Minesweeper {
	
	private MwData model;
	private MwGUI view;

	public Minesweeper() {
		model = new MwData(8);
		view = new MwGUI(model);
	}

	public static void main(String[] args) {
		Minesweeper controller = new Minesweeper();
		controller.view.setVisible(true);
	}

}
