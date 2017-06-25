package Game.Objects;

import Graphics.Entities.BiConnectGfx;
import Graphics.Entities.TriConnectGfx;
import Graphics.GfxStateControllers.GfxGameState;

/**
 * Created by George on 23/06/2017.
 */
public class BiConnect{


    private Tower tower1;
    private Tower tower2;

    private BiConnectGfx gfx;

    public BiConnect(Tower t1, Tower t2){
        tower1 = t1;
        tower2 = t2;

        gfx = new BiConnectGfx(tower1.getX(), tower1.getY(), tower2.getX(), tower2.getY());

        t1.setBiConnect(this);
        t2.setBiConnect(this);
    }

    public BiConnect(Tower t1, Tower t2, GfxGameState stateGfx){
        tower1 = t1;
        tower2 = t2;

        gfx = new BiConnectGfx(tower1.getX(), tower1.getY(), tower2.getX(), tower2.getY());
        stateGfx.getBiConnectGfx().add(gfx);

    }

    public void positionChanged(){
        gfx.positionChanged(tower1.getX(), tower1.getY(), tower2.getX(), tower2.getY());
    }

    public void connectToGfx(GfxGameState g){
        g.getBiConnectGfx().add(gfx);
    }

    public BiConnectGfx getGfx(){
        return gfx;
    }

    public Tower getTower1(){
        return tower1;
    }

    public Tower getTower2(){
        return tower2;
    }

    public void removeLinks(){
        tower1.setBiConnect(null);
        tower2.setBiConnect(null);

        tower1 = null;
        tower2 = null;
    }
}
