package ghost;

import processing.core.PImage;

import java.nio.channels.Pipe;
import java.util.Arrays;
import java.util.List;

public class Creature {
    int originalX;
    int originalY;
    PImage originalSprite;
    int x;
    int y;
    int speed;
    PImage sprite;
    Wall[][] map;


    int[] direction = new int[2];
    int pixel_x;
    int pixel_y;

    int leftLowerBound;
    int rightUpperBound;
    int topUpeerBound;
    int bottomLowerBound;
    int targetX;
    int targetY;


    int currentDirection;
    boolean turnUpDown = false;
    boolean turnLeftRight = false;


    public Creature(int y, int x, int speed, PImage sprite, Wall[][] map) {
        this.originalX = x;
        this.originalY = y;
        this.x = x;
        this.y = y;
        this.pixel_x = x * 16;
        this.pixel_y = y * 16;
        this.speed = speed;
        this.sprite = sprite;
        this.originalSprite = sprite;
        this.map = map;

    }


    public void setDirection(int event) {
        this.direction[0] = this.direction[1];
        this.direction[1] = event;
    }


    public void operation(int[] direction) {
        if (direction[1] == 39 && direction[0] == 0 || direction[1] == 39 && direction[0] == 37 || direction[1] == 39 && direction[0] == 39) { //initial start to right and left to right
            moveRight();
        } else if (direction[1] == 37 && direction[0] == 0 || direction[1] == 37 && direction[0] == 39 || direction[1] == 37 && direction[0] == 37) {  //initial start to left and right to left
            moveLeft();
        } else if (direction[1] == 38 && direction[0] == 0 || direction[1] == 38 && direction[0] == 40 || direction[1] == 38 && direction[0] == 38) { //initial start to up and down to up
            moveUp();
        } else if (direction[1] == 40 && direction[0] == 0 || direction[1] == 40 && direction[0] == 38 || direction[1] == 40 && direction[0] == 40) {  //initial start to down and up to down
            moveDown();
        } else if (direction[1] == 38 && direction[0] == 39) {  //right to up
            if (!collision(this.x - 1, this.y)) {
                if (turnUpDown) {
                    moveUp();
                    direction[1] = 38;
                    direction[0] = 0;
                } else {
                    moveRight();
                }
            } else {
                moveRight();
            }


        } else if (direction[1] == 38 && direction[0] == 37) {  // left to up
            if (!collision(this.x - 1, this.y)) {
                if (turnUpDown) {
                    moveUp();
                    direction[1] = 38;
                    direction[0] = 0;
                } else {
                    moveLeft();
                }
            } else {
                moveLeft();
            }
        } else if (direction[1] == 40 && direction[0] == 39) {  //right to down
            if (!collision(this.x + 1, this.y)) {
                if (turnUpDown) {
                    moveDown();
                    direction[1] = 40;
                    direction[0] = 0;
                } else {
                    moveRight();
                }
            } else {
                moveRight();
            }

        } else if (direction[1] == 40 && direction[0] == 37) {  //left to down
            if (!collision(this.x + 1, this.y)) {
                if (turnUpDown) {
                    moveDown();
                    direction[1] = 40;
                    direction[0] = 0;
                } else {
                    moveLeft();
                }
            } else {
                moveLeft();
            }
        } else if (direction[1] == 39 && direction[0] == 38) {   // up to right
            if (!collision(this.x, this.y + 1)) {
                if (turnLeftRight) {
                    moveRight();
                    direction[1] = 39;
                    direction[0] = 0;
                } else {
                    moveUp();
                }
            } else {
                moveUp();
            }

        } else if (direction[1] == 39 && direction[0] == 40) { //down to right
            if (!collision(this.x, this.y + 1)) {
                if (turnLeftRight) {
                    moveRight();
                    direction[1] = 39;
                    direction[0] = 0;
                } else {
                    moveDown();
                }
            } else {
                moveDown();
            }

        } else if (direction[1] == 37 && direction[0] == 38) { //up to left
            if (!collision(this.x, this.y - 1)) {
                if (turnLeftRight) {
                    moveLeft();
                    direction[1] = 37;
                    direction[0] = 0;
                } else {
                    moveUp();
                }
            } else {
                moveUp();
            }

        } else if (direction[1] == 37 && direction[0] == 40) { //down to left
            if (!collision(this.x, this.y - 1)) {
                if (turnLeftRight) {
                    moveLeft();
                    direction[1] = 37;
                    direction[0] = 0;
                } else {
                    moveDown();
                }
            } else {
                moveDown();
            }

        }

    }


