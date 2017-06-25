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
public class TriConnectGfx {

    private TransformationMatrix modelViewMatrix;
    private VertexArrayObject vao;
    private int count;
    private double rotationAngle = 0;

    public TriConnectGfx(int x1, int y1, int x2, int y2, int x3, int y3) {
        modelViewMatrix = new TransformationMatrix();

        //move to 0,0
        modelViewMatrix.translate(-1.025f, 1.225f);
        modelViewMatrix.rotate(rotationAngle);

        float[] vertices = {
                2*0.0025f*(x1+10), -2*0.0025f*(y1-10),
                2*0.0025f*(x2+10), -2*0.0025f*(y2-10),
                2*0.0025f*(x3+10), -2*0.0025f*(y3-10)
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

    public void draw() {
        //transformation matrix
        glUniformMatrix4fv(modelViewMatrix.getHandle(), false, modelViewMatrix.getFloatBuffer());
        //textre
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, TextureController.textureIDs.get(Textures.TRICONNECT.index()));
        //vertices
        glBindVertexArray(vao.vao);
        //draw
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
    }

    public void positionChanged(int x1, int y1, int x2, int y2, int x3, int y3) {
        float[] vertices = {
                2*0.0025f*(x1+10), -2*0.0025f*(y1-10),
                2*0.0025f*(x2+10), -2*0.0025f*(y2-10),
                2*0.0025f*(x3+10), -2*0.0025f*(y3-10)
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
}