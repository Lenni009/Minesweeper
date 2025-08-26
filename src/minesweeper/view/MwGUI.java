package minesweeper.view;

import java.awt.GridLayout;
import javax.swing.*;

import minesweeper.controller.Flag;
import minesweeper.controller.BombDistributor;
import minesweeper.controller.Field;
import minesweeper.model.MwData;

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
	            			Field m = data.getMine(posX,posY);
	            			
		                	if (SwingUtilities.isLeftMouseButton(e)) {
		                		leftclickEvent(b, m);
		                		System.out.println("links");
		                	}
		                	if (SwingUtilities.isRightMouseButton(e)) {
		                		rightclickEvent(b,m);
		                		System.out.println("rechts");
		                	}
		                	System.out.println(m.getFlagged());
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
		// alles zuordnen
		menueLeiste.add(dateiMenue);
		menueLeiste.add(onlineMenue);
		menueLeiste.add(helpMenue);
		dateiMenue.add(beenden);
		// zum Fenster hinzufuegen
		this.setJMenuBar(menueLeiste);
		

	}
	
	private void rightclickEvent(JButton b, Field m) {
		// TODO auslagern in Data
		if (m.getFlagged() == Flag.NOFLAG) { // flag bereits gesetzt
			m.setText("!");
			m.setFlagged(Flag.FLAGGED);
		}
		else if (m.getFlagged() == Flag.FLAGGED) {
			m.setText("?");
			m.setFlagged(Flag.QUESTIONMARK);
		}
		else {
			m.setText("");
			m.setFlagged(Flag.NOFLAG);
		}
		b.setText(m.getText());
		
	}
	
	private void leftclickEvent(JButton b, Field m) {
		// TODO auslagern in Data
		
		if (isFirstClick) {
			isFirstClick = false;
			BombDistributor bd = new BombDistributor(data);
			bd.distributeMines();
		}
		
		if (m.isMine()) {
			System.out.println("BOOOOOOOM");
			JOptionPane.showMessageDialog(null, "You Dead", "GAME OVER", JOptionPane.WARNING_MESSAGE);
		}
		else {
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
