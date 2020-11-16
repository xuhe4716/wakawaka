package ghost;


import processing.core.PImage;


import java.util.ArrayList;


public class Waka extends Creature {
    private int lives;
    private int mouthframe = 8;
    private boolean minuscout = true;

    /**
     * Marks if the waka loses all its lives
     */
    public boolean lose;
    /**
     * Marks if the waka collects all fruits
     */
    public boolean win;


    private int eatfruit;

    /**
     * @see Creature#Creature(int, int, int, PImage, Wall[][])
     * @see #moveRight()
     * @param y
     * @param x
     * @param speed
     * @param lives Lives of waka which read from config
     * @param sprite
     * @param map
     */
    public Waka (int y, int x, int speed, int lives,PImage sprite, Wall[][] map){
        super(y, x, speed, sprite, map);
        this.lives = lives;

    }


    /**
     * Pick up fruits and set the fruits to air.
     * @param app App object
     */
    public void eat(App app){
        if(map[this.x][this.y].key.equals("7")){
            map[this.x][this.y].key = "0";
            map[this.x][this.y].air = true;
            this.eatfruit ++;
        }
        pickSuperFruit(app);
    }

    /**
     * Set the image of waka's mouth.
     * @param app App object
     */
    public void mouth(App app){
        if(mouthframe == 0){
            this.sprite = app.loadImage("src/main/resources/playerClosed.png");
            minuscout = false;
        } else if(mouthframe == 8){
            if(currentDirection == 39){
                this.sprite = app.loadImage("src/main/resources/playerRight.png");
            }else if(currentDirection == 37){
                this.sprite = app.loadImage("src/main/resources/playerLeft.png");
            }else if(currentDirection == 40){
                this.sprite = app.loadImage("src/main/resources/playerDown.png");
            }else if(currentDirection == 38){
                this.sprite = app.loadImage("src/main/resources/playerUp.png");
            }
            minuscout = true;
        }
        if(minuscout == true){
            mouthframe --;
        }else {
            mouthframe++;
        }

    }

    /**
     * When encountering ghosts, reduce life and reset people and ghosts.
     * If the ghost is in frighten mode, set ghost.hit to true.
     * @param app App object
     * @return Whether meet ghosts
     */
    public boolean meetGhost(App app) {
        ArrayList<Ghost> ghosts_ls = app.ghosts;
        for (Ghost g : ghosts_ls) {
            if (g.x == this.x && g.y == this.y) {
                if(g.FRIGHTENED == false){
                    this.reset();
                    this.lives--;
                    ghosts_ls.forEach(e -> e.reset());
                    return true;
                }else {
                    g.hit = true;
                    return true;
                }

                }
        }
        return false;


    }

    /**
     * Pick up fruits and set the fruits to air.
     * Set ghosts' mode to frighten.
     * @param app App object
     */
    public void pickSuperFruit(App app){
        if(map[this.x][this.y].key.equals("8")){
            map[this.x][this.y].key = "0";
            map[this.x][this.y].air = true;
            this.eatfruit ++;
            ArrayList<Ghost> ghosts_ls = app.ghosts;
            ghosts_ls.forEach(e -> e.FRIGHTENED = true);
        }
    }

    /**
     * @see Creature#reset()
     */
    @Override
    public void reset(){
        super.reset();
        mouthframe = 8;
        minuscout = true;
    }

    /**
     * Display the remaining lives of waka in the lower left corner of the map.
     * @param app App object
     */
    public void diplayLives(App app){
        int[][] coner = getConer(map);
        int leftBottomX = coner[2][0]+2;
        int leftBottomY = coner[2][1];
        PImage sprite = app.loadImage("src/main/resources/playerRight.png");
        for(int i=0; i < this.lives;i++){
            app.image(sprite, (leftBottomY+i)*(16+10)-16, leftBottomX *16-3);
        }

    }

    /**
     * Check whether the waka loses all its lives
     */
    public void isLose(){
        if(lives ==0){
            lose = true;
        }
    }

    /**
     * Check whether the waka collects all fruits
     * @param app App object
     */
    public void isWin(App app){
        if(app.fruit == eatfruit){
            win = true;
        }
    }

    /**
     * Main draw method of waka
     * @param app App object
     */
    @Override
    public void draw(App app){
        isWin(app);
        isLose();
        if(win || lose){
            return;
        }

        meetGhost(app);
        mouth(app);
        operation(this.direction);
        eat(app);
        diplayLives(app);


        app.image(this.sprite, this.pixel_y - 3, this.pixel_x- 3);
    }





}
