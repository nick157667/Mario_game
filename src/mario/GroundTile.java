package mario;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GroundTile extends GameObj {
    
    public static String img_file = "Ground.gif";
    public static final int SIZE = 32;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    public static int vel_x;
    
    // Ground image
    //private static BufferedImage img;
    
    public GroundTile(int courtWidth, int courtHeight, int initX, int initY) {
        super(INIT_VEL_X, //v_x
        		INIT_VEL_Y, //v_y
        		initX, //pos_x
        		initY, //pos_y
        		courtWidth - SIZE, //max_x
        		courtHeight - SIZE, //max_y
                SIZE, //width
                SIZE, //height
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
    public void move() {
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
