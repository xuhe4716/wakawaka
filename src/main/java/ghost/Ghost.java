package ghost;
import ghost.Creature;
import ghost.Waka;
import ghost.App;

import processing.core.PImage;

import java.util.ArrayList;

public class Ghost extends Creature {

    private String types;
    private int frightenedLength;
    private ArrayList<Object> modeLengths;

    private int targetX;
    private int targetY;


    private int intersection_all;
    private boolean firstMove = true;
    private ArrayList<Integer> turn = new ArrayList<>();

    /**
     * Marks if debug mode is active
     */
    public boolean debug;
    /**
     * Marks if frighten mode is active
     */
    public boolean FRIGHTENED;
    private boolean SCATTERCHASE;
    /**
     * Marks if ghost in frighten is hit
     */
    public boolean hit;


    private int fps =0;
    private int seconds =0;
    private int frightenseconds = 0;
    private int index =0;
    private int turnfps=0;


    /**
     * @see Creature#Creature(int, int, int, PImage, Wall[][])
     * @param types Types of the ghost
     * @param frightenedLength Length of frighten mode
     * @param modeLengths Each mode's lengths of ghosts
     */
    public Ghost(int y, int x, int speed, PImage sprite, Wall[][] map, String types, int frightenedLength, ArrayList<Object> modeLengths) {
        super(y, x, speed,sprite, map);
        this.frightenedLength = frightenedLength;
        this.modeLengths = modeLengths;


        this.types = types;
        //this.seconds = Integer.parseInt(modeLengths.get(0).toString());
    }

    /**
     *
     * @param x Row of creature
     * @param y Column of creature
     * @return How many possible turn
     */
    public int meetIntersection(int x, int y) {
        ArrayList<Integer> validturn = new ArrayList<>();
        int intersection=0;
        if (!collision(x - 1, y)) {
            if(currentDirection != 38) {
                intersection++;
            }
            if(currentDirection != 40){
                validturn.add(38);
            }

        }
        if (!collision(x + 1, y)) {
            if(currentDirection != 40) {
                intersection++;
            }
            if(currentDirection != 38){
                validturn.add(40);
            }

        }
        if (!collision(x, y + 1)) {
            if(currentDirection != 39) {

                intersection++;
            }
            if(currentDirection != 37){
                validturn.add(39);
            }

        }
        if (!collision(x, y - 1)) {
            if(currentDirection != 37) {

                intersection++;
            }
            if(currentDirection != 39){
                validturn.add(37);
            }

        }
        turn = validturn;
        return intersection;

    }

    /**
     * Findpath from current location to the end
     * @param startx Current row of creature
     * @param starty Current column of creature
     * @param endx Destination row
     * @param endy Destination column
     */
    public void findpath(int startx, int starty, int endx, int endy){
        targetX = endx;
        targetY = endy;


        int move1 = 0;
        int move2 = 0;
        //int move3 = 0;
        int xDiff = endx-startx;
        int yDiff = endy-starty;

        if(Math.abs(xDiff) >= Math.abs(yDiff)){
            if(xDiff > 0){
                move1 = 40;
            }else if(xDiff < 0){
                move1 = 38;
            }
            if(yDiff >0){
                move2 = 39;
            }else if(yDiff<0){
                move2 = 37;
            }
        }else{
            if(yDiff >0){
                move1 = 39;
            }else if(yDiff<0){
                move1 = 37;
            }
            if(xDiff > 0){
                move2 = 40;
            }else if(xDiff < 0){
                move2 = 38;
            }
        }


        if(intersection_all >=2) {
            if (turn.contains(move1)) {
                if(direction[1] != move1){
                    setDirection(move1);
                }

                return;
            }


            else if (turn.contains(move2)) {
                if(direction[1] != move2){
                    setDirection(move2);
                }
                return;
            }

            else{
                for (int a : turn) {
                    if (a != currentDirection && a!=move1 && a!=move2) {
                        if(direction[1] != a){
                            setDirection(a);
                        }
                        return;
                    }
            }
        }

        }else{
            if(firstMove){
                setDirection(turn.get(0));
                return;
            }

        }

    }

    /**
     * Scatter mode.
     * @param type Type of ghost
     */
    public void scatter(String type){
        int[][] coner = getConer(map);
        if(type.equals("a")){
            findpath(x,y,coner[1][0],coner[1][1]);
               //return true;

        }else if(type.equals("c")){
            findpath(x,y,coner[0][0],coner[0][1]);
                //return true;

        }else if(type.equals("i")){
            findpath(x,y,coner[2][0],coner[2][1]);
                //return true;

        }else if(type.equals("w")){
            findpath(x,y,coner[3][0],coner[3][1]);
                //return true;
            //}
        }
        //return false;
    }

    /**
     * Chaser mode.
     * @param type Type of ghost
     * @param app App object
     */
    public void chaser(String type, App app){
        if(type.equals("c")){
            chaserChase(app.waka);
        }else if(type.equals("i")){
            ignorantChase(app.waka);
        }else if(type.equals("a")){
            ambusherChase(app.waka);
        }else if(type.equals("w")){
            whimChase(app.waka);
        }

    }

    /**
     * Chaser mode of Chaser.
     * @param waka Waka object
     */
    public void chaserChase(Waka waka){
        findpath(x,y,waka.x,waka.y);

    }

