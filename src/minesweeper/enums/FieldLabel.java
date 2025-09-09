package minesweeper.enums;

public enum FieldLabel {
	UNLABELLED(""), MINE("\uD83D\uDCA3"), EXPLOSION("\uD83D\uDCA5"), FLAG("\uD83D\uDEA9"), MAYBEFLAG("\u2753");

	private final String label;

	FieldLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
}
