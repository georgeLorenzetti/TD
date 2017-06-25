package Game.InputHandlers;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by George on 20/06/2017.
 */
public class MouseButtonInputHandler extends GLFWMouseButtonCallback {

    public static boolean[] keys = new boolean[1000];
    public static ArrayList<Integer> stack = new ArrayList<Integer>();

    @Override
    public void invoke(long window, int key, int action, int mods) {
        if(action != GLFW_RELEASE){
            for(int i = 0; i < stack.size(); i++){
                if(stack.get(i).intValue() == key){
                    return;
                }
            }
            stack.add(0, key);
        }else{
            for(int i = 0; i < stack.size(); i++){
                if(stack.get(i).intValue() == key){
                    stack.remove(i);
                }
            }
        }
    }
}

