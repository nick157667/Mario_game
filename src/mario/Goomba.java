package mario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Goomba extends Enemy {
    
    private static String[] img_files = {"Goomba.gif", "Goomba.gif", "Goomba2.gif", "Goomba2.gif"};
    private static String dead_file = "Goomba_dead.png";
    public static int INIT_WIDTH = 32;
    public static int INIT_HEIGHT = 32;
    
    public Goomba(int courtWidth, int courtHeight, int startDistance, String label) {
    	// public Enemy(int courtWidth, int courtHeight, int initWidth, int initHeight, int startDistance,String[] img_files)
        super(courtWidth, courtHeight, INIT_WIDTH, INIT_HEIGHT, startDistance, img_files,dead_file, label); 
    }
    
    @Override
    public int incrementScore(int score) {
        return score += 100;
    }
}
