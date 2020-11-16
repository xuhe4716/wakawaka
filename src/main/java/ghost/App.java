package ghost;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.core.PFont;
import java.util.*;
public class App extends PApplet {
    /**
     * Pixels map 's width
     */
    public static final int WIDTH = 448;
    /**
     * Pixels map 's height
     */
    public static final int HEIGHT = 576;
    /**
     * A Hashmap use for storing the image of each ghost, waka and wall
     */
    public HashMap<String, PImage> sprite = new HashMap<>();
    /**
     * Walls represent in grid
     */
    public Wall[][] imageGridsMap = new Wall[36][28];
    /**
     * Total fruits of map
     */
    public int fruit;
    /**
     * Player of the game
     */
    public Waka waka;
    /**
     * Ghosts of the game
     */
    public ArrayList<Ghost> ghosts = new ArrayList<>();
    /**
     * Imagine of game ending
     */
    private PFont end;
    /**
     * Mark if the game ends or not
     */
    private boolean gameEnd;
    /**
     * Mark if the game start or not
     */
    private boolean gameStart;

    /**
     * Initialize the game including imagines, player, ghosts, walls, lives and fruits.
     */
    public void setup() {
        frameRate(60);
        sprite.put("0", null);
        sprite.put("1", this.loadImage("src/main/resources/horizontal.png")); //horizontal wall
        sprite.put("2", this.loadImage("src/main/resources/vertical.png")); //vertical wall
        sprite.put("3", this.loadImage("src/main/resources/upLeft.png")); // up left corner
        sprite.put("4", this.loadImage("src/main/resources/upRight.png")); //up right corner
        sprite.put("5", this.loadImage("src/main/resources/downLeft.png")); //down left corner
        sprite.put("6", this.loadImage("src/main/resources/downRight.png")); //down right corner
        sprite.put("7", this.loadImage("src/main/resources/fruit.png")); // fruit
        sprite.put("8", this.loadImage("src/main/resources/superfruit.png")); // superfruit
        sprite.put("a", this.loadImage("src/main/resources/ambusher.png")); // ghost ambusher
        sprite.put("c", this.loadImage("src/main/resources/chaser.png")); // ghost chaser
        sprite.put("i", this.loadImage("src/main/resources/ignorant.png")); // ghost ignorant
        sprite.put("w", this.loadImage("src/main/resources/whim.png")); // ghost whim
        sprite.put("p", this.loadImage("src/main/resources/playerClosed.png")); // waka begining
        end = this.createFont("src/main/resources/PressStart2P-Regular.ttf", 25);
        Reader r = new Reader();
        r.set(this);
    }

    /**
     * Set up the width and the height of game background
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Receive an event from keyboard and set it to corresponding mode.
     * If event is between 37 and 40, set the event to waka's direction.
     * If event is equal to 32, mark the ghosts debug mode to true.
     * @param event A event from keyboard
     */
    public void keyPressed(KeyEvent event){
        int code = event.getKeyCode();
        if(code >=37 && code<=40){
            waka.setDirection(code);
        }
        if(code == 32){
            ghosts.forEach(e -> e.debug = true);
        }
    }

    /**
     * Receive an event from mouse and set the gameStart to true.
     */
    public void mousePressed(){
        gameStart = true;
    }

    /**
     * Draw entire game.
     * Return and show corresponding screen when waka loses all its lives or waka collects all fruits.
     * Restart the game in 10 seconds.
     * Show press start screen.
     * @exception InterruptedException
     */

    public void draw() {
        background(0, 0, 0);
        if(gameEnd){
            try {
                Thread.sleep(10000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
                }
            gameEnd = false;
            gameStart = false;
            Reader r = new Reader();
            r.set(this);
        }
        if(waka.lose){
            textFont(end);
            text("GAME OVER", 120, 220);
            gameEnd = true;
            return;
        }else if(waka.win){
            textFont(end);
            text("YOU WIN", 140, 220);
            gameEnd = true;
            return;
        }
        if(!gameStart){
            textFont(end);
            text("PRESS START", 100, 220);
        }else{
            Arrays.asList(imageGridsMap).stream().forEach(e -> Arrays.asList(e).stream().forEach(b -> b.draw(this)));
            this.waka.draw(this);
            ghosts.forEach(e -> e.draw(this));
        }

    }

    /**
     * The main method of the entire program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }
}
