package Game.Objects;

import Game.PathLoader;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by George on 17/06/2017.
 */
public class Map {

    public static int width;
    public static int height;
    public static ArrayList<ArrayList<Point>> paths;

    public Map(int width_, int height_){
        paths = new ArrayList<ArrayList<Point>>();
        PathLoader p = new PathLoader();
        paths.add(p.loadTexture("Map1.png"));
        width = width_;
        height = height_;
    }

}
