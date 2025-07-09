package minesweeper;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;

public class MwGUI extends JFrame {
	
	// Menueleiste
	private JMenuBar menueLeiste;
	private JMenu dateiMenue;
	private JMenu onlineMenue;
	private JMenu helpMenue;
	private JMenuItem beenden;
	
	public MwGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// JFrame-Konfiguration
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");
		this.setLayout(new GridLayout(8, 8));
		
		// Testweise paar Buttons
		// https://stackoverflow.com/a/6390444
        for (int i = 0; i < 64; ++i) {
        	// Statt JButton sollte es "Feld" sein (custom 
            Mine b = new Mine(String.valueOf(i));
            b.addMouseListener(new MouseAdapter() {
            	@Override
                public void mouseClicked(MouseEvent e) {
                    // TODO
                	if (SwingUtilities.isLeftMouseButton(e)) {
                		leftclickEvent(e, b);
                		System.out.println("links");
                	}
                	if (SwingUtilities.isRightMouseButton(e)) {
                		rightclickEvent(e,b);
                		System.out.println("rechts");
                	}
                	System.out.println(b.getFlagged());
                	
                }

				private void rightclickEvent(MouseEvent e, Mine b) {
					// TODO auslagern in Data
					if (b.getFlagged() == Flag.NOFLAG) { // flag bereits gesetzt
						b.setVisibleText("");
						b.setFlagged(Flag.FLAGGED);
					}
					else if (b.getFlagged() == Flag.FLAGGED) {
						b.setVisibleText("!");
						b.setFlagged(Flag.QUESTIONMARK);
					}
					else {
						b.setVisibleText("?");
						b.setFlagged(Flag.NOFLAG);
					}
					
				}

				private void leftclickEvent(MouseEvent e, Mine b) {
					// TODO auslagern in Data
					if (b.getMine()) {
						System.out.println("BOOOOOOOM");
						JOptionPane.showMessageDialog(null, "You Dead", "GAME OVER", JOptionPane.WARNING_MESSAGE);
					}
					else {
						b.setVisibleText(String.valueOf(b.getBombNeighbourCount()));
					}
					
				}
            });
            add(b);
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

}
