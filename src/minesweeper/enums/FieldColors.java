package minesweeper.enums;

import java.awt.Color;

public enum FieldColors {
	RED(Color.RED), ORANGE(Color.ORANGE), YELLOW(Color.YELLOW), DARKGREY(Color.DARK_GRAY);

	final Color color;

	FieldColors(Color color) {
		this.color = color;
	}
}
