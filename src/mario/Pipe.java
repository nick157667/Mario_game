package mario;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pipe extends GameObj {
    
    public static String[] img_file = {"PipeConnection.png","PipeBottom.png","Pipe2.png"};
    public static final int[] SIZE_X = {96,48,48};
    public static final int[] SIZE_Y = {48,24,125};
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    public static int vel_x;
    // Ground image
    //private static BufferedImage img;
    
    public Pipe(int courtWidth, int courtHeight, int initX, int initY,int n) {
        super(INIT_VEL_X, //v_x
        		INIT_VEL_Y, //v_y
        		initX, //pos_x
        		initY, //pos_y
        		courtWidth - SIZE_X[n], //max_x
        		courtHeight - SIZE_Y[n], //max_y
        		SIZE_X[n], //width
        		SIZE_Y[n], //height
                courtWidth, //court_height
                courtHeight, //court_height
                Direction.LEFT); //direction
        
        try {
            if (img == null) img = ImageIO.read(new File(img_file[n]));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    public Pipe(int courtWidth, int courtHeight, int startDistance) {
    	/*public GameObj(int v_x, int v_y, int pos_x, int pos_y, int max_x, int max_y, int width, int height, int court_width, int court_height, Direction direction)*/
        super(INIT_VEL_X, //v_x
        		INIT_VEL_Y, //v_y
        		startDistance, //pos_x
                courtHeight /*- GroundTile.SIZE*/ - SIZE_Y[2], //pos_y
                courtWidth, //max_x
                courtHeight, //max_y
                SIZE_X[2], //width
                SIZE_Y[2], //height
                courtWidth, //court_width
                courtHeight, //court_height
                Direction.LEFT); //direction
        
        try {
            if (img == null) img = ImageIO.read(new File(img_file[2]));
        } catch (IOException e) {
            System.out.println("Internal Error: " + e.getMessage());
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
    
    public boolean intersectsEndPipe(GameObj obj) {
        return (obj.pos_x + obj.width >= pos_x + width /2
                && obj.pos_y + obj.height == pos_y + height
                && obj.pos_x < pos_x + width);
    }

}
