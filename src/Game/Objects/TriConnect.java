package Game.Objects;

import Graphics.Entities.BiConnectGfx;
import Graphics.Entities.TriConnectGfx;
import Graphics.GfxStateControllers.GfxGameState;

/**
 * Created by George on 22/06/2017.
 */
public class TriConnect{

    private Tower tower1;
    private Tower tower2;
    private Tower tower3;

    private TriConnectGfx gfx;

    public TriConnect(Tower t1, Tower t2, Tower t3){
        tower1 = t1;
        tower2 = t2;
        tower3 = t3;

        gfx = new TriConnectGfx(tower1.getX(), tower1.getY(), tower2.getX(), tower2.getY(), tower3.getX(), tower3.getY());

        t1.setTriConnect(this);
        t2.setTriConnect(this);
        t3.setTriConnect(this);
    }

    public TriConnect(Tower t1, Tower t2, Tower t3, GfxGameState stateGfx){
        tower1 = t1;
        tower2 = t2;
        tower3 = t3;

        gfx = new TriConnectGfx(tower1.getX(), tower1.getY(), tower2.getX(), tower2.getY(), tower3.getX(), tower3.getY());
        stateGfx.getTriConnectGfx().add(gfx);

    }

    public void positionChanged(){
        gfx.positionChanged(tower1.getX(), tower1.getY(), tower2.getX(), tower2.getY(), tower3.getX(), tower3.getY());
    }

    public void connectToGfx(GfxGameState g){
        g.getTriConnectGfx().add(gfx);
    }

    public TriConnectGfx getGfx(){
        return gfx;
    }

    public void removeLinks(){
        tower1.setTriConnect(null);
        tower2.setTriConnect(null);
        tower3.setTriConnect(null);

        tower1 = null;
        tower2 = null;
        tower3 = null;
    }
}
