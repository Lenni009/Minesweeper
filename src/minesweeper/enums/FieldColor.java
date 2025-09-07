package minesweeper.enums;

import java.awt.Color;

public enum FieldColor {
	RED(Color.RED), ORANGE(Color.ORANGE), YELLOW(Color.YELLOW), LIGHTGREY(Color.LIGHT_GRAY), BLACK(Color.BLACK), WHITE(Color.WHITE);

	final Color color;

	FieldColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
