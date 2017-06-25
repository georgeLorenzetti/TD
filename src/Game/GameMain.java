package Game;

import Game.GameStateControllers.GameState;
import Game.GameStateControllers.InputEventHandler;
import Game.InputHandlers.CursorPosInputHandler;
import Game.InputHandlers.KeyInputHandler;
import Game.InputHandlers.MouseButtonInputHandler;
import Game.MenuStateControllers.MenuState;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import Graphics.*;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

/**
 * Created by George on 15/06/2017.
 */
public class GameMain {

    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private InputEventHandler keyPressHandler;
    private Graphics gfx;

    private GameState gameState;
    private MenuState menuState;
    private State currentState;

    public GameMain(Long window){

        glfwSetKeyCallback(window, keyCallback = new KeyInputHandler());
        glfwSetCursorPosCallback(window, cursorPosCallback = new CursorPosInputHandler());
        glfwSetMouseButtonCallback(window, mouseButtonCallback = new MouseButtonInputHandler());

        //setup states
        gameState = new GameState();
        menuState = new MenuState();
        currentState = gameState;
    }

    public void init(Graphics gfx_){
        gfx = gfx_;
        gameState.connectToGfx(gfx.getGfxGameState());
    }

    public void update(){
        currentState.update();
    }

    public void releaseKeyCallback(){
        keyCallback.release();
    }

    public GameState getGameState(){
        return gameState;
    }
}
