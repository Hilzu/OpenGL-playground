package graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.Util;

public class Graphics {

    public static final int DISPLAY_WIDTH = 800;
    public static final int DISPLAY_HEIGHT = 600;
    public static final int VERT_ATTRIB = 0;

    public static void init() {
        initDisplay();
        checkGLErrors("Display init");
        initGL();
        checkGLErrors("GL init");
        ShaderManager.initShaders();
        checkGLErrors("Shader init");
    }

    private static void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle("Best Game");
            Display.create();
        } catch (LWJGLException ex) {
            System.out.println("Could not init display!");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static void initGL() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public static void checkGLErrors(String msg) {
        while (true) {
            try {
                Util.checkGLError();
                return;
            } catch (OpenGLException ex) {
                System.out.print(msg + ": ");
                System.out.println(ex.getMessage());
            }
        }
    }

    public static FloatBuffer floatArrayToFloatBuffer(float[] floatArray) {
        FloatBuffer floatBuffer = ByteBuffer.allocateDirect(floatArray.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatBuffer.put(floatArray).position(0);
        return floatBuffer;
    }
}