    public void moveRight() {
        if (!collision(this.x, this.y + 1)) {
            this.pixel_y = (this.pixel_y + this.speed);
            //this.stop = false;
            currentDirection = 39;
            if (this.pixel_y % 16 == 0) {
                this.turnUpDown = true;
                this.y = this.pixel_y / 16;

            } else {
                this.turnUpDown = false;
            }
        } else {
            this.pixel_y = this.pixel_y;
            //this.stop = true;

        }

    }


    public void moveLeft() {
        if (!collision(this.x, this.y - 1)) {
            this.pixel_y = (this.pixel_y - this.speed);
            //this.stop = false;
            currentDirection = 37;
            if (this.pixel_y % 16 == 0) {
                this.turnUpDown = true;
                this.y = this.pixel_y / 16;
            } else {
                this.turnUpDown = false;
            }
        } else {
            this.pixel_y = this.pixel_y;
            //this.stop = true;
        }

    }


    public void moveDown() {
        if (!collision(this.x + 1, this.y)) {
            this.pixel_x = this.pixel_x + this.speed;
            //this.stop = true;
            currentDirection = 40;
            if (this.pixel_x % 16 == 0) {
                this.x = this.pixel_x / 16;
                this.turnLeftRight = true;
            } else {
                this.turnLeftRight = false;
            }
        } else {
            this.pixel_x = this.pixel_x;
            //this.stop = true;
        }


    }

    public void moveUp() {
        if (!collision(this.x - 1, this.y)) {
            this.pixel_x = (this.pixel_x - this.speed);
            //this.stop = false;
            currentDirection = 38;
            if (this.pixel_x % 16 == 0) {
                this.x = this.pixel_x / 16;
                this.turnLeftRight = true;
            } else {
                this.turnLeftRight = false;
            }
        } else {
            this.pixel_x = this.pixel_x;
            //this.stop = true;
        }

    }


    public boolean collision(int x, int y) {
        String[] arr = new String[]{"a", "c", "i", "w"};
        List<String> ghost = Arrays.asList(arr);
        if (map[x][y].key.equals("7") || map[x][y].nulll || map[x][y].key.equals("p") || ghost.contains(map[x][y].key) || map[x][y].key.equals("8")) {
            return false;
        }
        return true;
    }


    public boolean OutOfBoundary(int x, int y, int direction) {

        boolean outOfMap = false;
        if (x < 0) {
            x = 0;
            outOfMap = true;
        }
        if(x >= 36) {
            x = 35;
            outOfMap = true;
        }
        if (y < 0) {
            y = 0;
            outOfMap = true;
        }
        if (y >= 28) {
            y = 27;
            outOfMap = true;
        }



        /*

        if (x < 0 && y < 0) {
            return OutOfBoundary(0, 0, direction);
            //return true;
        } else if (x > 36 && y > 28) {

            /*
            int xdiff = Math.abs(x - 36);
            int ydiff = Math.abs(y - 28);

             */
            //return OutOfBoundary(35, 27, direction);
            /*
            if (xdiff < ydiff) {


            } else {
                OutOfBoundary(x, 27, 40);
            }


            //return true;

             */
        /*

        }else if(x < 0 && y >28){

            int xdiff = Math.abs(x);
            int ydiff = Math.abs(y - 28);

            if (xdiff < ydiff) {
                OutOfBoundary(0, y, 37);
            } else {
                OutOfBoundary(x, 27, 40);
            }




            return OutOfBoundary(0, 27, direction);


        }else if(x >36 && y <0) {

            int xdiff = Math.abs(x - 36);
            int ydiff = Math.abs(y);


            if (xdiff < ydiff) {
                OutOfBoundary(35, y, 39);
            } else {
                OutOfBoundary(x, 0, 38);
            }




            return OutOfBoundary(35, 0, direction);
        }

             */



        //}else {
        if (direction == 37) {
            for (int i = 0; i < map[x].length; i++) {
                if (Integer.parseInt(map[x][i].key) >= 1 && Integer.parseInt(map[x][i].key) <= 6) {
                    leftLowerBound = i + 1;
                    break;
                }
            }
            if (y < leftLowerBound) {
                //targetY = leftLowerBound;
                return true;
            } else {
                return false || outOfMap;
            }

        } else if (direction == 39) {
            for (int i = map[x].length - 1; i >= 0; i--) {
                if (Integer.parseInt(map[x][i].key) >= 1 && Integer.parseInt(map[x][i].key) <= 6) {
                    rightUpperBound = i - 1;
                    break;
                }
            }
            if (y > rightUpperBound) {
                return true;
                //targetY = rightUpperBound;
            } else {
                return false || outOfMap;
            }
        } else if (direction == 40) {
            for (int i = map.length - 1; i >= 0; i--) {
                if (Integer.parseInt(map[i][y].key) >= 1 && Integer.parseInt(map[i][y].key) <= 6) {
                    bottomLowerBound = i - 1;
                    break;
                }
            }
            if (x > bottomLowerBound) {
                //targetX = bottomLowerBound;
                return true;
            } else {
                return false || outOfMap;
            }


        } else if (direction == 38) {
            for (int i = 0; i < map.length; i++) {
                if (Integer.parseInt(map[i][y].key) >= 1 && Integer.parseInt(map[i][y].key) <= 6) {
                    topUpeerBound = i + 1;
                    break;
                }
            }
            if (x < topUpeerBound) {
                //targetX = bottomLowerBound;
                return true;
            } else {
                return false || outOfMap;
            }
        }
        return false || outOfMap;
    }





