package mario;

import java.util.LinkedList;
import java.util.Random;

public class SceneGenerator3 {
    private GroundTile[] ground; // array of ground tiles
    private LinkedList<Enemy> enemies;
    private LinkedList<Coin> coins;
    private LinkedList<Cloud> clouds;
    private LinkedList<BushAndHill> bushAndHill;
    private LinkedList<HardBlock> HardBlock;
    private Random r = new Random();
    
    public SceneGenerator3(int groundLength, int courtWidth, int courtHeight) {
        // Create the ground array based on the length of the ground
        ground = new GroundTile[groundLength];
        for (int i = 0; i < ground.length; i++) {
        	ground[i] = new GroundTile(courtWidth, courtHeight, GroundTile.SIZE * i, 
                    courtHeight - GroundTile.SIZE);
        }
        
        // Randomly place coins in groups of 3 along the screen
        coins = new LinkedList<Coin>();
        int i = 400;
        while (i < (groundLength - 15) * GroundTile.SIZE - courtWidth) {
            coins.add(new Coin(courtWidth, courtHeight, i));
            coins.add(new Coin(courtWidth, courtHeight, i + 2 * Coin.WIDTH));
            coins.add(new Coin(courtWidth, courtHeight, i + 4 * Coin.WIDTH));
            i += r.nextInt((250 - 100) + 1) + 100;
        }
        
        // Randomly place enemies, first a goomba, then a koopa troop
        enemies = new LinkedList<Enemy>();
        i = 450;
        while (i < (groundLength - 20)  * GroundTile.SIZE - courtWidth) {
            enemies.add(new Goomba(courtWidth, courtHeight, i, "Goomba"));
            i += r.nextInt((350 - 200) + 1) + 100;
            enemies.add(new GreenKoopaTroop(courtWidth, courtHeight, i, "GreenKoopaTroop"));
            i += r.nextInt((350 - 200 + 1) + 100);
        }
        
        clouds =new LinkedList<Cloud>();
        int x=0,y=0,n=0;
        while(x< (groundLength - 5) * GroundTile.SIZE - courtWidth) {
        	x += r.nextInt(151)+100;
        	y = r.nextInt(100)+75;
        	n = r.nextInt(3);
        	clouds.add(new Cloud(courtWidth,courtHeight,x,y,n));
        }
        
        bushAndHill = new LinkedList<BushAndHill>();
        int tmp=0;
        int m=4;
        while(tmp < (groundLength - 5) * GroundTile.SIZE - courtWidth) {
        	tmp += r.nextInt(200)+200;
        	m = r.nextInt(5);
        	bushAndHill.add(new BushAndHill(courtWidth,courtHeight,tmp,m));

        }
        
        // Create the HardBlock array 
        HardBlock = new LinkedList<HardBlock>();
        i = 1;
        for (; i <= 6; i++) {
        	for(int j = 1 ; j <= i ; j ++) {
        		HardBlock.add(new HardBlock(courtWidth, courtHeight, (groundLength - 22 + i ) * GroundTile.SIZE , j)); // 樓梯從倒數第20格開始
        	}	
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
    
    public LinkedList<HardBlock> getHardBlock(){
    	LinkedList<HardBlock>St = new LinkedList<HardBlock>();
    	for (HardBlock stair : HardBlock){
    		St.add(stair);
    	}
    	return St;
    }
 }
