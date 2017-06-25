package Graphics;

import com.sun.org.apache.xpath.internal.SourceTree;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import Utils.TransformationMatrix;
import de.matthiasmann.twl.utils.PNGDecoder;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Created by George on 17/06/2017.
 */


public class TextureController {

    public ArrayList<ArrayList<ByteBuffer>> textures = new ArrayList<ArrayList<ByteBuffer>>();
    public static ArrayList<Integer> textureIDs = new ArrayList<Integer>();

    public static int[] mapDims;
    public static int[] characterDims;
    public static int[] enemyDims;


    public TextureController(){
        mapDims = loadTexture("Map1.png");
        characterDims = loadTexture("main.png");
        loadTexture("mainSelected.png");
        enemyDims = loadTexture("enemy.png");
        loadTexture("triconnect.png");
        loadTexture("biconnect.png");
    }

    public int[] loadTexture(String name){
        int tWidth;
        int tHeight;

        try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream("src/Assets/Textures/" + name);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();

            System.out.println(name + ": " + tHeight + " - " + tWidth);

            textures.add(new ArrayList<ByteBuffer>());
            // Decode the PNG file in a ByteBuffer
            textures.get(textures.size()-1).add( ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight()));
            int size = textures.get(textures.size()-1).size();
            decoder.decode(textures.get(textures.size()-1).get(size-1), decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            textures.get(textures.size()-1).get(size-1).flip();
            in.close();


            //loading it into memory as an opengl texture
            textureIDs.add(glGenTextures());
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, textureIDs.get(textureIDs.size()-1));
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tWidth, tHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, textures.get(textures.size()-1).get(size-1));
            glGenerateMipmap(GL_TEXTURE_2D);

            int[] res = new int[2];
            res[0] = tWidth;
            res[1] = tHeight;

            return res;

        } catch (IOException e) {
            System.out.println("WRONG TEXTURE PATH");
            System.exit(1);
        }

        return null;
    }

    public ArrayList<Integer> getTextures(){
        return textureIDs;
    }
}

