package Game.Objects;

import Graphics.Entities.TowerGfx;
import Graphics.GfxStateControllers.GfxGameState;

/**
 * Created by George on 17/06/2017.
 */
public class Tower {

    private int x;
    private int y;
    private int ID;
    private TowerGfx gfx;
    private TriConnect triConnect;
    private BiConnect biConnect;

    public Tower(int x_, int y_, int ID_){
        x = x_;
        y = y_;
        ID = ID_;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setGfx(TowerGfx tgfx){
        gfx = tgfx;
    }

    public void move(int x_, int y_){

        if(x_ < 790 && x_ > 10 && y_ > 10 && y_ < 990) {
            int deltax = x - x_;
            int deltay = y - y_;
            x = x_;
            y = y_;
            gfx.positionChanged(-1.0f * (float) deltax, -1.0f * (float) deltay);
        }
        if(triConnect != null){
            triConnect.positionChanged();
        }
        if(biConnect != null) {
            biConnect.positionChanged();
        }
    }

    public void setTriConnect(TriConnect c){
        triConnect = c;
    }
    public TriConnect getTriConnect(){return triConnect; }
    public void setBiConnect(BiConnect c) { biConnect = c; }
    public BiConnect getBiConnect() { return biConnect; }

    public void setSelected(boolean b){
        gfx.setSelected(b);
    }

    public int getID(){
        return ID;
    }

    public void reassignConnect1(Tower t){
        if(biConnect != null) {
            this.biConnect.removeLinks();
        }
        if(triConnect != null) {
            this.triConnect.removeLinks();
        }
        if(t.getBiConnect() != null) {
            t.getBiConnect().removeLinks();
        }
        if(t.getTriConnect() != null) {
            t.getTriConnect().removeLinks();
        }

        this.biConnect = null;
        this.triConnect = null;

        t.setBiConnect(null);
        t.setTriConnect(null);

        BiConnect bi = new BiConnect(this, t);
        this.biConnect = bi;
        t.setBiConnect(bi);
    }

    public void reassignConnect2(Tower t2){
        Tower t1;

        if(biConnect.getTower1().getID() != ID){
            t1 = biConnect.getTower1();
        }else{
            t1 = biConnect.getTower2();
        }

        if(biConnect != null) {
            this.biConnect.removeLinks();
        }
        if(triConnect != null) {
            this.triConnect.removeLinks();
        }
        if(t1.getBiConnect() != null) {
            t1.getBiConnect().removeLinks();
        }
        if(t1.getTriConnect() != null) {
            t1.getTriConnect().removeLinks();
        }
        if(t2.getBiConnect() != null) {
            t2.getBiConnect().removeLinks();
        }
        if(t2.getTriConnect() != null) {
            t2.getTriConnect().removeLinks();
        }

        this.biConnect = null;
        this.triConnect = null;

        t1.setBiConnect(null);
        t1.setTriConnect(null);

        t2.setBiConnect(null);
        t2.setTriConnect(null);

        TriConnect ti = new TriConnect(this, t1, t2);
        this.triConnect = ti;
        t1.setTriConnect(ti);
        t2.setTriConnect(ti);
    }
}
