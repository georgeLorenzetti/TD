package Game.InputHandlers;

import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 * Created by George on 20/06/2017.
 */
public class CursorPosInputHandler extends GLFWCursorPosCallback {

    public static int x = 0;
    public static int y = 0;
    @Override
    public void invoke(long window, double x_, double y_){
        x = (int)x_;
        y = (int)y_;
    }
}
