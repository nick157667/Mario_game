package mario;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;


@SuppressWarnings("serial")
public class GameCourt3 extends JPanel {
    
	SceneGenerator3 lg;
    
    private Mario3 mario; // the Mario character, keyboard control
    public  static GroundTile[] ground; // array of ground tiles
    private EndCastle castle; // castle at the end of the level
    private FlagPole flagpole;
    private Flag flag;
    private LinkedList<Enemy> enemies; // list of enemies contained in the game
    private LinkedList<Enemy> dead_turtels; 
    private LinkedList<Coin> coins; // list containing all coins in the game
    private LinkedList<Cloud> clouds;
    private LinkedList<BushAndHill> bushAndHill;
    private LinkedList<HardBlock> stairs;
    private Pipe[] pipe = new Pipe[1];
    private LinkedList<Integer> marioMaxY; // record the MaxY of mario when calling standOnObj
    private BooleanValueHolder holder;
    private Timer timer;
    private Clip audioClip;
    private AudioInputStream audioStream;
    private File audioFile;
        
    public HighScores hs = new HighScores(); // High Scores object to write high scores
    public static String username = ""; // User set username
    
    public static boolean playing = false; // whether the game is running
    public boolean gameOver = false; // whether the user has lost
    public boolean gameWon = false; // whether the user has won
    public static boolean endTile = false; // whether the user has reached the end
    public static boolean mario_right_move = false;
    

    private JLabel doneLabel; // label that shows if the user wins or loses
    
    private static int finalScore; // the user's final score once they've won


    // Game constants
    public static final int COURT_WIDTH = 640;
    public static final int COURT_HEIGHT = 400;
    public static final int MARIO_X_VELOCITY = 6;
    public static final int MARIO_Y_VELOCITY = 10;
    public static final int GROUND_X_VELOCITY = 6;
    public static final int CLOUD_X_VELOCITY = 6;   
    public static final int BUSHANDHILL_X_VELOCITY = 6;   
    public static final int ENEMY_X_VELOCITY = 5;
    public static final int MAX_MARIO_X = 350;
    
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    
    public static int distanceTravelled;
    
