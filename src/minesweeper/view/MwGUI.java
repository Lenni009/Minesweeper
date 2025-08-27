package minesweeper.view;

import java.awt.GridLayout;
import javax.swing.*;

import minesweeper.controller.Flag;
import minesweeper.controller.BombDistributor;
import minesweeper.controller.Field;
import minesweeper.model.MwData;
import minesweeper.utils.Logger;

import java.awt.event.*;

public class MwGUI extends JFrame {
	
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
	
	public MwGUI(MwData model) {
		this.data = model;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// JFrame-Konfiguration
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");
		this.setLayout(new GridLayout(data.fieldLen, data.fieldLen));
		
		data.generateMinefield(data.fieldLen);
		
		for (int x = 0; x < (data.fieldLen); ++x) {
			final int posX = x;
			for (int y = 0; y < (data.fieldLen); ++y) {
				final int posY = y;
	            JButton b = new JButton("");
	            b.addMouseListener(new MouseAdapter() {
	            	@Override
	                public void mouseClicked(MouseEvent e) {
	            		if(b.isEnabled()) {
		                    // TODO
	            			
	            			// entsprechendes Minen-Objekt holen
	            			Field m = data.getField(posX,posY);
	            			
		                	if (SwingUtilities.isLeftMouseButton(e)) {
		                		leftclickEvent(b, m);
		                		Logger.info("links");
		                	}
		                	if (SwingUtilities.isRightMouseButton(e)) {
		                		rightclickEvent(b,m);
		                		Logger.info("rechts");
		                	}
		                	Logger.info(m.getFlagged().toString());
	            		}
	                }
	            });
	            add(b);
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
		menueLeiste.add(dateiMenue);
		menueLeiste.add(onlineMenue);
		menueLeiste.add(helpMenue);
		dateiMenue.add(beenden);
		menueLeiste.add(sep);
		menueLeiste.add(flags_label);
		// zum Fenster hinzufuegen
		this.setJMenuBar(menueLeiste);
		

	}
	
	private void rightclickEvent(JButton button, Field field) {
		Flag flag = Flag.NOFLAG;
		String text = "";
		// TODO auslagern in Data
		if (field.getFlagged() == Flag.NOFLAG) { // es war keine Flag gesetzt
			if (data.placedFlags < data.amountOfMines) {
				data.placedFlags++;
				flags_label.setText("Platzierte Flaggen: "+data.placedFlags+" / "+data.amountOfMines);
				text = "/ ! \\";
				flag = Flag.FLAGGED;
			}
			else {
				// es muss "?" platziert werden
				text = "/ ? \\";
				flag = Flag.QUESTIONMARK;
			}
		}
		else if (field.getFlagged() == Flag.FLAGGED) { // flag war bereits gesetzt
			text = "/ ? \\";
			flag = Flag.QUESTIONMARK;
			data.placedFlags--;
			flags_label.setText("Platzierte Flaggen: "+data.placedFlags+" / "+data.amountOfMines);
		}
		field.setText(text);
		field.setFlagged(flag);
		button.setText(field.getText());
		Logger.info("# platzierte Flaggen: " + data.placedFlags);
	}
	
	private void leftclickEvent(JButton b, Field m) {
		// TODO auslagern in Data
		
		// Linksklick vereiteln: FLAGGED Feld wird nicht aufgedeckt
		if (m.getFlagged() == Flag.FLAGGED) {
			Logger.info("FLAGGED Feld wird nicht aufgedeckt");
			return;
		}
		
		// Nach dem ersten Klick werden die Bomben verteilt
		if (isFirstClick) {
			isFirstClick = false;
			BombDistributor bd = new BombDistributor(data);
			bd.distributeMines();
		}
		
		// Wenn eine Mine aufgedeckt wird, endet das Spiel 
		if (m.isMine()) {
			Logger.info("BOOOOOOOM");
			JOptionPane.showMessageDialog(null, "You Dead", "GAME OVER", JOptionPane.WARNING_MESSAGE);
		}
		
		// Feld aufdecken
		else {
			m.setClickable(false);
			data.countMineNeighbours(m.getPosX(),m.getPosY());
			m.setText(String.valueOf(m.getBombNeighbourCount()));
			b.setText(m.getText());
			b.setEnabled(false);
			// [0] ist nicht der gesuchte Listener (sondern javax.swing.plaf.basic.BasicButtonListener)
			// [1] ist der gesuchte Listener
			b.removeMouseListener(b.getMouseListeners()[1]);
		}
		
	}

}
