package Graphics;

/**
 * Created by George on 15/03/2016.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;


public class Shader {

    public StringBuilder vertexShader = new StringBuilder();
    public StringBuilder fragmentShader = new StringBuilder();
    public int shaderProgram;

    public Shader(String vertShaderName, String fragShaderName){
        shaderProgram = glCreateProgram();
        int vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);

        //loading in vertex shader
        try{
            BufferedReader input = new BufferedReader(new FileReader("src/Graphics/" + vertShaderName));
            String line;
            while((line = input.readLine()) != null){
                vertexShader.append(line).append("\n");
            }
            input.close();
        }catch(IOException e){
            System.err.println("vertex shader couldnt load");
            System.exit(1);
        }

        //loading in fragment shader
        try{
            BufferedReader input = new BufferedReader(new FileReader("src/Graphics/" + fragShaderName));
            String line;
            while((line = input.readLine()) != null){
                fragmentShader.append(line).append("\n");
            }
            input.close();
        }catch(IOException e){
            System.err.println("fragment shader couldnt load");
            System.exit(1);
        }

        //compile shaders
        glShaderSource(vertexShaderID, vertexShader);
        glShaderSource(fragmentShaderID, fragmentShader);

        glCompileShader(vertexShaderID);
        if(glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE){
            System.err.println("vertex shader could not be compiled");
            System.exit(1);
        }

        glCompileShader(fragmentShaderID);
        if(glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE){
            System.err.println("fragment shader could not be compiled");
            System.exit(1);
        }

        //attach shaders
        glAttachShader(shaderProgram, vertexShaderID);
        glAttachShader(shaderProgram, fragmentShaderID);
        glLinkProgram(shaderProgram);
        glValidateProgram(shaderProgram);
        System.out.println("Vertex Shader Compiled");
        System.out.println("Fragment Shader Compiled");
    }
}
