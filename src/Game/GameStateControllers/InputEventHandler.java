package Game.GameStateControllers;


import Game.InputHandlers.*;
import Utils.Enum.InputStates;
import Utils.Enum.Inputs;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by George on 17/03/2016.
 */
public class InputEventHandler {

    public static int mousePosX;
    public static int mousePosY;

    public static ArrayList<Inputs> inputs = new ArrayList<Inputs>();

    public InputEventHandler(){
        mousePosX = CursorPosInputHandler.x;
        mousePosY = CursorPosInputHandler.y;
    }

    public void poll(){
        glfwPollEvents();
        handleInputs();
    }


    public void handleInputs(){

        //--handle keyboard inputs--//

        boolean shift = false;

        for(int i = 0; i < KeyInputHandler.stack.size(); i++){
            switch(KeyInputHandler.stack.get(i)){
                case GLFW_KEY_LEFT_SHIFT:
                    shift = true;
                    break;
                default:
            }
        }

        if(shift){
            if(!inputs.contains(Inputs.SHIFT)){
                inputs.add(Inputs.SHIFT);
            }
        }else{
            if(inputs.contains(Inputs.SHIFT)){
                inputs.remove(Inputs.SHIFT);
            }
        }


        //--handle mouse input--//

        if(inputs.contains(Inputs.LEFTMOUSEUP) || inputs.contains(Inputs.RIGHTMOUSEUP)){
            inputs.remove(Inputs.LEFTMOUSEUP);
            inputs.remove(Inputs.RIGHTMOUSEUP);
        }

        //left and right buttons
        boolean left = false;
        boolean right = false;
        if(MouseButtonInputHandler.stack.size() > 0){
            for(int i = 0; i < MouseButtonInputHandler.stack.size(); i++){
                if(MouseButtonInputHandler.stack.get(i) == GLFW_MOUSE_BUTTON_LEFT){
                    left = true;
                }
                if(MouseButtonInputHandler.stack.get(i) == GLFW_MOUSE_BUTTON_RIGHT){
                    right = true;
                }
            }
        }


        if(left){
            if(!inputs.contains(Inputs.LEFTMOUSEDOWN)){
                inputs.add(Inputs.LEFTMOUSEDOWN);
            }
        }else{
            for(int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) == Inputs.LEFTMOUSEDOWN) {
                    inputs.remove(Inputs.LEFTMOUSEDOWN);
                    inputs.add(Inputs.LEFTMOUSEUP);
                }
            }
        }

        if(right){
            if(!inputs.contains(Inputs.RIGHTMOUSEDOWN)){
                inputs.add(Inputs.RIGHTMOUSEDOWN);
            }
        }else{
            for(int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i) == Inputs.RIGHTMOUSEDOWN) {
                    inputs.remove(Inputs.RIGHTMOUSEDOWN);
                    inputs.add(Inputs.RIGHTMOUSEUP);
                }
                ;
            }
        }

        //update mouse position
        mousePosX = CursorPosInputHandler.x;
        mousePosY = CursorPosInputHandler.y;
    }

}
