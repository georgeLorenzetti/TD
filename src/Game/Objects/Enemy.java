package Game.Objects;

import Graphics.Entities.EnemyGfx;

/**
 * Created by George on 22/06/2017.
 */
public class Enemy {
    private double x;
    private double y;
    private int ID;
    private double speed = 2;
    private EnemyGfx gfx;
    private int pathID;

    private int currentVector = 1;

    public Enemy(int ID_, int pathID_){
        pathID = pathID_;
        x = Map.paths.get(pathID).get(0).x;
        y = Map.paths.get(pathID).get(0).y;
        ID = ID_;

        System.out.println(x + " " + y + " - " + Map.paths.get(pathID).get(1).x + " " + Map.paths.get(pathID).get(1).y);
    }

    public Enemy(int x_, int y_, int ID_, int pathID_){
        pathID = pathID_;
        x = x_;
        y = y_;
        ID = ID_;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public EnemyGfx getGfx(){
        return gfx;
    }
    public void setGfx(EnemyGfx tgfx){
        gfx = tgfx;
    }

    public boolean move(){
        double vx = (double)Map.paths.get(pathID).get(currentVector).x - (double)x;
        double vy = (double)Map.paths.get(pathID).get(currentVector).y - (double)y;

        double vd = Math.sqrt((Math.pow(vx, 2) + Math.pow(vy, 2)));

        System.out.println("CHECK" + vd);
        double dx = vx * (speed / vd);
        double dy = vy * (speed / vd);

        x += dx;
        y += dy;

       System.out.println(currentVector + " :: " + x + " - " + y + " // " + (double)Map.paths.get(pathID).get(currentVector).x + " - " + (double)Map.paths.get(pathID).get(currentVector).y + " // " + vx + " - " + vy + " // " + dx + " - " + dy);

        gfx.positionChanged(1.0f * (float)dx, 1.0f * (float)dy);

        if(Math.abs(vd) <= speed){
            currentVector++;

            if(currentVector > Map.paths.get(pathID).size()-1){
                return true;
            }
        }
        return false;
    }

    public int getID(){
        return ID;
    }
}
