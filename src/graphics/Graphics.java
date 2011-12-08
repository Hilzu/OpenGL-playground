package graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;

public class Graphics {

    public static final int DISPLAY_WIDTH = 800;
    public static final int DISPLAY_HEIGHT = 600;

    public static void init() {
        initDisplay();
        initGL();
        ShaderManager.initShaders();
        checkGLErrors("init");
    }

    private static void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle("Best Game");
            Display.create(new PixelFormat(), new ContextAttribs(3, 2).withProfileCore(true));
        } catch (LWJGLException ex) {
            System.out.println("Could not init display!");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_CULL_FACE);
    }

    public static void checkGLErrors(String msg) {
        try {
            org.lwjgl.opengl.Util.checkGLError();
        } catch (OpenGLException ex) {
            System.out.print(msg + ": ");
            System.out.println(ex.getMessage());
        }
    }
}
