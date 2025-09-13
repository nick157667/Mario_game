package mario;
import java.util.LinkedList;
import java.util.Random;

public class SceneGenerator2 {
    private UnderGroundTile[] ground; // array of ground tiles
    private UndergroundBrick[] brick;
    private LinkedList<Coin> coins;
    private LinkedList<Pipe> pipe;
    private Random r = new Random();
    
    public SceneGenerator2(int groundLength, int courtWidth, int courtHeight) {
        // Create the ground array based on the length of the ground
        ground = new UnderGroundTile[groundLength];
        for (int i = 0; i < ground.length; i++) {
            ground[i] = new UnderGroundTile(courtWidth, courtHeight, GroundTile.SIZE * i, 
                    courtHeight - GroundTile.SIZE);
        }
        brick = new UndergroundBrick[49];//0~12  13~21
        for(int i=0; i<13 ;i++)
        {
        	brick[i] = new UndergroundBrick(courtWidth, courtHeight, 0 , GroundTile.SIZE * i);
        }
        for(int i=0; i<9; i++)
        {
        	brick[4*i+13] = new UndergroundBrick(courtWidth, courtHeight, 176+32*i , 40);
        	brick[4*i+14] = new UndergroundBrick(courtWidth, courtHeight, 176+32*i , 272);
        	brick[4*i+15] = new UndergroundBrick(courtWidth, courtHeight, 176+32*i , 304);
        	brick[4*i+16] = new UndergroundBrick(courtWidth, courtHeight, 176+32*i , 336);
        }
        
        // Randomly place coins in groups of 3 along the screen
        coins = new LinkedList<Coin>();
        int i = 200;
        for(int j=124; j<=184;j+=30) 
        {
            coins.add(new Coin(courtWidth, courtHeight, i,j));
            coins.add(new Coin(courtWidth, courtHeight, i + 2 * Coin.WIDTH,j));
            coins.add(new Coin(courtWidth, courtHeight, i + 4 * Coin.WIDTH,j));
            coins.add(new Coin(courtWidth, courtHeight, i + 6 * Coin.WIDTH,j));
            coins.add(new Coin(courtWidth, courtHeight, i + 8 * Coin.WIDTH,j));
            coins.add(new Coin(courtWidth, courtHeight, i + 10 * Coin.WIDTH,j));
            coins.add(new Coin(courtWidth, courtHeight, i + 12 * Coin.WIDTH,j));
            coins.add(new Coin(courtWidth, courtHeight, i + 14 * Coin.WIDTH,j));
        }



        
        // Randomly place enemies, first a goomba, then a koopa troop
        pipe = new LinkedList<Pipe>();
        i=0;
        while(i < 400 -32)
        {
        	pipe.add(new Pipe(courtWidth, courtHeight, courtWidth - 48, i, 1));
        	i += 24;
        }
        pipe.add(new Pipe(courtWidth, courtHeight, courtWidth - 96, courtHeight - 32 - 48, 0));
    }
     
    public UnderGroundTile[] getUnderGroundTiles() {
        return ground.clone();
    }
    public UndergroundBrick[] getUndergroundBrick() {
        return brick.clone();
    }
        
    public LinkedList<Coin> getCoins() {
        LinkedList<Coin> c = new LinkedList<Coin>();
        for (Coin coin : coins) {
            c.add(coin);
        }
        return c;
    }
    

    
    public LinkedList<Pipe> getPipe(){
    	LinkedList<Pipe>p = new LinkedList<Pipe>();
    	for (Pipe pipe:pipe)
    	{
    		p.add(pipe);
    	}
    	return p;
    }
 }
