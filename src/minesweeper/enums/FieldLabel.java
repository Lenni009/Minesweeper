package minesweeper.enums;

public enum FieldLabel {
	UNLABELLED(""), MINE("o"), EXPLOSION("*"), FLAG("/ ! \\"), MAYBEFLAG("/ ? \\");

	private final String label;

	FieldLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}
