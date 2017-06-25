package Engine;

/**
 * Created by George on 15/06/2017.
 */
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import Graphics.Graphics;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.*;

import Graphics.*;
import Game.*;

public class Main implements Runnable{

    //window/running vars
    private Thread thread;
    private GLFWKeyCallback keyCallback;
    public boolean running = true;
    public static long window;

    //game vars
    public Graphics gfx;
    public GameMain game;

    //global variables
    public static Shader shader;
    public static int width = 800;
    public static int height = 1000;

    //fps calc
    private FPScontroller fpsController;


    public static void main(String[] args){
        Main game = new Main();
        game.start();
    }

    public void start(){
        running = true;
        thread = new Thread(this, "");
        thread.start();
    }

    public void init(){
        if(glfwInit() != GL_TRUE){
            System.err.println("Could Not Initialise");
        }

        //create window
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(width, height, "TD", NULL, NULL);
        if(window == NULL){
            System.err.println("Window is fucked m8");
        }

        //set videomode
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, 500, 35);

        //set up context
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        //initialise opengl and shaders
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        shader = new Shader("Main.vert", "Main.frag");
        gfx = new Graphics();

        //initialise game var.
        game = new GameMain(window);

        //initialise FPS
        fpsController = new FPScontroller();
        game.init(gfx);
        gfx.init(game);
    }

    @Override
    public void run() {
        init();

        fpsController = new FPScontroller();
        while(running){
            game.update();
            gfx.update();
            if(glfwWindowShouldClose(window) == GL_TRUE){
                running = false;
            }

            fpsController.sync();
        }

        game.releaseKeyCallback();
    }
}
