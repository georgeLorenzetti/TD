package Game.GameStateControllers;

import Game.Objects.*;
import Game.PathLoader;
import Game.State;
import Graphics.Entities.BiConnectGfx;
import Graphics.Entities.EnemyGfx;
import Graphics.Entities.TowerGfx;
import Graphics.Entities.TriConnectGfx;
import Graphics.GfxStateControllers.GfxGameState;
import Utils.Enum.Cursors;
import Utils.Enum.InputStates;
import Utils.Enum.Inputs;


import java.util.ArrayList;

import static Graphics.TextureController.*;

/**
 * Created by George on 18/06/2017.
 */
public class GameState extends State {

    //entities
    private ArrayList<Tower> towers;
    private ArrayList<Enemy> enemies;
    private ArrayList<TriConnect> triConnects;
    private ArrayList<BiConnect> biConnects;
    private Map map;

    private int towerCount = 0;
    private int enemyCount = 0;

    public int tick = 0;


    //gfx coinnection
    private GfxGameState gfxGameState;

    //inputs
    private InputEventHandler inputEventHandler;

    //state stuff
    private ArrayList<InputStates> inputState;
    private Tower selectedTower = null;
    private int stateCounter = 0;
    private  Tower lastClicked;

    public GameState(){
        inputEventHandler = new InputEventHandler();

        //initialise input state
        inputState = new ArrayList<InputStates>();
        inputState.add(InputStates.NOINPUT);

        map = new Map(mapDims[0]/100, mapDims[1]/100);

        //create initial towers (test)
        towers = new ArrayList<Tower>();
        towers.add(new Tower(30, 30, towerCount++));
        towers.add(new Tower(775, 800, towerCount++));
        towers.add(new Tower(10, 100, towerCount++));

        towers.add(new Tower(500, 30, towerCount++));
        towers.add(new Tower(500, 150, towerCount++));

        //create enemies (test)
        enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(enemyCount++, 0));

        //tri connects
        triConnects = new ArrayList<TriConnect>();
        triConnects.add(new TriConnect(towers.get(0), towers.get(1), towers.get(2)));

