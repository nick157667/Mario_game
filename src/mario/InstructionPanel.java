package mario;
import javax.swing.*;
import java.awt.*;
public class InstructionPanel extends JPanel {
	private final static int pos_x = 200;
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		new Font(pos_x,100,"> is move right.").draw(g);
		new Font(pos_x, 150,"< is move left.").draw(g);
		new Font(pos_x, 200, "'").drawRotate(g);//up png
		new Font(pos_x+8,200," is jump.").draw(g);
		new Font(pos_x,250,"3").drawRotate(g);
		new Font(pos_x+8,250," is down.").draw(g);
	}
}
