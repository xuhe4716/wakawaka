package ghost;
import processing.core.PApplet;
import processing.core.PImage;


import java.util.Arrays;
import java.util.List;

public class Wall{
    public int row;
    public int col;
    public PImage image;
    boolean nulll = false;
    public String key;
    public int pixel_x;
    public int pixel_y;
    String[] arr = new String[]{"a","c","i","w"};
    List<String> ghost = Arrays.asList(arr);


    public Wall(int row, int col, PImage image, String key){
        this.row = row;
        this.col = col;
        this.image = image;
        this.key = key;
        if(image == null){
            this.nulll = true;
        }

        this.pixel_x = this.row *16;
        this.pixel_y = this.col * 16;
    }




    public void draw(PApplet app) {
        if (!this.nulll && this.key!="p" && !this.ghost.contains(this.key) ) {
            app.image(this.image, row*16 , col*16);
        } else {
            return;
        }
    }


}