        //biConnect
        biConnects = new ArrayList<BiConnect>();
        biConnects.add(new BiConnect(towers.get(3), towers.get(4)));
        //biConnects.add(new BiConnect(towers.get(1), towers.get(4)));
    }

    public void update(){
        tick++;
        inputEventHandler.poll();
        handleInput();
        updateConnects();
        moveEnemies();
    }

    //connect this game state to it's gfx counterpart
    public void connectToGfx(GfxGameState g){
        gfxGameState = g;

        for(int i = 0; i < towers.size(); i++){
            TowerGfx tgfx = new TowerGfx(towers.get(i).getX(), towers.get(i).getY());
            towers.get(i).setGfx(tgfx);
            gfxGameState.getTowersGfx().add(tgfx);
        }

        for(int i = 0; i < enemies.size(); i++){
            EnemyGfx egfx = new EnemyGfx(enemies.get(i).getX(), enemies.get(i).getY());
            enemies.get(i).setGfx(egfx);
            gfxGameState.getEnemyGfx().add(egfx);
        }

        for(int i = 0; i < triConnects.size(); i++){
            triConnects.get(i).connectToGfx(gfxGameState);
        }

        for(int i = 0; i < biConnects.size(); i++){
            biConnects.get(i).connectToGfx(gfxGameState);
        }
    }

    public InputEventHandler getKeyPressHandler(){
        return inputEventHandler;
    }

    //handle the inputs from the event handler
    public void handleInput(){

        //iterate through the events and process state fsm accordingly
        for(int j = 0; j < inputState.size(); j++) {
            switch (inputState.get(j)) {
                case NOINPUT:
                    //if a tower is clicked select it and allow it to be dragged. If shift is held down then also add ReassignConnect
                    if (InputEventHandler.inputs.contains(Inputs.LEFTMOUSEDOWN)) {
                        for (int i = 0; i < towers.size(); i++) {
                            if (Math.abs(InputEventHandler.mousePosX - towers.get(i).getX()) < 10 && Math.abs(InputEventHandler.mousePosY - towers.get(i).getY()) < 10) {
                                //dragging tower
                                inputState.add(InputStates.DRAGGINGTOWER);

                                //towerselected
                                inputState.add(InputStates.TOWERSELECTED);
                                if(selectedTower != null){
                                    selectedTower.setSelected(false);
                                }
                                selectedTower = towers.get(i);
                                lastClicked = selectedTower;
                                selectedTower.setSelected(true);

                                //remove NOINPUT state
                                inputState.remove(InputStates.NOINPUT);
                                break;
                            }
                        }
                    }
                    break;
                case DRAGGINGTOWER:

                    //remove DRAGGINGTOWER if leftmouse up
                    if (InputEventHandler.inputs.contains(Inputs.LEFTMOUSEUP)) {
                        inputState.remove(InputStates.DRAGGINGTOWER);
                        break;
                    }

                    //GAME ACTION
                    selectedTower.move(InputEventHandler.mousePosX, InputEventHandler.mousePosY);

                    break;
                case TOWERSELECTED:

                    //if shift is pressed add REASSINGCONECT
                    if(InputEventHandler.inputs.contains(Inputs.SHIFT) && !inputState.contains(InputStates.REASSIGNINGCONNECT)){
                        inputState.add(InputStates.REASSIGNINGCONNECT);
                        gfxGameState.serCursor(Cursors.REASSIGN1);
                    }


                    if (InputEventHandler.inputs.contains(Inputs.LEFTMOUSEDOWN)) {
                        boolean towerClicked = false;

                        //check to see in new tower was clicked
                        for (int i = 0; i < towers.size(); i++) {
                            if (Math.abs(InputEventHandler.mousePosX - towers.get(i).getX()) < 10 && Math.abs(InputEventHandler.mousePosY - towers.get(i).getY()) < 10) {
                                if(!inputState.contains(InputStates.REASSIGNINGCONNECT) && !inputState.contains(InputStates.DRAGGINGTOWER)){
                                    //new tower was clicked
                                    if(selectedTower != null){
                                        selectedTower.setSelected(false);
                                    }
                                    selectedTower = towers.get(i);
                                    lastClicked = selectedTower;
                                    selectedTower.setSelected(true);
                                    towerClicked = true;

                                    //add DRAGGINGTOWER
                                    if(!inputState.contains(InputStates.DRAGGINGTOWER)){
                                        inputState.add(InputStates.DRAGGINGTOWER);
                                    }
                                }
                            }
                        }

                        //No tower was clicked an not dragging anything go back to NOINPUT
                        if(!towerClicked && !inputState.contains(InputStates.DRAGGINGTOWER) && !inputState.contains(InputStates.REASSIGNINGCONNECT)) {
                            inputState.remove(InputStates.TOWERSELECTED);
                            inputState.remove(InputStates.REASSIGNINGCONNECT);
                            if (selectedTower != null) {
                                selectedTower.setSelected(false);
                            }
                            selectedTower = null;
                        }
                    }
                    break;
                case REASSIGNINGCONNECT:
                    int crosshairMouseX = InputEventHandler.mousePosX + 10;
                    int crosshairMouseY = InputEventHandler.mousePosY + 10;

                    if (InputEventHandler.inputs.contains(Inputs.LEFTMOUSEDOWN)) {
                        boolean towerClicked = false;
                        //check to see in new tower was clicked
                        for (int i = 0; i < towers.size(); i++) {
                            if(towers.get(i) != lastClicked) {
                                if (Math.abs(crosshairMouseX - towers.get(i).getX()) < 10 && Math.abs(crosshairMouseY - towers.get(i).getY()) < 10) {
                                    towerClicked = true;
                                    lastClicked = towers.get(i);
                                    stateCounter++;
                                    if(stateCounter == 1){
                                        selectedTower.reassignConnect1(towers.get(i));
                                        gfxGameState.serCursor(Cursors.REASSIGN2);
                                    }
                                    if(stateCounter == 2){
                                        selectedTower.reassignConnect2(towers.get(i));
                                    }
                                }
                            }
                        }

                    }

                    if(stateCounter == 2 && InputEventHandler.inputs.contains(Inputs.LEFTMOUSEUP)){
                        stateCounter = 0;
                        lastClicked = selectedTower;
                        inputState.remove(InputStates.REASSIGNINGCONNECT);
                        gfxGameState.serCursor(Cursors.DEFAULT);
                    }else if(InputEventHandler.inputs.contains(Inputs.LEFTMOUSEUP)){
                        boolean towerClicked = false;
                        for (int i = 0; i < towers.size(); i++) {
                            if(stateCounter != 0) {
                                if (Math.abs(crosshairMouseX - towers.get(i).getX()) < 10 && Math.abs(crosshairMouseY - towers.get(i).getY()) < 10) {
                                    towerClicked = true;
                                }
                            }else{
                                towerClicked = true;
                            }
                        }
                        if(!towerClicked){
                            inputState.remove(InputStates.TOWERSELECTED);
                            inputState.remove(InputStates.REASSIGNINGCONNECT);
                            gfxGameState.serCursor(Cursors.DEFAULT);
                            if (selectedTower != null) {
                                selectedTower.setSelected(false);
                            }
                            selectedTower = null;
                            stateCounter = 0;
                        }
                    }
                    break;
                default:
            }
        }

        //if there's no states left then NOINPUT
        if(inputState.size() == 0){
            inputState.add(InputStates.NOINPUT);
        }
    }

    public void updateConnects(){
        biConnects = new ArrayList<BiConnect>();
        triConnects = new ArrayList<TriConnect>();

        ArrayList<BiConnectGfx> bg = new ArrayList<BiConnectGfx>();
        ArrayList<TriConnectGfx> tg = new ArrayList<TriConnectGfx>();

        for(int i = 0; i < towers.size(); i++){
            if(towers.get(i).getBiConnect() != null){
                if(!biConnects.contains(towers.get(i).getBiConnect())) {
                    biConnects.add(towers.get(i).getBiConnect());
                    bg.add(towers.get(i).getBiConnect().getGfx());
                }
            }else if(towers.get(i).getTriConnect() != null){
                if(!triConnects.contains(towers.get(i).getTriConnect())) {
                    triConnects.add(towers.get(i).getTriConnect());
                    tg.add(towers.get(i).getTriConnect().getGfx());
                }
            }
        }

        gfxGameState.setBiConnectGfx(bg);
        gfxGameState.setTriConnectGfx(tg);
    }

    public void moveEnemies(){
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).move()){
                gfxGameState.getEnemyGfx().remove(enemies.get(i).getGfx());
                enemies.remove(i);
            }
        }
    }
}
