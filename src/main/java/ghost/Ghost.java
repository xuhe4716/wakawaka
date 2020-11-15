package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ghost extends Creature {

    String types;
    int frightenedLength;
    ArrayList<Object> modeLengths;


    //int tryy;
    //int n = 39;

    //ArrayList<Integer> validturn;
    int intersection_all;
    boolean firstMove = true;
    ArrayList<Integer> turn = new ArrayList<>();

    boolean debug;
    boolean FRIGHTENED;
    boolean SCATTERCHASE;
    boolean hit;
    //boolean SCATTER = true;
    //boolean CHASE = false;
    //boolean meetWaka;
    //boolean scatter;

    int fps =0;
    int seconds =0;
    int frightenseconds = 0;
    int index =0;
    int turnfps=0;




    public Ghost(int y, int x, int speed, PImage sprite, Wall[][] map, String types, int frightenedLength, ArrayList<Object> modeLengths) {
        super(y, x, speed,sprite, map);
        this.frightenedLength = frightenedLength;
        this.modeLengths = modeLengths;


        this.types = types;
        //this.seconds = Integer.parseInt(modeLengths.get(0).toString());
    }


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


    public boolean findpath(int startx, int starty, int endx, int endy){
        targetX = endx;
        targetY = endy;


        //if(this.x == endx && this.y == endy){
            //return true;
        //}


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

                return false;
            }


            else if (turn.contains(move2)) {
                if(direction[1] != move2){
                    setDirection(move2);
                }
                return false;
            }

            else{
                for (int a : turn) {
                    if (a != currentDirection && a!=move1 && a!=move2) {
                        if(direction[1] != a){
                            setDirection(a);
                        }
                        return false;
                    }
            }
        }

        }else{
            if(firstMove){
                setDirection(turn.get(0));
                return false;
            }

        }

        return false;

    }

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


    public void chaserChase(Waka waka){
        findpath(x,y,waka.x,waka.y);

    }

    public void ambusherChase(Waka waka){
        int tx = 0;
        int ty = 0;
        if(waka.currentDirection == 37){
            if(!OutOfBoundary(waka.x,waka.y -4,37)){
                ty = waka.y - 4;

            }else {
                ty = leftLowerBound;
            }
            tx = waka.x;
        }else if(waka.currentDirection == 39){
            if(!OutOfBoundary(waka.x,waka.y +4,39)){
                ty = waka.y + 4;

            }else{
                ty = rightUpperBound;
            }
            tx = waka.x;

        }else if(waka.currentDirection == 40){
            if(!OutOfBoundary(waka.x+4 ,waka.y,40)){
                tx = waka.x+4;
            }else{
                tx = bottomLowerBound;
            }
            ty = waka.y;

        }else if(waka.currentDirection == 38){
            if(!OutOfBoundary(waka.x-4 ,waka.y,38)){
                tx = waka.x-4;
            }else {
                tx = topUpeerBound;
            }
            ty = waka.y;

        }
        findpath(x,y,tx,ty);
    }

    public void ignorantChase(Waka waka){
        double units = Math.sqrt(Math.pow(waka.x - this.x,2)+ Math.pow(waka.y - this.y,2));
        if(units > 8){
            chaserChase(waka);
        }else {
            scatter(this.types);
        }

    }

    public void whimChase(Waka waka){
        int ty =0;
        int tx =0;
        int aheady =waka.y;
        int aheadx =waka.x;
        int direction =0;
        if(waka.currentDirection == 37){
            direction = 37;
            aheady -=4;
        }else if(waka.currentDirection == 39){
            direction = 39;
            aheady +=4;
        }else if(waka.currentDirection == 40){
            direction = 40;
            aheadx +=4;
        }else if(waka.currentDirection == 38){
            direction = 38;
            aheadx -=4;
        }

        aheadx += (aheadx - this.x) *2;
        aheady += (aheady - this.y) *2;

        findpath(x,y,aheadx,aheady);
        System.out.println(String.format("before targetx: %s; targety :%s",targetX,targetY));
        System.out.println(String.format("direction: %s",direction));


        System.out.println(OutOfBoundary(aheadx,aheady,direction));



        if(OutOfBoundary(aheadx,aheady,direction)){
            //System.out.println(String.format("left : %s",leftLowerBound));
            if(direction == 37){
                aheady = leftLowerBound;
                System.out.println(String.format("left : %s",leftLowerBound));
            }else if(direction ==39){
                aheady = rightUpperBound;
            }else if(direction == 40){
                aheadx = bottomLowerBound;
            }else if(direction == 38){
                aheadx = topUpeerBound;
                System.out.println(String.format("top : %s",topUpeerBound));
            }

        }





        //System.out.println(OutOfBoundary(aheadx,aheady,waka.currentDirection));








        findpath(x,y,aheadx,aheady);


    }


    public void frighten(){
        
        debug = false;

        if(intersection_all >=2) {
            int r = 0;
            r = (int)(Math.random()*turn.size());
            int randomdirection = turn.get(r);

            if(Math.abs(turnfps - fps)>8){
                setDirection(randomdirection);
                turnfps = fps;
                //System.out.println(turn);
                //System.out.println(String.format("random: %s",r));

            }

        }else{


            if(firstMove){
                setDirection(turn.get(0));
            }

        }




    }



    public void timer(App app){
        //int time =0;
        if(FRIGHTENED){
            this.sprite = app.loadImage("src/main/resources/frightened.png");
            frighten();
            if(frightenseconds == frightenedLength){
                frightenseconds = 0;
                FRIGHTENED = false;
                //this.sprite = originalSprite;
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







    @Override
    public void reset(){
        super.reset();
        firstMove = true;
        FRIGHTENED = false;
        frightenseconds = 0;
        hit = false;
    }

    public void debug(App app){
        if(this.debug == true){
            app.stroke(255,255,255);
            app.line(this.y *16+3,this.x*16+3,targetY*16, targetX *16);
        }
    }


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

        System.out.println(String.format("tyeps: %s ; seconds %s; frighten seconds: %s; fps : %s; index : %s; turnpfps : %s ",this.types,seconds,frightenseconds,fps, index,turnfps));
        System.out.println(String.format("mode: %s; frigthenmode: %s",SCATTERCHASE,FRIGHTENED));


        operation(direction);


        firstMove = false;


        //System.out.println(modeLengths);
        //System.out.println(String.format("now x %s; y%s",targetX,targetY));

        debug(app);
        app.image(this.sprite, this.pixel_y - 3, this.pixel_x- 3);
    }
}
