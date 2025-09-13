package mario;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.LinkedList;

public class Stairs extends GameObj{
	
	public static String img_file = "bricks.gif";
    
	public static final int INIT_WIDTH = 32;
    public static final int INIT_HEIGHT = 32;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
	
    public static int vel_x;
    public boolean onStair = false; // mario ever stand on the stair until mario exit 
    		
    public Stairs(int courtWidth, int courtHeight, int startDistance, int num_bricks_high) {
    	super(INIT_VEL_X, // v_x
    			INIT_VEL_Y, // v_y
    			startDistance,// pos_x
                courtHeight - GroundTile.SIZE - (num_bricks_high * INIT_HEIGHT), // pos_y
                courtWidth - INIT_WIDTH, // max_x
                courtHeight - INIT_HEIGHT, // max_y
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
    public void move(){
    	if (vel_x < 0) direction = Direction.LEFT;
        if (vel_x > 0) direction = Direction.RIGHT;
        
        pos_x += vel_x;
        
        handleOffScreen();     
    }
    
    @Override
    public void handleOffScreen() {
    	if (pos_y < 0) pos_y = 0;
        else if (pos_y > max_y) pos_y = max_y; 
    }
     
}