package Graphics.Entities;

import Utils.Enum.Textures;
import Graphics.TextureController;
import Utils.TransformationMatrix;

import Utils.VertexArrayObject;
import Engine.Main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by George on 17/06/2017.
 */
public class MapGfx {

    public VertexArrayObject vao;
    private int tHeight = 2000;
    private int tWidth = 3800;
    public int count;
    public TransformationMatrix modelViewMatrix;

    public MapGfx(){
        modelViewMatrix = new TransformationMatrix();
        modelViewMatrix.setHandle(Main.shader.shaderProgram, "model_view_matrix");
        modelViewMatrix.reset();

        float[] vertices = {
                -2.0f, -2.5f,
                2.0f, -2.5f,
                2.0f, 2.5f,
                -2.0f, 2.5f
        };

        byte[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        float[] texCoords = {
                0.0f,1.0f,
                1.0f,1.0f,
                1.0f,0.0f,
                0.0f,0.0f
        };

        this.count = indices.length;
        vao = new VertexArrayObject(vertices, indices, texCoords, false);
    }

    public void draw(){
        glUniformMatrix4fv(modelViewMatrix.getHandle(), false, modelViewMatrix.getFloatBuffer());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, TextureController.textureIDs.get(Textures.MAP.index()));
        glBindVertexArray(vao.vao);
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);

    }

    public void centreprojection(){
        modelViewMatrix.translate(-0.05f*(tWidth/100)/2, -0.05f*(tHeight/100)/2);
    }

    public int tWidth(){
        return tWidth;
    }

    public int tHeight(){
        return tHeight;
    }

}
