package Utils;

/**
 * Created by George on 14/03/2016.
 */

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static Utils.Utils.createByteBuffer;
import static Utils.Utils.createFloatBuffer;
import static Engine.Main.*;
import static Engine.Main.shader;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArrayObject {

    public int vertLoc = 0;
    public int texLoc = 1;
    public int vao;
    public boolean isMainCharacter;


    public VertexArrayObject(float[] vertices, byte[] indices, float[] texCoords, boolean mainCharacter){
        createArrayObject(vertices, indices, texCoords);
        isMainCharacter = mainCharacter;
    }

    public void createArrayObject(float[] vertices, byte[] indices, float[] texCoords){
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        createVertexBuffer(vertices);
        createIndicesBuffer(indices);
        createTexCoordsBuffer(texCoords);
    }

    public void createVertexBuffer(float[] vertices){
        FloatBuffer v = createFloatBuffer(vertices);
        int buffer1 = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, buffer1);
        glBufferData(GL_ARRAY_BUFFER, v, GL_STATIC_DRAW);

        glBindAttribLocation(shader.shaderProgram, 0, "position");
        glEnableVertexAttribArray(vertLoc);
        glVertexAttribPointer(vertLoc, 2, GL_FLOAT, false, 0, 0);

    }

    private void createIndicesBuffer(byte[] indices){
        ByteBuffer i = createByteBuffer(indices);
        int buffer2 = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer2);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, i, GL_STATIC_DRAW);
    }

    private void createTexCoordsBuffer(float[] texCoords){
        FloatBuffer t = createFloatBuffer(texCoords);
        int buffer3 = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, buffer3);
        glBufferData(GL_ARRAY_BUFFER, t, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, buffer3);
        glBindAttribLocation(shader.shaderProgram, 0, "texcoord");

        glEnableVertexAttribArray(texLoc);
        glVertexAttribPointer(texLoc, 2, GL_FLOAT, false, 0, 0);
    }
}
