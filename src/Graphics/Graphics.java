package Graphics;

import Engine.Main;
import Game.GameMain;
import Graphics.GfxStateControllers.GfxGameState;
import Graphics.GfxStateControllers.GfxState;
import Utils.TransformationMatrix;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

/**
 * Created by George on 15/06/2017.
 */
public class Graphics {

    public static TransformationMatrix projectionMatrix;
    public static TransformationMatrix zoomMatrix;
    public static float zoomFactor = 0.5f;
    public static float aspectRatio;

    private GameMain game;
    private TextureController textureController;
    private GfxGameState gfxGameState;
    private GfxState currentState;

    public Graphics(){
        //textures
        textureController = new TextureController();

        //matrixes
        projectionMatrix = new TransformationMatrix();
        zoomMatrix = new TransformationMatrix();

        //aspect ratio
        aspectRatio = (float)Main.width/(float)Main.height;

        //game state
        gfxGameState = new GfxGameState();
        currentState = gfxGameState;

        //setup
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void update(){
        glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        zoomMatrix.scale(zoomFactor);
        projectionMatrix.scaleY(aspectRatio);

        projectionMatrix.setHandle(Main.shader.shaderProgram, "projection_matrix");
        zoomMatrix.setHandle(Main.shader.shaderProgram, "zoom_matrix");

        glUniformMatrix4fv(projectionMatrix.getHandle(), false, projectionMatrix.getFloatBuffer());
        glUniformMatrix4fv(zoomMatrix.getHandle(), false, zoomMatrix.getFloatBuffer());
        glUseProgram(Main.shader.shaderProgram);

        currentState.render();

        glfwSwapBuffers(Main.window);
        glFlush();
    }

    public void init(GameMain game_){
        game = game_;
    }

    public GfxGameState getGfxGameState() {
        return gfxGameState;
    }
}
