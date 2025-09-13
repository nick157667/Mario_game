package mario;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Coin extends GameObj {
    
    public static String[] img_files = {"Coin.gif", "Coin.gif", "Coin.gif", "Coin_2.gif", "Coin_2.gif",
            "Coin_2.gif", "Coin_3.gif", "Coin_3.gif", "Coin_3.gif", "Coin_4.gif", "Coin_4.gif",
            "Coin_4.gif"};
    
    public int startDistance; // Where the coin should start
    public static int INIT_VEL_X = 0;
    public static int INIT_VEL_Y = 0;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 28;
    public static final int START_HEIGHT = 250;
    
    private BufferedImage[] imgs;
    
    public static int vel_x;
    
    public Coin(int courtWidth, int courtHeight, int startDistance) {
        super(INIT_VEL_X, //v_x
        		INIT_VEL_Y, //v_y
        		startDistance, //pos_y
        		START_HEIGHT, //pos_y
        		courtWidth, //max_x
        		courtHeight, //max_y
        		WIDTH, //width
                HEIGHT, //height
                courtWidth, //court_width
                courtHeight, //court_height
                Direction.LEFT); //direction
        
        try {
            imgs = new BufferedImage[img_files.length];
            for (int i = 0; i < img_files.length; i++) {
                imgs[i] = ImageIO.read(new File(img_files[i]));
            }
            if (img == null) img = imgs[0];
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    public Coin(int courtWidth, int courtHeight, int startDistance, int startheight) {
        super(INIT_VEL_X, //v_x
        		INIT_VEL_Y, //v_y
        		startDistance, //pos_y
        		startheight, //pos_y
        		courtWidth, //max_x
        		courtHeight, //max_y
        		WIDTH, //width
                HEIGHT, //height
                courtWidth, //court_width
                courtHeight, //court_height
                Direction.LEFT); //direction
        
        try {
            imgs = new BufferedImage[img_files.length];
            for (int i = 0; i < img_files.length; i++) {
                imgs[i] = ImageIO.read(new File(img_files[i]));
            }
            if (img == null) img = imgs[0];
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
    
    public void spinCoin() {
        // Loop through the images & spin the coins
        for (int i = 0; i < imgs.length; i++) {
            if (i == imgs.length - 1 && img.equals(imgs[i])) {
                img = imgs[0];
                break;
            } else if (img.equals(imgs[i])) {
                img = imgs[i + 1];
                break;
            }
        }
    }


}
