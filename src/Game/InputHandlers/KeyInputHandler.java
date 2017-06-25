package Game.InputHandlers;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by George on 12/03/2016.
 */
public class KeyInputHandler extends GLFWKeyCallback{

    public static boolean[] keys = new boolean[1000];
    public static ArrayList<Integer> stack = new ArrayList<Integer>();

    @Override
    public void invoke(long window, int key, int scanCode, int action, int mods) {
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
