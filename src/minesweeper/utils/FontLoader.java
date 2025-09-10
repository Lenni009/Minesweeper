package minesweeper.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import minesweeper.controller.Minesweeper;

public class FontLoader {

	private static FontLoader instance;
	private Font emojiFont;

	private FontLoader() {
		// copied from
		// https://stackoverflow.com/questions/12998604/adding-fonts-to-swing-application-and-include-in-package
		InputStream is = Minesweeper.class.getResourceAsStream("/assets/font/segoe-ui-emoji.ttf");
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			emojiFont = font.deriveFont(20f);
			// end of copy
		} catch (FontFormatException | IOException e) {
			Logger.error(e.getMessage());
			// Fallback: try other font instead
			emojiFont = new Font("Arial", Font.PLAIN, 30);
		}
	}

	// singleton :3
	public static FontLoader getInstance() {
		if (instance == null) {
			instance = new FontLoader();
		}
		return instance;
	}

	public Font getFont() {
		return emojiFont;
	}
}
