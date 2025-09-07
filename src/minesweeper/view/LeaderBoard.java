package minesweeper.view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LeaderBoard extends JPanel {

	public LeaderBoard() {

		this.setLayout(new GridLayout(0, 3));
		JLabel rankLabel = new JLabel("Platzierung");
		JLabel timeLabel = new JLabel("Zeit");
		JLabel nameLabel = new JLabel("Name");
		JTextField input = new JTextField();

		JLabel userRankLabel = new JLabel("1");
		JLabel userTimeLabel = new JLabel("10:00");

		this.add(rankLabel);
		this.add(timeLabel);
		this.add(nameLabel);
		this.add(userRankLabel);
		this.add(userTimeLabel);
		this.add(input);
	}
}
