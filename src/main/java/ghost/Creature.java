package ghost;


import processing.core.PImage;
import ghost.Waka;
import ghost.App;
import ghost.Ghost;

import java.util.Arrays;
import java.util.List;


public abstract class Creature {
    /**
     * Initial value of row
     */
    public int originalX;
    /**
     * Initial value of column
     */
    public int originalY;
    /**
     * Initial image
     */
    public PImage originalSprite;
    /**
     * Row of creature
     */
    public int x;
    /**
     * Column of creature
     */
    public int y;
    /**
     * Speed of creature
     */
    public int speed;
    /**
     * Image of creature
     */
    public PImage sprite;
    /**
     * Map of game
     */
    public Wall[][] map;

    /**
     * Direction of creature.
     * The second element stores current direction and the first element stores last direction.
     */
    public int[] direction = new int[2];
    /**
     * Pixel column of the creature
     */
    public int pixel_x;
    /**
     * Pixel row of the creature
     */
    public int pixel_y;
    /**
     * Upper bound of the map
     */
    public int upperBoundary;
    /**
     * Right bound of the map
     */
    public int rightBoundary;
    /**
     * Lower bound of the map
     */
    public int lowerBoundary;
    /**
     * Left bound of the map
     */
    public int leftBoundary;



    /**
     * Current Direction of the creature
     * From 37 to 40, which represent left, up, right, and down respectively
     */
    public int currentDirection;
    /**
     * Whether the creature can change direction to up or down
     */
    public boolean turnUpDown = false;
    /**
     * Whether the creature can change direction to left or right
     */
    public boolean turnLeftRight = false;

    /**
     * Constructor for a creature, requires row, column, speed, image and map.
     * Default value of pixel x and y base on row multiply 16 and column multiply 16.
     * Default value of originalX and originalY base on row and column.
     * Default value of originalSprite base on sprite.
     * @param y Initial column
     * @param x Initial row
     * @param speed Speed read from config file
     * @param sprite Initial image
     * @param map Map of game
     */
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

    /**
     * Set value to direction.
     * The second element stores current direction and the first element stores last direction.
     * @param event From 37 to 40, which represent left, up, right, and down respectively
     */
    public void setDirection(int event) {
        this.direction[0] = this.direction[1];
        this.direction[1] = event;
    }

    /**
     * The main method of controlling creature's movement.
     * @param direction Direction of creature
     */
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

    /**
     * When the creature tries to turns right, set the row and column and make it move right.
     */
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

    /**
     * When the creature tries to turns left, set the row and column and make it move left.
     */
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

    /**
     * When the creature tries to turns down, set the row and column and make it move down.
     */
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

    /**
     * When the creature tries to turns up, set the row and column and make it move up.
     */
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

    /**
     * Check if the creature hits the wall at its current location.
     * @param x Row of creature
     * @param y Column of creature
     * @return Whether the current location a wall
     */
    public boolean collision(int x, int y) {
        String[] arr = new String[]{"a", "c", "i", "w"};
        List<String> ghost = Arrays.asList(arr);
        if (map[x][y].key.equals("7") || map[x][y].air || map[x][y].key.equals("p") || ghost.contains(map[x][y].key) || map[x][y].key.equals("8")) {
            return false;
        }
        return true;
    }

    /**
     * Check whether the current position of the creature exceeds the boundary of the map.
     * @param x Row of creature
     * @param y Column of creature
     * @return Whether the current position exceeds the boundary
     */
    public boolean OutOfBoundary(int x, int y) {
        setBoundary();
        if (x < upperBoundary) {
            return true;
        }
        if (x > lowerBoundary) {
            return true;
        }
        if (y < leftBoundary) {
            return true;
        }
        if (y > rightBoundary) {
            return true;
        }
        return false;
    }


    /**
     * If the current location of the creature exceeds the boundary of the map, find the closest point on the map to the current location.
     * @param x Row of creature
     * @param y Column of creature
     * @return The coordinates of the closest point
     */
    public int[] nearestPoint(int x, int y){

        setBoundary();
        int[] nearestPoint = new int[2];
        String[] arr = new String[]{"a","c","i","w"};
        List<String> ghost = Arrays.asList(arr);


        double distance = 999999;
        int nearestX = 0;
        int nearestY = 0;
        for(int i = upperBoundary; i <= lowerBoundary+1; i++){
            for(int j = leftBoundary; j <= rightBoundary+1; j++){
                if (!ghost.contains(map[i][j].key)){
                    double distance2 = Math.sqrt(Math.pow(x - i,2)+ Math.pow(y - j ,2));
                    if(distance > distance2){
                        distance = distance2;
                        nearestX = i;
                        nearestY = j;
                    }

                }
            }

        }
        nearestPoint[0] = nearestX;
        nearestPoint[1] = nearestY;
        return nearestPoint;

    }


    /**
     * Find the four corners of the map.
     * @param map Map of game
     * @return A two-dimensional array.
     * The first element stores the coordinates of the upper left corner.
     * The second element stores the coordinates of the upper right corner.
     * The third element stores the coordinates of the lower left corner.
     * The fourth element stores the coordinates of the lower right corner.
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

    /**
     * Set the boundary of map.
     */
    public void setBoundary(){
        int[][] coner = getConer(map);
        upperBoundary = Math.abs(coner[0][0]);
        rightBoundary = 28 - coner[2][1] - coner[0][1];
        lowerBoundary = 36 - coner[0][0];
        leftBoundary = Math.abs(coner[0][1]);
    }


    /**
     * Reset the creature.
     */
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


    /**
     * @see Waka#draw(App)
     * @see Ghost#draw(App)
     */
    public abstract void draw(App app);

}