    /**
     * Chaser mode of Ambusher.
     * @param waka Waka object
     */
    public void ambusherChase(Waka waka){
        int tx = 0;
        int ty = 0;


        if(waka.currentDirection == 37){
            if(!OutOfBoundary(waka.x,waka.y -4)){
                ty = waka.y - 4;

            }else {
                ty = nearestPoint(waka.x, waka.y-4)[1];
            }
            tx = waka.x;
        }else if(waka.currentDirection == 39){
            if(!OutOfBoundary(waka.x,waka.y +4)){
                ty = waka.y + 4;

            }else{
                ty = nearestPoint(waka.x, waka.y+4)[1];
            }
            tx = waka.x;

        }else if(waka.currentDirection == 40){
            if(!OutOfBoundary(waka.x+4 ,waka.y)){
                tx = waka.x+4;
            }else{
                tx = nearestPoint(waka.x + 4, waka.y)[0];
            }
            ty = waka.y;

        }else if(waka.currentDirection == 38){
            if(!OutOfBoundary(waka.x-4 ,waka.y)){
                tx = waka.x-4;
            }else {
                tx = nearestPoint(waka.x - 4, waka.y)[0];
            }
            ty = waka.y;

        }


        findpath(x,y,tx,ty);
    }


    /**
     * Chaser mode of Ignorant.
     * @param waka Waka object
     */
    public void ignorantChase(Waka waka){
        double units = Math.sqrt(Math.pow(waka.x - this.x,2)+ Math.pow(waka.y - this.y,2));
        if(units > 8){
            chaserChase(waka);
        }else {
            scatter(this.types);
        }

    }

    /**
     * Chaser mode of Whim.
     * @param waka Waka object
     */
    public void whimChase(Waka waka){
        int aheady =waka.y;
        int aheadx =waka.x;
        if(waka.currentDirection == 37){
            aheady -=4;
        }else if(waka.currentDirection == 39){
            aheady +=4;
        }else if(waka.currentDirection == 40){
            aheadx +=4;
        }else if(waka.currentDirection == 38){
            aheadx -=4;
        }

        aheadx += (aheadx - this.x) *2;
        aheady += (aheady - this.y) *2;

        //findpath(x,y,aheadx,aheady);
        //System.out.println(String.format("before targetx: %s; targety :%s",targetX,targetY));
        //System.out.println(String.format("direction: %s",direction));

        //System.out.println(String.format("before targetx: %s; targety :%s",aheadx,aheady));
        //System.out.println(OutOfBoundary(aheadx,aheady,direction));



        if(OutOfBoundary(aheadx,aheady)){
            aheadx = nearestPoint(aheadx,aheady)[0];
            aheady = nearestPoint(aheadx,aheady)[1];


        }


        findpath(x,y,aheadx,aheady);


    }

    /**
     * Frighten mode.
     */
    public void frighten(){
        debug = false;
        if(intersection_all >=2) {
            int r = 0;
            r = (int)(Math.random()*turn.size());
            int randomdirection = turn.get(r);

            if(Math.abs(turnfps - fps)>8){
                setDirection(randomdirection);
                turnfps = fps;

            }

        }else{
            if(firstMove){
                setDirection(turn.get(0));
            }

        }
    }


    /**
     * Game running timer.
     * @param app App object
     */
    public void timer(App app){
        //int time =0;
        if(FRIGHTENED){
            this.sprite = app.loadImage("src/main/resources/frightened.png");
            frighten();
            if(frightenseconds == frightenedLength){
                frightenseconds = 0;
                FRIGHTENED = false;
            }

        }else {
            this.sprite = originalSprite;
            if(!SCATTERCHASE){
                scatter(this.types);
            }else {
                chaser(this.types,app);
            }


            if (seconds == Integer.parseInt(modeLengths.get(index).toString())) {
                seconds = 0;
                SCATTERCHASE  = !SCATTERCHASE;
                index ++;
                if(index == modeLengths.size() -1){
                    index = modeLengths.size()-1;
                }


            }



        }
        //System.out.println(seconds);


    }

    /**
     * @see Creature#reset()
     */
    @Override
    public void reset(){
        super.reset();
        firstMove = true;
        FRIGHTENED = false;
        frightenseconds = 0;
        hit = false;
    }


    /**
     * Check if debug mode is active
     * @param app App object
     */
    public void debug(App app){
        if(this.debug){
            app.stroke(255,255,255);
            app.line(this.y *16+3,this.x*16+3,targetY*16, targetX *16);
        }
    }

    /**
     * Main draw method of waka
     * @param app App object
     */
    @Override
    public void draw(App app){
        if(hit == true){
            return;
        }
        fps++;
        if(fps %60 == 0) {
            if(FRIGHTENED == true){
                frightenseconds++;
            }else {
                seconds++;
            }
            //fps =0;

        }
        if(app.waka.win || app.waka.lose){
            return;
        }

        int a = meetIntersection(x,y);
        intersection_all = a;
        //frighten();
        //scatter(this.types);
        timer(app);
        //chaser(this.types,app);

        //System.out.println(String.format("tyeps: %s ; seconds %s; frighten seconds: %s; fps : %s; index : %s; turnpfps : %s ",this.types,seconds,frightenseconds,fps, index,turnfps));
        //System.out.println(String.format("mode: %s; frigthenmode: %s",SCATTERCHASE,FRIGHTENED));


        operation(direction);


        firstMove = false;


        //System.out.println(modeLengths);
        //System.out.println(String.format("now x %s; y%s",targetX,targetY));
        //System.out.println(String.format("try : x: %s; y: %s",nearestPoint(-1, -4)[0],nearestPoint(-1, -4)[1]));

        debug(app);
        app.image(this.sprite, this.pixel_y - 3, this.pixel_x- 3);
    }
}