        /*
        if(x < 0 && y <0){
            int xdiff = Math.abs(x);
            int ydiff = Math.abs(y);

            if(xdiff < ydiff){
                return OutOfBoundary(0,y,37);
            }else {
                return OutOfBoundary(x,0, 38);
            }
        }else if(x > 36 && y > 28){
            int xdiff = Math.abs(x-36);
            int ydiff = Math.abs(y - 28);

            if(xdiff < ydiff){
                return OutOfBoundary(35, y,39);
            }else{
                return OutOfBoundary(x,27,40);
            }
        }else if(x < 0 && y>36)

         */








    /*
    public boolean OutofMap(int x, int y) {
        if (x < 0 && y < 0) {
            int xdiff = Math.abs(x);
            int ydiff = Math.abs(y);

            if (xdiff < ydiff) {
                return OutOfBoundary(0, y, 37);
            } else {
                return OutOfBoundary(x, 0, 38);
            }
        } else if (x > 36 && y > 28) {
            int xdiff = Math.abs(x - 36);
            int ydiff = Math.abs(y - 28);

            if (xdiff < ydiff) {
                return OutOfBoundary(35, y, 39);
            } else {
                return OutOfBoundary(x, 27, 40);
            }

        }else if(x < 0 && y >28){
            int xdiff = Math.abs(x);
            int ydiff = Math.abs(y - 28);

            if (xdiff < ydiff) {
                return OutOfBoundary(0, y, 37);
            } else {
                return OutOfBoundary(x, 27, 40);
            }

        }else if(x >36 && y <0){
            int xdiff = Math.abs(x - 36);
            int ydiff = Math.abs(y);


            if (xdiff < ydiff) {
                return OutOfBoundary(35, y, 39);
            } else {
                return OutOfBoundary(x, 0, 38);
            }
        }else {

        }
    }

     */


    public int[][] getConer(Wall[][] map){
        int[][] coner = new int[4][2];
        for(int i = 0 ; i<map.length;i++){
            for(int left = 0 ; left<map[0].length; left++){   //upperleft
                if(coner[0][0] == 0 && coner[0][1] == 0){
                    if(map[i][left].key.equals("6")){
                        coner[0][0] = map[i+1][left+1].col;
                        coner[0][1] = map[i+1][left+1].row;
                    }
                }else{
                    break;
                }

            }

            for(int right = map[0].length-1; right >=0 ; right --){   //upperright
                if(coner[1][0] == 0 && coner[1][1] == 0){
                    if(map[i][right].key.equals("5")){
                        coner[1][0] = map[i+1][right-1].col;
                        coner[1][1] = map[i+1][right-1].row;
                    }
                }else{
                    break;
                }
            }
        }


        for(int j = map.length -1 ; j>=0;j--){
            for(int left = 0 ; left<map[0].length; left++){  //bottom left
                if(coner[2][0] == 0 && coner[2][1] == 0){
                    if(map[j][left].key.equals("4")){
                        coner[2][0] = map[j-1][left+1].col;
                        coner[2][1] = map[j-1][left+1].row;
                    }
                }else{
                    break;
                }

            }
            for(int right = map[0].length-1; right >=0 ; right --){   //bottom right
                if(coner[3][0] == 0 && coner[3][1] == 0){
                    if(map[j][right].key.equals("3")){
                        coner[3][0] = map[j-1][right-1].col;
                        coner[3][1] = map[j-1][right-1].row;
                    }
                }else{
                    break;
                }
            }

        }

        return coner;
    }



    public void reset(){
        direction = new int[2];
        x = originalX;
        y = originalY;
        pixel_x = x*16;
        pixel_y = y*16;
        currentDirection = 0;
        turnUpDown = false;
        turnLeftRight = false;
    }


    public void draw(App app){
        return;
    }

}
