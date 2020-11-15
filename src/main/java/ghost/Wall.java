package ghost;
//import processing.core.PApplet;
import processing.core.PImage;


import java.util.Arrays;
import java.util.List;

public class Wall{
    /**
     * row of the wall
     */
    public int row;
    /**
     * Colum of the wall
     */
    public int col;
    /**
     * Image of the wall
     */
    public PImage image;
    /**
     * Marks if the wall is air or not
     */
    boolean air = false;
    /**
     * Character of the wall
     */
    public String key;
    /**
     * pixel col of the wall
     */
    public int pixel_x;
    /**
     * pixel row of the wall
     */
    public int pixel_y;


    /**
     * Constructor for a wall, requires row, col image, and key
     * default state of air bases on the image
     * default state of pixel x and y base on col multiply 16 and row multiply 16
     * @param row row of the wall
     * @param col Colum of the wall
     * @param image Image of the wall
     * @param key Character of the wall
     */
    public Wall(int row, int col, PImage image, String key){
        this.row = row;
        this.col = col;
        this.image = image;
        this.key = key;
        if(image == null){
            this.air = true;
        }

        this.pixel_x = this.row *16;
        this.pixel_y = this.col * 16;
    }


    /**
     * Draw the wall if it is not waka and ghost
     * Given an app for image method
     * @param app PApplet object for image method
     */
    public void draw(App app) {
        String[] arr = new String[]{"a","c","i","w"};
        List<String> ghost = Arrays.asList(arr);
        if (!this.air && this.key!="p" && !ghost.contains(this.key) ) {
            app.image(this.image, row*16 , col*16);
        } else {
            return;
        }
    }





}
