package mario;
import javax.swing.*;
import java.awt.*;
import java.util.List;
public class MyPanel extends JPanel {
	private List<HighScore> highScoreList;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (highScoreList.size() != 0) {
            int i = 1;
            for (HighScore h : highScoreList) {
            	new Font(220, 10, "High Scores:").draw(g);
            	new Font(200, 10+i*30, i + ". " + h.getUsername() + " " + h.getScore()).draw(g);
                i++;
            }
		}
	}
	
	public void setHighScoreList(HighScores hs) {
		highScoreList = hs.getTopTenHighScores();
	}
}
