package Game;

import com.sun.prism.Texture;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Created by George on 24/06/2017.
 */
public class PathLoader {

    public PathLoader(){

    }

    public ArrayList<Point> loadTexture(String name){
        InputStream stream;
        BufferedImage image;
        int width;
        int height;

        try {
            stream = new FileInputStream("src/Assets/PathMaps/" + name);
            image = ImageIO.read(stream);

            width = image.getWidth();
            height = image.getHeight();

            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);

            int x = 0;
            int y = 0;

            ArrayList<Point> path = new ArrayList<Point>();
            for(int i = 0; i < pixels.length; i++){
                Color c = new Color(pixels[i]);
                if(c.getBlue() == 0 && c.getGreen() == 0 && c.getRed() == 0){
                    path.add(new Point(x, y));
                }
                x++;
                if(x == 800){
                    x = 0;
                    y++;
                }
            }

            return path;
        } catch (IOException e) {
            System.out.println("WRONG PATHIMAGE PATH");
            System.exit(1);
        }

        return null;
    }
}
