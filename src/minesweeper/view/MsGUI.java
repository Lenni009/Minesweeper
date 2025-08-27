package minesweeper.view;

import java.awt.GridLayout;
import javax.swing.*;

import minesweeper.controller.Flag;
import minesweeper.controller.BombDistributor;
import minesweeper.controller.Field;
import minesweeper.model.MwData;
import minesweeper.utils.Logger;

import java.awt.event.*;

public class MsGUI extends JFrame {
	
	private MwData data;
	
	private boolean isFirstClick = true; 
		
	// Menueleiste
	private JMenuBar menueLeiste;
	private JMenu dateiMenue;
	private JMenu onlineMenue;
	private JMenu helpMenue;
	private JMenuItem beenden;
	private JLabel flags_label;
	private JSeparator sep;
	
	public MsGUI(MwData model) {
		this.data = model;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// JFrame-Konfiguration
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");
		this.setLayout(new GridLayout(data.fieldLen, data.fieldLen));
		
		data.generateMinefield(data.fieldLen);
		
		for (int x = 0; x < data.fieldLen; ++x) {
		    final int posX = x;
		    for (int y = 0; y < data.fieldLen; ++y) {
		        final int posY = y;
		        JButton button = new JButton("");
		        button.addMouseListener(createMouseListener(button, posX, posY));
		        add(button);
		    }
		}

		// Menue
		menueLeiste = new JMenuBar();
		dateiMenue = new JMenu("Datei");
		beenden = new JMenuItem("Beenden");
		beenden.addActionListener(e -> {
			System.exit(0);
		});
		onlineMenue = new JMenu("Online");
		helpMenue = new JMenu("Hilfe");
		sep = new JSeparator();
		flags_label = new JLabel("Platzierte Flaggen: "+data.placedFlags+" / "+data.amountOfMines);
		// alles zuordnen
		dateiMenue.add(beenden);
		menueLeiste.add(dateiMenue);
		menueLeiste.add(onlineMenue);
		menueLeiste.add(helpMenue);
		menueLeiste.add(sep);
		menueLeiste.add(flags_label);
		// zum Fenster hinzufuegen
		this.setJMenuBar(menueLeiste);
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
	private void rightclickEvent(JButton button, Field field) { // TODO auslagern in Data
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
		
		flags_label.setText("Platzierte Flaggen: "+data.placedFlags+" / "+data.amountOfMines);
		button.setText(field.getText());
		
		Logger.info("# platzierte Flaggen: " + data.placedFlags);
	}
	
	/**
	 * processes leftclick
	 * @param button the clicked JButton
	 * @param field the corresponding Field
	 */
	private void leftclickEvent(JButton button, Field field) { // TODO auslagern in Data
		
		// Linksklick vereiteln: FLAGGED Feld wird nicht aufgedeckt
		if (field.getFlagged() == Flag.FLAGGED) {
			Logger.info("FLAGGED Feld wird nicht aufgedeckt");
			return;
		}
		
		// Nach dem ersten Klick werden die Bomben verteilt
		if (isFirstClick) {
			isFirstClick = false;
			BombDistributor bd = new BombDistributor(data, field);
			bd.distributeMines();
		}
		
		// Wenn eine Mine aufgedeckt wird, endet das Spiel 
		if (field.isMine()) {
			Logger.info("BOOOOOOOM");
			JOptionPane.showMessageDialog(null, "You Dead", "GAME OVER", JOptionPane.WARNING_MESSAGE);
		}
		
		// Feld aufdecken
		else {
			field.setClickable(false);
			data.countMineNeighbours(field.getPosX(),field.getPosY());
			field.setText(String.valueOf(field.getBombNeighbourCount()));
			button.setText(field.getText());
			button.setEnabled(false);
			// [0] ist nicht der gesuchte Listener (sondern javax.swing.plaf.basic.BasicButtonListener)
			// [1] ist der gesuchte Listener
			button.removeMouseListener(button.getMouseListeners()[1]);
		}
		
	}

}
