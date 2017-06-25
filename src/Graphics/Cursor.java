package Graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by George on 23/06/2017.
 */
public class Cursor {

    int width = -1;
    int height = -1;
    long ID;

    public Cursor(String imageName){
        InputStream stream;
        BufferedImage image;
        try {
            stream = new FileInputStream("src/Assets/Textures/" + imageName);
            image = ImageIO.read(stream);

            width = image.getWidth();
            height = image.getHeight();

            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);

            ByteBuffer buf = BufferUtils.createByteBuffer(width * height * 4);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = pixels[y * width + x];
                    buf.put((byte) ((pixel >> 16) & 0xFF));
                    buf.put((byte) ((pixel >> 8) & 0xFF));
                    buf.put((byte) (pixel & 0xFF));
                    buf.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            buf.flip();

            GLFWImage cursorImg= GLFWImage.create();
            cursorImg.width(width);
            cursorImg.height(height);
            cursorImg.pixels(buf);

            int x = 0;
            int y = 0;

            ID = GLFW.glfwCreateCursor(cursorImg, x , y);

        }catch(IOException e){
            System.out.println("WRONG CURSOR IMAGE PATH");
            System.exit(1);
        }
    }

    public long getID(){
        return ID;
    }

}
