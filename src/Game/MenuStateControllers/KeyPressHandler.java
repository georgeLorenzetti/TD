package Game.MenuStateControllers;


import static org.lwjgl.glfw.GLFW.glfwPollEvents;

/**
 * Created by George on 17/03/2016.
 */
public class KeyPressHandler {

    public int framesHeld = 0;
    public int movementThreshold = 3;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static int state = -1;
    private int previousKey = -1;

    public KeyPressHandler(){}

    public void poll(){
        glfwPollEvents();
        pollInputs();
    }

    public void pollInputs(){
    }

}
