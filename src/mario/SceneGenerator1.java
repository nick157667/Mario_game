package mario;
import java.util.LinkedList;
import java.util.Random;

public class SceneGenerator1 {
    private GroundTile[] ground; // array of ground tiles
    private LinkedList<Brick> bricks;
    private LinkedList<Enemy> enemies;
    private LinkedList<Coin> coins;
    private LinkedList<Cloud> clouds;
    private LinkedList<BushAndHill> bushAndHill;
    private Random r = new Random();
    
    public SceneGenerator1(int groundLength, int courtWidth, int courtHeight) {
        // Create the ground array based on the length of the ground
        ground = new GroundTile[groundLength];
        for (int i = 0; i < groundLength; i++) {
            ground[i] = new GroundTile(courtWidth, courtHeight, GroundTile.SIZE * i, 
                    courtHeight - GroundTile.SIZE);
        }
        bricks = new LinkedList<Brick>();
        for(int j = 0 ; j < 13 ; j ++) {
    		bricks.add(new Brick(courtWidth, courtHeight, (groundLength-2)* GroundTile.SIZE  , j*Brick.INIT_HEIGHT)); // 樓梯從倒數第20格開始
    	}	
        
        // Randomly place coins in groups of 3 along the screen
        coins = new LinkedList<Coin>();
        int i = 400;
        while (i < groundLength * GroundTile.SIZE - courtWidth) {
            coins.add(new Coin(courtWidth, courtHeight, i));
            coins.add(new Coin(courtWidth, courtHeight, i + 2 * Coin.WIDTH));
            coins.add(new Coin(courtWidth, courtHeight, i + 4 * Coin.WIDTH));
            i += r.nextInt((250 - 100) + 1) + 100;
        }
        
        // Randomly place enemies, first a goomba, then a koopa troop
        enemies = new LinkedList<Enemy>();
        i = 450;
        while (i < groundLength * GroundTile.SIZE - courtWidth) {
            enemies.add(new Goomba(courtWidth, courtHeight, i, "Goomba"));
            i += r.nextInt((350 - 200) + 1) + 200;
            enemies.add(new GreenKoopaTroop(courtWidth, courtHeight, i, "GreenKoopaTroop"));
            i += r.nextInt((350 - 200 + 1) + 200);
        }
        
        clouds =new LinkedList<Cloud>();
        int x=0,y=0,n=0;
        while(x< groundLength * GroundTile.SIZE - courtWidth/2) {
        	x += r.nextInt(151)+100;
        	y = r.nextInt(100)+75;
        	n = r.nextInt(3);
        	clouds.add(new Cloud(courtWidth,courtHeight,x,y,n));
        }
        
        bushAndHill = new LinkedList<BushAndHill>();
        int tmp=0;
        int m=4;
        while(tmp < groundLength * GroundTile.SIZE - courtWidth/2) {
        	bushAndHill.add(new BushAndHill(courtWidth,courtHeight,tmp,m));
        	tmp += r.nextInt(200)+200;
        	m = r.nextInt(5);
        	
        }
        
    }
     
    public GroundTile[] getGroundTiles() {
        return ground.clone();
    }
    
    public LinkedList<Enemy> getEnemies() {
        LinkedList<Enemy> e = new LinkedList<Enemy>();
        for (Enemy enemy : enemies) {
            e.add(enemy);
        }
        return e;
    }
    
    public LinkedList<Coin> getCoins() {
        LinkedList<Coin> c = new LinkedList<Coin>();
        for (Coin coin : coins) {
            c.add(coin);
        }
        return c;
    }
    
    public LinkedList<Cloud> getClouds(){
    	LinkedList<Cloud> cl = new LinkedList<Cloud>();
    	for (Cloud cloud: clouds)
    	{
    		cl.add(cloud);
    	}
    	return cl;
    }
    
    public LinkedList<BushAndHill> getBushAndHill(){
    	LinkedList<BushAndHill>bh = new LinkedList<BushAndHill>();
    	for (BushAndHill bushandhill:bushAndHill)
    	{
    		bh.add(bushandhill);
    	}
    	return bh;
    }
    public LinkedList<Brick> getBricks(){
    	LinkedList<Brick>br = new LinkedList<Brick>();
    	for (Brick a : bricks){
    		br.add(a);
    	}
    	return br;
    }
 }
