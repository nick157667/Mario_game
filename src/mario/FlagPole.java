package mario;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FlagPole extends GameObj{

	public static String img_file = "FlagPole.png";
    
    public static final int INIT_WIDTH = 24;
    public static final int INIT_HEIGHT = 240;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    public int startDistance;
    
    public FlagPole(int courtWidth, int courtHeight, int startDistance) {
    	super(INIT_VEL_X, // v_x
    			INIT_VEL_Y, // v_y
    			startDistance,// pos_x
                courtHeight - GroundTile.SIZE - INIT_HEIGHT, // pos_y
                courtWidth - INIT_WIDTH, // max_x
                courtHeight - GroundTile.SIZE, // max_y
                INIT_WIDTH,// width
                INIT_HEIGHT, // height
                courtWidth, // court_width
                courtHeight, // court_height
                Direction.LEFT); // direction
        try {
            if (img == null) img = ImageIO.read(new File(img_file));
        } catch (IOException e) {
            System.out.println("Internal Error: " + e.getMessage());
        }
    }
    
    @Override
    public void move() {
        if (v_x < 0) direction = Direction.LEFT;
        if (v_x > 0) direction = Direction.RIGHT;
        
        pos_x += v_x;
        
        handleOffScreen();        
    }
       
    @Override
    public void handleOffScreen() {
        if (pos_y < 0) pos_y = 0;
        else if (pos_y > max_y) pos_y = max_y; 
    }
      
}
