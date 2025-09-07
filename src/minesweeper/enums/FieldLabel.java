package minesweeper.enums;

public enum FieldLabel {
	UNLABELLED(""), BOMB("*"), FLAG("/ ! \\"), MAYBEFLAG("/ ? \\");

	final String label;

	FieldLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}
