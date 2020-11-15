package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Waka extends Creature{
    int lives;
    int mouthframe = 8;
    boolean minuscout = true;


    boolean lose;
    boolean win;


    int eatfruit;
    boolean hit;


    public Waka (int y, int x, int speed, int lives,PImage sprite, Wall[][] map){
        super(y, x, speed, sprite, map);
        this.lives = lives;
    }




    public void eat(App app){
        if(map[this.x][this.y].key.equals("7")){
            map[this.x][this.y].key = "0";
            map[this.x][this.y].nulll = true;
            this.eatfruit ++;
        }
        pickSuperFruit(app);
    }


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


    public boolean meetGhost(App app) {
        ArrayList<Ghost> ghosts_ls = app.ghosts;
        System.out.println(ghosts_ls);
        for (Ghost g : ghosts_ls) {
            if (g.x == this.x && g.y == this.y) {
                if(g.FRIGHTENED == false){
                    this.reset();
                    this.lives--;
                    ghosts_ls.forEach(e -> e.reset());
                    /*
                    for (Ghost g1 : ghosts_ls) {
                        g1.reset();
                    }

                     */
                    return true;
                }else {
                    g.hit = true;
                    return true;
                }

                }





        }
        return false;


    }


    public void pickSuperFruit(App app){
        if(map[this.x][this.y].key.equals("8")){
            map[this.x][this.y].key = "0";
            map[this.x][this.y].nulll = true;
            this.eatfruit ++;
            //this.hit = true;
            ArrayList<Ghost> ghosts_ls = app.ghosts;
            ghosts_ls.forEach(e -> e.FRIGHTENED = true);
            /*
            for(Ghost g: ghosts_ls){
                g.FRIGHTENED = true;
            }

             */
        }
    }


    @Override
    public void reset(){
        super.reset();
        mouthframe = 8;
        minuscout = true;
    }


    public void diplayLives(App app){
        int[][] coner = getConer(map);
        int leftBottomX = coner[2][0]+2;
        int leftBottomY = coner[2][1];
        PImage sprite = app.loadImage("src/main/resources/playerRight.png");
        for(int i=0; i < this.lives;i++){
            app.image(sprite, (leftBottomY+i)*(16+10)-16, leftBottomX *16-3);
        }

    }

    public void isLose(){
        if(lives ==0){
            lose = true;
        }
    }

    public void isWin(App app){
        if(app.fruit == eatfruit){
            win = true;
        }
    }


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
