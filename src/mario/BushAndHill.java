package mario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BushAndHill extends GameObj{
	public String[] img_files = {"Bush1.png","Bush2.png","Bush3.png","Hill1.png","Hill2.png"};
    public static int INIT_VEL_X = 0;
    public static int INIT_VEL_Y = 0;
    public static final int [] SIZE_X = {32,48,64,76,137};
    public static final int [] SIZE_Y = {32,32,32,30,60};

    public static int vel_x;
    private BufferedImage imgs;
    
	public BushAndHill(int courtWidth,int courtHeight,int initX,int n) 
	{
		super(INIT_VEL_X, //v_x
				INIT_VEL_Y, //v_y
				initX, //pos_x
				courtHeight - SIZE_Y[n] -GroundTile.SIZE, //pos_y
				courtWidth - SIZE_X[n], //max_x
				courtHeight - SIZE_Y[n] -GroundTile.SIZE, //max_y
				SIZE_X[n], //width
				SIZE_Y[n], //height
				courtWidth, //court_height
				courtHeight, //court_height
				Direction.LEFT); //direction
        try {
        	imgs = ImageIO.read(new File(img_files[n]));
        	if (img == null) 
        	{
        		img = imgs;
        		//System.out.println("Image loading failed for BushAndHill");
        	}
        	else
        		img =imgs;
        		
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