    public GameCourt3(/*JLabel score_label, JLabel coins_label, JLabel lives_label, */BooleanValueHolder holder) {

        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key
        // events will be handled by its key listener.
        setFocusable(true);

        // This key listener allows the characters on the screen to move as
        // long as an arrow key is pressed, by changing the objects' velocity
        // accordingly. (The tick method below actually moves the objects.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                
                if (e.getKeyCode() == KeyEvent.VK_LEFT && !mario.touch_flag_or_flagpole) {
                	mario.v_x = -MARIO_X_VELOCITY;
                    mario_right_move = false;
                    GroundTile.vel_x = GROUND_X_VELOCITY;
                    Cloud.vel_x = CLOUD_X_VELOCITY;
                    BushAndHill.vel_x = BUSHANDHILL_X_VELOCITY;
                    Coin.vel_x = GROUND_X_VELOCITY;
                    HardBlock.vel_x = GROUND_X_VELOCITY;
                    pipe[0].vel_x = GROUND_X_VELOCITY;
                    castle.v_x = GROUND_X_VELOCITY;   
                    flagpole.v_x = GROUND_X_VELOCITY;
                    flag.v_x = GROUND_X_VELOCITY;
                    
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !mario.touch_flag_or_flagpole) {
                    mario.v_x = MARIO_X_VELOCITY;
                    mario_right_move = true;
                    GroundTile.vel_x = -GROUND_X_VELOCITY;
                    Cloud.vel_x = -CLOUD_X_VELOCITY;
                    BushAndHill.vel_x = -BUSHANDHILL_X_VELOCITY;
                    Coin.vel_x = -GROUND_X_VELOCITY;
                    HardBlock.vel_x = -GROUND_X_VELOCITY;
                    pipe[0].vel_x = -GROUND_X_VELOCITY;
                    castle.v_x = -GROUND_X_VELOCITY;    
                    flagpole.v_x = -GROUND_X_VELOCITY;
                    flag.v_x = -GROUND_X_VELOCITY;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_UP && !mario.touch_flag_or_flagpole) {
                    if (!mario.gravityOn) {
                    	try {
                            audioFile = new File("small_jump.wav");
                            audioStream = AudioSystem.getAudioInputStream(audioFile);
                            audioClip = AudioSystem.getClip();
                            audioClip.open(audioStream);
                            audioClip.start();
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    	mario.v_y = -MARIO_Y_VELOCITY;
                    }
                    mario.gravityOn = true;
                    mario.onGround = false;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    if (gameWon || gameOver) {
                    	timer.stop();
                    	Game.lives = 3;
                    	Game.coins = 0;
                    	Game.scores = 0;
                        gameWon = false;
                        gameOver = false;
                        holder.setScene(1);
                        holder.setValue(true);
                    }
                
                }
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    if (gameWon || gameOver) {
                    	timer.stop();
                    	Game.lives = 3;
                    	Game.coins = 0;
                    	Game.scores = 0;
                        gameWon = false;
                        gameOver = false;
                        holder.setScene(4);
                        holder.setValue(true);
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    mario.v_x = 0;
                    mario_right_move = false;
                    GroundTile.vel_x = 0;
                    Cloud.vel_x = 0;
                    BushAndHill.vel_x = 0;
                    Coin.vel_x = 0;
                    HardBlock.vel_x = 0;
                    pipe[0].vel_x = 0;
                    castle.v_x = 0;    
                    flagpole.v_x = 0;
                    flag.v_x = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    mario.v_x = 0;
                    GroundTile.vel_x = 0;
                    Cloud.vel_x = 0;
                    BushAndHill.vel_x = 0;
                    Coin.vel_x = 0;
                    HardBlock.vel_x = 0;
                    pipe[0].vel_x = 0;
                    castle.v_x = 0;      
                    flagpole.v_x = 0;
                    flag.v_x = 0;
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (!mario.gravityOn) {
                        mario.v_y = 0;
                    }
                }         
            }
        });
        
        // Initialize
        this.holder = holder;

        
        doneLabel = new JLabel("");
    }
    
    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
    	timer.start(); // MAKE SURE TO START THE TIMER!
    	gameWon = false;
        gameOver = false;
        doneLabel.setText("");
        distanceTravelled = 0;
        endTile = false; //是否到最後面
        removeAll();     
        // Reset all of the game objects
        mario = new Mario3(COURT_WIDTH, COURT_HEIGHT);
        pipe[0] = new Pipe(COURT_WIDTH,COURT_HEIGHT,0,COURT_HEIGHT-Pipe.SIZE_Y[2],2);
        lg = new SceneGenerator3(101, COURT_WIDTH, COURT_HEIGHT);
        coins = lg.getCoins();
        enemies = lg.getEnemies();
        ground = lg.getGroundTiles();
        clouds = lg.getClouds();
        bushAndHill = lg.getBushAndHill();
        stairs = lg.getHardBlock();
        marioMaxY = new LinkedList<>();
        dead_turtels = new LinkedList<Enemy>();
        
        castle = new EndCastle(COURT_WIDTH, COURT_HEIGHT, (ground.length - 1) * GroundTile.SIZE -
                    EndCastle.INIT_WIDTH);
        
        flagpole = new FlagPole(COURT_WIDTH, COURT_HEIGHT, (ground.length - 9) * GroundTile.SIZE -
                    FlagPole.INIT_WIDTH);
        
        flag = new Flag(COURT_WIDTH, COURT_HEIGHT, (ground.length - 9) * GroundTile.SIZE -
                FlagPole.INIT_WIDTH - Flag.INIT_WIDTH + 12);
        
        
        // Start playing the game
        playing = true;
        
        // Appropriately set all of the game labels
        add(doneLabel);

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
    	//System.out.println("3"+KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner());
        if (gameWon && playing) 
        {      
            playing = false;
            
        } 
        else if (gameOver && playing) 
        {
        	try {
                audioFile = new File("death.wav");
                audioStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioStream);
                audioClip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
            // Handle the user losing the game
            playing = false;
        } 
        else if (playing && mario.pos_y <= COURT_HEIGHT) 
        {
            if (!(ground[ground.length-2].pos_x + GroundTile.SIZE > COURT_WIDTH)) {   
                endTile = true;  //終點出現了
            }
                         
            //	System.out.println("not to end"+ground[ground.length-2].pos_x);
            // Advance Mario in his current direction        
            mario.move();
            
            // Advance the ground & coins in their current direction
            // 到達max_mario_x後方塊會往左移動
            if (mario.pos_x + mario.width >= MAX_MARIO_X && !mario.dead &&
                    ground[ground.length-2].pos_x + GroundTile.SIZE > COURT_WIDTH) {
                for (int i = 0; i < ground.length; i++) {
                    
                	ground[i].move();

                }
                for (int i = 0; i < coins.size(); i++) {
                    coins.get(i).move();
                }
                for (int i = 0; i< clouds.size(); i++)
                {
                	clouds.get(i).move();
                }
                for (int i = 0; i< bushAndHill.size(); i++)
                {
                	bushAndHill.get(i).move();
                }
                for (int i = 0; i< stairs.size(); i++)
                {
                	stairs.get(i).move();
                }
                castle.move();
                flagpole.move();
                flag.move();
                pipe[0].move();
            }
            
            // Spin the coins & remove them if Mario collects them
            Coin[] cs = new Coin[coins.size()];
            coins.toArray(cs);
            for (Coin coin : cs) {
                coin.spinCoin();
                if (coin.intersects(mario)) {
                	try {
                        audioFile = new File("coin.wav");
                        audioStream = AudioSystem.getAudioInputStream(audioFile);
                        audioClip = AudioSystem.getClip();
                        audioClip.open(audioStream);
                        audioClip.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                	Game.coins++;
                	Game.scores += 100;
                    coins.remove(coin);
                }
            }
            
            // Move the enemies & handle their death & Mario's death
            Enemy[] es = new Enemy[enemies.size()];
            Enemy[] dead_es = new Enemy[dead_turtels.size()];
            
            enemies.toArray(es);
            for (Enemy enemy : es) {
                if (enemy.dead) {
                	if(mario.v_x > 0 && mario.pos_x + mario.width >=  MAX_MARIO_X)
                		enemy.v_x = -GROUND_X_VELOCITY;
                	else 
                		enemy.v_x = 0;

                	if(enemy.sec_dead == 20) {
                		if(enemy.label == "Goomba") enemies.remove(enemy);
                	}
                		
                	else 
                		enemy.sec_dead += 1;
                	
                	if(enemy.label == "GreenKoopaTroop" && enemy.sec_dead == 20) {
                		             		
                		if(enemy.intersectsRight(mario)) {
                			enemy.intersectsRight = true;
                			enemy.intersectsLeft = false;
                			dead_turtels.add(enemy);
                		}
                		else if(enemy.intersectsLeft(mario)) {
                			enemy.intersectsRight = false;
                			enemy.intersectsLeft = true;
                			dead_turtels.add(enemy);
                		}	
                		if(enemy.intersectsRight) {
                			enemy.pos_x -= ENEMY_X_VELOCITY;
                		}
                		if(enemy.intersectsLeft) {
                			enemy.pos_x += ENEMY_X_VELOCITY;
                		}
                	}
                		
                }
                else {
	                if (enemy.startDistance <= distanceTravelled + 50 && !mario.dead) {
	                    enemy.onScreen = true;
	                    if(mario.pos_x + mario.width >= MAX_MARIO_X && mario_right_move) {
	                    	enemy.v_x = -ENEMY_X_VELOCITY/2 - MARIO_X_VELOCITY;
	                    } else {
	                    	enemy.v_x = -ENEMY_X_VELOCITY;
	                    }
	                }
	                if (enemy.offScreenLeft()) enemies.remove(enemy);
	                if (!mario.dead && !enemy.dead && enemy.intersectsTop(mario) && mario.reachedMaxHeight) {
	                	Game.scores = enemy.incrementScore(Game.scores);
	                    enemy.dead = true;
	                } else if (!mario.dead && !enemy.dead && (enemy.intersectsLeft(mario)
	                        || enemy.intersectsRight(mario))) {
	                	Game.lives--;
	                    mario.dead = true;
	                }
                }
                
                for(Enemy deadturtel : dead_turtels) {
                	if(deadturtel.intersects(enemy)) {
                		if(!enemy.dead)
                			Game.scores = enemy.incrementScore(Game.scores);
                		enemy.dead = true;
                		
                	}
                }
                	
                enemy.move();   
            }
            
            int[] serial2 = {1}; //store the number of top stair
            mario.standOnObj(pipe, serial2, mario);
            marioMaxY.add(mario.max_y);	
            
            
            int[] serial = {1, 3, 6, 10, 15, 21, 28, 36, 45}; //store the number of top stair
            HardBlock[] st = new HardBlock[stairs.size()];
            stairs.toArray(st);   
            
            mario.standOnObj(st, serial, mario);
            marioMaxY.add(mario.max_y);
            
            
            int i=0;
            if(!mario.slideToBottom) {
            	
            	// flag and mario movedown together
            	if(flag.intersects(mario)) {
            
            		mario.touch_flag_or_flagpole = true;
	            	flag.movedown(0.5 * MARIO_Y_VELOCITY ); 
	            	mario.v_y = 0.5 * MARIO_Y_VELOCITY;
	            	mario.pos_x = flag.pos_x - mario.width ;
	            	mario.pos_y = flag.pos_y;
	            	if(mario.pos_y == mario.max_y) {
	            		mario.slideToBottom = true;
	            		mario.touch_flag_or_flagpole = false;
	            	}
	            		
	            	mario.v_x = 0;
	            	mario.v_y = 0;
	            }
	            
            	// wait flag movedown until flag intersects with mario	
	            if(flagpole.intersects(mario)) {
	            	mario.touch_flag_or_flagpole = true;
	            	mario.pos_x = flagpole.pos_x - mario.width + 13;
	            	flag.movedown(1 * MARIO_Y_VELOCITY);
	            	mario.pos_y += 0.3 * MARIO_Y_VELOCITY;
	            	if(mario.pos_y == mario.max_y) {
	            		mario.slideToBottom = true;
	            		mario.touch_flag_or_flagpole = false;
	            	}	
	            	mario.v_x = 0;
	            	mario.v_y = 0;
	            }
            }
            else {
            	flag.movedown(0.5 * MARIO_Y_VELOCITY );
            }
	            
            // win the game if Mario intersects the right half of the castle
            
            if (castle.intersectsCastle(mario)) {
            	try {
                    audioFile = new File("match_win.wav");
                    audioStream = AudioSystem.getAudioInputStream(audioFile);
                    audioClip = AudioSystem.getClip();
                    audioClip.open(audioStream);
                    audioClip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            	gameWon = true;
            	finalScore = Game.scores;
            	hs.addHighScore(new HighScore(username, finalScore));
            	//return;
            }
            
            // Stop everything from moving if mario is dead;
            if (mario.dead) {
            	
                GroundTile.vel_x = 0;
                Coin.vel_x = 0;
                Game.coins = 0;
            	Game.scores = 0;
                for (Enemy enemy : enemies) enemy.v_x = 0;
                if (Game.lives > 0)
                {
                	timer.stop();
                	holder.setScene(1);
                    holder.setValue(true);
                }
                else gameOver = true;
            }
            	
            
            mario.max_y = findMin(marioMaxY);
            marioMaxY.clear();
            // update the display
            repaint();
        } 
        else 
        {
            
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the castle first since it should be in the background
        castle.draw(g);

        
        for(BushAndHill bushandhill : bushAndHill)
        {
            if (bushandhill.pos_x <= COURT_WIDTH && bushandhill.pos_x + bushandhill.width >=0) {
            	bushandhill.draw(g);
            }
        }
        
        // Draw the ground if they're on screen
        for (int i = 0; i < ground.length; i++) {
            if ((ground[i].pos_x <= COURT_WIDTH && ground[i].pos_x >= 0)
                    || (ground[i].pos_x + ground[i].width <= COURT_WIDTH 
                        && ground[i].pos_x + ground[i].width >= 0)) 
                ground[i].draw(g);
        }
        pipe[0].draw(g);
        
        // Draw the coins if they're on screen
        for (Coin coin : coins) {
            coin.draw(g);
            if (coin.pos_x <= COURT_WIDTH && coin.pos_x + coin.width >= 0) {
                coin.draw(g);
            }
        }
        for (Cloud cloud : clouds) {
            if (cloud.pos_x <= COURT_WIDTH && cloud.pos_x + cloud.width >=0) {
            	cloud.draw(g);           
            }
        }
        
        for(HardBlock stair : stairs) {
        	if (stair.pos_x <= COURT_WIDTH && stair.pos_x + stair.width >=0) {
        		stair.draw(g);
            }
        }
        
        
        flagpole.draw(g);
        flag.draw(g);
        // Draw Mario
        mario.draw(g);
        
        // Draw the enemies if they're on screen
        for (Enemy enemy : enemies) {
            if (enemy.pos_x <= COURT_WIDTH && enemy.pos_x + enemy.width >=0 && enemy.onScreen) {
                enemy.draw(g);
            }
        }
        new Font(50,20,"Score:"+Integer.toString(Game.scores)).draw(g);
        new Font(300,20,"@:"+Integer.toString(Game.coins)).draw(g);
        new Font(510,20,"Mario").drawMario(g);
        new Font(530,20,":"+Integer.toString(Game.lives)).draw(g);
        if(gameOver) {
        	new Font(50,50,"Gameover! Press 'R' to play again!").draw(g);
        }
        if(gameWon)
        {
        	System.out.println("*");
        	new Font(50,50,"Victory! Press 'R' to play again!").draw(g);
        	new Font(195,80,"Press 'T' to see TOP10!").draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    public int findMin(LinkedList<Integer> link) {
        int min = 1000;
        for (int serial : link) {
        	if(serial < min)
        		min = serial;
        }
        return min;
    }
}
