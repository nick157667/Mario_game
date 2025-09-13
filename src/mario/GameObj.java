package mario;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameObj {
    
    /** Current position of the object (in terms of graphics coordinates)
     *  
     * Coordinates are given by the upper-left hand corner of the object.
     * This position should always be within bounds.
     *  0 <= pos_x <= max_x 
     *  0 <= pos_y <= max_y 
     */
    public int pos_x; 
    public int pos_y;

    /** Size of object, in pixels */
    public int width;
    public int height;
    
    /** Velocity: jber of pixels to move every time move() is called */
    public double v_x;
    public double v_y;

    /** Upper bounds of the area in which the object can be positioned.  
     *    Maximum permissible x, y positions for the upper-left 
     *    hand corner of the object
     */
    public int max_x;
    public int max_y; 
    
    public int court_width;
    public int court_height;
    
    public Direction direction;
     
    public boolean standOn = false; // mario ever stand on the stair until mario exit
    public boolean onGround = true;
    
    protected BufferedImage img;
    /*
     BufferedImage 是 Java 提供的一個圖像類，用於在記憶體中儲存圖像的像素數據。它是 java.awt.image 包的一部分，可以用於創建、操作和處理圖像。以下是 BufferedImage 的一些主要用途和功能：

	主要用途
		創建圖像：
			可以使用 BufferedImage 創建空白圖像，然後在其上繪製圖形或文字。
		加載圖像：
			可以從文件、URL 或輸入流中加載圖像到 BufferedImage 對象。
		操作圖像：
			可以操作圖像的像素數據，進行各種圖像處理操作，如旋轉、縮放、裁剪、過濾等。
		顯示圖像：
			可以將 BufferedImage 顯示在 Swing 組件（如 JPanel 或 JLabel）中。
		保存圖像：
			可以將 BufferedImage 保存為文件（如 PNG、JPEG 格式）。
     */
    /**
     * Constructor
     */
    public GameObj(int v_x, int v_y, int pos_x, int pos_y, int max_x, int max_y, 
        int width, int height, int court_width, int court_height, Direction direction) {
        this.v_x = v_x;
        this.v_y = v_y;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.max_x = max_x;
        this.max_y = max_y;
        this.width = width;
        this.height = height;
        this.court_width = court_width;
        this.court_height = court_height;
        
        this.direction = direction;
    }
    
    /**
     * Moves the object by its velocity.  Ensures that the object does
     * not go outside its bounds by clipping.
     */
    public abstract void move();
    
    public abstract void handleOffScreen();
    
    /**
     * Determine whether this game object is currently intersecting
     * another object.
     * 
     * Intersection is determined by comparing bounding boxes. If the 
     * bounding boxes overlap, then an intersection is considered to occur.
     * 
     * @param obj : other object
     * @return whether this object intersects the other object.
     */
    
    public void standOnObj(GameObj[] obj, int[] serial, GameObj mario) { // for mario
    	
    	int num = 0;
    	for(int j = 0; j < obj.length; j ++) {
    		
    		num++;
    		int i = 0;
    		for(; i < serial.length; i ++) {
        		if(num == serial[i])
        			break;
        	}
        	if(i == serial.length)
        		continue;

        	if(mario.onGround) {
        		Mario1.MAX_HEIGHT = 225;
            	Mario2.MAX_HEIGHT = 225;
            	Mario3.MAX_HEIGHT = 225;
            	mario.max_y = GameCourt1.COURT_HEIGHT - mario.height - GroundTile.SIZE;
        	}
        	
        	if (obj[j].intersectsTop(mario)) {
        		
        		
        		obj[j].standOn = true;
            	Mario1.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
            	Mario2.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
            	Mario3.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
            	
            	mario.max_y = obj[j].pos_y - mario.height;
            	break;
        	}
        	
        	if(obj[j].standOn) { 
          	   
        	   	// judge whether mario in the upper space of stair
        		if(j != (obj.length - 1)) {
        			
        			if(obj[j].pos_x + HardBlock.INIT_WIDTH == obj[j + 1].pos_x) {
        				if(mario.pos_x + mario.width <= obj[j].pos_x + HardBlock.INIT_WIDTH && mario.pos_x + mario.width > obj[j].pos_x) { 
		            		
		            		Mario1.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
		            		Mario2.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
		            		Mario3.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
		                	mario.max_y = obj[j].pos_y - mario.height;
		                	break;
		            	}
		            	else {
		            		obj[j].standOn = false;
		            	}
        			}
        			else {
        				if(mario.pos_x <= obj[j].pos_x + HardBlock.INIT_WIDTH && mario.pos_x + mario.width >= obj[j].pos_x) { 
                    		
                    		Mario1.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
                    		Mario2.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
                    		Mario3.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
                        	mario.max_y = obj[j].pos_y - mario.height;
                        	break;
                    	}
                    	else {
                    		obj[j].standOn = false;
                    	}
        			}
        				
        		}else {
        			if(mario.pos_x <= obj[j].pos_x + HardBlock.INIT_WIDTH && mario.pos_x + mario.width >= obj[j].pos_x) { 
                		
                		Mario1.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
                		Mario2.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
                		Mario3.MAX_HEIGHT = 225 - (GameCourt1.COURT_HEIGHT - GroundTile.SIZE - obj[j].pos_y);
                    	mario.max_y = obj[j].pos_y - mario.height;
                    	break;
                	}
                	else {
                		obj[j].standOn = false;
                	}
        		} 	
            }
        	
    	}
    	
    	for (int j = 0; j < obj.length; j ++) {
        	if(!obj[j].intersectsTop(mario)) {
        		if(obj[j].intersectsRight(mario)) {
            		mario.pos_x = obj[j].pos_x + obj[j].width;
            	}
            	else if(obj[j].intersectsLeft(mario)) {
            		mario.pos_x = obj[j].pos_x - mario.width;
            	}
        	}	
        }
    }
    
    
    public boolean intersects(GameObj obj) {
        return (intersectsTop(obj) || intersectsBottom(obj) ||
                intersectsRight(obj) || intersectsLeft(obj));
    }
    
    public boolean intersectsTop(GameObj obj) {
        return (obj.pos_y + obj.height >= pos_y
                && obj.pos_y + obj.height <= pos_y + ((double) height / 2)
                && obj.pos_x + obj.width >= pos_x
                && obj.pos_x <= pos_x + width
                );
    }
    
    public boolean intersectsBottom(GameObj obj) {
        return (obj.pos_y <= pos_y + height
                && obj.pos_y > pos_y + ((double) height / 2)
                && obj.pos_x + obj.width >= pos_x
                && obj.pos_x <= pos_x + width
                );
    }
    
    public boolean intersectsRight(GameObj obj) {
        return (obj.pos_x <= pos_x + width
                && obj.pos_x >= pos_x + ((double) width / 2)
                && obj.pos_y + obj.height >= pos_y
                && obj.pos_y <= pos_y + height
                );
    }
    
    public boolean intersectsLeft(GameObj obj) {
        return (obj.pos_x + obj.width >= pos_x
                && obj.pos_x + obj.width < pos_x + ((double) height / 2)
                && obj.pos_y + obj.height >= pos_y
                && obj.pos_y <= pos_y + height
                );
    }
    
    public boolean collides(GameObj obj) {
        return (collidesTop(obj) || collidesBottom(obj) || collidesLeft(obj) || collidesRight(obj));
    }
    
    public boolean collidesTop(GameObj obj) {
        return (obj.pos_y + obj.height == pos_y
                && obj.pos_x + obj.width >= pos_x
                && obj.pos_x <= pos_x + width
                );
    }
    
    public boolean collidesBottom(GameObj obj) {
        return (obj.pos_y == pos_y + height
                && obj.pos_x + obj.width >= pos_x
                && obj.pos_x <= pos_x + width
                );
    }
    
    public boolean collidesLeft(GameObj obj) {
        return (obj.pos_x + obj.width == pos_x
                && obj.pos_y + obj.height >= pos_y
                && obj.pos_y <= pos_y + height
                );
    }
    
    public boolean collidesRight(GameObj obj) {
        return (obj.pos_x == pos_x + width
                && obj.pos_y + obj.height >= pos_y
                && obj.pos_y <= pos_y + height
                );
    }
    
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }
}
