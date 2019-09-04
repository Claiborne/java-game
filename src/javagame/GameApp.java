package javagame;

import javax.swing.JFrame;

public class GameApp {

	public static void main(String[] args) {
		JFrame ui = new JFrame();
		Gameplay game = new Gameplay();
		ui.setBounds(10, 10, 700, 600);
		ui.setTitle("Java Game");
		ui.setResizable(false);
		ui.setVisible(true);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.add(game);
	}
}
