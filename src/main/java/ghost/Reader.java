package ghost;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processing.core.PImage;


import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Reader extends App{
    /**
     * Filename of map
     */
    private String filename;
    /**
     * Lives of waka
     */
    private int lives;
    /**
     * Speed of waka and ghosts
     */
    private int speed;
    /**
     * Frigthened length of ghosts
     */
    private int frightenedLength;
    /**
     * Each mode's lengths of ghosts
     */
    private ArrayList<Object> modeLengths = new ArrayList<>();


    /**
     * Read a file contains map.
     * @param filename A txt file
     * @return Grid of map
     * @exception FileNotFoundException When file does not exist
     */
    public ArrayList<String> readfile(String filename) {
        if (filename == null) {
            return null;
        }
        File f = new File(filename);
        ArrayList<String> rawfile = new ArrayList<>();
        try {
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                rawfile.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format("No such file: %s", filename));
        }
        return rawfile;
    }

    /**
     * Read a config file and setup the basic attributes of game.
     * @param filename A config file
     * @exception IOException
     * @exception ParseException
     */
    public void  readConfig(String filename){
        ArrayList<Integer> alis = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try (java.io.Reader reader = new FileReader(filename)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            this.filename = (String) jsonObject.get("map");
            Long lives_long = (Long) jsonObject.get("lives");
            Long speed_long = (Long) jsonObject.get("speed");
            Long frightenedLength_long = (Long) jsonObject.get("frightenedLength");
            JSONArray msg = (JSONArray) jsonObject.get("modeLengths");

            Iterator<Object> iterator = msg.iterator();
            while (iterator.hasNext()) {
                this.modeLengths.add(iterator.next());
            }
            this.speed = speed_long.intValue();
            this.lives = lives_long.intValue();
            this.frightenedLength = frightenedLength_long.intValue();


        }catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create new objects of waka and ghosts.
     * Count how many fruits are on the map and set up the fruits.
     * @param rawfile Grid of map
     * @param app PApplet object for image method
     * @exception Exception When error occurs in x row x col
     */
    public void setGrid (ArrayList<String> rawfile, App app) {
        int row = 0;
        int col = 0;
        try {
            for (int i = 0; i < rawfile.size(); i++) {
                for (int j = 0; j < rawfile.get(i).length(); j++) {
                    row = j;
                    col = i;
                    for (Map.Entry<String, PImage> entry : app.sprite.entrySet()) {
                        String str = String.valueOf(rawfile.get(i).charAt(j));
                        if (str.equals(entry.getKey())) {
                            Wall ig = new Wall(j, i, entry.getValue(), entry.getKey());
                            this.imageGridsMap[i][j] = ig;

                            if (entry.getKey().equals("p")) {
                                waka = new Waka(j, i, this.speed,this.lives, imageGridsMap[i][j].image, this.imageGridsMap);

                            } else if (entry.getKey().equals("a") || entry.getKey().equals("c") || entry.getKey().equals("i") || entry.getKey().equals("w")) {
                                ghosts.add(new Ghost(j, i, this.speed,imageGridsMap[i][j].image, this.imageGridsMap,entry.getKey(), this.frightenedLength, this.modeLengths));

                            } else if(entry.getKey().equals("7") || entry.getKey().equals("8")){
                                this.fruit++;
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(String.format("Error in row %s col %s",col,row));
        }
    }

    /**
     * Call all the method and pass basic attributes to main class.
     * @param app Game object for setup basic attributes
     */
    public void set(App app){
        readConfig("config.json");
        setGrid(readfile(this.filename),app);


        //app.lives = this.lives;
        //app.speed = this.speed;
        //app.modeLengths = this.modeLengths;
        //app.frightenedLength = this.frightenedLength;
        app.waka = this.waka;
        app.ghosts = this.ghosts;
        app.imageGridsMap = this.imageGridsMap;
        app.fruit = this.fruit;




    }



}
