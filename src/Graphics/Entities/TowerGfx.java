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
public class TowerGfx {

    private TransformationMatrix modelViewMatrix;
    private VertexArrayObject vao;
    private int count;
    private boolean selected = false;

    public TowerGfx(int x, int y){
        modelViewMatrix = new TransformationMatrix();

        //move to 0,0
        modelViewMatrix.translate(-1.025f, 1.225f);

        modelViewMatrix.translate(0.0025f*x, -0.0025f*y);

        float[] vertices = {
                0.0f, 0.0f,
                0.1f, 0.0f,
                0.0f, 0.1f,
                0.1f, 0.1f,
        };

        byte[] indices = {
                0, 1, 2,
                1, 2, 3
        };

        float[] texCoords = {
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f
        };

        vao = new VertexArrayObject(vertices, indices, texCoords, true);
        count = indices.length;
    }

    public void draw(){
        //transformation matrix
        glUniformMatrix4fv(modelViewMatrix.getHandle(), false, modelViewMatrix.getFloatBuffer());
       //textre
        glActiveTexture(GL_TEXTURE0);
        if(!selected) {
            glBindTexture(GL_TEXTURE_2D, TextureController.textureIDs.get(Textures.TOWER.index()));
        }else{
            glBindTexture(GL_TEXTURE_2D, TextureController.textureIDs.get(Textures.TOWERSELECTED.index()));
        }
        //vertices
        glBindVertexArray(vao.vao);
        //draw
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
    }

    public void positionChanged(float x, float y){
        modelViewMatrix.translate(0.0025f*x, -0.0025f*y);
    }

    public void setSelected(boolean b){
        selected = b;
    }
}
