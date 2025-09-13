package mario;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.LinkedList;

public class Brick extends GameObj{
	
	public static String img_file = "bricks.gif";
    
	public static final int INIT_WIDTH = 32;
    public static final int INIT_HEIGHT = 32;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
	
    public static int vel_x;
    public boolean onStair = false; // mario ever stand on the stair until mario exit 
    public Brick(int courtWidth, int courtHeight, int initX, int initY) {
        super(INIT_VEL_X, //v_x
        		INIT_VEL_Y, //v_y
        		initX, //pos_x
        		initY, //pos_y
        		courtWidth - INIT_WIDTH , //max_x
        		courtHeight - INIT_HEIGHT, //max_y
        		INIT_WIDTH , //width
        		INIT_HEIGHT, //height
                courtWidth, //court_height
                courtHeight, //court_height
                Direction.LEFT); //direction
        
        try {
            if (img == null) img = ImageIO.read(new File(img_file));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
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
