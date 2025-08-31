package minesweeper.view;

import java.awt.GridLayout;
import javax.swing.*;

import minesweeper.controller.Flag;
import minesweeper.controller.AutoRevealer;
import minesweeper.controller.BombDistributor;
import minesweeper.controller.Field;
import minesweeper.model.MsData;
import minesweeper.utils.Logger;

import java.awt.event.*;
import java.util.Set;

public class MineField extends JPanel {

	private MsData data;
	
	private boolean isFirstClick = true; 
	
	private JButton[][] buttons;
	
	public MineField(MsData model) {
		this.data = model;
		
		this.setLayout(new GridLayout(data.fieldLen, data.fieldLen));
		
		data.generateMinefield(data.fieldLen);
		
		buttons = new JButton[data.fieldLen][data.fieldLen];
		
		for (int x = 0; x < data.fieldLen; ++x) {
		    final int posX = x;
		    for (int y = 0; y < data.fieldLen; ++y) {
		        final int posY = y;
		        JButton button = new JButton("");
		        button.addMouseListener(createMouseListener(button, posX, posY));
		        add(button);
		        buttons[x][y] = button;
		    }
		}
		
		MsGUI.statusLabel.setText("Platzierte Flaggen: "+data.placedFlags+" / "+data.amountOfMines);
	}
	
	/**
	 * Adds MouseListener to JButton-Object
	 * @param button the JButton-Object 
	 * @param posX X-Coord of corresponding Field-Object
	 * @param posY Y-Coord of corresponding Field-Object
	 * @return
	 */
	private MouseListener createMouseListener(JButton button, int posX, int posY) { // TODO auslagern in Data
	    return new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (button.isEnabled()) {
	                Field field = data.getField(posX, posY);

	                if (SwingUtilities.isLeftMouseButton(e)) {
	                    leftclickEvent(button, field);
	                    Logger.info("links");
	                }
	                if (SwingUtilities.isRightMouseButton(e)) {
	                    rightclickEvent(button, field);
	                    Logger.info("rechts");
	                }
	                Logger.info(field.getFlagged().toString());
	            }
	        }
	    };
	}

	/**
	* processes rightclick
	* @param button the clicked JButton
	* @param field the corresponding Field
	*/
	private void rightclickEvent(JButton button, Field field) {
		Flag flag = Flag.NOFLAG;
		String text = "";


		if (field.getFlagged() == Flag.NOFLAG) { // es war keine Flag gesetzt
			if (data.placedFlags < data.amountOfMines) {
				data.placedFlags++;
				text = "/ ! \\";
				flag = Flag.FLAGGED;
			}
			else { // keine Flaggen mehr uebrig, platziere stattdessen "?"
				text = "/ ? \\";
				flag = Flag.QUESTIONMARK;
			}
		}
		else if (field.getFlagged() == Flag.FLAGGED) { // flag war bereits gesetzt
			text = "/ ? \\";
			flag = Flag.QUESTIONMARK;
			data.placedFlags--;
		}
				
		field.setText(text);
		field.setFlagged(flag);
		
		// TODO: get this working again (currently duplicated variable in MsGUI)
		MsGUI.statusLabel.setText("Platzierte Flaggen: "+data.placedFlags+" / "+data.amountOfMines);
		button.setText(field.getText());
		
		Logger.info("# platzierte Flaggen: " + data.placedFlags);
	}
	
	/**
	 * processes leftclick
	 * @param button the clicked JButton
	 * @param field the corresponding Field
	 */
	private void leftclickEvent(JButton button, Field field) { // TODO auslagern in Data
		
		AutoRevealer ar = new AutoRevealer(data);
		
		// prevent leftclick: FLAGGED fields will not be revealed
		if (field.getFlagged() == Flag.FLAGGED) {
			Logger.info("FLAGGED Feld wird nicht aufgedeckt");
			return;
		}
		
		// distribute bombs after initial click
		if (isFirstClick) {
			isFirstClick = false;
			BombDistributor bd = new BombDistributor(data, field);
			bd.distributeMines();
		}
		
		// if mine is revealed the game ends
		if (field.isMine()) {
			Logger.info("BOOOOOOOM");
			JOptionPane.showMessageDialog(null, "You Dead", "GAME OVER", JOptionPane.WARNING_MESSAGE);
		}
		
		// reveal field
		else {			
			Set<Field> revealedFields = ar.revealField(field);
			updateButtonsGUI(revealedFields);
		}
	}

	private void updateButtonsGUI(Set<Field> revealedFields) {
		for (Field changedField : revealedFields) {
			int posX = changedField.getPosX();
			int posY = changedField.getPosY();
			
			JButton changedButton = buttons[posX][posY];

			changedButton.setText(changedField.getText());
			changedButton.setEnabled(false);
			// [0] ist nicht der gesuchte Listener (sondern javax.swing.plaf.basic.BasicButtonListener)
			// [1] ist der gesuchte Listener
			changedButton.removeMouseListener(changedButton.getMouseListeners()[1]);
		}
	}
}
