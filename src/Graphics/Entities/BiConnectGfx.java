package Graphics.Entities;

import Graphics.TextureController;
import Utils.Enum.Textures;
import Utils.TransformationMatrix;
import Utils.VertexArrayObject;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by George on 23/06/2017.
 */
public class BiConnectGfx {

    private TransformationMatrix modelViewMatrix;
    private VertexArrayObject vao;
    private int count;
    private double rotationAngle = 0;

    public BiConnectGfx(int x1, int y1, int x2, int y2) {
        modelViewMatrix = new TransformationMatrix();

        //move to 0,0
        modelViewMatrix.translate(-1.025f, 1.225f);
        modelViewMatrix.rotate(rotationAngle);

        float[] vertices = {
                2*0.0025f*(x1+10), -2*0.0025f*(y1-10),
                2*0.0025f*(x2+10), -2*0.0025f*(y2-10),
        };

        byte[] indices = {
                0,1
        };

        float[] texCoords = {
                0.0f, 0.0f,
                1.0f, 0.0f,
        };

        vao = new VertexArrayObject(vertices, indices, texCoords, true);
        count = indices.length;
    }

    public void draw() {
        //transformation matrix
        glUniformMatrix4fv(modelViewMatrix.getHandle(), false, modelViewMatrix.getFloatBuffer());
        //textre
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, TextureController.textureIDs.get(Textures.BICONNECT.index()));
        //vertices
        glBindVertexArray(vao.vao);
        //draw
        glLineWidth(2.0f);
        glDrawElements(GL_LINES, count, GL_UNSIGNED_BYTE, 0);
    }

    public void positionChanged(int x1, int y1, int x2, int y2) {
        float[] vertices = {
                2*0.0025f*(x1+10), -2*0.0025f*(y1-10),
                2*0.0025f*(x2+10), -2*0.0025f*(y2-10),
        };

        byte[] indices = {
                0,1
        };

        float[] texCoords = {
                0.0f, 0.0f,
                1.0f, 0.0f,
        };

        vao = new VertexArrayObject(vertices, indices, texCoords, true);
        count = indices.length;
    }

}
