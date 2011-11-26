package graphics;

import game.Util;
import java.nio.FloatBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;

public class Graphics {

    public static final int DISPLAY_WIDTH = 800;
    public static final int DISPLAY_HEIGHT = 600;
    public static final int VERT_ATTRIB = 0;
    private static final float[] squareVerts = {
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f,
        -1.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 0.0f
    };

    public static void init() {
        initDisplay();
        initGL();
        ShaderManager.initShaders();

        // TODO: Must move after using several shaders
        ShaderManager.useShader(Shader.SIMPLE);

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
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        GL20.glEnableVertexAttribArray(VERT_ATTRIB);
        GL20.glVertexAttribPointer(VERT_ATTRIB, 3, GL11.GL_FLOAT, false, 0, 0);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // TODO: These must be moved to a manager that handles all certain type of objects
        FloatBuffer vertsBuffer = Util.floatArrayToBuffer(squareVerts);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertsBuffer, GL15.GL_STATIC_DRAW);
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
