package Graphics.GfxStateControllers;

import Engine.Main;
import Game.Objects.BiConnect;
import Game.Objects.Enemy;
import Game.Objects.TriConnect;
import Graphics.Cursor;
import Graphics.Entities.*;
import Utils.Enum.Cursors;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 17/06/2017.
 */
public class GfxGameState extends GfxState {

    private MapGfx mapGfx;
    private ArrayList<TowerGfx> towersGfx;
    private ArrayList<EnemyGfx> enemyGfx;
    private ArrayList<TriConnectGfx> triConnectGfx;
    private ArrayList<BiConnectGfx> biConnectGfx;
    private ArrayList<Cursor> cursors;

    public GfxGameState(){
        cursors = new ArrayList<Cursor>();
        cursors.add(new Cursor("defaultCursor.png"));
        cursors.add(new Cursor("reassignCursor1.png"));
        cursors.add(new Cursor("reassignCursor2.png"));
        GLFW.glfwSetCursor(Main.window, cursors.get(Cursors.DEFAULT.index()).getID());

        mapGfx = new MapGfx();
        towersGfx = new ArrayList<TowerGfx>();
        enemyGfx = new ArrayList<EnemyGfx>();
        triConnectGfx = new ArrayList<TriConnectGfx>();
        biConnectGfx = new ArrayList<BiConnectGfx>();
    }

    public void render(){
        mapGfx.draw();
        for(int i = 0; i < towersGfx.size(); i++){
            towersGfx.get(i).draw();
        }

        for(int i = 0; i < enemyGfx.size(); i++){
            enemyGfx.get(i).draw();
        }

        for(int i = 0; i < triConnectGfx.size(); i++){
            triConnectGfx.get(i).draw();
        }

        for(int i = 0; i < biConnectGfx.size(); i++){
            biConnectGfx.get(i).draw();
        }
    }

    public ArrayList<TowerGfx> getTowersGfx(){
        return towersGfx;
    }
    public ArrayList<EnemyGfx> getEnemyGfx() { return enemyGfx; }
    public ArrayList<TriConnectGfx> getTriConnectGfx() { return triConnectGfx; }
    public ArrayList<BiConnectGfx> getBiConnectGfx() { return biConnectGfx; }
    public void setTriConnectGfx(ArrayList<TriConnectGfx> t) { triConnectGfx = t; }
    public void setBiConnectGfx(ArrayList<BiConnectGfx> b) { biConnectGfx = b; }

    public void serCursor(Cursors c){
        GLFW.glfwSetCursor(Main.window, cursors.get(c.index()).getID());
    }
}
