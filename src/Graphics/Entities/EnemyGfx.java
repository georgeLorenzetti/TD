package Graphics.Entities;

import Engine.Main;
import Game.Objects.Map;
import Utils.Enum.Textures;
import Graphics.TextureController;
import Utils.TransformationMatrix;
import Utils.VertexArrayObject;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by George on 17/06/2017.
 */
public class EnemyGfx {

    private TransformationMatrix modelViewMatrix;
    private VertexArrayObject vao;
    private int count;
    private double rotationAngle = 0;

    public EnemyGfx(double x, double y){
        modelViewMatrix = new TransformationMatrix();

        //move to 0,0
        modelViewMatrix.translate(-1.0f, 1.25f);
        modelViewMatrix.translate(0.0025f*(float)x, -0.0025f*(float)y);
        modelViewMatrix.rotate(rotationAngle);

        float[] vertices = {
                0.0f, 0.0f,
                0.1f, 0.0f,
                0.05f, -0.1f
        };

        byte[] indices = {
                1, 2, 0
        };

        float[] texCoords = {
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f
        };

        vao = new VertexArrayObject(vertices, indices, texCoords, true);
        count = indices.length;
    }

    public void draw(){
        //transformation matrix
        glUniformMatrix4fv(modelViewMatrix.getHandle(), false, modelViewMatrix.getFloatBuffer());
        //textre
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,  TextureController.textureIDs.get(Textures.ENEMY.index()));
        //vertices
        glBindVertexArray(vao.vao);
        //draw
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
    }

    public void positionChanged(float x, float y){
        modelViewMatrix.translate(0.0025f*x, -0.0025f*y);
    }
}
