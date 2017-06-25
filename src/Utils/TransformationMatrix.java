package Utils;

/**
 * Created by George on 16/03/2016.
 */

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public class TransformationMatrix {

    private int handle;
    private float matrix[][] = new float[4][4];

    public TransformationMatrix(){
        matrix[0][0] = 1.0f;
        matrix[1][1] = 1.0f;
        matrix[2][2] = 1.0f;
        matrix[3][3] = 1.0f;
    }

    public void setHandle(int programID, String ID){
        handle = glGetUniformLocation(programID, ID);
    }

    public int getHandle(){
        return handle;
    }

    public float[][] getMatrix(){
        return matrix;
    }

    public FloatBuffer getFloatBuffer(){
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                fb.put(matrix[i][j]);
            }
        }
        fb.flip();
        return fb;
    }

    public void reset(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                matrix[i][j] = 0;
            }
        }
        matrix[0][0] = 1.0f;
        matrix[1][1] = 1.0f;
        matrix[2][2] = 1.0f;
        matrix[3][3] = 1.0f;
    }

    public void translate(float x, float y, float z){
        matrix[3][0] += x;
        matrix[3][1] += y;
        matrix[3][2] += z;
    }

    public void translate(float x, float y){
        matrix[3][0] += x;
        matrix[3][1] += y;
    }

    public void scale(float factor){
        matrix[0][0] = factor;
        matrix[1][1] = factor;
        matrix[2][2] = factor;
    }

    public void rotate(double angle){
        matrix[0][0] = (float)Math.cos(angle);
        matrix[1][0] = -1.0f * (float)Math.sin(angle);
        matrix[0][1] = (float)Math.sin(angle);
        matrix[1][1] = (float)Math.cos(angle);
    }

    public void scaleX(float factor){
        matrix[0][0] = factor;
    }

    public void scaleY(float factor){
        matrix[1][1] = factor;
    }
}
