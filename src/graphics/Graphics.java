package graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
import org.lwjgl.opengl.Util;

public class Graphics {

    public static final int DISPLAY_WIDTH = 800;
    public static final int DISPLAY_HEIGHT = 600;
    public static final int VERT_ATTRIB = 0;
    private static int vertexArrayObjectID;
    private static int vertexBufferObjectID;

    public static void init() {
        initDisplay();
        checkGLErrors("Display init");
        initGL();
        checkGLErrors("GL init");
        ShaderManager.initShaders();
    }

    private static void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle("Best Game");
            // Use OpenGL 3.2 Core profile so that deprecated functionality can't be used
            Display.create(new PixelFormat(), new ContextAttribs(3, 2).withProfileCore(true));
        } catch (LWJGLException ex) {
            System.out.println("Could not init display!");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static void initGL() {
        vertexArrayObjectID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayObjectID);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        vertexBufferObjectID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObjectID);
        
        GL20.glVertexAttribPointer(VERT_ATTRIB, 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(VERT_ATTRIB);
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
        FloatBuffer floatBuffer = ByteBuffer.allocateDirect(floatArray.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatBuffer.put(floatArray).position(0);
        return floatBuffer;
    }
    
    public static int getVAO() {
        return vertexArrayObjectID;
    }
    
    public static int getVBO() {
        return vertexBufferObjectID;
    }
}
