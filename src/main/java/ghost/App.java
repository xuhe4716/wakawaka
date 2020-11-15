package ghost;

//import org.json.simple.JSONArray;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.core.PFont;


import java.util.*;

public class App extends PApplet {

    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;
    HashMap<String, PImage> sprite = new HashMap<>();
    Wall[][] imageGridsMap = new Wall[36][28];

    String filename;
    int lives;
    int speed;
    int frightenedLength;
    int fruit;
    ArrayList<Object> modeLengths = new ArrayList<>();

    Waka waka;
    ArrayList<Ghost> ghosts = new ArrayList<>();
    PFont end;
    //PFont win;


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
        //win = this.createFont("src/main/resources/PressStart2P-Regular.ttf", 25);
        Reader r = new Reader();
        r.set(this);
    }
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void displayMap(){

        for(int i =0;i<this.imageGridsMap.length;i++) {
            for (int j = 0; j <this.imageGridsMap[i].length; j++) {
                this.imageGridsMap[i][j].draw(this);
            }
        }


    }

    public void keyPressed(KeyEvent event){
        int code = event.getKeyCode();
        if(code >=37 && code<=40){
            waka.setDirection(code);
        }
        if(code == 32){
            ghosts.forEach(e -> e.debug = true);
        }


    }

    public void draw() {
        background(0, 0, 0);
        if(waka.lose){
            textFont(end);
            text("GAME OVER", 120, 220);
            return;

        }else if(waka.win){
            textFont(end);
            text("YOU WIN", 140, 220);
            return;
        }

        displayMap();
        this.waka.draw(this);
        ghosts.forEach(e -> e.draw(this));

    }

    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }

}
