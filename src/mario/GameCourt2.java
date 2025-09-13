package mario;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

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
public class GameCourt2 extends JPanel {
    
    SceneGenerator2 lg;
    
    private Mario2 mario; // the Mario character, keyboard control
    private UnderGroundTile[] underground; // array of ground tiles
    private UndergroundBrick[] bricks;
    private LinkedList<Coin> coins; // list containing all coins in the game
    private LinkedList<Pipe> pipes;
    private Pipe pipe;
    private BooleanValueHolder holder;
    private Timer timer;
    public HighScores hs = new HighScores(); // High Scores object to write high scores
    public static String username = ""; // User set username
    private LinkedList<Integer> marioMaxY; // record the MaxY of mario when calling standOnObj
    private Clip audioClip;
    private AudioInputStream audioStream;
    private File audioFile;
    
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
    
    public GameCourt2(/*JLabel score_label, JLabel coins_label, JLabel lives_label, */BooleanValueHolder holder) {

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
        //setFocusable(true);

        // This key listener allows the characters on the screen to move as
        // long as an arrow key is pressed, by changing the objects' velocity
        // accordingly. (The tick method below actually moves the objects.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    mario.v_x = -MARIO_X_VELOCITY;
                    mario_right_move = false;
                    GroundTile.vel_x = GROUND_X_VELOCITY;
                    Cloud.vel_x = CLOUD_X_VELOCITY;
                    BushAndHill.vel_x = BUSHANDHILL_X_VELOCITY;
                    Coin.vel_x = GROUND_X_VELOCITY;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    mario.v_x = MARIO_X_VELOCITY;
                    mario_right_move = true;
                    GroundTile.vel_x = -GROUND_X_VELOCITY;
                    Cloud.vel_x = -CLOUD_X_VELOCITY;
                    BushAndHill.vel_x = -BUSHANDHILL_X_VELOCITY;
                    Coin.vel_x = -GROUND_X_VELOCITY;
                    
                }
                
                if (e.getKeyCode() == KeyEvent.VK_UP) {
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
                    	Game.lives= 3;
                    	Game.coins = 0;
                    	Game.scores = 0;
                        gameWon = false;
                        gameOver = false;
                        holder.setScene(1);
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
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    mario.v_x = 0;
                    GroundTile.vel_x = 0;
                    Cloud.vel_x = 0;
                    BushAndHill.vel_x = 0;
                    Coin.vel_x = 0;
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
        doneLabel.setText("");
        distanceTravelled = 0;
        endTile = false; //是否到最後面
        removeAll();
        
        // Reset all of the game objects
        mario = new Mario2(COURT_WIDTH, COURT_HEIGHT);
        lg = new SceneGenerator2(21, COURT_WIDTH, COURT_HEIGHT);
        coins = lg.getCoins();
        underground = lg.getUnderGroundTiles();
        pipes = lg.getPipe();
        bricks = lg.getUndergroundBrick();
        for(Pipe p:pipes)
        	pipe = p;

        marioMaxY = new LinkedList<>();
        
        
        
        // Start playing the game
        playing = true;
        
        // Appropriately set all of the game labels
        add(doneLabel);

    }
    
    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
    	if (gameOver && playing) 
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
            doneLabel.setText("Sorry! You've lost!\nPress 'S' to play again!");
            playing = false;
        } 
        else if (playing && mario.pos_y <= COURT_HEIGHT) 
        {
            if (!(underground[underground.length-2].pos_x + GroundTile.SIZE > COURT_WIDTH)) {   
                endTile = true;  //終點出現了
            }
            // Advance Mario in his current direction        
            mario.move();
            
            // Advance the ground & coins in their current direction
            // 到達max_mario_x後方塊會往左移動
            if (mario.pos_x + mario.width >= MAX_MARIO_X && !mario.dead &&
            		underground[underground.length-2].pos_x + GroundTile.SIZE > COURT_WIDTH) {
                for (int i = 0; i < underground.length; i++) {
                    
                	underground[i].move();

                }
                for (int i = 0; i < coins.size(); i++) {
                    coins.get(i).move();
                }
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
            
            
            // win the game if Mario intersects the right half of the castle
            if (pipe.intersectsLeft(mario) && mario.pos_y == COURT_HEIGHT - GroundTile.SIZE - Mario2.INIT_HEIGHT) {
            	timer.stop();
            	try {
                    audioFile = new File("pipe.wav");
                    audioStream = AudioSystem.getAudioInputStream(audioFile);
                    audioClip = AudioSystem.getClip();
                    audioClip.open(audioStream);
                    audioClip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            	finalScore = Game.scores;
            	hs.addHighScore(new HighScore(username, finalScore));
            	holder.setScene(3);
            	holder.setValue(true);
            	return;
            }
            
            int[] serial2 = {pipes.size()};
            Pipe[] pi = new Pipe[pipes.size()];
            pipes.toArray(pi);  
            mario.standOnObj(pi, serial2, mario);
            marioMaxY.add(mario.max_y);
            
            int[] serial = {15,19,23,27,31,35,39,43,47}; //store the number of top stair 
            //14,18,22,26,30,34,38,42,46
            mario.standOnObj(bricks, serial, mario);
            marioMaxY.add(mario.max_y);
            
            mario.max_y = findMin(marioMaxY);
            marioMaxY.clear();
            // update the display
            repaint();
        } 
        else 
        {
            // If Mario is dead, check to see if the game should be over
            if (Game.lives > 0) reset();
            else gameOver = true;
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the pipe first since it should be in the background
        for (Pipe p : pipes) {
            p.draw(g);
            if (p.pos_x <= COURT_WIDTH && p.pos_x + p.width >= 0) {
                p.draw(g);
            }
        }

        for (int i = 0; i < bricks.length; i++) {
        	if(bricks[i] != null)
            	bricks[i].draw(g);
        }
        
        // Draw the ground if they're on screen
        for (int i = 0; i < underground.length; i++) {
            if ((underground[i].pos_x <= COURT_WIDTH && underground[i].pos_x >= 0)
                    || (underground[i].pos_x + underground[i].width <= COURT_WIDTH 
                        && underground[i].pos_x + underground[i].width >= 0)) 
            	underground[i].draw(g);
        }
        
        // Draw the coins if they're on screen
        for (Coin coin : coins) {
            coin.draw(g);
            if (coin.pos_x <= COURT_WIDTH && coin.pos_x + coin.width >= 0) {
                coin.draw(g);
            }
        }
        


        // Draw Mario
        mario.draw(g);
        
        new Font(50,20,"Score:"+Integer.toString(Game.scores)).draw(g);
        new Font(300,20,"@:"+Integer.toString(Game.coins)).draw(g);
        new Font(510,20,"Mario").drawMario(g);
        new Font(530,20,":"+Integer.toString(Game.lives)).draw(g);
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
