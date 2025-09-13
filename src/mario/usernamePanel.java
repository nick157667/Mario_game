package mario;

import javax.swing.*;

import mario.Font;

import java.awt.*;

public class usernamePanel extends JPanel {
	private String name = "unnamed";

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		new Font(260, 200, name).draw(g);
	}

	public void getText(String text) {
		name = text;
		Graphics g = super.getGraphics();
		repaint();
		new Font(200, 200, name).draw(g);
	}
}
