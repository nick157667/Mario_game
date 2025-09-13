package mario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Mario3 extends GameObj {
    // Image file names for different states in Mario's motion
    public static String standing_rt_img_file = "Mario_Standing.gif";
    public static String standing_lt_img_file = "Mario_StandingL.gif";
    public static String jumping_rt_img_file = "Mario_Jump.gif";
    public static String jumping_lt_img_file = "Mario_JumpL.gif";
    public static String[] walking_rt_img_files = {"Mario_Walk_1.gif", "Mario_Walk_1.gif", 
            "Mario_Walk_2.gif", "Mario_Walk_2.gif", "Mario_Walk_3.gif", "Mario_Walk_3.gif"};
    public static String[] walking_lt_img_files = {"Mario_Walk_1L.gif", "Mario_Walk_1L.gif", 
            "Mario_Walk_2L.gif", "Mario_Walk_2L.gif", "Mario_Walk_3L.gif", "Mario_Walk_3L.gif"};
    public static String dead_file = "Mario_Dead.gif";
    
    public static final int INIT_WIDTH = 26;
    public static final int INIT_HEIGHT = 32;
    public static final int INIT_X = 8;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static int MAX_HEIGHT = 225;
    
    // Boolean values to assist in Mario's jumping
    public boolean reachedMaxHeight = false;
    //public boolean onGround = true;
    public boolean gravityOn = false;
    public boolean dead = false;
    public boolean slideToBottom = false;
    public boolean touch_flag_or_flagpole = false;
    
    // Images used to display different states of Mario's motion
    //private static BufferedImage img; // image currently being used
    private static BufferedImage standing_rt_img;
    private static BufferedImage standing_lt_img;
    private static BufferedImage jumping_rt_img;
    private static BufferedImage jumping_lt_img;
    private static BufferedImage[] walking_rt_imgs;
    private static BufferedImage[] walking_lt_imgs;
    private static BufferedImage dead_img;
    
    // Constructor that takes in the courtWidth & courtHeight
    public Mario3(int courtWidth, int courtHeight) {
        super(INIT_VEL_X,  //v_x
        		INIT_VEL_Y, //v_y
        		INIT_X,  //pos_x
        		courtHeight - INIT_HEIGHT - Pipe.SIZE_Y[2], //pos_y
                courtWidth - INIT_WIDTH, //max_x
                courtHeight - INIT_HEIGHT - GroundTile.SIZE, //max_y
                INIT_WIDTH, //width
                INIT_HEIGHT, //height
                courtWidth, //court_width
                courtHeight, //court_height
                Direction.RIGHT); //direction
        
        // Initializes all of the images
        try {
            if (img == null) {
                standing_rt_img = ImageIO.read(new File(standing_rt_img_file));
                standing_lt_img = ImageIO.read(new File(standing_lt_img_file));
                jumping_rt_img = ImageIO.read(new File(jumping_rt_img_file));
                jumping_lt_img = ImageIO.read(new File(jumping_lt_img_file));
                walking_rt_imgs = new BufferedImage[walking_rt_img_files.length];
                for (int i = 0; i < walking_rt_img_files.length; i++) {
                    walking_rt_imgs[i] = ImageIO.read(new File(walking_rt_img_files[i]));
                }
                walking_lt_imgs = new BufferedImage[walking_lt_img_files.length];
                for (int i = 0; i < walking_lt_img_files.length; i++) {
                    walking_lt_imgs[i] = ImageIO.read(new File(walking_lt_img_files[i]));
                }
                dead_img = ImageIO.read(new File(dead_file));
                img = standing_rt_img;
            }            
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    @Override
    public void move() {
        if (dead) {
            img = dead_img;
            v_x = 0;
            pos_y += 3;
            return;
        }
        
   
        // Changes the direction based on the direction of the velocity
        if (v_x < 0) direction = Direction.LEFT;
        else if (v_x > 0) direction = Direction.RIGHT;
        
        // Increment the distance travlled by Mario's velocity
        GameCourt3.distanceTravelled += v_x;
        
        // Increment Mario's position
        if (direction == Direction.LEFT || GameCourt3.endTile
                || (direction == Direction.RIGHT && pos_x + width < GameCourt3.MAX_MARIO_X)) 
            pos_x += v_x;
        
        // If Mario is currently in the air, increase his position by the
        // velocity until he reaches the maximum height, then decrease his
        // position by the velocity
        //gravity false就不能移動了
        if (gravityOn) {
            if (!reachedMaxHeight) pos_y += v_y;
            else pos_y -= v_y;
        }
        else if(pos_y < max_y) {
        	pos_y += GameCourt3.MARIO_Y_VELOCITY;
        }
        	

        handleOffScreen();
        
        // Determine if Mario has reached his maximum height
        if (pos_y <= MAX_HEIGHT) reachedMaxHeight = true;
        else if (pos_y == max_y) { 
        	reachedMaxHeight = false;
        	onGround = true;
        }
        // Turn gravity off once Mario hits the ground
        if (gravityOn && pos_y == max_y) {
            gravityOn = false;
            v_y = 0;
        }
        
        // Determine which image of Mario should be displayed based on his
        // current movement
        if (v_x == 0 && v_y == 0) {
            if (direction == Direction.RIGHT) img = standing_rt_img;
            else img = standing_lt_img;
        } else if (gravityOn) {
            if (direction == Direction.RIGHT) img = jumping_rt_img;
            else img = jumping_lt_img;
        } else {
            BufferedImage[] walkingDirection;
            BufferedImage[] oppositeDirection;
            if (direction == Direction.RIGHT) {
                walkingDirection = walking_rt_imgs;
                oppositeDirection = walking_lt_imgs;
            } else {
                walkingDirection = walking_lt_imgs;
                oppositeDirection = walking_rt_imgs;
            }
            if (img.equals(standing_rt_img) || img.equals(standing_lt_img) ||
                    img.equals(jumping_rt_img) || img.equals(jumping_lt_img)) {
                img = walkingDirection[0];
            } else {
                for (int i = 0; i < walkingDirection.length; i++) {
                    if ((i == walkingDirection.length - 1 && img.equals(walkingDirection[i])) ||
                            img.equals(oppositeDirection[i])) {
                        img = walkingDirection[0];
                        break;
                    } else if (img.equals(walkingDirection[i])) {
                        img = walkingDirection[i+1];
                        break;
                    }
                }
            }
        }
        
        // Reset Mario's max_y after changing the image
        //max_y = court_height - GroundTile.SIZE - img.getHeight();
        
        
    }

    public void movedown() {
    	pos_y += GameCourt3.MARIO_Y_VELOCITY ;
    	handleOffScreen();
    }
    
    @Override
    public void handleOffScreen() {
        if (pos_x < 0) pos_x = 0;
        else if (pos_x > max_x) pos_x = max_x;
        
        if (pos_y < 0) pos_y = 0;
        else if (pos_y > max_y) pos_y = max_y;
    }  
}
